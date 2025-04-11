import {getBadgeProperties} from "../utils/gameStateUtils.ts";
import {GameStateBadgeProps} from "../types/Game.ts";

export default function GameStateBadge({updateGame, game, gameState}: GameStateBadgeProps) {
    const { label, color } = getBadgeProperties(gameState);

    return (
        <button onClick={() => {
            if (updateGame && game) {
                updateGame(game.gameName, gameState)
            }}} className={`size-fit px-1 py-0.5 rounded-xs text-xs font-bold ${color}`}>{label}</button>
    );
}