import { FC } from "react";
import { Field } from "./Field";
import { BasicComponentInfo } from "./BasicComponentInfo";
import { useAtom, useSetAtom } from "jotai";
import { FieldType, fieldsAtom, groupsAtom } from "./store";
import { BsXLg } from "react-icons/bs";
import { ModalInputChoice } from "./ModalInputChoice";

type GroupProps = {
	index: number;
};

export const Group: FC<GroupProps> = (props) => {
	const [groups, setGroups] = useAtom(groupsAtom);
	const [fields, setFields] = useAtom(fieldsAtom);

	const group = groups.find((group) => group.index === props.index);
	const groupFields = fields.filter((field) => field.groupIndex === props.index);

	const handleAddField = (field: FieldType) => {
		setFields((fields) => fields.concat(field));
	};

	const handleRemoveGroup = () => {
		setGroups((groups) => groups.filter((group) => group.index !== props.index));
		setFields((fields) => fields.filter((field) => field.groupIndex !== props.index));
	};

	return (
		<div className="flex flex-col flex-gap2 border-dashed border-2 p-3 mb-10">
			<div className="flex flex-wrap justify-between">
				<BasicComponentInfo
					type="group"
					index={props.index}
					captionPlaceholder="Type the category of informations contained in this group"
					labelPlaceholder="Display name of the group"
					defaultCaptionValue={group?.caption}
					defaultLabelValue={group?.label}
				/>
				<button className="btn btn-sm btn-error" onClick={handleRemoveGroup}>
					<BsXLg />
				</button>
			</div>
			{/* <h1 className="text-2xl">Fields</h1> */}
			<div className="divider divider-info"></div>

			{groupFields.map((field) => (
				<Field key={field.index} fieldIndex={field.index} groupIndex={field.groupIndex} />
			))}
			<div className="flex self-center">
				<ModalInputChoice
					onSelect={(fieldType) => {
						handleAddField({
							index: fields.length,
							groupIndex: props.index,
							caption: "",
							label: "",
							name: "",
							rules: {
								fieldType: fieldType,
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
