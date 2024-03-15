import React from "react";
import "./home.css";
import {images} from "../misc/images";
import Summary from "../misc/Summary";

const Home = () => {

    return (
        <div id="home-page" >
            <header className="header">
                <h1 id="home">Stacks Of Laughs</h1>
            </header>

            <h3 id="slogan" className="text-2xl text-fuchsia-600">Laughing Together, Connecting Forever</h3>
            <div className="image-container">
                {images.map(image => {
                    return(
                    <div className="image" key={image}>
                        <img src={image} alt="people"/>
                    </div>
                    )
            })}
            </div>

            <div className="parallax-container">
                <div className="parallax-bg"></div>
                <div className="content gradient">
                    <h1 style={{
                        fontSize: "28px",
                        fontWeight: "bold",
                        marginBottom: "20px",
                        color: "#00ffcc"
                    }}>About Stacks Of Laughs</h1>
                    <Summary/>
                </div>
            </div>

            <footer className="footer">
                <div className="footer-inner">
                    <ul>
                        <li>Contact Stacks of Laughs</li>
                        <li>Privacy & cookies</li>
                        <li>Terms of use</li>
                        <li>Trademarks</li>
                        <li>About our CEO</li>
                        {/*<li><a href="#">&copy; Stacks Of Laughs 2023</a></li>*/}
                    </ul>
                </div>
            </footer>
        </div>
    );
};

export default Home;

