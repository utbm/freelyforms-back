import { FC, useState } from "react";
import { useAtom, useAtomValue } from "jotai";
import { FieldType, fieldsAtom } from "./store";
import { Schemas, associatedTypesWithTypeRules } from "../../apiClient/client";
import Select from "react-select";
import { Checkbox, Input, InputWithoutBorder } from "./Input";
import { BsXLg } from "react-icons/bs";
import { TypeRules } from "./TypeRules";

type RuleProps = {
	field: FieldType;
	onChange: (rule: Schemas.Rule) => void;
};

type SelectorProps = {
	onChange: (options: string[]) => void;
	defaultOptions?: string[];
};

export const Selector = ({ onChange, defaultOptions }: SelectorProps) => {
	const [selectorOptions, setSelectorOptions] = useState<string[]>(defaultOptions ?? ["Option 1"]);

	const handleOptionButtonChange = (name: string, index: number) => {
		setSelectorOptions((selectorOptions) => {
			const newSelectorOptions = [...selectorOptions];
			newSelectorOptions[index] = name;

			onChange(newSelectorOptions);
			return newSelectorOptions;
		});
	};

	const handleRemoveOption = (index: number) => {
		setSelectorOptions((selectorOptions) => {
			const newSelectorOptions = [...selectorOptions];
			newSelectorOptions.splice(index, 1);

			onChange(newSelectorOptions);
			return newSelectorOptions;
		});
	};

	return (
		<div>
			{selectorOptions.map((option, index) => (
				<OptionButton
					key={index}
					index={index}
					addableOption={false}
					name={option}
					defaultValue={option}
					onChange={(name) => handleOptionButtonChange(name, index)}
					onRemove={handleRemoveOption}
				/>
			))}
			<OptionButton
				index={selectorOptions.length}
				addableOption={true}
				onClick={() => setSelectorOptions([...selectorOptions, `Option ${selectorOptions.length + 1}`])}
			/>
		</div>
	);
};

type OptionButtonProps = {
	onClick?: () => void;
	index: number;
} & (
	| {
			addableOption: false;
			name: string;
			onChange: (name: string) => void;
			onRemove: (index: number) => void;
			defaultValue?: string;
	  }
	| {
			addableOption: true;
	  }
);
export const OptionButton = (props: OptionButtonProps) => {
	const currentLetter = (props.index + 10).toString(36).toUpperCase();

	return (
		<article
			onClick={props.onClick}
			className={`flex flex-row items-center gap-3 mb-3 ${
				props.addableOption ? "opacity-30" : "opacity-100"
			} hover:opacity-100 transition-opacity duration-200`}
		>
			<div className="relative cursor-pointer border-neutral px-10 flex-1 flex justify-center items-center border rounded-lg bg-accent">
				<div className="border text-[7px] left-3 absolute rounded-sm w-4 h-4 mr-2 flex justify-center items-center border-neutral">
					{currentLetter}
				</div>
				{props.addableOption ? (
					<p className="whitespace-nowrap text-neutral">Add option</p>
				) : (
					<InputWithoutBorder
						name="option"
						placeholder={props.name}
						type="text"
						defaultValue={props.defaultValue}
						onChange={(ev) => props.onChange(ev.target.value)}
					/>
				)}
			</div>
			{!props.addableOption && <BsXLg className="cursor-pointer" onClick={() => props.onRemove(props.index)} />}
		</article>
	);
};

export const Rule: FC<RuleProps> = (props) => {
	const [fields, setFields] = useAtom(fieldsAtom);
	const [rules, setRules] = useState<Schemas.Rule>(props.field.rules);

	const handleRuleChange = (rule: Schemas.Rule) => {
		setRules(rule);
		setFields((fields) => {
			const newFields = [...fields];

			const fieldIndex = newFields.findIndex((field) => field.index === props.field.index);

			newFields[fieldIndex] = {
				...newFields[fieldIndex],
				rules: rule,
			};

			return newFields;
		});
		props.onChange(rule);
	};
	const currentField = fields.find((field) => field.index === props.field.index);
	const nextFieldsToCurrent = fields
		.filter((field) => field.groupIndex === props.field.groupIndex)
		.filter((field) => field.index > props.field.index)
		.filter((field) => field.name);

	const goodOne = associatedTypesWithTypeRules.filter((rule) => rule.associatedType === rules.fieldType);
	const typeRules = goodOne.length > 0 ? goodOne[0].typeRules.map((rule) => rule) : [];

	const handleCheckboxChange = (event: React.ChangeEvent<HTMLInputElement>) => {
		const { name, checked } = event.target;

		handleRuleChange({ ...rules, [name]: checked });
	};

	return (
		<div className="flex flex-row gap-3">
			<div className="flex flex-col w-[230px]">
				<Checkbox name="optional" label="Optional" onChange={handleCheckboxChange} />
				{/* why it's need to be hidden, it was made like this last year (2022) but we don't understand why */}
				<Checkbox name="hidden" label="Hidden" onChange={handleCheckboxChange} />
				{currentField?.rules.optional && (
					<>
						<p className="text-sm">Excludes fields:</p>
						<Select
							isMulti
							placeholder="Select one or more"
							className="basic-multi-select"
							classNamePrefix="select"
							options={nextFieldsToCurrent}
							styles={{}}
							onChange={(options) => {
								handleRuleChange({ ...rules, excludes: options.map((option) => option.name) });
							}}
						/>
					</>
				)}
			</div>
			<TypeRules
				possibleTypeRules={typeRules}
				selectedTypeRules={props.field.rules.typeRules}
				fieldType={props.field.rules.fieldType}
				fieldIndex={props.field.index}
			/>
		</div>
	);
};
