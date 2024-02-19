import * as React from "react";
import "./signup.css";
import {useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {signUp} from "../redux/auth";

const SignUp = () => {
    const dispatch = useDispatch();
    const message = useSelector((state) => state.message);

    const initialNewUser = {
        firstName: "",
        lastName: "",
        email: "",
        password: ""
    };
    const [newUser, setNewUser] = useState(initialNewUser);

    const handleInputChange = event => {
        const {name, value} = event.target
        setNewUser({...newUser, [name]: value});
    };

    const handleRegister = async (e) => {
        e.preventDefault();
        const { firstName, lastName, email, password } = newUser;

        dispatch(signUp({ firstName, lastName, email, password }))
            .unwrap()
            .then(() => {
                setNewUser(initialNewUser);

            })
            .catch(() => {

            });
    };

    return (
        <div className="flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">
            <div className="sm:mx-auto sm:w-full sm:max-w-sm profile-picture">
                <img
                    className="mx-auto  rounded-xl"
                    src="https://static.vecteezy.com/system/resources/thumbnails/009/209/212/small/neon-glowing-profile-icon-3d-illustration-vector.jpg"
                    alt="avatar"
                />
            </div>

            <h2 className="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900" style={{
                color: "#f8961d"
            }}>
                Sign up and join the club
            </h2>

            <div className="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
                <form className="space-y-6" onSubmit={handleRegister}>

                    <div>
                        <div className="flex items-center justify-between">
                            <div className="text-red-500">
                                {message}
                            </div>

                        </div>

                        <div className="mt-2">
                            <input
                                id="firstName"
                                name="firstName"
                                type="text"
                                required
                                onChange={handleInputChange}
                                value={newUser.firstName}
                                placeholder="First Name"
                                className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-orange-500 sm:text-sm sm:leading-6"
                            />
                        </div>
                    </div>

                    <div>
                        <div className="flex items-center justify-between">

                        </div>
                        <div className="mt-2">
                            <input
                                id="lastName"
                                name="lastName"
                                type="text"
                                required
                                value={newUser.lastName}
                                onChange={handleInputChange}
                                placeholder="Last Name"
                                className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-orange-500 sm:text-sm sm:leading-6"
                            />
                        </div>
                    </div>

                    <div>
                        <div className="flex items-center justify-between">


                        </div>
                        <div className="mt-2">
                            <input
                                id="email"
                                name="email"
                                type="email"
                                required
                                value={newUser.email}
                                onChange={handleInputChange}
                                placeholder="E-mail"
                                className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-orange-500 sm:text-sm sm:leading-6"
                            />
                        </div>
                    </div>

                    <div>
                        <div className="flex items-center justify-between">

                        </div>
                        <div className="mt-2">
                            <input
                                id="password"
                                name="password"
                                type="password"
                                placeholder="Password"
                                value={newUser.password}
                                required
                                onChange={handleInputChange}
                                className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-orange-500 sm:text-sm sm:leading-6"
                            />
                        </div>
                    </div>

                    <div>
                        <button
                            type="submit"
                            className="flex w-full justify-center rounded-md bg-indigo-900 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-amber-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
                        >
                            Sign Up
                        </button>
                    </div>
                </form>

            </div>
        </div>
    );
}
export default SignUp;
