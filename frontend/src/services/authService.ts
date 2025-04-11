import axios from "axios";

export const submitLogin = async (
    username: string,
    password: string,
    setError: (message: string | null) => void,
    setToken: (token: string | null) => void,
    navigate: (path: string) => void
): Promise<void> => {
    try {
        setError(null);
        const response = await axios.post("/api/auth/login", { username, password });
        if (response.data.message === "Invalid credentials") {
            setError(response.data.message);
            return;
        }
        setToken(response.data.token);
        navigate("/");
    } catch (error) {
        console.error("Login failed", error);
        setError("Login failed. Please try again.");
    }
};

export const submitRegister = async (
    username: string,
    password: string,
    repeatPassword: string,
    setError: (message: string | null) => void,
    setToken: (token: string | null) => void,
    navigate: (path: string) => void
): Promise<void> => {
    try {
        setError(null);
        const response = await axios.post("/api/auth/register", { username, password, repeatPassword });
        if (response.data.message === "User already exists") {
            setError(response.data.message);
            return;
        }
        setToken(response.data.token);
        navigate("/");
    } catch (error) {
        console.error("Registration failed", error);
        setError("Registration failed. Please try again.");
    }
};