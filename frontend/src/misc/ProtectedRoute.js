import {Navigate, Outlet} from "react-router-dom";
import {useSelector} from "react-redux";
const ProtectedRoute = () => {
    const isAuthenticated = useSelector((state) => state.persistedReducer.isLoggedIn)

    return isAuthenticated ? (
        <Outlet />
    ) : (
        <Navigate to="/signin" replace={true} />
    );
};
export default ProtectedRoute
