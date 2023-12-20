// Menu.js
import React, { useState, useRef } from 'react';
import { Link, useNavigate } from 'react-router-dom';  // Import useNavigate
import './Menu.css';
import FormPopup from './FormPopup';

const Menu = () => {
  const [isPopupOpen, setPopupOpen] = useState(false);
  const [groups, setGroups] = useState([]);
  const buttonRef = useRef(null);
  const navigate = useNavigate();  // Use useNavigate instead of useHistory


  const loadForm = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/formdata/Group/All?formDataID=5f9a514b49d94064dcd66337');
      const data = await response.json();
      setGroups(data);
      setPopupOpen(true);
    } catch (error) {
      console.error('Error loading form data:', error);
    }
  };

  const handleGroupSelection = (groupName) => {
    setPopupOpen(false);
    // You can perform additional actions with the selected group name if needed
    // Use Link or navigate to '/home'
    navigate('/home'); // Use navigate instead of history.push
  };
  return (
    <div className="menu-container">
      <strong><h1 className="menu-title">FreelyForms</h1></strong> 
      <div> 
      <Link to="/home" className="menu-button">
        New Form
      </Link>
      <button ref={buttonRef} onClick={loadForm} className="menu-button">
          Load a Form
        </button>
      </div>
      <br />
      <img src="./src/assets/img_map.PNG" alt="Map" className="menu-map" />
      <div className='menu-footer'>
        DA52 Project - A23 <br/> Matthieu Delisle - Wael Mohana <br/> Sulyvan Durand - Carlo Boscarol - Walid El Faiz
      </div>
      <br />
      <Link to="/exit" className="menu-small-button">
        Exit
      </Link>
      {isPopupOpen && <FormPopup groups={groups} onSelect={handleGroupSelection} triggerButtonRef={buttonRef} />}

    </div>
  );
};

export default Menu;
