import {useAuth} from "../contexts/AuthContext.tsx";
import {useEffect, useState} from "react";
import {UserProps} from "../types/User.ts";
import {fetchUsers} from "../services/userService.ts";
import {useNavigate} from "react-router-dom";
import {formatDate} from "../utils/dateUtils.ts";

export default function AllUsersPage() {
    const {token} = useAuth();
    const navigate = useNavigate();

    const [users, setUsers] = useState<UserProps[] | []>([]);

    useEffect(() => {
        if (!token) return;
        fetchUsers(token, setUsers);
    }, []);



    return (
        <div className="py-4">
            <table className="w-full">
                <thead className="border-b-mgl-dark-400 border-b-2">
                <tr>
                    <th className="text-start">Username</th>
                    <th className="text-start">Created</th>
                    <th className="text-start">Games</th>
                </tr>
                </thead>
                <tbody>
                {users.map((user) => (
                    <tr
                        key={user.id}
                        className="h-8 not-last:border-b-mgl-dark-400 not-last:border-b hover:cursor-pointer hover:bg-mgl-dark-700"
                        onClick={() => navigate(`/user/${user.username}`)}
                    >
                        <td>{user.username}</td>
                        <td>{formatDate(user.createdAt)}</td>
                        <td>{user.games.length}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}