package de.protagor.pong.client.logic;

import de.protagor.pong.client.gui.PongWindow;
import de.protagor.pong.client.manager.GameManager;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class Ball extends GameObject {

    private Point position = new Point((float) PongWindow.WIDTH/2, (float) PongWindow.HEIGHT/2);

    private int directionX, directionY;
    private double speed;
    private int size;

    private Bar bar1, bar2;

    private GameManager gameManager;

    public Ball(GameManager gameManager, Bar bar1, Bar bar2) {
        super(gameManager);

        directionX = ((int) (Math.random() * 2)) == 0 ? 1 : -1;
        directionY = ((int) (Math.random() * 2)) == 0 ? 1 : -1;
        speed = 0.5;
        size = 40;

        this.bar1 = bar1;
        this.bar2 = bar2;

        this.gameManager = gameManager;
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.setColor(Color.WHITE);
        graphics.fillOval((int) position.getX(), (int) position.getY(), size, size);
    }

    @Override
    public void run() {
        double x = position.getX();
        double y = position.getY();

        position.setLocation(x + (directionX * speed),
                y - (directionY * speed));

        if (position.getY() < 0) {
            directionY = -1;
        }

        if (position.getY() > PongWindow.HEIGHT - size*1.75) {
            directionY = 1;
        }

        if (position.getX() <= 0) {
            gameManager.scorePointOther();
        } else if (position.getX() >= PongWindow.WIDTH-size*2) {
            gameManager.scorePoint();
        }

        if (toRectangle().intersects(bar1.toRectangle())) {
            directionX = 1;
        }

        if (toRectangle().intersects(bar2.toRectangle())) {
            directionX = -1;
        }
    }

    public Rectangle2D toRectangle() {
        return new Ellipse2D.Double(position.getX(), position.getY(), size, size).getBounds2D();
    }

    public Point getPosition() {
        return position;
    }

    public static class Point {
        private double x;
        private double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public void setLocation(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

}
