package com.github.kevinldg.mygameslist.backend.game;

public record Game(
        int gameId,
        String gameName,
        String gameSummary,
        int artworkId,
        String artworkUrl,
        GameState gameState
) {
    public Game withGameState(GameState gameState) {
        return new Game(this.gameId(), this.gameName(), this.gameSummary(), this.artworkId(), this.artworkUrl, gameState);
    }
}
