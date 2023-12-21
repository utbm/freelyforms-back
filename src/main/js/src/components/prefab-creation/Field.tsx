import { FC } from "react";
import { BasicComponentInfo } from "./BasicComponentInfo";
import { Rule } from "./Rule";
import { FieldType, fieldsAtom } from "./store";
import { useSetAtom } from "jotai";
import { BsXLg } from "react-icons/bs";

type FieldProps = {
	groupIndex: number;
	fieldIndex: number;
};

const removeField = (fields: FieldType[], fieldIndex: number) => {
	return fields.filter((_, index) => index !== fieldIndex);
};

export const Field: FC<FieldProps> = (props) => {
	const setFields = useSetAtom(fieldsAtom);

	return (
		<div className="m-1 border-solid border-1">
			<div className="flex flex-row gap-5">
				<BasicComponentInfo
					type="field"
					labelPlaceholder="Display name"
					captionPlaceholder="Type placeholder text"
					index={props.fieldIndex}
					group={props.fieldIndex}
				/>
				<button
					className="btn btn-sm btn-error"
					onClick={() => {
						setFields((fields) => removeField(fields, props.fieldIndex));
					}}
				>
					<BsXLg />
				</button>
			</div>
			<Rule fieldIndex={props.fieldIndex} onChange={console.log} />
		</div>
	);
};
