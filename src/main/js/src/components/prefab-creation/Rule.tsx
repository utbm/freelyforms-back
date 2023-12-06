import { FC } from "react";
import { Input } from "./Input";

export const Rule: FC = () => {
	return (
		<div>
			<select name="inputType" id="inputType">
				<option value="">--Please choose an option--</option>
				<option value="text">Text</option>
				<option value="number">Number</option>
				<option value="date">Date</option>
				<option value="binary">Select binary</option>
				<option value="multiple">Multiple choice</option>
			</select>
			<Input type="checkbox" label="hidden" name="Optional" />
			<Input type="checkbox" label="hidden" name="Hidden" />
		</div>
	);
};
