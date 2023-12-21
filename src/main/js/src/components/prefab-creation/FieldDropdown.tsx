import { useState, FC } from "react";

const items = ["Text", "Audio"];

type FieldDropdownProps = {
	onSelect: (item: string) => void;
};

export const FieldDropdown: FC<FieldDropdownProps> = (props) => {
	const [isOpen, setIsOpen] = useState(false);

	const handleItemClick = (item: string) => {
		props.onSelect(item);
		setIsOpen(!isOpen);
	};

	const handleDropdownClick = () => {
		setIsOpen(!isOpen);
	};

	return (
		<div>
			{items.map((item) => (
				<div className="flex flex-col" onClick={() => handleItemClick(item)}>
					{item}
				</div>
			))}
		</div>
	);
};
