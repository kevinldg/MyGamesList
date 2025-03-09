import {Link} from "react-router-dom";
import {useUser} from "../contexts/UserContext.tsx";

export default function Header() {
    const user = useUser();

    return (
        <header className="py-2 flex justify-between items-center">
            <Link to={"/"}>
                <h1 className="text-3xl font-bold">MyGamesList</h1>
            </Link>
            <p>{user ? user.username : "Guest"}</p>
        </header>
    );
}