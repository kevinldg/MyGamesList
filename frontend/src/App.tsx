import {Navigate, Route, Routes} from "react-router-dom";
import Layout from "./layout.tsx";
import ProfilePage from "./pages/profile/ProfilePage.tsx";
import IndexPage from "./pages/IndexPage.tsx";
import LoginPage from "./pages/LoginPage.tsx";
import RegisterPage from "./pages/RegisterPage.tsx";
import {AuthProvider, useAuth} from "./contexts/AuthContext.tsx";
import {JSX} from "react";
import AddGamePage from "./pages/profile/AddGamePage.tsx";
import UserSearchPage from "./pages/UserSearchPage.tsx";

const PrivateRoute = ({ children }: { children: JSX.Element }) => {
    const { token } = useAuth();
    return token ? children : <Navigate to="/login" />;
};

export default function App() {
    return (
        <AuthProvider>
            <Routes>
                <Route path="/" element={<Layout/>}>
                    <Route index element={
                        <PrivateRoute>
                            <IndexPage/>
                        </PrivateRoute>
                    }/>
                    <Route path="/login" element={<LoginPage/>}/>
                    <Route path="/register" element={<RegisterPage/>}/>
                    <Route path="/profile" element={
                        <PrivateRoute>
                            <ProfilePage/>
                        </PrivateRoute>
                    }/>
                    <Route path="/profile/add-game" element={
                        <PrivateRoute>
                            <AddGamePage/>
                        </PrivateRoute>
                    }/>
                    <Route path="/user/search" element={
                        <PrivateRoute>
                            <UserSearchPage/>
                        </PrivateRoute>
                    }/>
                    <Route path="/user/:username" element={
                        <PrivateRoute>
                            <ProfilePage/>
                        </PrivateRoute>
                    }/>
                </Route>
            </Routes>
        </AuthProvider>
    );
}