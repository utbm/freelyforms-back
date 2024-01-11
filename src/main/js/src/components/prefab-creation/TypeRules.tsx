import { FC, useState } from "react";
import { PossibleTypes, PossibleTypeRules, Schemas } from "../../apiClient/client";
import { Input } from "./Input";
import { fieldsAtom } from "./store";
import { useSetAtom } from "jotai";
import { PossibleAlternativeDisplays, translationsTypeRules } from "../../shared/TypeRules";

type TypeRulesProps = {
	possibleTypeRules: PossibleTypeRules[];
	selectedTypeRules: Schemas.TypeRule[];
	fieldType: PossibleTypes;
	fieldIndex: number;
};

export const TypeRules: FC<TypeRulesProps> = ({ possibleTypeRules, fieldType, selectedTypeRules, ...rest }) => {
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

			const fieldIndex = newFields.findIndex((field) => field.index === rest.fieldIndex);
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
					{possibleTypeRules.map((typeRule) => (
						<TypeRule
							key={typeRule}
							fieldType={fieldType}
							typeRuleName={typeRule}
							onChange={handleChange}
							defaultValue={selectedTypeRules.find((selectedTypeRule) => selectedTypeRule.name === typeRule)?.value}
						/>
					))}
				</div>
			</div>
		</div>
	);
};

type TypeRuleProps = {
	typeRuleName: PossibleTypeRules;
	fieldType: PossibleTypes;
	defaultValue?: string;
	onChange: ({}: { typeRuleName: PossibleTypeRules; toggled: boolean; value: string }) => void;
};

const TypeRule: FC<TypeRuleProps> = ({ fieldType, typeRuleName, onChange, defaultValue }) => {
	const [isToggled, setToggled] = useState(defaultValue ? true : false);
	const [_, setValue] = useState("");

	const handleToggle = () => {
		setToggled((toggled) => {
			onChange({ typeRuleName, toggled: !toggled, value: "" });
			return !toggled;
		});
	};

	const handleInputChange = (value: string) => {
		setValue(value);
		onChange({ typeRuleName, toggled: isToggled, value });
	};

	let input: JSX.Element | undefined;

	if (typeRuleName === "EmailRegexMatch") {
		input = undefined;
	} else {
		const inputType = typeRuleName === "MaximumRule" || typeRuleName === "MinimumRule" ? "number" : "text";

		input = (
			<Input
				type={inputType}
				defaultValue={defaultValue}
				name="typeRule"
				placeholder="Type value"
				onChange={(ev) => handleInputChange(ev.target.value)}
			/>
		);
	}

	if (typeRuleName === "AlternativeDisplay") {
		input = (
			<select
				className="select select-bordered select-sm select-accent w-full max-w-xs"
				onChange={(ev) => handleInputChange(ev.target.value)}
				defaultValue={defaultValue}
			>
				{PossibleAlternativeDisplays.map((display) => (
					<option key={display.value} value={display.value}>
						{display.label}
					</option>
				))}
			</select>
		);
	}

	return (
		<div className="form-control">
			<label className="label cursor-pointer p-0 pb-2">
				<span className="label-text mr-2">{translationsTypeRules[typeRuleName]}</span>
				<input type="checkbox" className="toggle toggle-primary" onChange={handleToggle} defaultChecked={isToggled} />
			</label>

			{isToggled && input}
		</div>
	);
};
