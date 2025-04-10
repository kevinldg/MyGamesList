import {GameState} from "../enums/GameState.ts";
import {Game} from "../types/Game.ts";

type GameStateBadgeProps = {
    updateGame?: (gameName: string, gameState: GameState) => void;
    game?: Game;
    gameState: GameState;
}

const getBadgeProperties = (state: GameState): {label: string, color: string} => {
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

export default function GameStateBadge({updateGame, game, gameState}: GameStateBadgeProps) {
    const { label, color } = getBadgeProperties(gameState);

    return (
        <button onClick={() => {
            if (updateGame && game) {
                updateGame(game.gameName, gameState)
            }}} className={`size-fit px-1 py-0.5 rounded-xs text-xs font-bold ${color}`}>{label}</button>
    );
}