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

    private Timer timer;
    private List<GameObject> gameObjects = new ArrayList<>();

    private KeyHandler currentGameKeyHandler;

    private boolean winner = false;

    private int ownPoints, otherPoints;

    private int countdown = 0;

    final private static GameManager gameManager = new GameManager();

    private GameManager() {
        gameState = null;
        registerGameStateable(this);
    }

    public void startGame() {
        gameObjects = new ArrayList<>();
        PongGraphics.getInstance().emptyDrawables();
        if (timer != null)
            timer.cancel();
        timer = new Timer();

        PongWindow.getInstance().removeKeyListener(currentGameKeyHandler);
        currentGameKeyHandler = null;

        setGameState(GameState.IN_GAME);

        Bar barOwn = new Bar(this, true);
        Bar barOther = new Bar(this, false);
        Ball ball = new Ball(this, barOwn, barOther);

        barOther.controlAutomatically(new StandardController(ball));

        currentGameKeyHandler = new KeyHandler(barOwn);
        PongWindow.getInstance().addKeyListener(currentGameKeyHandler);

        countdown = 4;
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                countdown--;
                if (countdown == 0) {
                    this.cancel();
                }
            }
        }, 0L, 1000L);

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                gameObjects.forEach(GameObject::run);

            }
        }, 3000L, 1L);
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
        if (newGameState == old) return;
        if (old == GameState.IN_GAME && newGameState != GameState.PAUSE) {
            gameObjects = new ArrayList<>();
            timer.cancel();

            PongWindow.getInstance().removeKeyListener(currentGameKeyHandler);
            currentGameKeyHandler = null;
        }
    }

    public void scorePoint() {
        ownPoints++;
        if (ownPoints == 5) finishGame(true);
        else startGame();
    }

    public void scorePointOther() {
        otherPoints++;
        if (otherPoints == 5) finishGame(false);
        else startGame();
    }

    public int getOwnPoints() {
        return ownPoints;
    }

    public int getOtherPoints() {
        return otherPoints;
    }

    public boolean isWinner() {
        return winner;
    }

    public int getCountdown() {
        return countdown;
    }
}
