import { FC } from "react";
import { Field } from "./Field";
import { BasicComponentInfo } from "./BasicComponentInfo";
import { useAtom, useSetAtom } from "jotai";
import { FieldType, fieldsAtom, groupsAtom } from "./store";
import { BsXLg } from "react-icons/bs";
import { ModalInputChoice } from "./ModalInputChoice";

type GroupProps = {
	uuid: string;
};

export const Group: FC<GroupProps> = (props) => {
	const setGroups = useSetAtom(groupsAtom);
	const [fields, setFields] = useAtom(fieldsAtom);

	const groupFields = fields.filter((field) => field.groupUUID === props.uuid);

	const handleAddField = (field: FieldType) => {
		setFields((fields) => fields.concat(field));
	};

	return (
		<div className="flex flex-col flex-gap2 border-dashed border-2 p-3 mb-10">
			<div className="flex flex-wrap justify-between">
				<BasicComponentInfo
					type="group"
					uuid={props.uuid}
					captionPlaceholder="Type the category of informations contained in this group"
					labelPlaceholder="Display name of the group"
				/>
				<button
					className="btn btn-sm btn-error"
					onClick={() => {
						setGroups((groups) => groups.filter((group) => group.uuid !== props.uuid));
					}}
				>
					<BsXLg />
				</button>
			</div>
			{/* <h1 className="text-2xl">Fields</h1> */}
			<div className="divider divider-info"></div>

			{groupFields.map((field) => (
				<Field key={field.uuid} fieldUUID={field.uuid} groupUUID={field.groupUUID} />
			))}
			<div className="flex self-center">
				<ModalInputChoice
					onSelect={(fieldType) => {
						handleAddField({
							uuid: crypto.randomUUID(),
							groupUUID: props.uuid,
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
