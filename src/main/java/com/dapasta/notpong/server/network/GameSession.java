package com.dapasta.notpong.server.network;

import com.dapasta.notpong.server.game.Player;

public class GameSession extends Session {

    private String name;

    public GameSession(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

    public void addPlayer(int id, Player player) {
        player.setGameSession(name);
        super.addPlayer(id, player);
    }

}
