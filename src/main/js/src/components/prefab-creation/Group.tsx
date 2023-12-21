import { FC } from "react";
import { Field } from "./Field";
import { BasicComponentInfo } from "./BasicComponentInfo";
import { useAtom, useSetAtom } from "jotai";
import { FieldType, fieldsAtom, groupsAtom } from "./store";
import { BsXLg } from "react-icons/bs";
import { FieldDropdown } from "./FieldDropdown";

type GroupProps = {
	index: number;
};

const addField = (fields: FieldType[], field: FieldType) => {
	return fields.concat(field);
};

export const Group: FC<GroupProps> = (props) => {
	const setGroups = useSetAtom(groupsAtom);
	const [fields, setFields] = useAtom(fieldsAtom);

	const groupFields = fields.filter((field) => field.groupIndex === props.index);

	const handleAddField = (field: FieldType) => {
		setFields((fields) => addField(fields, field));
	};

	return (
		<div className="flex flex-col flex-gap2 border-dashed border-2 p-3 mb-10">
			<div className="flex flex-wrap justify-between">
				<h1 className="text-3xl">Group</h1>
				<button
					className="btn btn-error"
					onClick={() => {
						setGroups((groups) => groups.filter((_, groupIndex) => groupIndex !== props.index));
					}}
				>
					<BsXLg />
				</button>
			</div>
			<BasicComponentInfo
				type="group"
				index={props.index}
				captionPlaceholder="Type the category of informations contained in this group"
				labelPlaceholder="Display name of the group"
			/>
			<h1 className="text-2xl">Fields</h1>
			{groupFields.map((field, index) => (
				<Field key={field.name} fieldIndex={index} groupIndex={props.index} />
			))}
			<div className="flex self-center">
				<FieldDropdown
					onSelect={(item) => {
						handleAddField({
							fieldIndex: fields.length,
							groupIndex: props.index,
							caption: "",
							label: "",
							name: "",
							rules: {
								fieldType: "",
								excludes: [],
								hidden: false,
								optional: false,
								selectorValues: [],
								typeRules: [],
							},
						});
					}}
				/>
			</div>
		</div>
	);
};
