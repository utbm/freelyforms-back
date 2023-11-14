import React from 'react';
import './Material.css';

type MaterialType = {
  id: string;
  type: string;
  location: string;
};

type MaterialProps = {
  material: MaterialType;
};

export default class Material extends React.Component<MaterialProps, { showFields: boolean }> {
  constructor(props: MaterialProps) {
    super(props);
    this.state = {
      showFields: false,
    };
  }

  toggleFields = () => {
    this.setState(prevState => ({ showFields: !prevState.showFields }));
  };

  render() {
    return (
      <div className="material">
        <div className="data">

              <div>ID: {this.props.material.id}</div>
              <div>({this.props.material.type})</div>
              <button>
                <i className="material-icons">location_on</i>
              </button>
              <button>
                <i className="material-icons">delete</i>
              </button>

            <button onClick={this.toggleFields}>
            <i className="material-icons">{this.state.showFields ? 'keyboard_arrow_up' : 'keyboard_arrow_down'}</i>
          </button>
          {this.state.showFields && (
            <>
            <br/>
              azer
            </>
          )}
        </div>
      </div>
    );
  }

  fieldList: any[] = [];
  
  addField(field: any) {
    this.fieldList.push(field);
  }

  removeField(field: any) {
    const index = this.fieldList.indexOf(field);
    if (index > -1) {
      this.fieldList.splice(index, 1);
    }
  }

  updateField(oldField: any, newField: any) {
    const index = this.fieldList.indexOf(oldField);
    if (index > -1) {
      this.fieldList[index] = newField;
    }
  }
}
