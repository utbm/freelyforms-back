// Header.js
import React from 'react';
import { Link } from 'react-router-dom';
import './Header.css';
const Header = () => {
  return (
    <header>
      <nav>
        <ul>
          <li>
            <Link to="/">Menu</Link>
          </li>
          <li>
            <Link to="/home">Map</Link>
          </li>
        </ul>
      </nav>
    </header>
  );
};

export default Header;
