import React from 'react';
import "./loading.css"

const Loading = () => {
    return (
        <div className="cyberpunk-loading-container">
            <div className="cyberpunk-loading">
                <div className="loading-bar"></div>
                <div className="loading-bar"></div>
                <div className="loading-bar"></div>
                <div className="loading-bar"></div>
                <div className="loading-bar"></div>
            </div>
            <div className="loading-text">Loading...</div>
        </div>
    );
};
export default Loading;
