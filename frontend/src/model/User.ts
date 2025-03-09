export type User = {
    id: string,
    username: string,
    password: string,
    games: {
        name: string,
        stateOnGame: string
    }[]
};