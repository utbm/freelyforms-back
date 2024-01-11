// Menu.js
import React, { useState, useRef } from 'react';
import { Link, useNavigate } from 'react-router-dom';  
import './Menu.css';
import FormPopup from './FormPopup';

const Menu = () => {
  const [isLoadPopupOpen, setLoadPopupOpen] = useState(false);
  const [isDeletePopupOpen, setDeletePopupOpen] = useState(false);
  const [groups, setGroups] = useState([]);
  const buttonRef = useRef(null);
  const navigate = useNavigate();

  const fetchGroups = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/formdata/Group/All?formDataID=5f9a514b49d94064dcd66337');
      const data = await response.json();
      setGroups(data);
    } catch (error) {
      console.error('Error fetching groups:', error);
    }
  };

  const loadForm = async () => {
    await fetchGroups(); // Use the common fetchGroups function
    setLoadPopupOpen(true);
  };

  const deleteForm = async () => {
    await fetchGroups(); // Use the common fetchGroups function
    setDeletePopupOpen(true);
  };

  const handleGroupSelection = (groupName) => {
    setLoadPopupOpen(false);
    navigate(`/home?groupName=${encodeURIComponent(groupName)}`);
  };

  const handleDeleteGroup = async (groupName) => {
    try {
      // Make API call to delete the selected group
      const formDataID = '5f9a514b49d94064dcd66337';  // Replace with actual formDataID
      const deleteResponse = await fetch('http://localhost:8080/api/formdata/Group/delete', {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          formDataID,
          groupName,
        }),
      });

      if (deleteResponse.ok) {
        console.log(`Group "${groupName}" deleted successfully.`);
      } else {
        console.error('Failed to delete the group.');
      }
    } catch (error) {
      console.error('Error deleting group:', error);
    }

    setDeletePopupOpen(false);
  };

  const handleClosePopup = () => {
    setLoadPopupOpen(false);
    setDeletePopupOpen(false);
  };

  return (
    <div className="menu-container">
      <strong><h1 className="menu-title">FreelyForms</h1></strong> 
      
      <div className='menu-button-all'> 
        <Link to="/home" className="menu-button">
          New Form
        </Link>
        <button ref={buttonRef} onClick={loadForm} className="menu-button">
          Load a Form
        </button>
        <button onClick={deleteForm} className="menu-button">
          Delete Form
        </button>
      </div>
      <br />
      <img src="./src/assets/img_map.PNG" alt="Map" className="menu-map" />
      <div className='menu-footer'>
        DA50 Project - A23 <br/> Matthieu Delisle - Wael Mohana <br/> Sulyvan Durand - Carlo Boscarol - Walid El Faiz
      </div>
      <br />
      <Link to="/exit" className="menu-small-button">
        Exit
      </Link>
      {isLoadPopupOpen && (
        <FormPopup groups={groups} onSelect={handleGroupSelection} onClose={handleClosePopup} triggerButtonRef={buttonRef} />
      )}
      {isDeletePopupOpen && (
        <FormPopup groups={groups} onSelect={handleDeleteGroup} onClose={handleClosePopup} triggerButtonRef={buttonRef} isDeletePopup />
      )}
    </div>
  );
};

export default Menu;
