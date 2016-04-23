package com.dapasta.notpong.server.game;

import com.dapasta.notpong.packets.client.MovementRequest;
import com.dapasta.notpong.packets.server.MovementResponse;
import com.dapasta.notpong.server.network.GameSession;

import java.util.concurrent.ConcurrentLinkedQueue;

public class GameLoop extends Thread {

    private GameManager manager;

    private boolean running;
    private ConcurrentLinkedQueue<MovementRequest> queue;

    private double interval = 1000000000.0 / 60.0;
    private double delta = 0;

    private long lastTime = System.nanoTime();

    private static final float SPEED = 0.4f;


    public GameLoop(GameManager manager) {
        this.manager = manager;

        running = true;
        queue = new ConcurrentLinkedQueue<>();
    }

    public void run() {
        while (running) {
            long currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / interval;
            lastTime = currentTime;

            while (delta >= 1) {
                tick();
                delta--;
            }
        }
    }

    private void tick() {
        MovementRequest packet;
        while ((packet = queue.poll()) != null) {
            GameSession session = manager.gameSessions.get(packet.sessionId);
            Player player = session.getPlayer(packet.playerId);

            player.getPaddle().setPosition(packet.x);

            MovementResponse response = new MovementResponse();
            response.x = player.getPaddle().getX();
            response.id = packet.id;
            manager.network.sendTcpPacket(packet.playerId, response);
        }
    }

    public void addPacket(MovementRequest packet) {
        queue.add(packet);
    }

}
