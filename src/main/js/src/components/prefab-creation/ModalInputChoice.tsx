import { GoNumber } from "react-icons/go";
import { BsTextareaT } from "react-icons/bs";
import { BiSelectMultiple } from "react-icons/bi";
import { BsToggles } from "react-icons/bs";
import { BsCalendarDate } from "react-icons/bs";
import { FC } from "react";
import { PossibleTypes, Schemas } from "../../apiClient/client";
import { IconType } from "react-icons";

type Item = {
	name: PossibleTypes;
	label: string;
	icon: IconType;
};

const items = [
	{
		name: "STRING",
		label: "Text",
		icon: BsTextareaT,
	},
	{
		name: "INTEGER",
		label: "Number",
		icon: GoNumber,
	},
	{
		name: "FLOAT",
		label: "Decimal number",
		icon: GoNumber,
	},
	{
		name: "SELECTOR",
		label: "Multiple choice",
		icon: BiSelectMultiple,
	},
	{
		name: "DATE",
		label: "Date",
		icon: BsCalendarDate,
	},
	{
		name: "DATETIME",
		label: "Date and time",
		icon: BsCalendarDate,
	},
	{
		name: "BOOLEAN",
		label: "Boolean",
		icon: BsToggles,
	},
] as const;

type ModalInputChoiceProps = {
	onSelect: (fieldType: PossibleTypes) => void;
};

export const ModalInputChoice: FC<ModalInputChoiceProps> = (props) => {
	const handleModalOpen = () => {
		const modal = document.getElementById("modalInputChoice") as HTMLDialogElement;

		modal.showModal();
	};

	const handleSelect = (fieldType: PossibleTypes) => {
		const modal = document.getElementById("modalInputChoice") as HTMLDialogElement;

		modal.close();
		props.onSelect(fieldType);
	};

	return (
		<>
			<button className="btn" onClick={() => handleModalOpen()}>
				open modal
			</button>
			<dialog id="modalInputChoice" className="modal">
				<div className="modal-box flex w-full">
					<div className="flex flex-col">
						<h4 className="text-lg">Input blocks</h4>
						{items.map((item) => (
							<Item key={item.name} item={item} onSelect={handleSelect} />
						))}
					</div>
					<div className="divider divider-horizontal"></div>
					<div>preview of component</div>
					<form method="dialog">
						<button className="btn btn-sm btn-circle btn-ghost absolute right-2 top-2">✕</button>
					</form>
				</div>
			</dialog>
		</>
	);
};

const Item: FC<{
	item: Item;
	onSelect?: (fieldType: PossibleTypes) => void;
}> = (props) => {
	return (
		<div
			onClick={() => props.onSelect?.(props.item.name)}
			id="group"
			className="group flex text-zinc-800 flex-row gap-2 items-center hover:bg-zinc-200	h-7 cursor-pointer mx-1 px-2.5 py-0 rounded-md select-none"
		>
			<div className="text-zinc-400">
				<props.item.icon size={20} className="group-hover:text-black" />
			</div>
			{props.item.label}
		</div>
	);
};
