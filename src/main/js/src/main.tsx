import React from "react";
import ReactDOM from "react-dom/client";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import { createApiClient } from "./apiClient/client";
import { DevTools } from "jotai-devtools";

import "./index.css";
import Home from "./pages/Home";
import FourOFour from "./pages/404";
import { RealHome } from "./pages/RealHome";
import { Prefab } from "./pages/Prefab";
import { PrefabCreation } from "./components/prefab-creation/PrefabCreation";
import { FormAnswers } from "./pages/FormAnswers";

export const apiclient = createApiClient((method, url, params) => {
	if (method === "get" || method === "head") {
		return fetch(url, {
			method,
			headers: {
				"Content-Type": "application/json",
			},
		});
	}

	return fetch(url, {
		method,
		headers: {
			"Content-Type": "application/json",
		},
		body: JSON.stringify(params),
	});
}, "http://localhost:8080");
const router = createBrowserRouter([
	{
		path: "/",
		element: <RealHome />,
		errorElement: <FourOFour />,
	},
	{
		path: "/question",
		element: <Home />,
		errorElement: <FourOFour />,
	},
	{
		path: "/prefab-creation",
		element: <PrefabCreation />,
		errorElement: <FourOFour />,
	},
	{
		path: "/form/:formName",
		element: <Prefab />,
		errorElement: <FourOFour />,
	},
	{
		path: "/form/:formName/answers",
		element: <FormAnswers />,
		errorElement: <FourOFour />,
	},
]);

const root = document.getElementById("root");

if (!root) {
	throw new Error("Root element not found");
}

ReactDOM.createRoot(root).render(
	<React.StrictMode>
		<DevTools />
		<RouterProvider router={router} />
	</React.StrictMode>,
);
