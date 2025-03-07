export default function ProfilePage() {

    const games: {name: string; stateOnGame: string;}[] = [
        {
            name: "Grand Theft Auto V",
            stateOnGame: "Completed"
        },
        {
            name: "Days Gone",
            stateOnGame: "Completed"
        },
        {
            name: "Assassin's Creed: Syndicate",
            stateOnGame: "Dropped"
        }
    ];

    return (
        <div>
            <div className="bg-mgl-dark-700 border-b-mgl-dark-400 border-b px-2 py-1">
                <p className="text-lg font-semibold">Username's Profile</p>
            </div>
            <div className="p-2 flex flex-col">
                {
                    games.map(game => (
                        <div className="py-4 leading-4 border-b-mgl-dark-400 not-last:border-b">
                            <p className="text-mgl-purple-200">{game.name}</p>
                            <p className="text-sm italic">{game.stateOnGame}</p>
                        </div>
                    ))
                }
            </div>
        </div>
    );
}