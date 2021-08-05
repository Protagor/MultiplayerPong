package de.protagor.pong.client.controller;

import de.protagor.pong.client.gui.PongWindow;
import de.protagor.pong.client.logic.Ball;
import de.protagor.pong.client.logic.Bar;

public class StandardController implements AutoController {

    private double barPosition;
    private Ball ball;

    public StandardController(Ball ball) {
        barPosition = ((float) PongWindow.HEIGHT / 2) - (float) Bar.HEIGHT/2;

        this.ball = ball;
    }

    @Override
    public double getPosition() {
        if (ball.getPosition().getY() > barPosition + Bar.HEIGHT - 35) {
            barPosition += Bar.SPEED;
        } else if (ball.getPosition().getY() < barPosition + 35) {
            barPosition -= Bar.SPEED;
        }
        return barPosition;
    }

}
