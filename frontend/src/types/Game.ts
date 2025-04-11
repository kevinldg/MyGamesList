import {GameState} from "../enums/GameState.ts";

export type Game = {
    gameId: number;
    gameName: string;
    gameSummary: string;
    artworkId: number;
    artworkUrl: string;
    gameState: GameState;
};

export type GameEntryProps = {
    game: Game;
    updateGame?: (gameName: string, gameState: GameState) => void;
    deleteGame?: (gameName: string) => void;
    favorGame?: (game: Game) => void;
    dontShowGameState?: boolean;
}

export type GameStateBadgeProps = {
    updateGame?: (gameName: string, gameState: GameState) => void;
    game?: Game;
    gameState: GameState;
}