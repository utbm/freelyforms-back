import { atom } from "jotai";
import { Schemas } from "../../apiClient/client";

type PrefabWithoutGroups = Omit<Schemas.Prefab, "groups">;

export type GroupType = Schemas.Group & {
	groupIndex: number;
};

export type FieldType = Schemas.Field & {
	groupIndex: number;
	fieldIndex: number;
};

export const prefabAtom = atom<PrefabWithoutGroups>({
	caption: "",
	label: "",
	name: "",
});

export const groupsAtom = atom<GroupType[]>([]);
export const fieldsAtom = atom<FieldType[]>([]);
