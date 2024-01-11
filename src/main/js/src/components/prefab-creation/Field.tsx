import { FC } from "react";
import { BasicComponentInfo } from "./BasicComponentInfo";
import { Rule, Selector } from "./Rule";
import { FieldType, fieldsAtom } from "./store";
import { useAtom } from "jotai";
import { BsXLg } from "react-icons/bs";
import { typeRulesIcons } from "../../shared/TypeRules";

type FieldProps = {
	groupIndex: number;
	fieldIndex: number;
};

const removeField = (fields: FieldType[], fieldIndex: number) => {
	const field = fields.filter((field) => field.index !== fieldIndex);

	return field;
};

export const Field: FC<FieldProps> = (props) => {
	const [fields, setFields] = useAtom(fieldsAtom);

	const field = fields.find((field) => field.index === props.fieldIndex);
	if (!field) {
		return null;
	}

	const handleSelectorChange = (options: string[]) => {
		setFields((fields) => {
			const newFields = [...fields];

			const fieldIndex = newFields.findIndex((field) => field.index === props.fieldIndex);

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

	const typeRulesIcon = typeRulesIcons.find(
		(item) => item.name === field.rules.fieldType,
	) as (typeof typeRulesIcons)[number];

	return (
		<div className="m-1 p-2">
			<div className="flex flex-row gap-5">
				<div className="flex flex-col gap-4">
					<BasicComponentInfo
						type="field"
						labelPlaceholder="Display name"
						captionPlaceholder={field.rules.fieldType === "SELECTOR" ? "" : "Field placeholder"}
						index={props.fieldIndex}
						groupIndex={props.groupIndex}
						tooltipChildren={typeRulesIcon.label}
						defaultCaptionValue={field.caption}
						defaultLabelValue={field.label}
					>
						<typeRulesIcon.icon size={20} />
					</BasicComponentInfo>
					{field.rules.fieldType === "SELECTOR" && (
						<Selector defaultOptions={field.rules.selectorValues} onChange={handleSelectorChange} />
					)}
				</div>
				<Rule field={field} onChange={() => {}} />

				<button
					className="btn btn-sm btn-error"
					onClick={() => {
						setFields((fields) =>
							removeField(
								fields,
								fields.findIndex((field) => field.index === props.fieldIndex),
							),
						);
					}}
				>
					<BsXLg />
				</button>
			</div>
			<div className="divider"></div>
		</div>
	);
};
