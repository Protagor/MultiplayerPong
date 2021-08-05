package de.protagor.pong.client.manager;

public interface GameStateable {

    public void onGameStateChange(GameState newGameState, GameState old);
}
