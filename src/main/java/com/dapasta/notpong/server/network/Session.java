package com.dapasta.notpong.server.network;

import com.dapasta.notpong.packets.Packet;
import com.dapasta.notpong.packets.server.Game;
import com.dapasta.notpong.server.game.GameManager;
import com.dapasta.notpong.server.game.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Session {

    protected Map<Integer, Player> players;

    public Session() {
        players = new HashMap<>();
    }

    public void addPlayer(int id, Player player) {
        players.put(id, player);
    }

    public void removePlayer(int id) {
        players.remove(id);
    }

    public Player getPlayer(int id) {
        return players.get(id);
    }

    public Map<Integer, Player> getPlayers() {
        return players;
    }

    public boolean isEmpty() {
        return players.isEmpty();
    }

    public void broadcastTcp(Packet packet) {
        for (int id : players.keySet()) {
            GameManager.network.sendTcpPacket(id, packet);
        }
    }

    public void broadcastUdp(Packet packet) {
        for (int id : players.keySet()) {
            GameManager.network.sendUdpPacket(id, packet);
        }
    }
}
