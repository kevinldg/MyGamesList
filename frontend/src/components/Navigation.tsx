import {Link, useLocation} from "react-router-dom";

type NavigationItemProps = {
    name: string;
    url: string;
    isEnabled: boolean;
    showOnRoutes: string[];
    dontShowOnRoutes: string[];
};

export default function Navigation() {
    const location = useLocation();
    const currentPath = location.pathname;

    const navigationItems: NavigationItemProps[] = [
        {
            name: "Home",
            url: "/",
            isEnabled: true,
            showOnRoutes: ["*"],
            dontShowOnRoutes: ["/login", "/register"]
        },
        {
            name: "My Profile",
            url: "/profile",
            isEnabled: true,
            showOnRoutes: ["*"],
            dontShowOnRoutes: ["/login", "/register"]
        }
    ];

    const shouldShowItem = (item: {showOnRoutes: string[], dontShowOnRoutes?: string[]}) => {
        if (item.dontShowOnRoutes && item.dontShowOnRoutes.includes(currentPath)) {
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