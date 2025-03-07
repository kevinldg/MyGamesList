import {Outlet} from "react-router-dom";
import Header from "./components/Header.tsx";
import Container from "./components/Container.tsx";

export default function Layout() {
    return (
        <div className="w-screen min-h-screen bg-mgl-dark-700 text-white">
            <Container>
                <Header />
                <Outlet />
            </Container>
        </div>
    );
}