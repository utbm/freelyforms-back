import { apiclient } from "../main";
import { BasicComponentInfo } from "../components/prefab-creation/BasicComponentInfo";
import { Group } from "../components/prefab-creation/Group";
import { useAtom, useAtomValue } from "jotai";
import { fieldsAtom, groupsAtom, prefabAtom } from "../components/prefab-creation/store";
import { FiPlus } from "react-icons/fi";

function removePropertiesRecursively<T extends object>(obj: T, propertiesToRemove: string[]): T {
	for (const key in obj) {
		if (Object.prototype.hasOwnProperty.call(obj, key)) {
			if (propertiesToRemove.includes(key)) {
				delete obj[key];
			} else if (typeof obj[key] === "object" && obj[key] !== null) {
				removePropertiesRecursively(obj[key] as unknown as object, propertiesToRemove);
			}
		}
	}
	return obj;
}

export const PrefabCreation = () => {
	const [prefab, setPrefab] = useAtom(prefabAtom);
	const [groups, setGroups] = useAtom(groupsAtom);
	const fields = useAtomValue(fieldsAtom);

	const handleSendPrefab = (ev: React.FormEvent<HTMLFormElement>) => {
		ev.preventDefault();
		const prefabWithFieldGroups = {
			...prefab,
			groups: groups.map((group) => ({
				...group,
				fields: fields.filter((field) => field.groupUUID === group.uuid),
			})),
		};
		const clonedPrefab = JSON.parse(JSON.stringify(prefabWithFieldGroups));

		const refinedPrefab = removePropertiesRecursively(clonedPrefab, ["uuid", "groupUUID"]);
		console.log(refinedPrefab);

		// // create form data
		// const formData = new FormData(ev.target);
		// console.log(formData);
		// // iterate on form data
		// for (let [key, value] of formData.entries()) {
		// 	console.log(key, value);
		// }

		apiclient.post("/api/prefabs", {
			...refinedPrefab,
		});
	};

	return (
		<div data-theme="light" className="w-screen h-screen p-2 lg:p-6">
			<form className="flex-col gap-2" onSubmit={handleSendPrefab}>
				<div>
					<h2 className="text-2xl">Create a form</h2>
					<BasicComponentInfo
						type="prefab"
						captionPlaceholder="A description about your form"
						labelPlaceholder="Display label"
						namePlaceholder="Technical name"
					/>
				</div>
				<div>
					{groups.map((group, index) => (
						<Group key={group.uuid} uuid={group.uuid} />
					))}
					<div className="flex justify-center">
						<button
							className="btn btn-primary"
							onClick={() => {
								setGroups((groups) =>
									groups.concat({
										uuid: crypto.randomUUID(),
										fields: [],
										label: "",
										caption: "",
										name: "",
									}),
								);
							}}
						>
							<span className="flex gap-x-1 items-center">
								Add group
								<FiPlus size={20} />
							</span>
						</button>
					</div>
				</div>
				<div className="flex justify-end">
					<button className="btn btn-primary btn-success" type="submit">
						Submit the form
						<span className={`pt-0.5 pl-1.5 `}>
							<img src="/icons/check.svg" height={11} width={14} />
						</span>
					</button>
				</div>
			</form>
		</div>
	);
};
