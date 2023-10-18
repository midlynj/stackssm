import React from 'react';
import "./modal.css"

const Modal = ({ isOpen, onClose, children }) => {
    return isOpen ? (
        <div className="modal-overlay">
            <div className="modal">
                {/*<button className="close-button" onClick={onClose}>*/}
                {/*    Close*/}
                {/*</button>*/}
                {children}
            </div>
        </div>
    ) : null;
};

export default Modal;
