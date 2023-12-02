import { FC } from "react";
import { BasicComponentInfo } from "./BasicComponentInfo";
import { Rule } from "./Rule";
import { prefabAtom } from "./store";
import { useAtom } from "jotai";

type FieldProps = {
	groupIndex: number;
	fieldIndex: number;
};

export const Field: FC<FieldProps> = (props) => {
	const [prefab, setPrefab] = useAtom(prefabAtom);

	if (!prefab.groups) {
		return null;
	}
	const group = prefab.groups[props.groupIndex];

	if (!group || !group.fields) {
		return null;
	}

	const field = group.fields[props.fieldIndex];

	return (
		<div>
			<BasicComponentInfo />
			Rules :
			<Rule />
			<button
				onClick={() => {
					setPrefab((prefab) => ({
						...prefab,
						groups: prefab.groups?.map((group, gindex) => {
							if (props.groupIndex === gindex) {
								return {
									...group,
									fields: group.fields?.filter((field, findex) => props.fieldIndex !== findex),
								};
							}
							return group;
						}),
					}));
				}}
			>
				Remove field
			</button>
		</div>
	);
};
