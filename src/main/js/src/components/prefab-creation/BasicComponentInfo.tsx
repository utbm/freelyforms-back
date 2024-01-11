import { useSetAtom } from "jotai";
import { Input, InputWithoutBorder } from "./Input";
import { fieldsAtom, groupsAtom, prefabAtom } from "./store";
import { FC, PropsWithChildren } from "react";

type CommonProps = {
	captionPlaceholder: string;
	labelPlaceholder: string;
	defaultCaptionValue?: string;
	defaultLabelValue?: string;
};

type GroupComponent = {
	type: "group";
	index: number;
};

type FieldComponent = {
	type: "field";
	index: number;
	groupIndex: number;
	tooltipChildren: string;
};

type PrefabComponent = {
	type: "prefab";
	namePlaceholder: string;
	defaultNameValue?: string;
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

				const groupIndex = newGroups.findIndex((group) => group.index === props.index);

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

				const fieldIndex = newFields.findIndex((field) => field.index === props.index);

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
				<InputWithoutBorder
					className="text-xl"
					name="label"
					placeholder={props.labelPlaceholder}
					onChange={handleChange}
					tooltipChildren={props.tooltipChildren}
					defaultValue={props.defaultLabelValue}
				>
					{props.children}
				</InputWithoutBorder>
				{props.captionPlaceholder ? (
					<InputWithoutBorder
						required={false}
						defaultValue={props.defaultCaptionValue}
						name="caption"
						placeholder={props.captionPlaceholder}
						onChange={handleChange}
					/>
				) : null}
			</div>
		);
	}

	return (
		<div className="flex flex-col w-10/12">
			<InputWithoutBorder
				defaultValue={props.defaultLabelValue}
				autoFocus
				className="text-xl"
				name="label"
				placeholder={props.labelPlaceholder}
				onChange={handleChange}
			/>
			{props.type === "prefab" ? (
				<InputWithoutBorder
					defaultValue={props.defaultNameValue}
					name="name"
					placeholder={props.namePlaceholder}
					onChange={handleChange}
				/>
			) : null}
			<InputWithoutBorder
				required={false}
				defaultValue={props.defaultCaptionValue}
				name="caption"
				placeholder={props.captionPlaceholder}
				onChange={handleChange}
			/>
			{props.children}
		</div>
	);
};
