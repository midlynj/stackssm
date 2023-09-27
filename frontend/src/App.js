import './App.css';
import React from "react";
import {BrowserRouter as Router, Routes, Route, Link} from "react-router-dom";
import SignIn from "./component/SignIn";
import SignUp from "./component/SignUp";
import Error from "./component/Error";
import Home from "./component/Home";
import HomeFeed from "./component/HomeFeed";
import Profile from "./component/Profile";

function App() {
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

            <Link className="hover:text-violet-400" to={"/signin"}>
              Sign In
            </Link>

            <Link className="hover:text-violet-400" to={"/signup"}>
              Sign Up
            </Link>

            <Link className="hover:text-violet-400" to={"/profile"}>
              Profile
            </Link>

            <Link className="hover:text-violet-400" to={"/homefeed"}>
              Home Feed
            </Link>

          </nav>

          <div>
            <Routes>
              <Route path="/" element={<Home/>} />
              <Route path="/signin" element={<SignIn/> } />
              <Route path="/signup" element={<SignUp/>} />
              <Route path="/profile" element={<Profile/>} />
              <Route path="/homefeed" element={<HomeFeed/>} />

              <Route path="*" element={<Error/>} />


            </Routes>

          </div>

        </div>


      </Router>
    </div>
  );
}

export default App;
