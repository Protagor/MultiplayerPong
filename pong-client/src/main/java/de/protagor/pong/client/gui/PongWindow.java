package de.protagor.pong.client.gui;

import de.protagor.pong.client.Pong;
import de.protagor.pong.client.manager.GameManager;
import de.protagor.pong.client.manager.GameState;
import de.protagor.pong.client.manager.GameStateable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PongWindow extends JFrame implements GameStateable {

    final public static int WIDTH = 1200, HEIGHT = 800;

    final private static PongWindow pongWindow = new PongWindow();

    private JButton singlePlayerButton = new JButton("Start Singeplayer Game");
    private JButton multiPlayerButton = new JButton("Start Multiplayer Game");

    private PongWindow() {

    }

    public void open() {
        setTitle(Pong.TITLE);
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setBackground(Color.BLACK);
        setVisible(true);

        singlePlayerButton.setOpaque(true);
        singlePlayerButton.setFocusable(false);
        singlePlayerButton.setBounds(300, 200, 600, 75);
        singlePlayerButton.setVisible(false);
        singlePlayerButton.setFont(new Font("Arial", Font.BOLD, 30));
        singlePlayerButton.setForeground(Color.BLACK);
        singlePlayerButton.addActionListener(actionEvent -> GameManager.getInstance().startGame());
        add(singlePlayerButton);

        multiPlayerButton.setOpaque(true);
        multiPlayerButton.setFocusable(false);
        multiPlayerButton.setBounds(300, 400, 600, 75);
        multiPlayerButton.setVisible(false);
        multiPlayerButton.setFont(new Font("Arial", Font.BOLD, 30));
        multiPlayerButton.setForeground(Color.BLACK);
        add(multiPlayerButton);
    }

    @Override
    public void onGameStateChange(GameState newGameState, GameState old) {

        switch (newGameState) {
            case MENU:
                singlePlayerButton.setVisible(true);
                multiPlayerButton.setVisible(true);

                break;
            case IN_GAME:
                singlePlayerButton.setVisible(false);
                multiPlayerButton.setVisible(false);
        }

    }

    public static PongWindow getInstance() {
        return pongWindow;
    }
}
