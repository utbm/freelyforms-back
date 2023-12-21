import { ComponentProps, FC } from "react";

export const Input: FC<{
	type: React.JSX.IntrinsicElements["input"]["type"];
	name: string;
	placeholder: string;
	onChange?: (ev: React.ChangeEvent<HTMLInputElement>) => void;
}> = ({ name, type, onChange, placeholder }) => {
	return (
		<>
			<input
				required
				type={type}
				name={name}
				onChange={onChange}
				placeholder={placeholder}
				className="input input-bordered input-sm w-full max-w-xs"
			/>
		</>
	);
};

type InputWithoutBorderProps = {
	onChange?: (ev: React.ChangeEvent<HTMLInputElement>) => void;
	placeholder?: string;
	name?: string;
} & ComponentProps<"input">;

export const InputWithoutBorder: FC<InputWithoutBorderProps> = (props) => {
	const { onChange, placeholder, name, ...rest } = props;
	return (
		<input
			{...rest}
			autoComplete="off"
			required
			placeholder={props.placeholder}
			onChange={props.onChange}
			name={props.name}
			className="input input-ghost input-sm w-full max-w-xs border-transparent focus:border-transparent focus:ring-0 focus:outline-none"
		/>
	);
};
