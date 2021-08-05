package de.protagor.pong.client.manager;

import de.protagor.pong.client.controller.StandardController;
import de.protagor.pong.client.gui.PongGraphics;
import de.protagor.pong.client.gui.PongWindow;
import de.protagor.pong.client.controller.KeyHandler;
import de.protagor.pong.client.logic.Ball;
import de.protagor.pong.client.logic.Bar;
import de.protagor.pong.client.logic.GameObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GameManager implements GameStateable{

    private GameState gameState;
    private List<GameStateable> gameStateables = new ArrayList<>();

    private Timer timer = new Timer();
    private List<GameObject> gameObjects = new ArrayList<>();

    private KeyHandler currentGameKeyHandler;

    private boolean winner = false;

    final private static GameManager gameManager = new GameManager();

    private GameManager() {
        gameState = null;
        registerGameStateable(this);
    }

    public void startGame() {
        setGameState(GameState.IN_GAME);

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                gameObjects.forEach(GameObject::run);

            }
        }, 0L, 1L);
    }

    public void registerGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
        PongGraphics.getInstance().registerDrawable(gameObject);
    }

    public void registerGameStateable(GameStateable gameStateable) {
        gameStateables.add(gameStateable);
    }

    public void setGameState(GameState gameState) {
        gameStateables.forEach(gameStateable -> gameStateable.onGameStateChange(gameState, this.gameState));
        this.gameState = gameState;
    }

    public void finishGame(boolean winner) {
        this.winner = winner;
        setGameState(GameState.GAME_OVER);
    }

    public static GameManager getInstance() {
        return gameManager;
    }

    @Override
    public void onGameStateChange(GameState newGameState, GameState old) {
        if (newGameState == GameState.IN_GAME) {
            Bar barOwn = new Bar(this, true);
            Bar barOther = new Bar(this, false);
            Ball ball = new Ball(this, barOwn, barOther);

            barOther.controlAutomatically(new StandardController(ball));

            currentGameKeyHandler = new KeyHandler(barOwn);
            PongWindow.getInstance().addKeyListener(currentGameKeyHandler);
        }
        if (old == GameState.IN_GAME && newGameState != GameState.PAUSE) {
            gameObjects = new ArrayList<>();
            timer.cancel();

            PongWindow.getInstance().removeKeyListener(currentGameKeyHandler);
            currentGameKeyHandler = null;
        }
    }

    public boolean isWinner() {
        return winner;
    }
}
