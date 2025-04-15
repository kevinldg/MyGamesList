import {useAuth} from "../contexts/AuthContext.tsx";
import {GameState} from "../enums/GameState.ts";
import GameStateList from "../components/GameStateList.tsx";

export default function IndexPage() {
    const {user} = useAuth();

    const gamesPlaying = user?.games.filter(game => game.gameState === GameState.PLAYING) || [];
    const gamesCompleted = user?.games.filter(game => game.gameState === GameState.COMPLETED) || [];
    const gamesOnHold = user?.games.filter(game => game.gameState === GameState.ON_HOLD) || [];
    const gamesDropped = user?.games.filter(game => game.gameState === GameState.DROPPED) || [];
    const gamesPlannedToPlay = user?.games.filter(game => game.gameState === GameState.PLANNED_TO_PLAY) || [];

    return (
        <div className="mt-4 flex flex-col gap-4">
            <div className="w-full flex justify-between items-center">
                <div>
                    <p className="text-2xl">Hello, {user?.username}!</p>
                    <p>You have currently {user?.games.length} games listed{user?.favoriteGame && ` and your favorite game is ${user.favoriteGame.gameName}`}.</p>
                </div>
                {user?.favoriteGame && (
                    <img src={user?.favoriteGame.artworkUrl} alt={`${user?.favoriteGame.gameName} Cover`} className="size-32 rounded" />
                )}
            </div>
            <div className="grid grid-cols-2 gap-4">
                {gamesPlaying.length > 0 && <GameStateList list={gamesPlaying} state="playing"/>}
                {gamesCompleted.length > 0 && <GameStateList list={gamesCompleted} state="completed"/>}
                {gamesOnHold.length > 0 && <GameStateList list={gamesOnHold} state="on hold"/>}
                {gamesDropped.length > 0 && <GameStateList list={gamesDropped} state="dropped"/>}
                {gamesPlannedToPlay.length > 0 && <GameStateList list={gamesPlannedToPlay} state="planned to play"/>}
            </div>
        </div>
    );
}