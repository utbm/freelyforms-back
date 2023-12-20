// FormPopup.js
import React, { useEffect, useState } from 'react';
import './FormPopup.css';

const FormPopup = ({ groups, onSelect, triggerButtonRef }) => {
  const [popupStyle, setPopupStyle] = useState({}); 

  useEffect(() => {
    const updatePosition = () => {
      const buttonRect = triggerButtonRef.current ? triggerButtonRef.current.getBoundingClientRect() : null;

      if (!buttonRect) {
        return;
      }

      const newPopupStyle = {
        position: 'absolute',
        top: buttonRect.bottom + 40,
        left: buttonRect.left + 250,
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

  return (
    <div className="form-popup" style={popupStyle}>
      <h2>Select a Group</h2>

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
