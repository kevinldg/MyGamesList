import { useState } from "react";
import { useAuth } from "../contexts/AuthContext.tsx";
import { Navigate, useNavigate } from "react-router-dom";
import axios from "axios";
import LoginRegistrationForm from "../components/LoginRegistrationForm";

export default function RegisterPage() {
    const [error, setError] = useState<string | null>(null);
    const { token, setToken } = useAuth();
    const navigate = useNavigate();

    const handleSubmit = (username: string, password: string) => {
        axios.post("/api/auth/register", { username, password })
            .then(response => {
                setToken(response.data.token);
                navigate("/");
            })
            .catch(error => {
                console.error("Registration failed", error);
                setError("Registration failed. Please try again.");
            });
    };

    return token ? <Navigate to="/" /> : (
        <LoginRegistrationForm
            formType="register"
            onSubmit={handleSubmit}
            error={error}
        />
    );
}