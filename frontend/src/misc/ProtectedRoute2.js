import {useSelector} from "react-redux";
import {Navigate, Outlet} from "react-router-dom";

const ProtectedRoute2 = () => {
    const isAuthenticated = useSelector((state) => state.persistedReducer.isLoggedIn);

    return !isAuthenticated ? (
        <Outlet />
    ) : (
        <Navigate to="/profile" replace={true} />
    );
}
export default ProtectedRoute2
