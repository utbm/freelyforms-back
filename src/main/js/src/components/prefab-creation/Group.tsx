import { FC } from "react";
import { Field } from "./Field";
import { BasicComponentInfo } from "./BasicComponentInfo";
import { useAtom, useSetAtom } from "jotai";
import { FieldType, fieldsAtom, groupsAtom } from "./store";
import { BsXLg } from "react-icons/bs";
import { ModalInputChoice } from "./ModalInputChoice";
import { PossibleType } from "../../apiClient/client";

type GroupProps = {
	index: number;
};

export const Group: FC<GroupProps> = (props) => {
	const [groups, setGroups] = useAtom(groupsAtom);
	const [fields, setFields] = useAtom(fieldsAtom);

	const group = groups.find((group) => group.index === props.index);
	const groupFields = fields.filter((field) => field.groupIndex === props.index);

	const handleAddField = (fieldType: PossibleType) => {
		setFields((fields) =>
			fields.concat({
				index: fields.length,
				groupIndex: props.index,
				caption: "",
				label: "",
				name: "",
				rules: {
					fieldType,
					excludes: [],
					hidden: false,
					optional: false,
					selectorValues: [],
					typeRules:
						fieldType === "SELECTOR"
							? [{ associatedTypes: [], name: "AlternativeDisplay", value: "MULTIPLE_CHOICE" }]
							: [],
				},
			}),
		);
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
					captionPlaceholder="Group description"
					labelPlaceholder="Group display name"
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
						handleAddField(fieldType);
					}}
				/>
			</div>
		</div>
	);
};
