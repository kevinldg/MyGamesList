import {useState} from "react";
import axios from "axios";
import {useAuth} from "../../contexts/AuthContext.tsx";
import {Game} from "../../types/Game.ts";
import {GameState} from "../../enums/GameState.ts";
import {useNavigate} from "react-router-dom";

export default function AddGamePage() {
    const { token, fetchUser } = useAuth();
    const [gameName, setGameName] = useState("");
    const [foundGame, setFoundGame] = useState<Game | null>(null);
    const [gameState, setGameState] = useState<GameState>(GameState.PLAYING);
    const navigate = useNavigate();

    const onSearchSubmit = (event: { preventDefault: () => void; }) => {
        event.preventDefault();

        axios.get("/api/igdb/game-and-artwork", {
            params: {name: gameName},
            headers: {
                Authorization: `Bearer ${token}`
            }
        })
            .then(response => setFoundGame(response.data))
            .catch(error => {
                console.error("Game search error", error);
            });

    };

    const onAddSubmit = (event: { preventDefault: () => void; }) => {
        event.preventDefault();

        axios.post(`/api/user/games`,
            {
                "gameName": foundGame?.gameName,
                "gameState": gameState
            },
            {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
            .then(response => {
                if (response.status === 200) {
                    fetchUser();
                    navigate("/profile");
                }
            })
            .catch(error => {
                console.error("Add game error", error);
            });

    };

    return (
        <div className="pt-2 flex flex-col gap-2">
            <h2 className="text-xl font-semibold">Add Game</h2>
            <form onSubmit={onSearchSubmit} className="flex items-center gap-2">
                <input
                    type="text"
                    name="game"
                    placeholder="Game"
                    value={gameName}
                    onChange={event => {
                        setFoundGame(null);
                        setGameName(event.target.value);
                    }}
                    className="border-white border px-2 rounded"
                />
                <button type="submit" className="px-1 py-0.5 rounded bg-blue-500">Suchen</button>
            </form>
            {foundGame && (
                <div className="p-4 bg-mgl-dark-700 flex flex-col gap-4">
                    <div className="flex gap-4">
                        <img src={foundGame.artworkUrl} alt={`${foundGame.gameName} Cover`} className="size-32" />
                        <div>
                            <h3>{foundGame.gameName}</h3>
                            <small>{foundGame.gameSummary}</small>
                        </div>
                    </div>
                    <form onSubmit={onAddSubmit} className="flex items-center gap-2">
                        <select
                            value={gameState}
                            onChange={event => {
                                setGameState(event.target.value as GameState);
                            }}
                            className="bg-mgl-dark-900">
                            <option value="PLAYING">Playing</option>
                            <option value="COMPLETED">Completed</option>
                            <option value="ON_HOLD">On Hold</option>
                            <option value="DROPPED">Dropped</option>
                            <option value="PLANNED_TO_PLAY">Planned to play</option>
                        </select>
                        <button type="submit" className="px-1 py-0.5 rounded text-sm bg-blue-500">Add</button>
                    </form>
                </div>
            )}
        </div>
    );
}