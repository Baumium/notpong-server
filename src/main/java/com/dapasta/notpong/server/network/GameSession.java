package com.dapasta.notpong.server.network;

import com.dapasta.notpong.packets.server.PlayerJoinBroadcast;
import com.dapasta.notpong.server.game.GameField;
import com.dapasta.notpong.server.game.Player;

public class GameSession extends Session {

    // Session info
    private String id;
    private String name;
    private String creator;
    private int playerSize;

    // Game info
    private GameField field;


    public GameSession(String id, String name, String creator, int playerSize, float fieldSize, float ballRadius, float ballSpeed, float paddleWidth, float paddleHeight) {
        super();
        this.id = id;
        this.name = name;
        this.creator = creator;
        this.playerSize = playerSize;
        this.field = new GameField(fieldSize, ballRadius, ballSpeed, paddleWidth, paddleHeight);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCreator() {
        return creator;
    }

    public int getPlayerSize() {
        return playerSize;
    }

    public void addPlayer(int id, Player player) {
        player.setGameSession(this.id);
        super.addPlayer(id, player);
    }

    public void removePlayer(int id) {
        getPlayer(id).setGameSession("");
        super.removePlayer(id);
    }

    public GameField getGameField() {
        return field;
    }

    public void update(float delta) {
        getGameField().update(delta, players.values());
    }

}
