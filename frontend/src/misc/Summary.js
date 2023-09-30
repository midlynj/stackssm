import React from "react";
import "./summary.css";

const Summary = () => {
    return (
        <div>
            <div className="about-section text-center">
                <p className="about-description">
                    Welcome to Stacks of Laughs, where humor knows no bounds! We're on a mission to spread joy, laughter, and the sheer delight of connecting through humor. Laughter truly is the universal language, and we've built a vibrant online community that celebrates it in all its forms.
                </p>
            </div>

            <div className="summary-section">
                <h3 className="summary-title">Summary</h3>
                <p className="summary-content">
                    At Stacks of Laughs, we believe that laughter is the best way to bring people together. Our platform is your one-stop destination for all things funny, from hilarious memes to side-splitting videos and everything in between. Whether you're a seasoned comedian or just someone who enjoys a good laugh, you'll find a home here.
                </p>
            </div>
        </div>
    );
};

export default Summary;
