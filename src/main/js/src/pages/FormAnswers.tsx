import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { Schemas } from "../apiClient/client";
import { apiclient } from "../main";

export const FormAnswers = () => {
	const { formName } = useParams();
	const [formDatas, setAnswers] = useState<Required<Schemas.FormData[]>>([]);
	const [prefabs, setPrefabs] = useState(null);
	const [inited, setInited] = useState(false);

	useEffect(() => {
		// Fetch Form Data
		apiclient
			.get(`/api/formdata/${formName}`)
			.then((res) => res.json())
			.then((formDataRes: Required<Schemas.FormData>[]) => {
				setAnswers(formDataRes);
				// Fetch Prefab Data
				return apiclient.get(`/api/prefabs/${formName}`);
			})
			.then((res) => res.json())
			.then((prefabRes) => {
				setPrefabs(prefabRes);
				setInited(true);
			});
	}, []);

	const formatData = (fieldValue, fieldType) => {
		if (fieldType === "DATE") {
			return new Date(fieldValue).toLocaleDateString();
		}
		// Add other type formats here
		return fieldValue;
	};

	const findFieldInfo = (fieldName) => {
		for (let group of prefabs.groups) {
			for (let field of group.fields) {
				if (field.name === fieldName) {
					return { label: field.label, fieldType: field.rules.fieldType };
				}
			}
		}
		return { label: fieldName, fieldType: "STRING" }; // Default label and type
	};

	if (!inited) {
		return <div>Loading...</div>;
	}

	return (
		<div className="flex flex-row gap-4">
			{formDatas.map((formData, formDataIndex) => (
				<div key={formDataIndex} className="flex flex-col">
					<div className="mb-2">Answer date: {new Date(formData._id.date).toLocaleDateString()}</div>
					{formData.groups?.map((group, groupIndex) => (
						<div key={groupIndex} className="flex flex-col border-2 border-gray-300 rounded-lg p-4 mb-4">
							<div className="text-2xl font-bold mb-2">Group: {group.name}</div>
							{group.fields?.map((field, fieldIndex) => {
								const { label, fieldType } = findFieldInfo(field.name);
								return (
									<div key={fieldIndex} className="flex flex-col mb-2">
										<div className="mb-1">
											{label}: {formatData(field.value, fieldType)}
										</div>
									</div>
								);
							})}
						</div>
					))}
				</div>
			))}
		</div>
	);
};

export default FormAnswers;
