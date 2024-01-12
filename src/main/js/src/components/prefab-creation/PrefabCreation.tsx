import { useAtom, useAtomValue } from "jotai";
import { FiPlus } from "react-icons/fi";
import { apiclient } from "../../main";
import { BasicComponentInfo } from "./BasicComponentInfo";
import { Group } from "./Group";
import { prefabAtom, groupsAtom, fieldsAtom } from "./store";
import { FC } from "react";
import { Schemas } from "../../apiClient/client";
import { Link, useParams } from "react-router-dom";
import { useNavigate } from "react-router-dom";

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

type PrefabCreationProps = {
	editionMode?: boolean;
};

export const PrefabCreation: FC<PrefabCreationProps> = ({ editionMode }) => {
	const navigate = useNavigate();
	const [prefab, setPrefab] = useAtom(prefabAtom);
	const [groups, setGroups] = useAtom(groupsAtom);
	const fields = useAtomValue(fieldsAtom);
	const { formName } = useParams();

	const handleSendPrefab = (ev: React.FormEvent<HTMLFormElement>) => {
		ev.preventDefault();
		const prefabWithFieldGroups = {
			...prefab,
			groups: groups.map((group) => ({
				...group,
				fields: fields.filter((field) => field.groupIndex === group.index),
			})),
		};
		const clonedPrefab = JSON.parse(JSON.stringify(prefabWithFieldGroups));

		const refinedPrefab: Schemas.Prefab = removePropertiesRecursively(clonedPrefab, ["index", "groupIndex"]);
		// @ts-ignore
		refinedPrefab.name = refinedPrefab.name.toLowerCase().replace(/ /g, "-");
		console.log(refinedPrefab);

		if (editionMode) {
			apiclient.put(`/api/prefabs/${prefab.name}`, {
				...refinedPrefab,
			});
			return;
		}

		apiclient
			.post("/api/prefabs", {
				...refinedPrefab,
			})
			.then((res) => {
				if (res.status === 200) {
					navigate(`/form/${prefab.name}`);
				}
			});
	};

	return (
		<div data-theme="light" className="w-screen h-screen p-2 lg:p-6 overflow-y-auto">
			<form className="flex-col gap-2" onSubmit={handleSendPrefab}>
				<div>
					<h2 className="text-2xl text-primary text-center">
						{editionMode ? `Edit the form ${formName}` : "Create a form"}
					</h2>
					<Link to={`/form/${formName}/answers`} className="btn btn-primary mb-4 text-center">
						See answers
					</Link>
					<BasicComponentInfo
						type="prefab"
						captionPlaceholder="A description about your form"
						labelPlaceholder="Display label"
						namePlaceholder="Technical name"
						defaultCaptionValue={prefab.caption}
						defaultLabelValue={prefab.label}
						defaultNameValue={prefab.name}
					/>
				</div>
				<div>
					{groups.map((group, index) => (
						<Group key={group.index} index={group.index} />
					))}
					<div className="flex justify-center">
						<button
							className="btn btn-primary"
							onClick={() => {
								setGroups((groups) =>
									groups.concat({
										index: groups.length,
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
						{editionMode ? "Update the form" : "Create the form"}
						<span className={`pt-0.5 pl-1.5 `}>
							<img src="/icons/check.svg" height={11} width={14} />
						</span>
					</button>
				</div>
			</form>
		</div>
	);
};
