import {FormEvent, useState} from "react";
import {useUser} from "../../contexts/UserContext.tsx";
import {updateGamesFromUser} from "../../services/gameService.ts";
import {useNavigate} from "react-router-dom";

export default function AddGamePage() {
    const user = useUser();
    const navigate = useNavigate();

    const [name, setName] = useState("");
    const [stateOnGame, setStateOnGame] = useState("PLAYING");

    const handleSubmit = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();

        if (user) {
            const games = user.games || [];
            updateGamesFromUser([...games, {name, stateOnGame}], user.id);
            navigate(`/profile`);
        }
    };

    return (
        <div className="p-2">
            <form onSubmit={handleSubmit} className="flex flex-col gap-2 w-fit">
                <input required type="text" name="name" placeholder="Game" value={name} onChange={event => setName(event.target.value)} className="border-b-white border px-1"/>
                <select required name="stateOnGame" value={stateOnGame} onChange={event => setStateOnGame(event.target.value)} className="bg-mgl-dark-800">
                    <option value="COMPLETED">Completed</option>
                    <option value="PLAYING">Playing</option>
                </select>
                <button type="submit" className="bg-mgl-purple-500 text-sm font-bold py-0.5 rounded-xs hover:bg-mgl-purple-400 hover:cursor-pointer">Add Game</button>
            </form>
        </div>
    );
}