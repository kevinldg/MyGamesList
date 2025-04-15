import {Link} from "react-router-dom";
import {useAuth} from "../contexts/AuthContext.tsx";

export default function Header() {
    const {user, setToken} = useAuth();

    return (
        <header className="py-2 flex justify-between items-center">
            <Link to={"/"}>
                <h1 className="text-3xl font-bold">MyGamesList</h1>
            </Link>
            {user && (
                <div className="flex items-center gap-4">
                    <p>{user?.username}</p>
                    <button onClick={() => setToken(null)} className="bg-mgl-red-400 px-2 py-0.5 rounded hover:bg-red-800 hover:cursor-pointer">Logout</button>
                </div>
            )}
        </header>
    );
}