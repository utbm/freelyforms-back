import { FC, useState } from "react";
import { PossibleTypes, PossibleTypeRules, Schemas } from "../../apiClient/client";
import { Input } from "./Input";
import { fieldsAtom } from "./store";
import { useSetAtom } from "jotai";

type TypeRulesProps = {
	typeRules: PossibleTypeRules[];
	fieldType: PossibleTypes;
	fieldUUID: string;
};

const translationsTypeRules: Record<PossibleTypeRules, string> = {
	EmailRegexMatch: "Is a email input ?",
	MaximumRule: "Maximum input length",
	MinimumRule: "Minimum input length",
	AlternativeDisplay: "Alternative Display",
	RegexMatch: "The input must match a regex",
	SelectDataSet: "Select Data Set",
};

export const TypeRules: FC<TypeRulesProps> = ({ typeRules, fieldType, fieldUUID }) => {
	const setFieldsAtom = useSetAtom(fieldsAtom);

	const handleChange = ({
		typeRuleName,
		toggled,
		value,
	}: {
		typeRuleName: PossibleTypeRules;
		toggled: boolean;
		value: string;
	}) => {
		setFieldsAtom((fields) => {
			const newFields = [...fields];

			const fieldIndex = newFields.findIndex((field) => field.uuid === fieldUUID);
			const typeRules = newFields[fieldIndex].rules.typeRules;

			const index = typeRules.findIndex((typeRule) => typeRule.name === typeRuleName);
			if (index !== -1) {
				typeRules.splice(index, 1);
			}

			// I don't want to add the type rule if there is no value
			// except for EmailRegexMatch because the value is managed by the backend
			if (toggled && (value || typeRuleName === "EmailRegexMatch")) {
				typeRules.push({
					name: typeRuleName,
					value,
					associatedTypes: [],
				});
			}

			return newFields;
		});
	};

	return (
		<div className="flex flex-col">
			<div className="dropdown">
				<div tabIndex={0} role="button" className="btn btn-primary btn-success btn-sm">
					Rules
				</div>
				<div
					tabIndex={0}
					className="dropdown-content w-max flex flex-col gap-2 z-[1] menu shadow bg-base-100 rounded-box w-52 p-3"
				>
					{typeRules.map((typeRule) => (
						<TypeRule key={typeRule} fieldType={fieldType} typeRuleName={typeRule} onChange={handleChange} />
					))}
				</div>
			</div>
		</div>
	);
};

type TypeRuleProps = {
	typeRuleName: PossibleTypeRules;
	fieldType: PossibleTypes;
	onChange: ({}: { typeRuleName: PossibleTypeRules; toggled: boolean; value: string }) => void;
};

const TypeRule: FC<TypeRuleProps> = ({ fieldType, typeRuleName, onChange }) => {
	const [toggled, setToggled] = useState(false);
	const [_, setValue] = useState("");

	const handleToggle = () => {
		setToggled((toggled) => {
			onChange({ typeRuleName, toggled: !toggled, value: "" });
			return !toggled;
		});
	};

	const handleInputChange = (value: string) => {
		setValue(value);
		onChange({ typeRuleName, toggled, value });
	};

	let input: JSX.Element | undefined;

	if (typeRuleName === "EmailRegexMatch") {
		input = undefined;
	} else {
		const inputType = typeRuleName === "MaximumRule" || typeRuleName === "MinimumRule" ? "number" : "text";

		input = (
			<Input
				type={inputType}
				name="typeRule"
				placeholder="Type value"
				onChange={(ev) => handleInputChange(ev.target.value)}
			/>
		);
	}

	return (
		<div className="form-control">
			<label className="label cursor-pointer p-0 pb-2">
				<span className="label-text mr-2">{translationsTypeRules[typeRuleName]}</span>
				<input type="checkbox" className="toggle toggle-primary" onChange={handleToggle} />
			</label>

			{toggled && input}
		</div>
	);
};
