import { useState, useEffect } from "react";
import { useParams, Link } from "react-router-dom";
import { Schemas } from "../apiClient/client";
import { apiclient } from "../main";

export const FormAnswers = () => {
	const { formName } = useParams();
	const [answers, setAnswers] = useState<Required<Schemas.FormData>[]>([]);
	const [inited, setInited] = useState(false);

	useEffect(() => {
		apiclient.get(`/api/formdata/${formName}`).then((res: Required<Schemas.FormData>[]) => {
			setAnswers(res);
			setInited(true);
		});
	}, []);

	if (!inited) {
		return <div>Loading...</div>;
	}

	return <div className="flex flex-col"></div>;
};
