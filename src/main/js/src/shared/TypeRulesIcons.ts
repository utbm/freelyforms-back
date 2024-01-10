import { GoNumber } from "react-icons/go";
import { BsTextareaT } from "react-icons/bs";
import { BiSelectMultiple } from "react-icons/bi";
import { BsToggles } from "react-icons/bs";
import { BsCalendarDate } from "react-icons/bs";

export const typeRulesIcons = [
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
	// {
	// 	name: "FLOAT",
	// 	label: "Decimal number",
	// 	icon: GoNumber,
	// },
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
