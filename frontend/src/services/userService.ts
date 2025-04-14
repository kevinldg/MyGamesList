import axios from "axios";
import {Dispatch, SetStateAction} from "react";
import {UserProps} from "../types/User.ts";

export const fetchUser = (username: string, token: string, setFetchedUser: Dispatch<SetStateAction<UserProps | null>>) => {
    axios.get(`/api/user/${username}`, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    })
        .then(response => setFetchedUser(response.data))
        .catch(error => {
            console.error("Error getting user", error);
            setFetchedUser(null);
        });
};

export const fetchUsers = (token: string, setFetchedUsers: Dispatch<SetStateAction<UserProps[] | []>>) => {
    axios.get(`/api/user`, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    })
        .then(response => setFetchedUsers(response.data))
        .catch(error => {
            console.error("Error getting users", error);
            setFetchedUsers([]);
        });
};