import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faGithub} from "@fortawesome/free-brands-svg-icons";
import {Link} from "react-router-dom";

export default function Footer() {
    return (
        <div className="p-4 bg-mgl-purple-400 flex justify-center items-center gap-4">
            <Link to="/about" className="hover:underline hover:cursor-pointer">About</Link>
            <span>|</span>
            <p>© 2025 by kevinldg</p>
            <span>|</span>
            <a href="https://github.com/kevinldg/MyGamesList" className="rotate-0 transition-transform duration-500 hover:rotate-45"><FontAwesomeIcon icon={faGithub} className="text-3xl"/></a>
        </div>
    );
}