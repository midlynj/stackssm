import "./unauthorized.css"

const UnAuthorized = () => {
    return (
        <div className="unauthorized-error-page">
            <h1 className="unauth">401 - Unauthorized</h1>
            <p className="permission">You do not have permission to access this page.</p>
        </div>
    );

}
export default UnAuthorized;
