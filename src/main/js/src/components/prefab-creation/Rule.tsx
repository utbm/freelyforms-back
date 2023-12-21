import { FC, useState } from "react";
import { useAtomValue } from "jotai";
import { fieldsAtom } from "./store";
import { Schemas, associatedTypesWithTypeRules } from "../../apiClient/client";

type RuleProps = {
	fieldIndex: number;
	onChange: (rule: Schemas.Rule) => void;
};

export const Rule: FC<RuleProps> = (props) => {
	const fields = useAtomValue(fieldsAtom);
	const [rules, setRules] = useState<Schemas.Rule>({
		fieldType: "",
		excludes: [],
		hidden: false,
		optional: false,
		selectorValues: [],
		typeRules: [],
	});
	const [selectorOptions, setSelectorOptions] = useState<string[]>([]);
	const [fieldsExcluded, setFieldsExcluded] = useState<string[]>([]);

	const handleRuleChange = (rule: Schemas.Rule) => {
		console.log(rule);

		setRules(rule);
		props.onChange(rule);
	};

	const fieldsExceptCurrent = fields.filter((_, index) => index !== props.fieldIndex);

	const Selector = () => {
		const [value, setValue] = useState("");

		return (
			<div>
				{selectorOptions.map((option) => option)}
				<input type="text" onChange={(ev) => setValue(ev.target.value)} />
				<button
					onClick={() => {
						setSelectorOptions([...selectorOptions, value]);
					}}
				>
					Add option
				</button>
			</div>
		);
	};

	const Excludes = () => {
		return (
			<div>
				{fieldsExcluded.map((field) => field)}
				<select
					name="inputType"
					id="inputType"
					onChange={(event) => {
						setFieldsExcluded([...fieldsExcluded, event.target.value]);
					}}
				>
					<option value="">--Please choose an option--</option>
					{fieldsExceptCurrent.map((field) => (
						<option value={field.label}>{field.label}</option>
					))}
				</select>
			</div>
		);
	};

	const goodOne = associatedTypesWithTypeRules.filter((rule) => rule.associatedType === rules.fieldType);

	const typeRules = goodOne.length > 0 ? goodOne[0].typeRules.map((rule) => rule) : [];

	return (
		<div>
			<select
				name="inputType"
				id="inputType"
				onChange={(event) => {
					handleRuleChange({ ...rules, fieldType: event.target.value });
				}}
			>
				<option value="">--Please choose an option--</option>
				<option value="STRING">STRING</option>
				<option value="INTEGER">INTEGER</option>
				<option value="FLOAT">FLOAT</option>
				<option value="DATE">DATE</option>
				<option value="DATETIME">DATETIME</option>
				<option value="BOOLEAN">BOOLEAN</option>
				<option value="SELECTOR">SELECTOR</option>
			</select>
			{rules.fieldType === "SELECTOR" && <Selector />}
			<input type="checkbox" name="Optional" />
			<input type="checkbox" name="Hidden" />
			Excludes fields:
			<Excludes />
			Typerule :
			<select>
				<option value="">--Please choose an option--</option>
				{typeRules.map((rule) => {
					return <option value={rule}>{rule}</option>;
				})}
			</select>
			{/* <select
				name="inputType"
				id="inputType"
				onChange={(event) => {
					handleRuleChange({ ...rules, excludes: event.target.value });
				}}
			>
				<option value="">--Please choose an option--</option>
				{fieldsExceptCurrent.map((field) => (
					<option value={field.label}>{field.label}</option>
				))}
			</select> */}
		</div>
	);
};
