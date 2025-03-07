import {Link} from "react-router-dom";

export default function Navigation() {
    const navigationItems: {name: string; url: string; isEnabled: boolean;}[] = [
        {
            name: "My Profile",
            url: "/profile",
            isEnabled: true
        },
        {
            name: "Add Game",
            url: "/",
            isEnabled: false
        }
    ];

    return (
        <nav className="bg-mgl-purple-400 flex justify-between items-center text-sm">
            {navigationItems.map(item => item.isEnabled && (
                <Link to={item.url} className="w-full p-2 text-center font-bold hover:bg-mgl-dark-500">{item.name}</Link>
            ))}
        </nav>
    );
}