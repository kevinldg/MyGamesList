import {GameState} from "../enums/GameState.ts";
import {Game} from "../types/Game.ts";

type GameStateBadgeProps = {
    updateGame: (gameName: string, gameState: GameState) => void;
    game: Game;
    gameState: GameState;
}

export default function GameStateBadge({updateGame, game, gameState}: GameStateBadgeProps) {
    return (
        <button onClick={() => {updateGame(game.gameName, gameState)}} className="size-fit px-1 py-0.5 rounded-xs text-xs font-bold bg-blue-500">{gameState}</button>
    );
}