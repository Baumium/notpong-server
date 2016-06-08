package com.dapasta.notpong.server.game;

import java.util.Collection;

public class Ball {
    private float x;
    private float y;
    private float radius;
    private float speedX;
    private float speedY;

    public Ball(float x, float y, float radius, float speed) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.speedX = speed;
        this.speedY = speed * 2 / 3;
    }

    public void update(float delta, float fieldSize, Collection<Player> players) {
        move(speedX * delta, speedY * delta);

        // Board collisions
        if (getX() - radius <= 0 || getX() + radius >= fieldSize) {
            speedX *= -1;
        }
        if (getY() - radius <= 0 || getY() + radius >= fieldSize) {
            speedY  *= -1;
        }

        // Paddle collisions
        for (Player player : players) {
            Paddle paddle = player.getPaddle();
            switch (paddle.getSide()) {
                case LEFT:
                    if (getY() + radius >= paddle.getX() - paddle.getHeight() / 2
                            && getY() - radius <= paddle.getX() + paddle.getHeight() / 2
                            && getX() - radius <= 2 * paddle.getWidth()) {
                        speedX *= -1;
                    }
                    break;
                case RIGHT:
                    if (getY() + radius >= paddle.getX() - paddle.getHeight() / 2
                            && getY() - radius <= paddle.getX() + paddle.getHeight() / 2
                            && getX() + radius >= fieldSize - 2 * paddle.getWidth()) {
                        speedX *= -1;
                    }
            }
        }
    }

    public float getRadius() {
        return radius;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void move(float dx, float dy) {
        x += dx;
        y += dy;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
