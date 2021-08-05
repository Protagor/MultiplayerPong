package de.protagor.pong.client.gui;

import de.protagor.pong.client.manager.GameManager;
import de.protagor.pong.client.manager.GameState;
import de.protagor.pong.client.manager.GameStateable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PongGraphics extends JLabel implements GameStateable {

    final private static PongGraphics pongGraphics = new PongGraphics(PongWindow.getInstance());

    private Consumer<Graphics> drawFunction = (graphics -> {});

    private List<Drawable> drawables = new ArrayList<>();

    private PongGraphics(JFrame window) {
        window.add(this);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D graphics2D = (Graphics2D) graphics;

        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        graphics2D.setColor(Color.BLACK);
        graphics2D.fillRect(0,0,PongWindow.WIDTH, PongWindow.HEIGHT);

        drawFunction.accept(graphics2D);

        repaint();
    }


    @Override
    public void onGameStateChange(GameState newGameState, GameState old) {
        switch (newGameState) {
            case MENU:
                drawFunction = (graphics) -> {
                    graphics.setColor(Color.WHITE);
                    graphics.setFont(new Font("Arial", Font.BOLD, 60));
                    graphics.drawString("PONG", 500, 100);
                };

                break;
            case IN_GAME:
                drawFunction = (graphics) -> drawables.forEach(drawable -> drawable.draw(graphics));

                break;
            case GAME_OVER:

                String outcome = GameManager.getInstance().isWinner() ? "YOU WON!" : "YOU LOST!";
                drawFunction = (graphics) -> {
                    drawables.forEach(drawable -> drawable.draw(graphics));
                    graphics.setColor(Color.WHITE);
                    graphics.setFont(new Font("Arial", Font.BOLD, 80));
                    graphics.drawString(outcome, PongWindow.WIDTH/2 - 200, PongWindow.HEIGHT/2 - 50);
                };
        }
    }

    public void registerDrawable(Drawable drawable) {
        drawables.add(drawable);
    }

    public static PongGraphics getInstance() {
        return pongGraphics;
    }
}
