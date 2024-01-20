import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { apiclient } from "../main";
import { useAtom, useSetAtom } from "jotai";
import { prefabAtom, groupsAtom, fieldsAtom, FieldType } from "../components/prefab-creation/store";
import { PrefabCreation } from "../components/prefab-creation/PrefabCreation";
import { Schemas } from "../apiClient/client";

export const Prefab = () => {
	const { formName } = useParams();

	const [prefab, setPrefab] = useAtom(prefabAtom);
	const setGroups = useSetAtom(groupsAtom);
	const setFields = useSetAtom(fieldsAtom);
	const [isLoading, setIsLoading] = useState(true);

	useEffect(() => {
		apiclient
			.get(`/api/prefabs/${formName}`, null)
			.then((res) => {
				if (res.status !== 200) {
					setIsLoading(false);
				}
				return res;
			})
			.then((res) => res.json())
			.then((res: Required<Schemas.Prefab>) => {
				setPrefab(res);
				setGroups((groups) => {
					const newGroups = [...groups];
					res.groups.forEach((group, index) => {
						newGroups[index] = {
							...newGroups[index],
							...group,
							index,
						};
					});
					return newGroups;
				});

				const fields: FieldType[] = [];
				res.groups.forEach((group, groupIndex) => {
					group.fields.forEach((field, index) => {
						fields.push({
							...field,
							index: fields.length,
							groupIndex: groupIndex,
						});
					});
				});

				setFields(fields);
				setIsLoading(false);
			});
	}, []);

	if (isLoading) {
		return (
			<div data-theme="light" className="w-screen h-screen p-2 lg:p-6 overflow-y-auto">
				Loading...
			</div>
		);
	}

	if (!prefab.name) {
		return (
			<div data-theme="light" className="w-screen h-screen p-2 lg:p-6 overflow-y-auto">
				Form not found
			</div>
		);
	}

	return <PrefabCreation editionMode />;
};
