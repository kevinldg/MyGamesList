import {useAuth} from "../../contexts/AuthContext.tsx";
import {formatDate} from "../../utils/dateUtils.ts";
import {Game} from "../../types/Game.ts";
import {useEffect, useState} from "react";
import {Link} from "react-router-dom";
import axios from "axios";
import {GameState} from "../../enums/GameState.ts";
import GameEntry from "../../components/GameEntry.tsx";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faStar} from "@fortawesome/free-solid-svg-icons";

export default function ProfilePage() {
    const {user, token} = useAuth();
    const [games, setGames] = useState<Game[] | undefined>(user?.games);
    const [favoriteGame, setFavoriteGame] = useState<Game | undefined>(user?.favoriteGame);

    useEffect(() => {
        setFavoriteGame(user?.favoriteGame);
        setGames(user?.games);
    }, [user]);

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

    const favorGame = (game: Game) => {
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
                {
                    favoriteGame && (
                        <div>
                            <div className="border-b-mgl-dark-400 border-b flex items-center gap-2">
                                <h2 className="text-lg font-semibold">Favorite Game</h2>
                                <FontAwesomeIcon icon={faStar}/>
                            </div>
                            <GameEntry game={favoriteGame} dontShowGameState={true} />
                        </div>
                    )
                }
                <div>
                    <div className="border-b-mgl-dark-400 border-b flex justify-between items-center">
                        <h2 className="text-lg font-semibold">Games</h2>
                        <Link to="/profile/add-game" className="px-1 py-0.5 rounded-xs text-xs bg-blue-500">Add Game</Link>
                    </div>
                    <div className="mb-4">
                        {games && games.map((game: Game) => (
                            favoriteGame?.gameName === game.gameName ? (
                                <GameEntry
                                    key={game.gameName}
                                    game={game}
                                    updateGame={updateGame}
                                    deleteGame={deleteGame}
                                />
                            ) : (
                                <GameEntry
                                    key={game.gameName}
                                    game={game}
                                    updateGame={updateGame}
                                    deleteGame={deleteGame}
                                    favorGame={favorGame}
                                />
                            )
                        ))}
                    </div>
                </div>
            </div>
        </div>
    );
}