import {Game} from "../../model/Game.ts";
import {useUser} from "../../contexts/UserContext.tsx";
import Badge from "../../components/Badge.tsx";

export default function ProfilePage() {

    const user = useUser();
    const games: Game[] | undefined = user?.games;

    return (
        <div>
            <div className="bg-mgl-dark-700 border-b-mgl-dark-400 border-b px-2 py-1">
                <p className="text-lg font-semibold">{`${user?.username}'s Profile`}</p>
            </div>
            <div className="p-2 flex flex-col">
                {
                    games ? games.map(game => (
                        <div className="py-4 flex items-center gap-2 border-b-mgl-dark-400 not-last:border-b">
                            <p className="text-mgl-purple-200">{game.name}</p>
                            <Badge text={game.stateOnGame} />
                        </div>
                    )) : "Keine Games gefunden"
                }
            </div>
        </div>
    );
}