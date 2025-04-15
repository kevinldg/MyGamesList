import {useState} from "react";
import {useAuth} from "../../contexts/AuthContext.tsx";
import {Game} from "../../types/Game.ts";
import {GameState} from "../../enums/GameState.ts";
import {useNavigate} from "react-router-dom";
import {addGame, searchGame} from "../../services/gameService.ts";
import {getBadgeProperties} from "../../utils/gameStateUtils.ts";

export default function AddGamePage() {
    const { token, fetchUser } = useAuth();

    const [gameName, setGameName] = useState("");
    const [foundGame, setFoundGame] = useState<Game | null>(null);
    const [gameState, setGameState] = useState<GameState>(GameState.PLAYING);

    const navigate = useNavigate();

    const onSearchSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();

        if (token) {
            searchGame(gameName, token)
                .then(game => { setFoundGame(game);})
                .catch(error => { console.error("Game search error", error); });
        }
    };

    const onAddSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();

        if (token && foundGame) {
            addGame(foundGame, gameState, token)
                .then(response => { if (response.status === 200) { fetchUser(); navigate("/profile"); } })
                .catch(error => { console.error("Add game error", error); });
        }
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
                <button type="submit" className="px-1 py-0.5 rounded bg-blue-500 hover:bg-blue-600 hover:cursor-pointer">Suchen</button>
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
                            {Object.values(GameState).map(state => {
                                const { label } = getBadgeProperties(state as GameState);
                                return (
                                    <option key={state} value={state}>
                                        {label}
                                    </option>
                                );
                            })}
                        </select>
                        <button type="submit" className="px-1 py-0.5 rounded text-sm bg-blue-500 hover:bg-blue-600 hover:cursor-pointer">Add</button>
                    </form>
                </div>
            )}
        </div>
    );
}