package com.dapasta.notpong.server.game;

public class Player {
    private String name;
    private String gameSession;
    private Paddle paddle;

    public Player(String name) {
        this.name = name;
        gameSession = "";
    }

    public Player() {
        this(null);
    }

    public String getName() {
        return name;
    }

    public void setGameSession(String gameSession) {
        this.gameSession = gameSession;
    }

    public String getGameSession() {
        return gameSession;
    }

    public void createPaddle(float x, float width, float height) {
        this.paddle = new Paddle(x, width, height);
    }

    public Paddle getPaddle() {
        return paddle;
    }
}
