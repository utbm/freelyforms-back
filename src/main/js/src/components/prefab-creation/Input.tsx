import { ComponentProps, FC } from "react";

export const Checkbox: FC<{
	name: string;
	label?: string;
	onChange?: (ev: React.ChangeEvent<HTMLInputElement>) => void;
}> = ({ name, label, onChange }) => {
	return (
		<div className="form-control">
			<label className="label cursor-pointer p-0 pb-2">
				<span className="label-text">{label ?? name}</span>
				<input name={name} type="checkbox" className="checkbox checkbox-sm checkbox-primary" onChange={onChange} />
			</label>
		</div>
	);
};

export const Input: FC<{
	type: React.JSX.IntrinsicElements["input"]["type"];
	name: string;
	placeholder?: string;
	defaultValue?: string;
	onChange?: (ev: React.ChangeEvent<HTMLInputElement>) => void;
}> = ({ name, type, onChange, placeholder, defaultValue }) => {
	return (
		<>
			<input
				required
				defaultValue={defaultValue}
				type={type}
				name={name}
				onChange={onChange}
				placeholder={placeholder}
				className={`input input-bordered input-sm w-full max-w-xs`}
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
	const { onChange, placeholder, name, className, ...rest } = props;
	const classNames = ["message", className].join(" ");

	return (
		<input
			{...rest}
			className={
				"input required input-ghost input-sm w-full max-w-xs border-transparent focus:border-transparent focus:ring-0 focus:outline-none" +
				(classNames ? " " + classNames : "")
			}
			autoComplete="off"
			required
			placeholder={props.placeholder}
			onChange={props.onChange}
			name={props.name}
		/>
	);
};
