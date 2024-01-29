import "./admindashboard.css"
import {useMutation, useQuery, useQueryClient} from "react-query";
import React from "react";
import URL from "../misc/url";
import Loading from "./Loading";
import Error from "./Error";
import {useSelector} from "react-redux";

const AdminDashBoard = () => {
    const userEmail = useSelector((state) => state.persistedReducer.user.email);

    const fetchNonAdminUsers = async () => {
        try {
            const response = await fetch( `${URL.BASE_URL}/admin/all/users?email=${userEmail}` );
            return response.json();

        } catch (error) {
            throw new Error("Error fetching data");
        }
    };

    const { data, isLoading, isError } = useQuery("fetchUsers", fetchNonAdminUsers);

    const queryClient = useQueryClient();

    const deleteUser = useMutation(
        ({ userId }) => {

            return fetch(`${URL.BASE_URL}/admin/delete/${userId}`, {
                method: "PUT",
            });
        },
        {
            onSuccess: response => {
                queryClient.invalidateQueries("fetchUsers");
            },
            onError: error => {

            }
        }
    );

    const handleDeleteUser = ( userId) => {
        deleteUser.mutate({ userId });
    };

    const activateUser = useMutation(
        ({ userId }) => {

            return fetch(`${URL.BASE_URL}/admin/activate/${userId}`, {
                method: "PUT",
            });
        },
        {
            onSuccess: response => {
                queryClient.invalidateQueries("fetchUsers");

            },
            onError: error => {

            }
        }
    );

    const handleActivateUser = ( userId) => {
        activateUser.mutate({ userId });
    };

    const restrictUser = useMutation(
        ({ userId }) => {

            return fetch(`${URL.BASE_URL}/admin/restrict/${userId}`, {
                method: "PUT",
            });
        },
        {
            onSuccess: response => {
                queryClient.invalidateQueries("fetchUsers");

            },
            onError: error => {

            }
        }
    );

    const handleRestrictUser = ( userId) => {
        restrictUser.mutate({ userId });
    };

    if (isLoading)
        return <div><Loading/></div>

    if (isError)
        return <div><Error/></div>

    return (
        <div className="wrapper text-white">

            <div className="table-container">
                <table className="main_table">
                    <thead>
                    <tr className="table_head">
                        <th>Id</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Role</th>
                        <th>Status</th>
                        <th>Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    {data.map((user) => (
                        <tr key={user.id} className="rowItem">
                            <td>{user.id}</td>
                            <td>{user.firstName}</td>
                            <td>{user.lastName}</td>
                            <td>USER</td>
                            <td>{user.status}</td>
                            <td>
                                <button
                                    className="bg-green-400"
                                    style={{ borderRadius: "60px" }}
                                    onClick={() => handleActivateUser(user.id)}
                                >

                                    Active
                                </button>
                            </td>
                            <td>
                                <button
                                    className="bg-red-500"
                                    style={{ borderRadius: "60px" }}
                                    onClick={() => handleDeleteUser(user.id)}
                                >
                                    Inactive
                                </button>
                            </td>
                            <td>
                                <button
                                    className="bg-orange-500"
                                    style={{ borderRadius: "60px" }}
                                    onClick={() => handleRestrictUser(user.id)}
                                >
                                    Restricted
                                </button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
}
export default AdminDashBoard;
