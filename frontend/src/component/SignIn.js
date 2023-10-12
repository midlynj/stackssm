import "./signup.css"
import {Link} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {signIn} from "../redux/auth";
import {useState} from "react";

const SignIn = () => {
    const dispatch = useDispatch();

    const initialUser = {
        email: "",
        password: ""
    }
    const [userSignIn, setUserSignIn] = useState(initialUser);

    const handleInputChange = event => {
        const {name, value} = event.target
        setUserSignIn({...userSignIn, [name]: value})
    }

    const handleSignIn = async (e) => {
        e.preventDefault()
        const {  email, password } = userSignIn;

        dispatch(signIn({ email, password }))
            .unwrap()
            .then(() => {
                // setSuccessful(true);
            })
            .catch(() => {
                // setSuccessful(false);
            });
    };


    return (
        <>
            <div className="flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">
                <div className="sm:mx-auto sm:w-full sm:max-w-sm profile-picture" style={{
                    border: "3px solid #5a03e7"
                }}>
                    <img
                        className="mx-auto  rounded-xl"
                        src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTZvcRQTKVSYvcbKzck7XWGSwjmbtiYgkoAED7ANkUiX0QDTez-B-90x2bwp-QrwvEU7-s&usqp=CAU"
                        alt="avatar pic"
                    />

                </div>
                <h2 className="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900" style={{
                    color: "#5a03e7"
                }}>
                    Sign in to your account
                </h2>

                <div className="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
                    <form className="space-y-6">
                        <div>
                            <label htmlFor="email" className="block text-sm font-medium leading-6 text-gray-900" style={{
                                color: "whitesmoke"
                            }}>
                                Email address
                            </label>
                            <div className="mt-2">
                                <input
                                    id="email"
                                    name="email"
                                    type="email"
                                    autoComplete="email"
                                    value={userSignIn.email}
                                    onChange={handleInputChange}
                                    required
                                    className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                                />
                            </div>
                        </div>

                        <div>
                            <div className="flex items-center justify-between">
                                <label htmlFor="password" className="block text-sm font-medium leading-6 text-gray-900" style={{
                                    color: "whitesmoke"
                                }}>
                                    Password
                                </label>
                                <div className="text-sm">
                                    <a href="#" className="font-semibold text-indigo-600 hover:text-indigo-500">
                                        Forgot password?
                                    </a>
                                </div>
                            </div>
                            <div className="mt-2">
                                <input
                                    id="password"
                                    name="password"
                                    type="password"
                                    value={userSignIn.password}
                                    onChange={handleInputChange}
                                    autoComplete="current-password"
                                    required
                                    className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                                />
                            </div>
                        </div>

                        <div>
                            <button
                                type="submit"
                                onClick={handleSignIn}
                                className="flex w-full justify-center rounded-md bg-indigo-900 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
                            >
                                Sign in
                            </button>
                        </div>
                    </form>

                    <p className="mt-10 text-center text-sm text-gray-500 gradient">
                        Not a member?{' '}
                        <span className="font-semibold leading-6 text-indigo-600 hover:text-indigo-500">
                            <Link to={"/signup"}>
                            Join Now

                            </Link>
                        </span>

                    </p>
                </div>
            </div>
        </>
    );
}
export default SignIn;
