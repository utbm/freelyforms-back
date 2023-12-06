import { atom } from "jotai";
import { Schemas } from "../../apiClient/client";

export const prefabAtom = atom<Schemas.Prefab>({
	_id: {
		date: "",
		timestamp: 0,
	},
	caption: "",
	groups: [],
	label: "",
	name: "",
});
