import React, {useEffect} from 'react';
import './home.css';
import {images} from "../images";

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
                        <img src={image} alt=""/>

            </div>
                    )
            })}
            </div>

            <div className="parallax-container">
                <div className="parallax-bg"></div>
                <div className="content">
                    <h1>Welcome to Parallax Scrolling</h1>
                    <p>Scroll down to see the effect</p>
                </div>
            </div>

            <footer className="footer">
                <div className="footer-inner">
                    <ul>
                        <li><a href="#">Sitemap</a></li>
                        <li><a href="#">Contact Stacks of Laughs</a></li>
                        <li><a href="#">Privacy & cookies</a></li>
                        <li><a href="#">Terms of use</a></li>
                        <li><a href="#">Trademarks</a></li>
                        <li><a href="#">About our CEO</a></li>
                        <li><a href="#">&copy; Stacks Of Laughs 2023</a></li>
                    </ul>
                </div>
            </footer>
        </div>
    );
};

export default Home;

