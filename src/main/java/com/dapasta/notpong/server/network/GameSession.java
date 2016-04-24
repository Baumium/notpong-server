package com.dapasta.notpong.server.network;

import com.dapasta.notpong.packets.server.PlayerJoinBroadcast;
import com.dapasta.notpong.server.game.GameField;
import com.dapasta.notpong.server.game.Player;

public class GameSession extends Session {

    // Session info
    private String name;
    private String creator;
    private String id;

    // Game info
    GameField field;

    public GameSession(String id, String name, String creator, float gameSize) {
        super();
        this.id = id;
        this.name = name;
        this.creator = creator;
        this.field = new GameField(gameSize);
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

}
