import { GoNumber } from "react-icons/go";
import { BsTextareaT } from "react-icons/bs";
import { BiSelectMultiple } from "react-icons/bi";
import { BsToggles } from "react-icons/bs";
import { BsCalendarDate } from "react-icons/bs";
import { FC, useRef } from "react";
import { PossibleTypes, Schemas } from "../../apiClient/client";
import { IconType } from "react-icons";
import { FiPlus } from "react-icons/fi";

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
	const dropdownRef = useRef<HTMLDivElement>(null);

	const handleSelect = (fieldType: PossibleTypes) => {
		props.onSelect(fieldType);

		dropdownRef.current?.blur();
	};

	return (
		<>
			<div className="dropdown">
				<div tabIndex={0} role="button" className="btn btn-primary btn-success btn-sm">
					<span className="flex gap-x-1 items-center">
						Add field
						<FiPlus size={20} />
					</span>
				</div>
				<div
					ref={dropdownRef}
					tabIndex={0}
					className={`dropdown-content flex flex-col gap-2 z-[1] menu shadow bg-base-100 rounded-box w-52 p-3`}
				>
					{items.map((item) => (
						<ModalItem key={item.name} item={item} onSelect={handleSelect} />
					))}
				</div>
			</div>
		</>
	);
};

const ModalItem: FC<{
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
