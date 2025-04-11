import {FormEvent, useEffect, useState} from "react";
import { Link } from "react-router-dom";
import {FormProps} from "../types/Form.ts";

export default function LoginRegistrationForm({ formType, onSubmit, error }: FormProps) {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [repeatPassword, setRepeatPassword] = useState("");
    const [localError, setLocalError] = useState<string | null>(null);
    const [displayError, setDisplayError] = useState<string | null>(error);

    useEffect(() => {
        setDisplayError(error);
    }, [error]);

    const isLogin = formType === "login";

    const handleSubmit = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();

        if (formType === "register") {
            if (username.length < 5 || username.length > 20) {
                setLocalError("Username must be at least 5 and maximum 20 characters long.");
                return;
            }

            if (password.length < 8) {
                setLocalError("Password must be at least 8 characters long.");
                return;
            }

            if (username.startsWith(" ")) {
                setLocalError("The user name must not begin with a space.");
                return;
            }

            if (username.endsWith(" ")) {
                setLocalError("The user name must not end with a space.");
                return;
            }

            if (/\s\s/.test(username)) {
                setLocalError("The user name must not contain consecutive spaces.");
                return;
            }

            const hasNumber = /\d/.test(password);
            const hasSpecialChar = /[!@#$%^&*()_+\-={}[\];':"\\|,.<>/?]+/.test(password);

            if (!hasNumber || !hasSpecialChar) {
                setLocalError("The password must contain at least one number and one special character.\nAllowed special characters: !@#$%^&*()_+-={}[];:\"\\|,.<>/?");
                return;
            }

            if (password !== repeatPassword) {
                setLocalError("The passwords do not match.");
                return;
            }
        }

        onSubmit(username, password, repeatPassword);
    };

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
                            setDisplayError(null);
                        }}
                        className="bg-mgl-dark-900 px-2 py-1"
                    />
                    {formType === "register" && (
                        <input
                            type="password"
                            name="repeatPassword"
                            placeholder="Repeat Password"
                            value={repeatPassword}
                            onChange={event => {
                                setRepeatPassword(event.target.value);
                                setLocalError(null);
                                setDisplayError(null);
                            }}
                            onPaste={(event) => event.preventDefault()}
                            className="bg-mgl-dark-900 px-2 py-1"
                        />
                    )}
                    <button className="bg-blue-500 py-1 rounded">
                        {isLogin ? "Login" : "Sign up"}
                    </button>
                </form>
                <Link to={isLogin ? "/register" : "/login"}>
                    {isLogin ? "Want to register?" : "Want to login?"}
                </Link>
                {(displayError || localError) && (
                    <div className="border-2 border-red-950 bg-red-900 text-red-400 p-4">
                        <p className="font-bold">Error</p>
                        <p>{displayError || localError}</p>
                    </div>
                )}
            </div>
        </div>
    );
}