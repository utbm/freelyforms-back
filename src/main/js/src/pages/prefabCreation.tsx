import { apiclient } from "../main";
import { BasicComponentInfo } from "../components/prefab-creation/BasicComponentInfo";
import { Group } from "../components/prefab-creation/Group";
import { useAtom } from "jotai";
import { prefabAtom } from "../components/prefab-creation/store";

export const PrefabCreation = () => {
	const [prefab, setPrefab] = useAtom(prefabAtom);

	const addInput = () => {
		setPrefab({
			...prefab,
			groups: [
				{
					fields: [
						{
							rules: {
								typeRules: [{}],
							},
						},
					],
				},
			],
		});
	};

	const sendPrefab = () => {
		console.log("Sending prefab");
		apiclient.post("/api/prefabs", {
			body: prefab,
		});
	};

	return (
		<div className="flex-col gap-2" onSubmit={sendPrefab}>
			<div>
				<h2 className="text-3xl">Form details</h2>
				<BasicComponentInfo />
			</div>
			<div>
				<h2 className="text-3xl">Groups</h2>
				{prefab.groups?.map((group, index) => <Group index={index} />)}
				<button
					onClick={() => {
						setPrefab((prefab) => ({
							...prefab,
							groups: prefab.groups?.concat({
								fields: [],
							}),
						}));
					}}
				>
					Add group
				</button>
			</div>
			<button type="submit"> Validate</button>
		</div>
	);
};
