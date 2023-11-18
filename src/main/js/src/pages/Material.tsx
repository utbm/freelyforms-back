import React from 'react';
import Location from './Location';
import './Material.css'
type MaterialType = {
  id: string;
  type: string;
  location: string;
  fields: { name: string; type: string; data: any }[];
  locationDetails?: Location;
};

type MaterialProps = {
  material: MaterialType;
};

type MaterialState = {
  showFields: boolean;
};

export default class Material extends React.Component<MaterialProps, MaterialState> {
  constructor(props: MaterialProps) {
    super(props);
    this.state = {
      showFields: false,
    };
  }

  toggleFields = () => {
    this.setState((prevState) => ({ showFields: !prevState.showFields }));
  };

  render() {
    const { material } = this.props;
    const { showFields } = this.state;

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
                  <strong>{field.name}</strong> : {field.data} (Type: {field.type})
                </div>
              ))}
              {material.locationDetails && (
                <Location {...material.locationDetails} />
              )}
            </>
          )}
        </div>
      </div>
    );
  }
}
