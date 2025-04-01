import {useAuth} from "../../contexts/AuthContext.tsx";
import {formatDate} from "../../utils/dateUtils.ts";
import {Game} from "../../types/Game.ts";
import {useEffect, useState} from "react";
import {Link, useLocation} from "react-router-dom";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faPen, faTrash} from "@fortawesome/free-solid-svg-icons";
import axios from "axios";
import {GameState} from "../../enums/GameState.ts";

export default function ProfilePage() {
    const {user, token} = useAuth();
    const [games, setGames] = useState<Game[] | undefined>(user?.games);

    useEffect(() => {
        setGames(user?.games);
    }, [user]);

    const location = useLocation();
    useEffect(() => {
        if (location.state && location.state.addedGame) {
            setGames(prevGames => [...(prevGames || []), location.state.addedGame]);
        }
    }, [location.state]);

    const deleteGame = (gameName: string) => {
        axios.delete(`/api/user/${user?.id}/games`, {
            headers: {
                Authorization: `Bearer ${token}`
            },
            params: {
                gameName: gameName
            }
        })
            .then(() => {
                setGames(prevGames => prevGames?.filter(game => game.gameName !== gameName));
            })
            .catch(error => {
                console.error("Delete game error", error);
            });
    };

    const updateGame = (gameName: string, gameState: GameState) => {
        axios.put(`/api/user/${user?.id}/games`,
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
                setGames(prevGames => prevGames?.map(game =>
                    game.gameName === gameName ? { ...game, gameState: gameState } : game
                ));

                const stateSelection = document.getElementById(gameName + "-state-select");
                if (stateSelection) stateSelection.classList.toggle("hidden");
            })
            .catch(error => {
                console.error("Update game error", error);
            });
    };

    return (
        <div>
            <div className="bg-mgl-dark-700 border-b-mgl-dark-400 border-b px-2 py-1 flex justify-between items-center">
                <p className="text-lg font-semibold">{`${user?.username}'s Profile`}</p>
                <p>{user?.id}</p>
            </div>
            <div className="px-2 py-1 flex flex-col gap-2">
                <div>
                    <h2 className="text-lg font-semibold border-b-mgl-dark-400 border-b">Details</h2>
                    <p>Account created: {formatDate(user?.createdAt)}</p>
                </div>
                <div>
                    <div className="border-b-mgl-dark-400 border-b flex justify-between items-center">
                        <h2 className="text-lg font-semibold">Games</h2>
                        <Link to="/profile/add-game" className="px-1 py-0.5 rounded-xs text-xs bg-blue-500">Add Game</Link>
                    </div>
                    <div>
                        {
                            games && games.map((game: Game) => (
                                <div key={game.gameId} className="flex gap-4 py-2 max-h-24 not-last:border-b-mgl-dark-400 not-last:border-b">
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
                                        <div id={game.gameName + "-state-select"} className="hidden flex items-center gap-2">
                                            <p>Select new state:</p>
                                            <button onClick={() => {updateGame(game.gameName, GameState.PLAYING)}} className="size-fit px-1 py-0.5 rounded-xs text-xs font-bold bg-blue-500">PLAYING</button>
                                            <button onClick={() => {updateGame(game.gameName, GameState.COMPLETED)}} className="size-fit px-1 py-0.5 rounded-xs text-xs font-bold bg-blue-500">COMPLETED</button>
                                            <button onClick={() => {updateGame(game.gameName, GameState.ON_HOLD)}} className="size-fit px-1 py-0.5 rounded-xs text-xs font-bold bg-blue-500">ON HOLD</button>
                                            <button onClick={() => {updateGame(game.gameName, GameState.DROPPED)}} className="size-fit px-1 py-0.5 rounded-xs text-xs font-bold bg-blue-500">DROPPED</button>
                                            <button onClick={() => {updateGame(game.gameName, GameState.PLANNED_TO_PLAY)}} className="size-fit px-1 py-0.5 rounded-xs text-xs font-bold bg-blue-500">PLANNED TO PLAY</button>
                                        </div>
                                    </div>
                                </div>
                            ))
                        }
                    </div>
                </div>
            </div>
        </div>
    );
}