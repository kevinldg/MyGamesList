import { useState } from "react";
import { useAuth } from "../contexts/AuthContext.tsx";
import { Navigate, useNavigate } from "react-router-dom";
import axios from "axios";
import LoginRegistrationForm from "../components/LoginRegistrationForm";

export default function LoginPage() {
    const [error, setError] = useState<string | null>(null);
    const { token, setToken } = useAuth();
    const navigate = useNavigate();

    const handleSubmit = (username: string, password: string) => {
        axios.post("/api/auth/login", { username, password })
            .then(response => {
                if (response.data.message === "Invalid credentials") {
                    setError(response.data.message);
                    return;
                }
                setToken(response.data.token);
                navigate("/");
            })
            .catch(error => {
                console.error("Login failed", error);
                setError("Login failed. Please try again.");
            });
    };

    return token ? <Navigate to="/" /> : (
        <LoginRegistrationForm
            formType="login"
            onSubmit={handleSubmit}
            error={error}
        />
    );
}