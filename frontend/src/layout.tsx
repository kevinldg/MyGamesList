import Container from "./components/Container.tsx";
import {Outlet} from "react-router-dom";
import Header from "./components/Header.tsx";
import Navigation from "./components/Navigation.tsx";

export default function Layout() {
    return (
        <div className="w-screen min-h-screen bg-mgl-dark-900 text-white">
            <Container>
                <Header />
                <Navigation />
                <Outlet />
            </Container>
        </div>
    );
}