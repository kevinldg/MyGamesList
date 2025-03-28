import {useAuth} from "../contexts/AuthContext.tsx";
import { formatDate } from "../utils/dateUtils.ts";

export default function ProfilePage() {
    const { user } = useAuth();

    return (
        <div>
            <div className="bg-mgl-dark-700 border-b-mgl-dark-400 border-b px-2 py-1 flex justify-between items-center">
                <p className="text-lg font-semibold">{`${user?.username}'s Profile`}</p>
                <p>{user?.id}</p>
            </div>
            <div>
                <p>Account created: {formatDate(user?.createdAt)}</p>
            </div>
        </div>
    );
}