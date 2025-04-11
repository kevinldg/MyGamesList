export type NavigationItemProps = {
    name: string;
    url: string;
    isEnabled: boolean;
    showOnRoutes: string[];
    dontShowOnRoutes: string[];
};