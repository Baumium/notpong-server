package com.dapasta.notpong.server.game;

import static com.dapasta.notpong.server.game.Side.*;

public class Paddle {
    private float x;
    private float width;
    private float height;
    private Side side;

    public Paddle(float x, float width, float height, int num) {
        this.x = x;
        this.width = width;
        this.height = height;

        switch (num) {
            case 0:
                side = LEFT;
                break;
            case 1:
                side = RIGHT;
                break;
            case 2:
                side = TOP;
                break;
            case 3:
                side = BOTTOM;
                break;
            default:
                new Exception("There are " + num + " players in a session when creating a paddle").printStackTrace();
        }
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

    public void setPosition(float x) {
        this.x = x;
    }

    public Side getSide() {
        return side;
    }
}
