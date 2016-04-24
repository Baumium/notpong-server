package com.dapasta.notpong.server.game;

import com.dapasta.notpong.packets.client.CreateGameRequest;
import com.dapasta.notpong.packets.client.GamesRequest;
import com.dapasta.notpong.packets.client.JoinGameRequest;
import com.dapasta.notpong.packets.client.MovementRequest;
import com.dapasta.notpong.packets.server.*;
import com.dapasta.notpong.server.network.GameSession;
import com.dapasta.notpong.server.network.NetworkManager;
import com.dapasta.notpong.server.network.Session;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.util.*;

public class GameManager {

    public static NetworkManager network;
    private GameLoop gameLoop;

    public Session lobbySession;
    public Map<String, GameSession> gameSessions;

    public GameManager() {
        network = new NetworkManager();
        gameLoop = new GameLoop(this);

        lobbySession = new Session();
        gameSessions = new HashMap<>();
    }

    public void execute() {
        network.addListener(new Listener() {
            @Override
            public void connected(Connection connection) {
                super.connected(connection);

                lobbySession.addPlayer(connection.getID(), new Player());
            }

            @Override
            public void disconnected(Connection connection) {
                super.disconnected(connection);

                Player player = lobbySession.getPlayer(connection.getID());
                String sessionId = player.getGameSession();
                if (!sessionId.equals("")) {
                    GameSession session = gameSessions.get(sessionId);

                    // Notify other players of disconnect
                    PlayerDisconnectBroadcast broadcast = new PlayerDisconnectBroadcast();
                    broadcast.id = connection.getID();
                    session.broadcastTcp(broadcast);


                    session.removePlayer(connection.getID());
                    if (session.isEmpty()) {
                        gameSessions.remove(sessionId);
                    }
                    lobbySession.removePlayer(connection.getID());
                }
            }

            @Override
            public void received(Connection connection, Object object) {
                super.received(connection, object);
                if (object instanceof CreateGameRequest) {
                    CreateGameRequest request = (CreateGameRequest) object;


                    Player player = lobbySession.getPlayer(connection.getID());
                    player.createPaddle(0.5f, 0.03f, 0.1f);
                    String sessionId = UUID.randomUUID().toString();
                    GameSession gameSession = new GameSession(sessionId, request.gameName, player.getName(), 100);
                    gameSession.addPlayer(connection.getID(), player);
                    gameSessions.put(sessionId, gameSession);

                    CreateGameResponse response = new CreateGameResponse();
                    response.gameName = request.gameName;
                    response.creatorName = request.creatorName;
                    response.sessionId = sessionId;

                    network.sendTcpPacket(connection.getID(), response);
                } else if (object instanceof GamesRequest) {
                    GamesResponse response = new GamesResponse();
                    response.games = new ArrayList<>();

                    for (String key : gameSessions.keySet()) {
                        GameSession gameSession = gameSessions.get(key);
                        Game game = new Game();
                        game.id = key;
                        game.size = 3;
                        game.name = gameSession.getName();
                        game.creator = "DaPasta";

                        response.games.add(game);
                    }

                    network.sendTcpPacket(connection.getID(), response);
                } else if (object instanceof JoinGameRequest) {
                    JoinGameRequest request = (JoinGameRequest) object;

                    GameSession session = gameSessions.get(request.sessionId);
                    Player player = lobbySession.getPlayer(connection.getID());
                    player.createPaddle(0.5f, 0.03f, 0.1f);
                    session.addPlayer(connection.getID(), player);

                    //Notify all existing players of new player
                    PlayerJoinBroadcast broadcast = new PlayerJoinBroadcast();
                    broadcast.id = connection.getID();
                    broadcast.name = player.getName();
                    session.broadcastTcp(broadcast);

                    JoinGameResponse response = new JoinGameResponse();
                    response.sessionId = session.getId();
                    response.gameName = session.getName();
                    response.creatorName = session.getCreator();
                    response.players = new HashMap<>();
                    Map<Integer, Player> players = session.getPlayers();
                    for (Integer id : players.keySet()) {
                        response.players.put(id, players.get(id).getName());
                    }
                    network.sendTcpPacket(connection.getID(), response);
                } else if (object instanceof MovementRequest) {
                    gameLoop.addPacket((MovementRequest) object);
                }
            }
        });
        network.startServer(54555, 54777);

        gameLoop.start();
    }
}
