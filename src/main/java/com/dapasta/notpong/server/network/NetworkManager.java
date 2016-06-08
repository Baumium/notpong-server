package com.dapasta.notpong.server.network;

import com.dapasta.notpong.packets.ErrorResponse;
import com.dapasta.notpong.packets.Packet;
import com.dapasta.notpong.packets.client.CreateGameRequest;
import com.dapasta.notpong.packets.client.GamesRequest;
import com.dapasta.notpong.packets.client.JoinGameRequest;
import com.dapasta.notpong.packets.client.MovementRequest;
import com.dapasta.notpong.packets.server.*;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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
        kryo.register(HashMap.class);

        kryo.register(ErrorResponse.class);

        kryo.register(GameInfoPacket.class);
        kryo.register(CreateGameRequest.class);
        kryo.register(CreateGameResponse.class);
        kryo.register(GamesRequest.class);
        kryo.register(GamesResponse.class);
        kryo.register(JoinGameRequest.class);
        kryo.register(JoinGameResponse.class);
        kryo.register(Game.class);
        kryo.register(PlayerJoinBroadcast.class);
        kryo.register(PlayerDisconnectBroadcast.class);

        kryo.register(MovementRequest.class);
        kryo.register(MovementResponse.class);
        kryo.register(PlayerUpdateBroadcast.class);
        kryo.register(BallBroadcast.class);
    }

    public void sendTcpPacket(int connectionId, Packet packet) {
        server.sendToTCP(connectionId, packet);
    }

    public void sendUdpPacket(int connectionId, Packet packet) {
        server.sendToUDP(connectionId, packet);
    }
}
