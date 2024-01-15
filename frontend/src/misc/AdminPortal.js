import {useSelector} from "react-redux";
import {Navigate, Outlet} from "react-router-dom";

const AdminPortal = () => {
    const validatingUserRole = useSelector((state) => state.persistedReducer.role);
    const isAdmin = validatingUserRole.some(user => user.name === "ADMIN");

    return isAdmin ? (
        <Outlet />
    ) : (
        <Navigate to="/unauth" replace={true} />
    );
}
export default AdminPortal;
