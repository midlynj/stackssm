import './App.css';
import React, {useCallback} from "react";
import {BrowserRouter as Router, Routes, Route, Link} from "react-router-dom";
import SignIn from "./component/SignIn";
import SignUp from "./component/SignUp";
import Error from "./component/Error";
import Home from "./component/Home";
import HomeFeed from "./component/HomeFeed";
import Profile from "./component/Profile";
import {useSelector, useDispatch} from "react-redux";
import ProtectedRoute from "./misc/ProtectedRoute";
import FriendProfile from "./component/FriendProfile";
import {signOut} from "./redux/auth";

function App() {
  const isAuthenticated = useSelector((state) => state.persistedReducer.isLoggedIn);

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
              <Route path="/signin" element={<SignIn/> } />
              <Route path="/signup" element={<SignUp/>} />
              <Route element={<ProtectedRoute />}>
                <Route path="/profile" element={<Profile/>} />
              </Route>

              <Route element={<ProtectedRoute />}>
                <Route path="/homefeed" element={<HomeFeed/>} />
              </Route>
              {/*<Route path="/modal" element={<ModalT/>} />*/}
              <Route path="/profile/:id" element={<FriendProfile/>}/>
              <Route path="*" element={<Error/>} />


            </Routes>

          </div>

        </div>


      </Router>
    </div>
  );
}

export default App;
