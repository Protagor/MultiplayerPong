package de.protagor.pong.client.controller;

import de.protagor.pong.client.logic.Bar;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    private Bar bar;

    public KeyHandler(Bar bar) {
        this.bar = bar;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_UP) {
            bar.setMovingUp(true);
        }
        if (keyEvent.getKeyCode() == KeyEvent.VK_DOWN) {
            bar.setMovingDown(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_UP) {
            bar.setMovingUp(false);
        }
        if (keyEvent.getKeyCode() == KeyEvent.VK_DOWN) {
            bar.setMovingDown(false);
        }
    }
}
