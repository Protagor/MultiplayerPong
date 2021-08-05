package de.protagor.pong.client.logic;

import de.protagor.pong.client.gui.Drawable;
import de.protagor.pong.client.manager.GameManager;

public abstract class GameObject implements Drawable {

    public GameObject(GameManager gameManager) {
        gameManager.registerGameObject(this);
    }

    public abstract void run();
}
