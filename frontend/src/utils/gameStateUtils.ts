import {GameState} from "../enums/GameState";

export const getBadgeProperties = (state: GameState): {label: string, color: string} => {
    switch (state) {
        case GameState.PLAYING:
            return { label: "Playing", color: "bg-blue-500" };
        case GameState.COMPLETED:
            return { label: "Completed", color: "bg-green-500" };
        case GameState.ON_HOLD:
            return { label: "On Hold", color: "bg-yellow-500" };
        case GameState.DROPPED:
            return { label: "Dropped", color: "bg-red-500" };
        case GameState.PLANNED_TO_PLAY:
            return { label: "Planned to play", color: "bg-gray-500" };
    }
};