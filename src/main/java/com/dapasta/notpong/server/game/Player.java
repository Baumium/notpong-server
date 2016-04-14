package com.dapasta.notpong.server.game;

public class Player {
    private String name;
    private Paddle paddle;

    public Player(String name) {
        this.name = name;
    }

    public Player() {
        this(null);
    }

    public void createPaddle(float x, float width, float height) {
        this.paddle = new Paddle(x, width, height);
}

    public String getName() {
        return name;
    }

    public Paddle getPaddle() {
        return paddle;
    }
}
