import React, { useEffect, useState } from "react";
import { apiclient } from "../main";
import { Schemas } from "../apiClient/client";
import { Link } from "react-router-dom";

export const Home = () => {
	const [prefabs, setPrefabs] = useState<Schemas.Prefab[]>([]);

	useEffect(() => {
		apiclient
			.get("/api/prefabs")
			.then((res) => res.json())
			.then((res) => {
				setPrefabs(res as Schemas.Prefab[]);
			});
	}, []);

	return (
		<div className="container mx-auto p-4">
			<Link to="/prefab-creation" className="btn btn-primary mb-4">
				Créer un nouveau formulaire
			</Link>
			<h1 className="text-2xl font-bold mb-4">Liste des Formulaires</h1>
			<ul className="list-disc">
				{prefabs.map((prefab, index) => (
					<li key={index} className="mb-2">
						<a href={`/form/${prefab.name}`} className="text-blue-500 hover:text-blue-700">
							{prefab.caption}
						</a>
						<p className="text-sm text-gray-600">{prefab.label}</p>
					</li>
				))}
			</ul>
		</div>
	);
};
