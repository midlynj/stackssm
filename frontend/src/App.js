import "./App.css";
import React, {useCallback} from "react";
import {BrowserRouter as Router, Routes, Route, Link} from "react-router-dom";
import SignIn from "./component/SignIn";
import SignUp from "./component/SignUp";
import Home from "./component/Home";
import HomeFeed from "./component/HomeFeed";
import Profile from "./component/Profile";
import {useSelector, useDispatch} from "react-redux";
import ProtectedRoute from "./misc/ProtectedRoute";
import FriendProfile from "./component/FriendProfile";
import {signOut} from "./redux/auth";
import AdminDashBoard from "./component/AdminDashBoard";
import UnAuthorized from "./component/UnAuthorized";
import AdminPortal from "./misc/AdminPortal";
import ProtectedRoute2 from "./misc/ProtectedRoute2";
import NotFound from "./component/NotFound";

function App() {
  const isAuthenticated = useSelector((state) => state.persistedReducer.isLoggedIn);
  const userRole = useSelector((state) => state.persistedReducer.role);
  const isAdmin = userRole.some(user => user.name === "ADMIN");
  const dispatch = useDispatch();

  const logOut = useCallback(() => {
    dispatch(signOut());
  }, [dispatch]);


  return (
    <div className="App">
      <Router>
        <div>
          <nav className="text-amber-300 space-x-28 p-4 bg-black" style={{
            textAlign: "center"
          }}>
            <Link className="hover:text-violet-400" to={"/"}>
              Home
            </Link>

            {isAdmin ?

                <Link className="hover:text-violet-400" to={"/admin"}>
                  Admin
                </Link>
                : null
            }

            {isAuthenticated ?
            (  <>

                  <Link className="hover:text-violet-400" to={"/homefeed"}>
                    Home Feed
                  </Link>

                  <Link className="hover:text-violet-400" to={"/profile"}>
                    Profile
                  </Link>

                  <a className="hover:text-violet-400" href="/signin" onClick={logOut}>
                    SignOut
                  </a>
            </>
            ) :
                (
                    <>

                      <Link className="hover:text-violet-400" to={"/signin"}>
                        Sign In
                      </Link>

                      <Link className="hover:text-violet-400" to={"/signup"}>
                        Sign Up
                      </Link>
                    </>

                ) }


          </nav>

          <div>
            <Routes>
              <Route path="/" element={<Home/>} />

              <Route element={<ProtectedRoute />}>
                <Route path="/profile" element={<Profile/>} />
              </Route>

              <Route element={<ProtectedRoute />}>
                <Route path="/homefeed" element={<HomeFeed/>} />
              </Route>

              <Route element={<ProtectedRoute />}>
                <Route path="/profile/:id" element={<FriendProfile/>}/>
              </Route>

              <Route element={<ProtectedRoute2 />}>
                <Route path="/signin" element={<SignIn/>}/>
              </Route>

              <Route element={<ProtectedRoute2 />}>
                <Route path="/signup" element={<SignUp/>}/>
              </Route>

              <Route path="/unauth" element={<UnAuthorized/>} />

              <Route element={<AdminPortal />}>
                <Route path="/admin" element={<AdminDashBoard/>}/>
              </Route>

              <Route path="*" element={<NotFound/>} />


            </Routes>

          </div>

        </div>


      </Router>
    </div>
  );
}

export default App;
