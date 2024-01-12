import React, { useState, useRef, useLayoutEffect } from 'react';
import './CreateGroupPopup.css';

const CreateGroupPopup = ({ onSave, buttonRef }) => {
  const [groupName, setGroupName] = useState('');
  const [popupPosition, setPopupPosition] = useState({ top: 0, left: 0 });

  useLayoutEffect(() => {
    if (buttonRef.current) {
      const buttonRect = buttonRef.current.getBoundingClientRect();
      const newPopupPosition = {

        position : 'fixed',
        top: buttonRect.bottom ,
        left: buttonRect.left,
      };
      setPopupPosition(newPopupPosition);
    }
  }, [buttonRef]);

  const handleSave = () => {
    onSave(groupName);
    // Reset the group name after saving 
    setGroupName('');
  };

  return (
    <div className="create-group-popup" style={{ position: 'absolute', ...popupPosition, zIndex: 1000 }}>
      <h3>Enter a name for the new group:</h3>
      <input
        type="text"
        value={groupName}
        onChange={(e) => setGroupName(e.target.value)}
      />
      <button onClick={handleSave}>Save Group</button>
    </div>
  );
};

export default CreateGroupPopup;
