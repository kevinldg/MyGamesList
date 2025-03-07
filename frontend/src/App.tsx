import {Route, Routes} from "react-router-dom";
import IndexPage from "./pages/IndexPage.tsx";


export default function App() {
    return (
        <Routes>
            <Route path="/" element={<IndexPage/>} />
        </Routes>
    );
}