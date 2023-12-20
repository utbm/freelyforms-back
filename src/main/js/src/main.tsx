import React from "react";
import ReactDOM from "react-dom/client";
import { Route, BrowserRouter as Router, Routes} from 'react-router-dom';
import Home from "./pages/index";
import FourOFour from "./pages/error/404";
import Menu from "./pages/Menu/Menu";
import "./index.css";
import Header from "./pages/Header/Header";


const root = document.getElementById("root");

if (!root) {
	throw new Error("Root element not found");
}

ReactDOM.createRoot(root).render(
  <React.StrictMode>
    <Router>
      <Header />
      <Routes>
        <Route path="/" element={<Menu />} />
        <Route path="/home" element={<Home />} />
        <Route path="*" element={<FourOFour />} />
      </Routes>
    </Router>
  </React.StrictMode>,
);