import { FC } from "react";
import { BasicComponentInfo } from "./BasicComponentInfo";
import { Rule, Selector } from "./Rule";
import { FieldType, fieldsAtom } from "./store";
import { useAtom } from "jotai";
import { BsXLg } from "react-icons/bs";
import { PossibleTypes } from "../../apiClient/client";

const IconMapToType: Record<PossibleTypes, string> = {
	STRING: "🔤",
	FLOAT: "🔢",
	INTEGER: "🔢",
	DATE: "📅",
	SELECTOR: "🔘",
	BOOLEAN: "✅",
	DATETIME: "📅",
};

type FieldProps = {
	groupUUID: string;
	fieldUUID: string;
};

const removeField = (fields: FieldType[], fieldUUID: string) => {
	const field = fields.filter((field) => field.uuid !== fieldUUID);

	return field;
};

export const Field: FC<FieldProps> = (props) => {
	const [fields, setFields] = useAtom(fieldsAtom);

	const field = fields.find((field) => field.uuid === props.fieldUUID);
	if (!field) {
		return null;
	}

	const handleSelectorChange = (options: string[]) => {
		setFields((fields) => {
			const newFields = [...fields];

			const fieldIndex = newFields.findIndex((field) => field.uuid === props.fieldUUID);

			newFields[fieldIndex] = {
				...newFields[fieldIndex],
				rules: {
					...newFields[fieldIndex].rules,
					selectorValues: options,
				},
			};

			return newFields;
		});
	};

	return (
		<div className="m-1 p-2">
			<div className="flex flex-row gap-5">
				<div className="flex flex-col gap-4">
					<BasicComponentInfo
						type="field"
						labelPlaceholder="Display name"
						captionPlaceholder={field.rules.fieldType === "SELECTOR" ? "" : "Type placeholder text"}
						uuid={props.fieldUUID}
						groupUUID={props.groupUUID}
					>
						{IconMapToType[field.rules.fieldType]}
					</BasicComponentInfo>
					{field.rules.fieldType === "SELECTOR" && <Selector onChange={handleSelectorChange} />}
				</div>
				<Rule field={field} onChange={() => {}} />

				<button
					className="btn btn-sm btn-error"
					onClick={() => {
						setFields((fields) => removeField(fields, field.uuid));
					}}
				>
					<BsXLg />
				</button>
			</div>
			<div className="divider"></div>
		</div>
	);
};
