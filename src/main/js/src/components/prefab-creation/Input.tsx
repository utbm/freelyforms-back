import { ComponentProps, FC, PropsWithChildren } from "react";

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

type InputWithoutBorderProps = PropsWithChildren<{
	onChange?: (ev: React.ChangeEvent<HTMLInputElement>) => void;
	placeholder?: string;
	name?: string;
	tooltipChildren?: string;
}> &
	ComponentProps<"input">;

export const InputWithoutBorder: FC<InputWithoutBorderProps> = (props) => {
	const { onChange, placeholder, name, className, children, ...rest } = props;
	const classNames = ["message", className].join(" ");
	const inputClassName =
		"input input-ghost  input-sm w-full max-w-xs border-transparent focus:border-transparent focus:ring-0 focus:outline-none" +
		(classNames ? " " + classNames : "");

	if (props.children) {
		return (
			<div className="flex justify-end items-center">
				<input
					{...rest}
					className={inputClassName}
					autoComplete="off"
					required
					placeholder={props.placeholder}
					onChange={props.onChange}
					name={props.name}
				/>
				<span className="tooltip" data-tip={rest.tooltipChildren}>
					{props.children}
				</span>
			</div>
		);
	}

	return (
		<input
			{...rest}
			className={inputClassName}
			autoComplete="off"
			required
			placeholder={props.placeholder}
			onChange={props.onChange}
			name={props.name}
		/>
	);
};
