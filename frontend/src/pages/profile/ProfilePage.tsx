import {useAuth} from "../../contexts/AuthContext.tsx";
import {formatDate} from "../../utils/dateUtils.ts";
import {Game} from "../../types/Game.ts";
import {useEffect, useState} from "react";
import {Link, useLocation} from "react-router-dom";
import axios from "axios";
import {GameState} from "../../enums/GameState.ts";
import GameEntry from "../../components/GameEntry.tsx";

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
        axios.delete(`/api/user/games`, {
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
                                <GameEntry game={game} updateGame={updateGame} deleteGame={deleteGame} />
                            ))
                        }
                    </div>
                </div>
            </div>
        </div>
    );
}