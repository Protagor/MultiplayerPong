package de.protagor.pong.client.logic;

import de.protagor.pong.client.controller.AutoController;
import de.protagor.pong.client.gui.PongWindow;
import de.protagor.pong.client.manager.GameManager;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Bar extends GameObject {

    public final static int WIDTH = 30, HEIGHT = 175, X_POSITION_OWN = 100, X_POSITION_OTHER = PongWindow.WIDTH-100;
    public final static double SPEED = 0.4;

    private boolean own;
    private double position;

    private boolean movingUp;
    private boolean movingDown;

    private AutoController controller = null;

    public Bar(GameManager gameManager, boolean own) {
        super(gameManager);

        this.own = own;
        this.position = ((float) PongWindow.HEIGHT / 2) - (float) HEIGHT/2;
    }

    public void moveUp() {
        if (position - SPEED >= 0)
            position -= SPEED;
    }

    public void moveDown() {
        if (position + SPEED < PongWindow.HEIGHT - HEIGHT - 10)
            position += SPEED;
    }

    @Override
    public void draw(Graphics graphics) {
        if (own) {
            graphics.setColor(Color.WHITE);
            graphics.fillRect(X_POSITION_OWN, (int) position, WIDTH, HEIGHT);
        } else {
            graphics.setColor(Color.WHITE);
            graphics.fillRect(X_POSITION_OTHER, (int) position, WIDTH, HEIGHT);
        }
    }

    public Rectangle2D toRectangle() {
        return new Rectangle2D.Double(own ? X_POSITION_OWN : X_POSITION_OTHER, position, WIDTH, HEIGHT).getFrame();
    }

    @Override
    public void run() {
        if (controller == null) {
            if (movingUp) {
                moveUp();
            }
            if (movingDown) {
                moveDown();
            }
        } else {
            position = controller.getPosition();
        }
    }

    public void setMovingUp(boolean movingUp) {
        this.movingUp = movingUp;
    }

    public void setMovingDown(boolean movingDown) {
        this.movingDown = movingDown;
    }

    public void controlAutomatically(AutoController controller) {
        this.controller = controller;
    }
}
