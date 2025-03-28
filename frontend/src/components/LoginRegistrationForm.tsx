import { FormEvent, useState } from "react";
import { Link } from "react-router-dom";

type FormProps = {
    formType: "login" | "register";
    onSubmit: (username: string, password: string) => void;
    error: string | null;
};

export default function LoginRegistrationForm({ formType, onSubmit, error }: FormProps) {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [localError, setLocalError] = useState<string | null>(null);

    const handleSubmit = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();

        if (formType === "register") {
            if (username.length < 8 || password.length < 8) {
                setLocalError("Username and password must each be at least 8 characters long.");
                return;
            }

            const hasNumber = /\d/.test(password);
            const hasSpecialChar = /[!@#$%^&*()_+\-={}[\];':"\\|,.<>/?]+/.test(password);

            if (!hasNumber || !hasSpecialChar) {
                setLocalError("The password must contain at least one number and one special character.\nAllowed special characters: !@#$%^&*()_+-={}[];:\"\\|,.<>/?");
                return;
            }
        }

        onSubmit(username, password);
    };

    const isLogin = formType === "login";
    const displayError = error || localError;

    return (
        <div className="w-full h-full flex justify-center">
            <div className="p-16 bg-mgl-dark-700 flex flex-col gap-4">
                <p className="text-center">{isLogin ? "Login" : "Registration"}</p>
                <form onSubmit={handleSubmit} className="flex flex-col gap-4">
                    <input
                        type="text"
                        name="username"
                        placeholder="Username"
                        value={username}
                        onChange={event => {
                            setUsername(event.target.value);
                            setLocalError(null);
                        }}
                        className="bg-mgl-dark-900 px-2 py-1"
                    />
                    <input
                        type="password"
                        name="password"
                        placeholder="Password"
                        value={password}
                        onChange={event => {
                            setPassword(event.target.value);
                            setLocalError(null);
                        }}
                        className="bg-mgl-dark-900 px-2 py-1"
                    />
                    <button className="bg-blue-500 py-1 rounded">
                        {isLogin ? "Login" : "Sign up"}
                    </button>
                </form>
                <Link to={isLogin ? "/register" : "/login"}>
                    {isLogin ? "Want to register?" : "Want to login?"}
                </Link>
                {displayError && (
                    <div className="border-2 border-red-950 bg-red-900 text-red-400 p-4">
                        <p className="font-bold">Error</p>
                        <p>{displayError}</p>
                    </div>
                )}
            </div>
        </div>
    );
}