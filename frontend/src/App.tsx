import {Route, Routes} from "react-router-dom";
import IndexPage from "./pages/IndexPage.tsx";
import Layout from "./layout.tsx";
import ProfilePage from "./pages/profile/ProfilePage.tsx";
import {UserProvider} from "./contexts/UserContext.tsx";
import AddGamePage from "./pages/profile/AddGamePage.tsx";


export default function App() {
    return (
        <UserProvider>
            <Routes>
                <Route path="/" element={<Layout/>}>
                    <Route index element={<IndexPage/>} />
                    <Route path="/profile" element={<ProfilePage/>} />
                    <Route path="/profile/add-game" element={<AddGamePage/>} />
                </Route>
            </Routes>
        </UserProvider>
    );
}