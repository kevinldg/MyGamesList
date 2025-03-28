import {FormEvent, useState} from "react";
import {useAuth} from "../contexts/AuthContext.tsx";
import {Link, Navigate, useNavigate} from "react-router-dom";
import axios from "axios";

export default function LoginPage() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const { token, setToken } = useAuth();
    const navigate = useNavigate();

    const handleSubmit = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();

        axios.post("/api/auth/login", {username: username, password: password})
            .then(response => {
                setToken(response.data.token);
                navigate("/");
            })
            .catch(error => {console.error("Login failed", error)})
    };

    return token ? <Navigate to="/"/> : (
        <div className="w-full h-full flex justify-center">
            <div className="p-16 bg-mgl-dark-700 flex flex-col gap-4">
                <p className="text-center">Login</p>
                <form onSubmit={handleSubmit} className="flex flex-col gap-4">
                    <input type="text" name="username" placeholder="Username" value={username} onChange={event => setUsername(event.target.value)} className="bg-mgl-dark-900 px-2 py-1" />
                    <input type="password" name="password" placeholder="Password" value={password} onChange={event => setPassword(event.target.value)} className="bg-mgl-dark-900 px-2 py-1" />
                    <button className="bg-blue-500 py-1 rounded">Login</button>
                </form>
                <Link to="/register">Want to register?</Link>
            </div>
        </div>
    );
}