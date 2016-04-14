package com.dapasta.notpong.server.game;

import com.dapasta.notpong.packets.client.CreateGameRequest;
import com.dapasta.notpong.packets.client.GamesRequest;
import com.dapasta.notpong.packets.client.MovementRequest;
import com.dapasta.notpong.packets.server.CreateGameResponse;
import com.dapasta.notpong.packets.server.Game;
import com.dapasta.notpong.packets.server.GamesResponse;
import com.dapasta.notpong.server.network.GameSession;
import com.dapasta.notpong.server.network.NetworkManager;
import com.dapasta.notpong.server.network.Session;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GameManager {

    public NetworkManager network;
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
            public void received(Connection connection, Object object) {
                super.received(connection, object);
                if (object instanceof CreateGameRequest) {
                    CreateGameRequest request = (CreateGameRequest) object;

                    GameSession gameSession = new GameSession(request.gameName);
                    Player player = lobbySession.getPlayer(connection.getID());
                    player.createPaddle(0.5f, 0.03f, 0.1f);
                    gameSession.addPlayer(connection.getID(), player);
                    lobbySession.removePlayer(connection.getID());

                    String sessionId = UUID.randomUUID().toString();
                    gameSessions.put(sessionId, gameSession);

                    CreateGameResponse response = new CreateGameResponse();
                    response.gameName = request.gameName;
                    response.gameId = sessionId;
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
                        game.name = gameSession.name();
                        game.creator = "DaPasta";

                        response.games.add(game);
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
