import {Link} from "react-router-dom";

export default function Header() {

    return (
        <header className="py-2">
            <Link to={"/"}>
                <h1 className="text-3xl font-bold">MyGamesList</h1>
            </Link>
        </header>
    );
}