import {useParams} from "react-router-dom";
import axios from "axios";
import {useAuth} from "../contexts/AuthContext.tsx";
import {useEffect, useState} from "react";
import {Game} from "../types/Game.ts";
import GameEntry from "../components/GameEntry.tsx";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faStar} from "@fortawesome/free-solid-svg-icons";

type UserProps = {
    id: string;
    username: string;
    createdAt: string;
    games: Game[];
    favoriteGame: Game;
};

export default function UserPage() {
    const {token} = useAuth();
    const [user, setUser] = useState<UserProps | null>();

    const params = useParams();
    const username: string | undefined = params.username;

    const fetchUser = () => {
        if (token && username) {
            axios.get(`/api/user/${username}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
                .then(response => setUser(response.data))
                .catch(error => {
                    console.error("Error getting user", error);
                    setUser(null);
                });
        } else {
            setUser(null);
        }
    };

    useEffect(() => {
        fetchUser();
    }, [username]);

    return (
        <div>
            <div className="bg-mgl-dark-700 border-b-mgl-dark-400 border-b px-2 py-1">
                <p className="text-lg font-semibold">{`${user?.username}'s Profile`}</p>
            </div>
            <div className="px-2 py-1 flex flex-col gap-2">
                {
                    user?.favoriteGame && (
                        <div>
                            <div className="border-b-mgl-dark-400 border-b flex items-center gap-2">
                                <h2 className="text-lg font-semibold">Favorite Game</h2>
                                <FontAwesomeIcon icon={faStar}/>
                            </div>
                            <GameEntry game={user.favoriteGame} />
                        </div>
                    )
                }
                <div>
                    <div className="border-b-mgl-dark-400 border-b flex justify-between items-center">
                        <h2 className="text-lg font-semibold">Games</h2>
                    </div>
                    <div className="mb-4">
                        {
                            user?.games.map((game: Game) => (
                                <GameEntry game={game} />
                            ))
                        }
                    </div>
                </div>
            </div>
        </div>
    );
}