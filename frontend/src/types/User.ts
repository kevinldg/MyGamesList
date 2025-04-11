import {Game} from "./Game.ts";

export type User = {
    id: string;
    username: string;
    password: string;
    createdAt: string;
    games: Game[];
    favoriteGame: Game;
}

export type UserProps = {
    id: string;
    username: string;
    createdAt: string;
    games: Game[];
    favoriteGame: Game;
};