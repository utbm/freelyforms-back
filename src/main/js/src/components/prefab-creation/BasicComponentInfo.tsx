import { Input } from "./Input";

export const BasicComponentInfo = () => {
	return (
		<>
			<Input type="text" name="name" label="name" />
			<Input type="text" name="caption" label="caption" />
			<Input type="text" name="label" label="label" />
		</>
	);
};
