import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faPen, faTrash} from "@fortawesome/free-solid-svg-icons";
import GameStateBadge from "./GameStateBadge.tsx";
import {GameState} from "../enums/GameState.ts";
import {Game} from "../types/Game.ts";

type GameEntryProps = {
    game: Game;
    updateGame: (gameName: string, gameState: GameState) => void;
    deleteGame: (gameName: string) => void;
}

export default function GameEntry({game, deleteGame, updateGame}: GameEntryProps) {
    return (
        <div key={game.gameId} className="flex gap-4 py-2 max-h-28 not-last:border-b-mgl-dark-400 not-last:border-b">
            <img src={game.artworkUrl} alt={`${game.gameName} Cover`} className="size-20 border-white border" />
            <div>
                <div className="flex items-center gap-2">
                    <p className="text-lg">{game.gameName}</p>
                    <span className="size-fit px-1 py-0.5 rounded-xs text-xs font-bold bg-blue-500">{game.gameState}</span>
                    <button onClick={() => deleteGame(game.gameName)} className="px-1 rounded-xs bg-red-500"><FontAwesomeIcon icon={faTrash} className="text-sm" /></button>
                    <button onClick={() => {
                        const stateSelection = document.getElementById(game.gameName + "-state-select");
                        if (stateSelection) stateSelection.classList.toggle("hidden");
                    }} className="px-1 rounded-xs bg-green-600"><FontAwesomeIcon icon={faPen} className="text-sm" /></button>
                </div>
                <small className="line-clamp-2">{game.gameSummary}</small>
                <div id={game.gameName + "-state-select"} className="hidden pt-1 flex items-center gap-2">
                    <p>Select new state:</p>
                    <GameStateBadge updateGame={updateGame} game={game} gameState={GameState.PLAYING} />
                    <GameStateBadge updateGame={updateGame} game={game} gameState={GameState.COMPLETED} />
                    <GameStateBadge updateGame={updateGame} game={game} gameState={GameState.ON_HOLD} />
                    <GameStateBadge updateGame={updateGame} game={game} gameState={GameState.DROPPED} />
                    <GameStateBadge updateGame={updateGame} game={game} gameState={GameState.PLANNED_TO_PLAY} />
                </div>
            </div>
        </div>
    );
}