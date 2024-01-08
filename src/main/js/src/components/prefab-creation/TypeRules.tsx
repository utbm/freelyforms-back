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

			if (toggled) {
				typeRules.push({
					name: typeRuleName,
					value,
					// @TODO REMOVE
					associatedTypes: ["INTEGER", "FLOAT", "STRING", "DATE", "DATETIME"],
				});
			}

			return newFields;
		});
	};

	return (
		<div className="flex flex-col">
			<div className="dropdown">
				<div tabIndex={0} role="button" className="btn btn-primary btn-success btn-sm">
					TypeRules
				</div>
				<div
					tabIndex={0}
					className="dropdown-content w-fit flex flex-col gap-2 z-[1] menu shadow bg-base-100 rounded-box w-52 p-3"
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

	const input =
		typeRuleName === "MaximumRule" || typeRuleName === "MinimumRule" ? (
			<Input
				type="number"
				name="typeRule"
				placeholder="Type value"
				onChange={(ev) => handleInputChange(ev.target.value)}
			/>
		) : (
			<Input
				type="text"
				name="typeRule"
				placeholder="Type value"
				onChange={(ev) => handleInputChange(ev.target.value)}
			/>
		);

	return (
		<div className="form-control">
			<label className="label cursor-pointer p-0 pb-2">
				<span className="label-text mr-2">{typeRuleName}</span>
				<input type="checkbox" className="toggle toggle-primary" onChange={handleToggle} />
			</label>

			{toggled && input}
		</div>
	);
};
