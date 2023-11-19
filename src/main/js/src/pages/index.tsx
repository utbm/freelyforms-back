import React from 'react';
import { MapContainer, TileLayer, Marker, Popup } from 'react-leaflet';
import L from 'leaflet';
import Material from './Material';
import './leaflet.css';
import './styles.css';

class Home extends React.Component<{}, AppState> {
  constructor(props: {}) {
    super(props);
    this.state = {
      materials: [
        {
          id: '1',
          type: 'Type 1',
          fields: [
            { name: 'Field 1', type: 'string', data: 'Data 1' },
            { name: 'Field 2', type: 'number', data: 42 },
          ],
          locations: [
            {
              x: 51.52,
              y: -0.09,
              radius: 10,
              address: 'Location 1 Address',
            },
            {
              x: 51.53,
              y: -0.09,
              radius: 10,
              address: 'Location 1 Address 2 ',
            },
          ],
        },
        {
          id: '2',
          type: 'Type 2',
          fields: [
            { name: 'Field 1', type: 'boolean', data: 'true' },
            { name: 'Field 2', type: 'string', data: 'Data 2' },
          ],
          locations: [
            {
              x: 51.52,
              y: -0.08,
              radius: 0,
              address: 'Location 2 Address',
            },
          ],
        },
        {
          id: '3',
          type: 'Type 3',
          fields: [
            { name: 'Field 1', type: 'string', data: 'Data 3' },
            { name: 'Field 2', type: 'number', data: 48 },
          ],
        },
      ],
    };
  }
  handleLocationChange = (
    materialId: string,
    locationIndex: number,
    newCoordinates: { x: number; y: number }
  ) => {
    this.setState((prevState) => {
      const updatedMaterials = prevState.materials.map((material) => {
        if (material.id === materialId && material.locations) {
          const updatedLocations = [...material.locations];
          updatedLocations[locationIndex] = {
            ...updatedLocations[locationIndex],
            ...newCoordinates,
          };

          return {
            ...material,
            locations: updatedLocations,
          };
        }

        return material;
      });

      return {
        materials: updatedMaterials,
      };
    });
  };

  handleFieldEdit = (
    materialId: string,
    locationIndex: number,
    fieldName: string,
    editedValue: string
  ) => {
    this.setState((prevState) => {
      const updatedMaterials = prevState.materials.map((material) => {
        if (material.id === materialId && material.locations) {
          const updatedLocations = [...material.locations];
          updatedLocations[locationIndex] = {
            ...updatedLocations[locationIndex],
            [fieldName]: editedValue,
          };

          return {
            ...material,
            locations: updatedLocations,
          };
        }

        return material;
      });

      return {
        materials: updatedMaterials,
      };
    });
  };

 
  handleFieldChange = (
    materialId: string,
    locationIndex: number,
    fieldName: string,
    editedValue: string
  ) => {
    event?.stopPropagation();
    this.setState({
      editingMaterialId: materialId,
      editingLocationIndex: locationIndex,
      editingFieldName: fieldName,
      editedFieldValue: editedValue,
    });
  };

  handleKeyPress = (
    materialId: string,
    locationIndex: number,
    fieldName: string,
    event: React.KeyboardEvent<HTMLInputElement>
  ) => {
    if (event.key === 'Enter') {
      this.handleFieldEdit(
        materialId,
        locationIndex,
        fieldName,
        this.state.editedFieldValue
      );

      // Clear editing state after Enter is pressed
      this.setState({
        editingMaterialId: null,
        editingLocationIndex: null,
        editingFieldName: null,
        editedFieldValue: '',
      });
    }
  };

  handlePopupToggle = (materialId: string, locationIndex: number) => {
    this.setState((prevState) => {
      const updatedMaterials = prevState.materials.map((material) => {
        if (material.id === materialId && material.locations) {
          const updatedLocations = [...material.locations];
          updatedLocations[locationIndex] = {
            ...updatedLocations[locationIndex],
            popupOpen: !updatedLocations[locationIndex].popupOpen,
          };

          return {
            ...material,
            locations: updatedLocations,
          };
        }

        return material;
      });

      return {
        materials: updatedMaterials,
      };
    });
  };

  render() {
    const blueIcon = new L.Icon({
      iconUrl: '/../../node_modules/leaflet/dist/images/marker-icon.png',
      shadowUrl: '/../..//node_modules/leaflet/dist/images/marker-shadow.png',
    });

    return (
      <div style={{ display: 'flex' }}>
        <MapContainer
          center={[51.505, -0.09]}
          zoom={13}
          style={{ height: '900px', width: '50%' }}
        >
          <TileLayer
            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
            attribution='© <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
          />
          {this.state.materials.map((material) =>
            material.locations &&
            material.locations.map((location, index) => (
              <Marker
                key={`${material.id}_${index}`}
                position={[location.x, location.y]}
                icon={blueIcon}
              >
                <Popup
                  onOpen={() => this.handlePopupToggle(material.id, index)}
                  onClose={() => this.handlePopupToggle(material.id, index)}
                >
                  ID: {material.id}
                  <br />
                  Location {index + 1}:
                  {Object.entries(location).map(
                    ([fieldName, fieldValue]) => (
                      <div key={fieldName}>
                        <strong>{fieldName}</strong>:{' '}
                        {this.state.editingMaterialId === material.id &&
                        this.state.editingLocationIndex === index &&
                        this.state.editingFieldName === fieldName ? (
                          <input
                            type="text"
                            value={this.state.editedFieldValue}
                            onChange={(e) =>
                              this.handleFieldChange(
                                material.id,
                                index,
                                fieldName,
                                e.target.value
                              )
                            }
                            onKeyPress={(e) =>
                              this.handleKeyPress(
                                material.id,
                                index,
                                fieldName,
                                e
                              )
                            }
                          />
                        ) : (
                          <span
                            onClick={() =>
                              this.handleFieldChange(
                                material.id,
                                index,
                                fieldName,
                                fieldValue
                              )
                            }
                          >
                            {fieldValue}
                          </span>
                        )}
                      </div>
                    )
                  )}
                </Popup>
              </Marker>
            ))
          )}
        </MapContainer>
        <div style={{ width: '50%' }}>
          {this.state.materials.map((material) => (
            <Material
              key={material.id}
              material={material}
              onLocationChange={(index, newCoordinates) =>
                this.handleLocationChange(material.id, index, newCoordinates)
              }
              onFieldEdit={(index, fieldName, editedValue) =>
                this.handleFieldEdit(material.id, index, fieldName, editedValue)
              }
            />
          ))}
        </div>
      </div>
    );
  }
}

export default Home;