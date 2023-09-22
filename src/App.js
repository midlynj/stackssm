import './App.css';
import React from "react";
import {BrowserRouter as Router, Routes, Route, Link} from "react-router-dom";
import SignIn from "./component/SignIn";

function App() {
  return (
    <div className="App">
      <Router>
        <div>
          <nav className="text-amber-300 space-x-28 p-4 bg-black">
            <Link className="hover:text-violet-400" to={"/"}>
              Home
            </Link>

            <Link className="hover:text-violet-400" to={"/signin"}>
              Sign In
            </Link>

            <Link className="hover:text-violet-400" to={"/"}>
              Sign Up
            </Link>

          </nav>

          <div>
            <Routes>
              {/*<Route path="/" element={Home/>} />*/}
              <Route path="/signin" element={<SignIn/> } />
              {/*<Route path="/" element={Home/>} />*/}
              {/*<Route path="/" element={Home/>} />*/}

            </Routes>

          </div>

        </div>


      </Router>
    </div>
  );
}

export default App;
