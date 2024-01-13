import React from 'react';
import "./modal.css"

const Modal = ({ isOpen, children }) => {
    return isOpen ? (
        <div className="modal-overlay">
            <div className="modal">
                {children}
            </div>
        </div>
    ) : null;
};

export default Modal;
