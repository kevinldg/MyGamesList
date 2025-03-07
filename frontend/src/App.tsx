import {Route, Routes} from "react-router-dom";
import IndexPage from "./pages/IndexPage.tsx";
import Layout from "./layout.tsx";
import ProfilePage from "./pages/ProfilePage.tsx";


export default function App() {
    return (
        <Routes>
            <Route path="/" element={<Layout/>}>
                <Route index element={<IndexPage/>} />
                <Route path="/profile" element={<ProfilePage/>} />
            </Route>
        </Routes>
    );
}