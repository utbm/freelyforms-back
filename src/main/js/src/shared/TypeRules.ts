import { GoNumber } from "react-icons/go";
import { BsTextareaT } from "react-icons/bs";
import { BiSelectMultiple } from "react-icons/bi";
import { BsToggles } from "react-icons/bs";
import { BsCalendarDate } from "react-icons/bs";
import { PossibleTypeRules } from "../apiClient/client";

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

export const PossibleAlternativeDisplays = [
	{
		label: "Multiple choice",
		value: "MULTIPLE_CHOICE",
	},
	{
		label: "Radio",
		value: "RADIO",
	},
	// {
	// 	label: "Dropdown",
	// 	value: "DROPDOWN",
	// },
	// {
	// 	label: "Checkbox",
	// 	value: "CHECKBOX",
	// },
	// {
	// 	label: "Slider",
	// 	value: "SLIDER",
	// },
];

export const translationsTypeRules: Record<PossibleTypeRules, string> = {
	EmailRegexMatch: "Is a email input ?",
	MaximumRule: "Maximum input length",
	MinimumRule: "Minimum input length",
	AlternativeDisplay: "Alternative Display",
	RegexMatch: "The input must match a regex",
	// SelectDataSet: "Select Data Set",
};
