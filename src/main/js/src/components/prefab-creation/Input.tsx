import { FC } from "react";

export const Input: FC<{
	type: React.JSX.IntrinsicElements["input"]["type"];
	name: string;
	label: string;
}> = ({ name, type }) => {
	return (
		<>
			<label>
				{name}
				<input type={type} name={name} />
			</label>
		</>
	);
};
