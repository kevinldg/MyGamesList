import {FormEvent, useState} from "react";
import axios from "axios";
import {Link, Navigate, useNavigate} from "react-router-dom";
import {useAuth} from "../contexts/AuthContext.tsx";

export default function RegisterPage() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState<string | null>(null);
    const { token, setToken } = useAuth();
    const navigate = useNavigate();

    const handleSubmit = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();

        if (username.length < 8 || password.length < 8) {
            setError("Username and password must each be at least 8 characters long.");
            return;
        }

        const hasNumber = /\d/.test(password);
        const hasSpecialChar = /[!@#$%^&*()_+\-={}[\];':"\\|,.<>/?]+/.test(password);

        if (!hasNumber || !hasSpecialChar) {
            setError("The password must contain at least one number and one special character.\nAllowed special characters: !@#$%^&*()_+-={}[];:\"\\|,.<>/?");
            return;
        }

        axios.post("/api/auth/register", {username: username, password: password})
            .then(response => {
                setToken(response.data.token);
                navigate("/");
            })
            .catch(error => {console.error("Registration failed", error)})
    };

    return token ? <Navigate to="/"/> : (
        <div className="w-full h-full flex justify-center">
            <div className="p-16 bg-mgl-dark-700 flex flex-col gap-4">
                <p className="text-center">Registration</p>
                <form onSubmit={handleSubmit} className="flex flex-col gap-4">
                    <input type="text" name="username" placeholder="Username" value={username} onChange={event => {
                        setUsername(event.target.value);
                        setError(null);
                    }} className="bg-mgl-dark-900 px-2 py-1"/>
                    <input type="password" name="password" placeholder="Password" value={password} onChange={event => {
                        setPassword(event.target.value);
                        setError(null);
                    }} className="bg-mgl-dark-900 px-2 py-1"/>
                    <button className="bg-blue-500 py-1 rounded">Sign up</button>
                </form>
                <Link to="/login">Want to login?</Link>
                {error && (
                    <div className="border-2 border-red-950 bg-red-900 text-red-400 p-4">
                        <p className="font-bold">Error</p>
                        <p>{error}</p>
                    </div>
                )}
            </div>
        </div>
    );
}