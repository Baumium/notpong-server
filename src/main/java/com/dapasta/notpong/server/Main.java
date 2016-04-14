package com.dapasta.notpong.server;

import com.dapasta.notpong.server.game.GameManager;

public class Main {
    public static void main(String[] args) {
        GameManager gameManager = new GameManager();
        gameManager.execute();
    }
}
