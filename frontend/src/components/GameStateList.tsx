import {Game} from "../types/Game.ts";

export default function GameStateList({list, state}: {list: Game[], state: string}) {
    return (
        <div className="bg-mgl-dark-800 border-mgl-dark-500 border-2 p-4">
            <p className="font-bold">Games {state}:</p>
            {list.map(game => (
                <p key={game.gameId}>{game.gameName}</p>
            ))}
        </div>
    );
}