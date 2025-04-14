import {Link, useLocation} from "react-router-dom";
import {NavigationItemProps} from "../types/Navigation.ts";

export default function Navigation() {
    const location = useLocation();
    const currentPath = location.pathname;

    if (currentPath === "/login" || currentPath === "/register") {
        return null;
    }

    const navigationItems: NavigationItemProps[] = [
        {
            name: "Home",
            url: "/",
            isEnabled: true,
            showOnRoutes: ["*"]
        },
        {
            name: "My Profile",
            url: "/profile",
            isEnabled: true,
            showOnRoutes: ["*"]
        },
        {
            name: "Search user",
            url: "/user/search",
            isEnabled: true,
            showOnRoutes: ["*"]
        }
    ];

    const shouldShowItem = (item: {showOnRoutes: string[], dontShowOnRoutes?: string[]}) => {
        if (item.dontShowOnRoutes?.includes(currentPath)) {
            return false;
        }

        if (item.showOnRoutes.includes("*")) return true;
        return item.showOnRoutes.includes(currentPath);
    };

    return (
        <nav className="bg-mgl-purple-400 flex justify-between items-center text-sm">
            {
                navigationItems
                    .filter(shouldShowItem)
                    .map(item => item.isEnabled && (
                        <Link key={item.url} to={item.url} className="w-full p-2 text-center font-bold hover:bg-mgl-dark-500">{item.name}</Link>
                    ))
            }
        </nav>
    );
}