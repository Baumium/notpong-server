package com.dapasta.notpong.server.network;

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
}
