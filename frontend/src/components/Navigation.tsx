import {Link, useLocation} from "react-router-dom";

export default function Navigation() {
    const location = useLocation();
    const currentPath = location.pathname;

    const navigationItems: {name: string; url: string; isEnabled: boolean; showOnRoutes: string[];}[] = [
        {
            name: "My Profile",
            url: "/profile",
            isEnabled: true,
            showOnRoutes: ["*"]
        },
        {
            name: "Add Game",
            url: "/profile/add-game",
            isEnabled: true,
            showOnRoutes: ["/profile"]
        }
    ];

    const shouldShowItem = (item: {showOnRoutes: string[]}) => {
        if (item.showOnRoutes.includes("*")) return true;
        return item.showOnRoutes.includes(currentPath);
    };

    return (
        <nav className="bg-mgl-purple-400 flex justify-between items-center text-sm">
            {
                navigationItems
                    .filter(shouldShowItem)
                    .map(item => item.isEnabled && (
                    <Link to={item.url} className="w-full p-2 text-center font-bold hover:bg-mgl-dark-500">{item.name}</Link>
                ))
            }
        </nav>
    );
}