import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faPen, faStar, faTrash} from "@fortawesome/free-solid-svg-icons";
import GameStateBadge from "./GameStateBadge.tsx";
import {GameState} from "../enums/GameState.ts";
import {Game} from "../types/Game.ts";

type GameEntryProps = {
    game: Game;
    updateGame?: (gameName: string, gameState: GameState) => void;
    deleteGame?: (gameName: string) => void;
    favorGame?: (game: Game) => void;
    dontShowGameState?: boolean;
}

export default function GameEntry({game, deleteGame, updateGame, favorGame, dontShowGameState}: GameEntryProps) {
    return (
        <div key={game.gameId} className="flex gap-4 py-2 max-h-28 not-last:border-b-mgl-dark-400 not-last:border-b">
            <img src={game.artworkUrl} alt={`${game.gameName} Cover`} className="size-20 border-white border" />
            <div className="w-full">
                <div className="flex justify-between items-center">
                    <div className="flex items-center gap-2">
                        <p className="text-lg">{game.gameName}</p>
                        {!dontShowGameState && <GameStateBadge gameState={game.gameState} />}
                    </div>
                    {
                        (updateGame || deleteGame || favorGame) && (
                            <div className="flex items-center gap-2">
                                {favorGame && <button onClick={() => favorGame(game)} className="px-1 rounded-xs bg-yellow-500"><FontAwesomeIcon icon={faStar} className="text-sm" /></button>}
                                {deleteGame && <button onClick={() => deleteGame(game.gameName)} className="px-1 rounded-xs bg-red-500"><FontAwesomeIcon icon={faTrash} className="text-sm" /></button>}
                                {updateGame && <button onClick={() => {
                                    const stateSelection = document.getElementById(game.gameName + "-state-select");
                                    if (stateSelection) stateSelection.classList.toggle("hidden");
                                }} className="px-1 rounded-xs bg-green-600"><FontAwesomeIcon icon={faPen} className="text-sm" /></button>}
                            </div>
                        )
                    }
                </div>
                <small className="line-clamp-2">{game.gameSummary}</small>
                {
                    updateGame && (
                        <div id={game.gameName + "-state-select"} className="hidden pt-1 flex items-center gap-2">
                            <p>Select new state:</p>
                            <GameStateBadge updateGame={updateGame} game={game} gameState={GameState.PLAYING} />
                            <GameStateBadge updateGame={updateGame} game={game} gameState={GameState.COMPLETED} />
                            <GameStateBadge updateGame={updateGame} game={game} gameState={GameState.ON_HOLD} />
                            <GameStateBadge updateGame={updateGame} game={game} gameState={GameState.DROPPED} />
                            <GameStateBadge updateGame={updateGame} game={game} gameState={GameState.PLANNED_TO_PLAY} />
                        </div>
                    )
                }
            </div>
        </div>
    );
}