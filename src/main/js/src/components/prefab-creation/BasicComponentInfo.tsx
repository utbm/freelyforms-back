import { useSetAtom } from "jotai";
import { Input, InputWithoutBorder } from "./Input";
import { fieldsAtom, groupsAtom, prefabAtom } from "./store";
import { FC, PropsWithChildren } from "react";

type CommonProps = {
	captionPlaceholder: string;
	labelPlaceholder: string;
};

type GroupComponent = {
	type: "group";
	uuid: string;
};

type FieldComponent = {
	type: "field";
	uuid: string;
	groupUUID: string;
};

type PrefabComponent = {
	type: "prefab";
	namePlaceholder: string;
};
type Props = (GroupComponent | FieldComponent | PrefabComponent) & CommonProps;
type BasicComponentInfoProps = PropsWithChildren<Props>;

export const BasicComponentInfo: FC<BasicComponentInfoProps> = (props) => {
	const setPrefab = useSetAtom(prefabAtom);
	const setGroups = useSetAtom(groupsAtom);
	const setFields = useSetAtom(fieldsAtom);

	const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
		const { name, value } = e.target;
		if (props.type === "group") {
			setGroups((groups) => {
				const newGroups = [...groups];

				const groupIndex = newGroups.findIndex((group) => group.uuid === props.uuid);

				newGroups[groupIndex] = {
					...newGroups[groupIndex],
					[name]: value,
					...(name === "label" ? { name: value } : {}),
				};

				return newGroups;
			});
		} else if (props.type === "field") {
			setFields((fields) => {
				const newFields = [...fields];

				const fieldIndex = newFields.findIndex((field) => field.uuid === props.uuid);

				newFields[fieldIndex] = {
					...newFields[fieldIndex],
					[name]: value,
					...(name === "label" ? { name: value } : {}),
				};

				return newFields;
			});
		} else if (props.type === "prefab") {
			setPrefab((prefab) => ({
				...prefab,
				[name]: value,
			}));
		}
	};

	if (props.type === "field") {
		return (
			<div className="flex flex-col">
				{props.children}

				<InputWithoutBorder
					className="text-xl"
					name="label"
					placeholder={props.labelPlaceholder}
					onChange={handleChange}
				/>
				{props.captionPlaceholder ? (
					<InputWithoutBorder name="caption" placeholder={props.captionPlaceholder} onChange={handleChange} />
				) : null}
			</div>
		);
	}

	return (
		<div className="flex flex-col w-10/12">
			<InputWithoutBorder
				autoFocus
				className="text-xl"
				name="label"
				placeholder={props.labelPlaceholder}
				onChange={handleChange}
			/>
			{props.type === "prefab" ? (
				<InputWithoutBorder name="name" placeholder={props.namePlaceholder} onChange={handleChange} />
			) : null}
			<InputWithoutBorder name="caption" placeholder={props.captionPlaceholder} onChange={handleChange} />
			{props.children}
		</div>
	);
};
