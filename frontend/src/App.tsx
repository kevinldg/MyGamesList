import {Route, Routes} from "react-router-dom";
import IndexPage from "./pages/IndexPage.tsx";
import Layout from "./layout.tsx";


export default function App() {
    return (
        <Routes>
            <Route path="/" element={<Layout/>}>
                <Route index element={<IndexPage/>} />
            </Route>
        </Routes>
    );
}