import { apiclient } from "../main";
import { BasicComponentInfo } from "../components/prefab-creation/BasicComponentInfo";
import { Group } from "../components/prefab-creation/Group";
import { useAtom, useAtomValue } from "jotai";
import { fieldsAtom, groupsAtom, prefabAtom } from "../components/prefab-creation/store";
import { FiPlus } from "react-icons/fi";
import Select from "../components/question/select";

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
				fields: fields.filter((field) => field.groupIndex === group.groupIndex),
			})),
		};

		console.log(prefabWithFieldGroups);
		// console.log(ev);

		// // create form data
		// const formData = new FormData(ev.target);
		// console.log(formData);
		// // iterate on form data
		// for (let [key, value] of formData.entries()) {
		// 	console.log(key, value);
		// }

		apiclient.post("/api/prefabs", {
			...prefabWithFieldGroups,
		});
	};

	return (
		<div data-theme="light" className="w-screen h-screen p-2 lg:p-6">
			<div className="flex-col gap-2">
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
						<Group key={group.name} index={group.groupIndex} />
					))}
					<div className="flex justify-center">
						<button
							className="btn btn-primary"
							onClick={() => {
								setGroups((groups) =>
									groups.concat({
										uuid: crypto.randomUUID(),
										groupIndex: groups.length,
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
						Validate
						<span className={`pt-0.5 pl-1.5 `}>
							<img src="/icons/check.svg" height={11} width={14} />
						</span>
					</button>
				</div>
			</div>
		</div>
	);
};
