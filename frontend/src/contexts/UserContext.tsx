import {createContext, ReactNode, useContext, useEffect, useState} from "react";
import {getUser} from "../services/userService.ts";
import {User} from "../model/User.ts";

const UserContext = createContext<User | null>(null);

export const UserProvider = ({children}: {children: ReactNode}) => {
    const [user, setUser] = useState<User | null>(null);

    useEffect(() => {
        const fetchUser = async () => {
            try {
                const fetchedUser = await getUser();
                setUser(fetchedUser);
            } catch (error) {
                console.error("Error fetching user: ", error);
            }
        };

        fetchUser();
    }, []);

    return (
        <UserContext.Provider value={user}>{children}</UserContext.Provider>
    );
};

export const useUser = () => useContext(UserContext);