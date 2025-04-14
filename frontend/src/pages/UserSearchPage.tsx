import {useState} from "react";
import {useAuth} from "../contexts/AuthContext.tsx";
import {fetchUser} from "../services/userService.ts";
import {UserProps} from "../types/User.ts";
import {Link} from "react-router-dom";
import {formatDate} from "../utils/dateUtils.ts";

export default function UserSearchPage() {
    const { token } = useAuth();

    const [username, setUsername] = useState("");
    const [user, setUser] = useState<UserProps | null>(null);

    const handleUserSearch = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        if (!username || !token) return;

        fetchUser(username, token, setUser);
    };

    return (
        <div className="py-2 flex flex-col gap-2">
            <form onSubmit={handleUserSearch} className="flex items-center gap-2">
                <input
                    type="text"
                    name="username"
                    placeholder="Username"
                    value={username}
                    onChange={event => {setUsername(event.target.value)}}
                    className="border-white border px-2 rounded"
                />
                <button type="submit" className="px-1 py-0.5 rounded bg-blue-500">Suchen</button>
            </form>
            {user && (
                <div className="p-4 bg-mgl-dark-700">
                    <p className="font-bold">User found!</p>
                    <div className="flex items-center gap-4">
                        <p>{user.username}</p>
                        <p>User since: {formatDate(user.createdAt)}</p>
                        <Link to={`/user/${user.username}`} className="px-1 py-0.5 rounded bg-blue-500">View Profile</Link>
                    </div>
                </div>
            )}
        </div>
    );
}