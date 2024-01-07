import { FC, useState } from "react";
import { AssociatedTypesWithTypeRule, PossibleTypes, PossibleTypeRules } from "../../apiClient/client";
import { Input } from "./Input";

type TypeRulesProps = {
	typeRules: PossibleTypeRules[];
	fieldType: PossibleTypes;
};

export const TypeRules: FC<TypeRulesProps> = ({ typeRules, fieldType }) => {
	const [selectedTypeRules, setSelectedTypeRules] = useState<PossibleTypeRules[]>([]);

	const typeRulesRemaining = typeRules.filter((typeRule) => !selectedTypeRules.includes(typeRule));

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
					{typeRulesRemaining.map((typeRule) => (
						<TypeRule key={typeRule} fieldType={fieldType} typeRule={typeRule} />
					))}
				</div>
			</div>
		</div>
	);
};

type TypeRuleProps = {
	typeRule: PossibleTypeRules;
	fieldType: PossibleTypes;
};

const TypeRule: FC<TypeRuleProps> = ({ fieldType, typeRule }) => {
	const [toggled, setToggled] = useState(false);

	const handleToggle = () => {
		setToggled((toggled) => !toggled);
	};

	const input =
		typeRule === "MaximumRule" || typeRule === "MinimumRule" ? (
			<Input name="typeRule" placeholder="Type value" type="number" />
		) : (
			<Input name="typeRule" placeholder="Type value" type="text" />
		);

	return (
		<div className="form-control">
			<label className="label cursor-pointer p-0 pb-2">
				<span className="label-text mr-2">{typeRule}</span>
				<input type="checkbox" className="toggle toggle-primary" onChange={handleToggle} />
			</label>

			{toggled && input}
		</div>
	);
};
