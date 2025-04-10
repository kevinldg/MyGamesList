import {GameState} from "../enums/GameState.ts";

export type Game = {
    gameId: number;
    gameName: string;
    gameSummary: string;
    artworkId: number;
    artworkUrl: string;
    gameState: GameState;
};