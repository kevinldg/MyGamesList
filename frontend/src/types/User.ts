import {Game} from "./Game.ts";

export type User = {
    id: string;
    username: string;
    password: string;
    createdAt: string;
    games: Game[];
    favoriteGame: Game;
}