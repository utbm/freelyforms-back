import { FC } from "react";
import { Field } from "./Field";
import { BasicComponentInfo } from "./BasicComponentInfo";
import { useAtom } from "jotai";
import { prefabAtom } from "./store";

type GroupProps = {
	index: number;
};

export const Group: FC<GroupProps> = (props) => {
	const [prefab, setPrefab] = useAtom(prefabAtom);
	if (!prefab.groups) {
		return null;
	}
	const group = prefab.groups[props.index];

	if (!group) {
		return null;
	}

	return (
		<div className="flex flex-col flex-gap2">
			<h1 className="text-3xl">Group</h1>
			<BasicComponentInfo />
			<h1 className="text-3xl">Fields</h1>
			{group.fields?.map((field, index) => <Field fieldIndex={index} groupIndex={props.index} />)}
			<button
				onClick={() => {
					setPrefab((prefab) => ({
						...prefab,
						groups: prefab.groups?.map((group, groupIndex) => {
							if (groupIndex === props.index) {
								return {
									...group,
									fields: group.fields?.concat({
										rules: {
											typeRules: [{}],
										},
									}),
								};
							}
							return group;
						}),
					}));
				}}
			>
				Add field
			</button>
			<button
				onClick={() => {
					setPrefab((prefab) => ({
						...prefab,
						groups: prefab.groups?.filter((group, groupIndex) => groupIndex !== props.index),
					}));
				}}
			>
				Remove group
			</button>
		</div>
	);
};
