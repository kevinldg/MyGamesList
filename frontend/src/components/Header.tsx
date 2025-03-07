export default function Header() {
    return (
        <header className="flex justify-between items-center">
            <h1 className="text-3xl font-bold">MyGamesList</h1>
            <div className="flex items-center gap-4 font-bold">
                <p>Login</p>
                <p>Setup</p>
            </div>
        </header>
    );
}