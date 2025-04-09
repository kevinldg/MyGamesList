import {useAuth} from "../../contexts/AuthContext.tsx";
import {formatDate} from "../../utils/dateUtils.ts";
import {Game} from "../../types/Game.ts";
import {useEffect, useState} from "react";
import {Link, useParams} from "react-router-dom";
import axios from "axios";
import GameEntry from "../../components/GameEntry.tsx";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faStar} from "@fortawesome/free-solid-svg-icons";
import {deleteGame, updateGame, favorGame} from "../../services/gameService.ts";
import {GameState} from "../../enums/GameState.ts";

type UserProps = {
    id: string;
    username: string;
    createdAt: string;
    games: Game[];
    favoriteGame: Game;
};

export default function ProfilePage() {
    const {user, token} = useAuth();

    const params = useParams();
    const username = params.username;

    const [fetchedUser, setFetchedUser] = useState<UserProps | null>(null);
    const [games, setGames] = useState<Game[] | null>(null);
    const [favoriteGame, setFavoriteGame] = useState<Game | null>(null);

    const fetchUser = () => {
        axios.get(`/api/user/${username}`, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        })
            .then(response => setFetchedUser(response.data))
            .catch(error => {
                console.error("Error getting user", error);
                setFetchedUser(null);
            });
    };

    const handleDeleteGame = (gameName: string) => {
        if (!token) return;
        deleteGame(gameName, token, setGames);
    };

    const handleUpdateGame = (gameName: string, gameState: GameState) => {
        if (!token) return;
        updateGame(gameName, gameState, token, setGames);
    };

    const handleFavorGame = (game: Game) => {
        if (!token) return;
        favorGame(game, token, setFavoriteGame);
    };

    useEffect(() => {
        if (username) {
            fetchUser();
        } else if (user) {
            setFetchedUser(user);
        }
    }, [user, username]);

    useEffect(() => {
        if (fetchedUser) {
            setFavoriteGame(fetchedUser.favoriteGame);
            setGames(fetchedUser.games);
        }
    }, [fetchedUser]);

    return (
        <div>
            <div className="bg-mgl-dark-700 border-b-mgl-dark-400 border-b px-2 py-1 flex justify-between items-center">
                <p className="text-lg font-semibold">{`${fetchedUser?.username}'s Profile`}</p>
                {!username && <p>{fetchedUser?.id}</p>}
            </div>
            <div className="px-2 py-1 flex flex-col gap-2">
                {!username && (
                    <div>
                        <h2 className="text-lg font-semibold border-b-mgl-dark-400 border-b">Details</h2>
                        <p>Account created: {formatDate(fetchedUser?.createdAt)}</p>
                    </div>
                )}
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
                        {!username && <Link to="/profile/add-game" className="px-1 py-0.5 rounded-xs text-xs bg-blue-500">Add Game</Link>}
                    </div>
                    <div className="mb-4">
                        {games?.map((game: Game) => {
                            if (!username) {
                                if (favoriteGame?.gameName === game.gameName) {
                                    return <GameEntry key={game.gameName} game={game} updateGame={handleUpdateGame} deleteGame={handleDeleteGame} />;
                                }
                                return <GameEntry key={game.gameName} game={game} updateGame={handleUpdateGame} deleteGame={handleDeleteGame} favorGame={handleFavorGame} />;
                            } else {
                                return <GameEntry key={game.gameName} game={game} />;
                            }
                        })}
                    </div>
                </div>
            </div>
        </div>
    );
}