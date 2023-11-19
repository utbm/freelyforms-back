import React from 'react';
import Location from './Location';
import './Material.css';

type MaterialType = {
  id: string;
  type: string;
  location: string;
  fields: { name: string; type: string; data: any }[];
  locations?: Location[];
};

type MaterialProps = {
  material: MaterialType;
  onLocationChange?: (index: number, newCoordinates: { x: number; y: number }) => void;
};

type MaterialState = {
  showFields: boolean;
  editingFieldIndex: number | null;
  editingLocationField: string | null;
  editedFieldValue: string; 
  editingLocationIndex: number | null; 
};

export default class Material extends React.Component<
  MaterialProps,
  MaterialState
> {
  private outsideClickListener: () => void = () => {};

  constructor(props: MaterialProps) {
    super(props);
    this.state = {
      showFields: false,
      editingFieldIndex: null,
      editingLocationField: null,
      editedFieldValue: '',
      editingLocationIndex: null,
    };
  }

  componentDidMount() {
    document.addEventListener('mousedown', this.outsideClickListener);
  }

  componentWillUnmount() {
    document.removeEventListener('mousedown', this.outsideClickListener);
  }

  toggleFields = () => {
    this.setState((prevState) => ({ showFields: !prevState.showFields }));
  };

  startEditingField = (index: number) => {
    const fieldValue = this.props.material.fields[index].data;
    this.setState({ editingFieldIndex: index, editedFieldValue: fieldValue });
  };

  startEditingLocationField = (fieldName: string, index: number) => {
    const { material } = this.props;
    const fieldValue = material.locations![index][fieldName];
    this.setState({
      editingLocationField: fieldName,
      editedFieldValue: fieldValue,
      editingLocationIndex: index,
    });
  };

  stopEditingField = () => {
    this.setState({
      editingFieldIndex: null,
      editingLocationField: null,
      editedFieldValue: '',
      editingLocationIndex: null,
    });
  };

  handleFieldChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    this.setState({ editedFieldValue: event.target.value });
  };

  handleLocationFieldChange = (
    event: React.ChangeEvent<HTMLInputElement>,
    fieldName: string
  ) => {
    this.setState({ editedFieldValue: event.target.value });
  };

  handleOutsideClick = (event: MouseEvent) => {
    if (
      this.state.editingFieldIndex !== null ||
      this.state.editingLocationField !== null
    ) {
      const target = event.target as Node;

      if (
        !target.closest('.material') &&
        target instanceof HTMLElement &&
        target.tagName !== 'INPUT'
      ) {
        this.updateField();
      }
    }
  };

  handleKeyPress = (event: React.KeyboardEvent<HTMLInputElement>) => {
    if (event.key === 'Enter') {
      this.updateField();
    }
  };

  updateField = () => {
    const { material, onLocationChange } = this.props;
    const {
      editingFieldIndex,
      editingLocationField,
      editedFieldValue,
      editingLocationIndex,
    } = this.state;

    if (editingFieldIndex !== null) {
      const updatedMaterial = { ...material };
      updatedMaterial.fields[editingFieldIndex].data = editedFieldValue;

      // TODO: Add logic to update state or dispatch an action to update state in a Redux store
    } else if (editingLocationField !== null && editingLocationIndex !== null) {
      const updatedMaterial = { ...material };
      updatedMaterial.locations![editingLocationIndex][
        editingLocationField
      ] = editedFieldValue;

      // Update coordinates in parent component
      onLocationChange?.(editingLocationIndex, {
        x: parseFloat(updatedMaterial.locations![editingLocationIndex].x),
        y: parseFloat(updatedMaterial.locations![editingLocationIndex].y),
      });

      // TODO: Add logic to update state or dispatch an action to update state in a Redux store
    }

    this.stopEditingField();
  };

  render() {
    const { material } = this.props;
    const {
      showFields,
      editingFieldIndex,
      editingLocationField,
      editedFieldValue,
      editingLocationIndex,
    } = this.state;

    return (
      <div className="material">
        <div className="data">
          <div>ID: {material.id}</div>
          <div>({material.type})</div>
          <button>
            <i className="material-icons">location_on</i>
          </button>
          <button>
            <i className="material-icons">delete</i>
          </button>
          <button onClick={this.toggleFields}>
            <i className="material-icons">
              {showFields ? 'keyboard_arrow_up' : 'keyboard_arrow_down'}
            </i>
          </button>
        </div>
        <div className="fields">
          {showFields && (
            <>
              {material.fields.map((field, index) => (
                <div key={index} style={{ marginLeft: '20px' }}>
                  <strong>{field.name}</strong> :{' '}
                  {editingFieldIndex === index ? (
                    <input
                      type="text"
                      value={editedFieldValue}
                      onChange={this.handleFieldChange}
                      onBlur={this.stopEditingField}
                      onKeyPress={this.handleKeyPress}
                    />
                  ) : (
                    <span onClick={() => this.startEditingField(index)}>
                      {field.data}
                    </span>
                  )}{' '}
                  (Type: {field.type})
                </div>
              ))}
              {material.locations &&
                material.locations.map((location, index) => (
                  <div key={index} style={{ marginLeft: '20px' }}>
                    <strong>Location {index + 1}:</strong>
                    {Object.entries(location).map(
                      ([fieldName, fieldValue]) => (
                        <div key={fieldName}>
                          <strong>{fieldName}</strong> :{' '}
                          {editingLocationField === fieldName &&
                          editingLocationIndex === index ? (
                            <input
                              type="text"
                              value={editedFieldValue}
                              onChange={(e) =>
                                this.handleLocationFieldChange(e, fieldName)
                              }
                              onBlur={this.stopEditingField}
                              onKeyPress={this.handleKeyPress}
                            />
                          ) : (
                            <span
                              onClick={() =>
                                this.startEditingLocationField(fieldName, index)
                              }
                            >
                              {fieldValue}
                            </span>
                          )}
                        </div>
                      )
                    )}
                  </div>
                ))}
            </>
          )}
        </div>
      </div>
    );
  }
}
