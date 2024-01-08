// FormPopup.js
import React, { useEffect, useState, useRef } from 'react';
import './FormPopup.css';

const FormPopup = ({ groups, onSelect, triggerButtonRef, onClose, isDeletePopup }) => {
  const [popupStyle, setPopupStyle] = useState({});
  const popupRef = useRef(null);

  useEffect(() => {
    const updatePosition = () => {
      const buttonRect = triggerButtonRef.current ? triggerButtonRef.current.getBoundingClientRect() : null;

      if (!buttonRect) {
        return;
      }

      const newPopupStyle = {
        position: 'absolute',
        top: buttonRect.bottom + 40,
        left: buttonRect.left + 390,
        zIndex: 1000,
      };

      setPopupStyle(newPopupStyle);
    };

    updatePosition(); // Call it once on mount
    window.addEventListener('resize', updatePosition); // Update on window resize

    return () => {
      window.removeEventListener('resize', updatePosition); // Cleanup the event listener
    };
  }, [triggerButtonRef]);

  const handleOutsideClick = (event) => {
    if (popupRef.current && !popupRef.current.contains(event.target)) {
      onClose();
    }
  };

  useEffect(() => {
    const handleMouseDown = (event) => {
      handleOutsideClick(event);
    };

    document.addEventListener('mousedown', handleMouseDown);

    return () => {
      document.removeEventListener('mousedown', handleMouseDown);
    };
  }, [onClose]);

  return (
    <div ref={popupRef} className="form-popup" style={popupStyle}>
      <h2>{isDeletePopup ? 'Delete a Group' : 'Select a Group'}</h2>

      <div className="group-buttons">
        {groups.map((group) => (
          <button
            key={group.name}
            onClick={() => onSelect(group.name)}
            className="group-button"
          >
            {group.name}
          </button>
        ))}
      </div>
    </div>
  );
};

export default FormPopup;
