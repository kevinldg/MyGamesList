import axios from "axios";
import {GameState} from "../enums/GameState.ts";
import {Game} from "../types/Game.ts";
import {Dispatch, SetStateAction} from "react";

export const deleteGame = (gameName: string, token: string, setGames: Dispatch<SetStateAction<Game[] | null>>) => {
    axios.delete(`/api/user/games`, {
        headers: {
            Authorization: `Bearer ${token}`
        },
        params: {
            gameName: gameName
        }
    })
        .then(() => {
            setGames(prevGames => prevGames ? prevGames.filter(game => game.gameName !== gameName) : null);
        })
        .catch(error => {
            console.error("Delete game error", error);
        });
};

export const updateGame = (gameName: string, gameState: GameState, token: string, setGames: Dispatch<SetStateAction<Game[] | null>>) => {
    axios.put(`/api/user/games`,
        {
            "gameName": gameName,
            "gameState": gameState
        },
        {
            headers: {
                Authorization: `Bearer ${token}`
            }
        })
        .then(() => {
            setGames(prevGames => prevGames ? prevGames.map(game =>
                game.gameName === gameName ? { ...game, gameState: gameState } : game
            ) : null);

            const stateSelection = document.getElementById(gameName + "-state-select");
            if (stateSelection) stateSelection.classList.toggle("hidden");
        })
        .catch(error => {
            console.error("Update game error", error);
        });
};

export const favorGame = (game: Game, token: string, setFavoriteGame: Dispatch<SetStateAction<Game | null>>) => {
    axios.post(`/api/user/games/favorite`,
        game,
        {
            headers: {
                Authorization: `Bearer ${token}`
            }
        })
        .then(() => {
            setFavoriteGame(game);
        })
        .catch(error => {
            console.error("Set favorite game error", error);
        });
};