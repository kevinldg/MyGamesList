import {useAuth} from "../contexts/AuthContext.tsx";

export default function IndexPage() {
    const { setToken } = useAuth();

    return (
        <div className="mt-4 flex flex-col gap-4">
            <button onClick={() => setToken(null)} className="bg-blue-500 px-2 py-1 rounded w-32">Logout</button>
        </div>
    );
}