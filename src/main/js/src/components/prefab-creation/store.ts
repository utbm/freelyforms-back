import { atom } from "jotai";
import { Schemas } from "../../apiClient/client";

type PrefabWithoutGroups = Omit<Schemas.Prefab, "groups">;

export type GroupType = Schemas.Group & {
	index: number;
};

export type FieldType = Schemas.Field & {
	index: number;
	groupIndex: number;
};

export const prefabAtom = atom<PrefabWithoutGroups>({
	caption: "",
	label: "",
	name: "",
});

export const groupsAtom = atom<GroupType[]>([]);
export const fieldsAtom = atom<FieldType[]>([]);
fieldsAtom.debugLabel = "fieldsAtom";
groupsAtom.debugLabel = "groupsAtom";
prefabAtom.debugLabel = "prefabAtom";
