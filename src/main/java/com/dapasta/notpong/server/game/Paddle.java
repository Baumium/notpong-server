package com.dapasta.notpong.server.game;

public class Paddle {
    private float x;
    private float width;
    private float height;

    public Paddle(float x, float width, float height) {
        this.x = x;
        this.width = width;
        this.height = height;
    }

    public float getX() {
        return x;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void move(float dx) {
        x += dx;
    }
}
