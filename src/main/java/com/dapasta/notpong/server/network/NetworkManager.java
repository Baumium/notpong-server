package com.dapasta.notpong.server.network;

import com.dapasta.notpong.packets.ErrorResponse;
import com.dapasta.notpong.packets.Packet;
import com.dapasta.notpong.packets.client.CreateGameRequest;
import com.dapasta.notpong.packets.client.GamesRequest;
import com.dapasta.notpong.packets.client.MovementRequest;
import com.dapasta.notpong.packets.server.CreateGameResponse;
import com.dapasta.notpong.packets.server.Game;
import com.dapasta.notpong.packets.server.GamesResponse;
import com.dapasta.notpong.packets.server.MovementResponse;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.util.ArrayList;

public class NetworkManager {
    Server server;

    public NetworkManager() {
        server = new Server();
        server.start();

    }

    public void startServer(int tcpPort, int udpPort) {
        try {
            server.bind(tcpPort, udpPort);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        registerPackets();
    }

    public void addListener(Listener listener) {
        server.addListener(listener);
    }

    private void registerPackets() {
        Kryo kryo = server.getKryo();

        kryo.register(ArrayList.class);

        kryo.register(ErrorResponse.class);

        kryo.register(CreateGameRequest.class);
        kryo.register(CreateGameResponse.class);
        kryo.register(GamesRequest.class);
        kryo.register(GamesResponse.class);
        kryo.register(Game.class);

        kryo.register(MovementRequest.class);
        kryo.register(MovementResponse.class);
    }

    public void sendTcpPacket(int connectionId, Packet packet) {
        server.sendToTCP(connectionId, packet);
    }

    public void sendUdpPacket(int connectionId, Packet packet) {
        server.sendToUDP(connectionId, packet);
    }
}
