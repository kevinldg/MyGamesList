import {createContext, useState, useContext, ReactNode, useEffect} from 'react';
import axios from "axios";
import { User } from '../types/User';

interface AuthContextType {
    token: string | null;
    user: User | null;
    setToken: (token: string | null) => void;
    fetchUser: () => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider = ({ children }: { children: ReactNode }) => {
    const [token, setToken] = useState<string | null>(localStorage.getItem('token'));
    const [user, setUser] = useState<User | null>(null);

    const fetchUser = () => {
        if (token) {
            axios.get("/api/auth/me", {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
                .then(response => setUser(response.data))
                .catch(error => {
                    console.error("Error getting user", error);
                    setToken(null);
                    setUser(null);
                });
        } else {
            setUser(null);
        }
    };

    useEffect(() => {
        fetchUser();
    }, [token]);

    const handleSetToken = (token: string | null) => {
        if (token) {
            localStorage.setItem('token', token);
        } else {
            localStorage.removeItem('token');
        }
        setToken(token);
    };

    return (
        <AuthContext.Provider value={{ token, user, setToken: handleSetToken, fetchUser }}>
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => {
    const context = useContext(AuthContext);
    if (!context) throw new Error('useAuth must be used inside of AuthProvider');
    return context;
};
