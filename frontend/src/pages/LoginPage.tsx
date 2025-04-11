import { useState } from "react";
import { useAuth } from "../contexts/AuthContext.tsx";
import { Navigate, useNavigate } from "react-router-dom";
import LoginRegistrationForm from "../components/LoginRegistrationForm";
import { submitLogin } from "../services/authService.ts";

export default function LoginPage() {
    const [error, setError] = useState<string | null>(null);
    const { token, setToken } = useAuth();
    const navigate = useNavigate();

    const handleSubmit = (username: string, password: string) => {
        submitLogin(username, password, setError, setToken, navigate);
    };

    return token ? <Navigate to="/" /> : (
        <LoginRegistrationForm
            formType="login"
            onSubmit={handleSubmit}
            error={error}
        />
    );
}