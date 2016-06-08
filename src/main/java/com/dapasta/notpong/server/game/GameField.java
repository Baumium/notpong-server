package com.dapasta.notpong.server.game;

import java.util.Collection;

public class GameField {
    private float fieldSize;
    private Ball ball;

    //TODO: Possibly fix this mess
    private float paddleWidth;
    private float paddleHeight;

    public GameField(float fieldSize, float ballRadius, float ballSpeed, float paddleWidth, float paddleHeight) {
        this.fieldSize = fieldSize;
        this.ball = new Ball(fieldSize / 2, fieldSize / 2, ballRadius, ballSpeed);

        this.paddleWidth = paddleWidth;
        this.paddleHeight = paddleHeight;
    }

    public float getFieldSize() {
        return fieldSize;
    }

    public void update(float delta, Collection<Player> players) {
        ball.update(delta, fieldSize, players);
    }

    public Ball getBall() {
        return ball;
    }

    public float getPaddleWidth() {
        return paddleWidth;
    }

    public float getPaddleHeight() {
        return paddleHeight;
    }
}
