package com.dapasta.notpong.server.network;

public class GameSession extends Session {

    private String name;


    public GameSession(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

}
