import axios from "axios";
import {Game} from "../model/Game.ts";

export const updateGamesFromUser = async (games: Game[], userId: string) => {
    try {
        await axios.put(`/api/user/${userId}`, {games: games});
    } catch (error) {
        console.error("Error adding game: ", error);
        throw error;
    }
}