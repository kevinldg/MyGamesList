import { useState } from "react";
import { useAuth } from "../contexts/AuthContext.tsx";
import { Navigate, useNavigate } from "react-router-dom";
import LoginRegistrationForm from "../components/LoginRegistrationForm";
import {submitRegister} from "../services/authService.ts";

export default function RegisterPage() {
    const [error, setError] = useState<string | null>(null);
    const { token, setToken } = useAuth();
    const navigate = useNavigate();

    const handleSubmit = (username: string, password: string, repeatPassword?: string) => {
        setError(null);

        if (repeatPassword === undefined) {
            setError("Please repeat the password.");
            return;
        }

        submitRegister(username, password, repeatPassword, setError, setToken, navigate);
    };

    return token ? <Navigate to="/" /> : (
        <LoginRegistrationForm
            formType="register"
            onSubmit={handleSubmit}
            error={error}
        />
    );
}