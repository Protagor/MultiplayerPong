package de.protagor.pong.client;

import de.protagor.pong.client.gui.PongGraphics;
import de.protagor.pong.client.gui.PongWindow;
import de.protagor.pong.client.manager.GameManager;
import de.protagor.pong.client.manager.GameState;

public class Pong {

    public static final String TITLE = "Pong";

    public static void main(String[] args) {

        PongWindow.getInstance().open();

        GameManager.getInstance().registerGameStateable(PongGraphics.getInstance());
        GameManager.getInstance().registerGameStateable(PongWindow.getInstance());

        GameManager.getInstance().setGameState(GameState.MENU);
    }
}
