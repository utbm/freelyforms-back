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
	groupIndex: number;
	fieldIndex: number;
};

const removeField = (fields: FieldType[], fieldUUID: string) => {
	const field = fields.filter((field) => field.uuid !== fieldUUID);

	return field;
};

export const Field: FC<FieldProps> = (props) => {
	const [fields, setFields] = useAtom(fieldsAtom);

	const field = fields[props.fieldIndex];

	return (
		<div className="m-1">
			<div className="flex flex-row gap-5">
				<div className="flex flex-col gap-4">
					<BasicComponentInfo
						type="field"
						labelPlaceholder="Display name"
						captionPlaceholder={field.rules.fieldType === "SELECTOR" ? "" : "Type placeholder text"}
						index={props.fieldIndex}
						group={props.fieldIndex}
					>
						{IconMapToType[field.rules.fieldType]}
					</BasicComponentInfo>
					{/* @TODO: Add to state */}
					{field.rules.fieldType === "SELECTOR" && <Selector onChange={console.log} />}
				</div>
				<Rule field={field} onChange={console.log} />

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
