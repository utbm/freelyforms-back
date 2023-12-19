// Menu.js
import React from 'react';
import { Link } from 'react-router-dom';
import mapImage from './map-image.jpg'; // Replace with your map image path
import './Menu.css';
const Menu = () => {
  return (
    <div className="menu-container">
      <h1 className="menu-title">FreelyForms</h1>
      <Link to="/home" className="menu-button">
        New Form
      </Link>
      <Link to="/home" className="menu-button">
        Load a Form
      </Link>
      <br />
      <img src="./src/assets/img_map.PNG" alt="Map" className="menu-map" />
      <Link to="/exit" className="menu-small-button">
        Exit
      </Link>
    </div>
  );
};

export default Menu;
