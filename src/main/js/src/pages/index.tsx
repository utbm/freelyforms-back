import React, { useEffect, useState } from 'react';
import { MapContainer, TileLayer, Marker, Popup, Circle } from 'react-leaflet';
import L from 'leaflet';
import Material from './Material';
import './leaflet.css';
import './styles.css';
import Location from './Location';

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
              radius: 100,
              address: 'Location 1 Address',
            },
            {
              x: 51.53,
              y: -0.09,
              radius: 600,
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
              radius: 400,
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
      assignedColors: {}, // New array to track assigned colors
      colorIndex:0,
    };
  }

  componentDidMount() {
    this.getMaterials();
  }

  getMaterials() {
      const fetchData = async () => {
        try {
          const response = await fetch('http://localhost:8080/api/prefabs/materials');
          console.log('Response:', response);
          const result = await response.json();
          console.log('Data:', result);
        } catch (error) {
          console.error('Error fetching data:', error);
        }
      };
  
      fetchData();

  }

  

handleCreateMaterial = () => {
  // Create a new material with default values
  const newMaterial = {
    id: Date.now().toString(), // Generate a unique ID (you can use a library like uuid for better uniqueness)
    type: 'New Type', // Set default type
    fields: [
      { name: 'Default Field', type: 'string', data: 'Default Data' },
    ],
    locations: [
      { x: 51.5, y: -0.1, radius: 200, address: 'Default Location Address' },
    ],
  };

  // Update the state to include the new material
  this.setState((prevState) => ({
    materials: [...prevState.materials, newMaterial],
  }));
};

handleDeleteMaterial = (materialId) => {
  // Update the state to remove the selected material
  this.setState((prevState) => ({
    materials: prevState.materials.filter((material) => material.id !== materialId),
  }));
};



  handleLocationChange = (
    materialId: string,
    locationIndex: number,
    newCoordinates: { x: number; y: number }
  ) => {
    this.setState((prevState: { materials: any[]; }) => {
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
    this.setState((prevState: { materials: any[]; }) => {
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



handleAddLocation = (materialId, newLocationData) => {
  this.setState((prevState) => {
    const updatedMaterials = prevState.materials.map((material) => {
      if (material.id === materialId && material.locations) {
        return {
          ...material,
          locations: [...material.locations, newLocationData],
        };
      }

      return material;
    });

    return {
      materials: updatedMaterials,
    };
  });
};

handleDeleteLocation = (materialId, locationIndex) => {
  this.setState((prevState) => {
    const updatedMaterials = prevState.materials.map((material) => {
      if (material.id === materialId && material.locations) {
        const updatedLocations = [...material.locations];
        updatedLocations.splice(locationIndex, 1);

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
    this.setState((prevState: { materials: any[]; }) => {
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

  getRandomColor() {
    const colors = ['blue', 'red', 'green', 'orange', 'black', 'grey', 'yellow', 'violet'];
    const randomIndex = Math.floor(Math.random() * colors.length);
    return colors[randomIndex];
  }

  getColorCode (color: string){
    switch (color) {
      case 'blue':
        return '#2A81CB';
      case 'red':
        return '#CB2B3E';
      case 'green':
        return '#2AAD27';
      case 'orange':
        return '#CB8427';
      case 'black':
        return '#3D3D3D';
      case 'grey':
        return '#7B7B7B';
      case 'yellow':
        return '#CAC428';
      case 'violet':
        return '#9C2BCB';
      default:
        return '#0000FF'; // Handle other cases or return a default colorr
    }
  }

  handleSetManually = (materialId: string, locationIndex: number) => {
    this.setState({
      manualSetMaterialId: materialId,
      manualSetLocationIndex: locationIndex,
    });
  };

  handleMapClick = (event: any) => {
    const { manualSetMaterialId, manualSetLocationIndex } = this.state;

    if (manualSetMaterialId !== null && manualSetLocationIndex !== null) {
      const newCoordinates = { x: event.latlng.lat, y: event.latlng.lng };
      this.handleLocationChange(manualSetMaterialId, manualSetLocationIndex, newCoordinates);

      // Clear manual set state
      this.setState({
        manualSetMaterialId: null,
        manualSetLocationIndex: null,
      });
    }
  };

  render() {
    return (
      <div style={{ display: 'flex' }}>
        <MapContainer
          center={[51.505, -0.09]}
          zoom={13}
          style={{ height: '900px', width: '50%' }}
          onClick={this.handleMapClick}
        >
          <TileLayer
            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
            attribution='© <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
          />
          {this.state.materials.map((material: { locations: any[]; id: string | number | boolean | React.ReactElement<any, string | React.JSXElementConstructor<any>> | Iterable<React.ReactNode> | null | undefined; }) =>
            material.locations &&
            material.locations.map((location, index) => {
              // Assign a color to the material if not assigned yet
              if (!this.state.assignedColors[material.id]) {
                const color = this.getRandomColor();

                this.setState((prevState) => ({
                  assignedColors: {
                    ...prevState.assignedColors,
                    [material.id]: color,
                  },
                }));
              }

              return (
                <React.Fragment key={`${material.id}_${index}`}>
                  <Marker
                    key={`${material.id}_${index}`}
                    position={[location.x, location.y]}
                    icon={
                      L.icon({
                        iconUrl: `https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-${this.state.assignedColors[material.id]}.png`,
                        shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
                        iconSize: [25, 41],
                        iconAnchor: [12, 41],
                        popupAnchor: [1, -34],
                        shadowSize: [41, 41],
                      }) as any
                    }
                  >
                    <Popup
                      onOpen={() => this.handlePopupToggle(material.id, index)}
                      onClose={() => this.handlePopupToggle(material.id, index)}
                    >
                      ID: {material.id}
                      <br />
                      Location {index + 1}:
                      <br />
                      <button className='btn_set_coordinates'
                      onClick={() => this.handleSetManually(material.id, index)}
                    >
                      Set Manually
                    </button>
                      {Object.entries(location).map(([fieldName, fieldValue]) => (
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
                      ))}
                    </Popup>
                  </Marker>
                  {location.radius !== 0 && (
                    <Circle
                      key={`${material.id}_${index}_circle_${location.radius}`}
                      center={[location.x, location.y]}
                      radius={location.radius}
                      pathOptions={{ color: this.getColorCode(this.state.assignedColors[material.id]) }}
                    />
                  )}
                </React.Fragment>
              );
            })
          )}
        </MapContainer>
        <button className="btn_add_material" onClick={this.handleCreateMaterial}>
    Add New Material
  </button>
        <div style={{ width: '50%' }}>
          {this.state.materials.map((material: { id: any; type?: string; location?: string; fields?: { name: string; type: string; data: any; }[]; locations?: Location[] | undefined; }) => (
            <Material
              key={material.id}
              material={material}
              onLocationChange={(index, newCoordinates) =>
                this.handleLocationChange(material.id, index, newCoordinates)
              }
              onFieldEdit={(index, fieldName, editedValue) =>
                this.handleFieldEdit(material.id, index, fieldName, editedValue)
              }
              onDelete={() => this.handleDeleteMaterial(material.id)} // Add this line
              onAddLocation={this.handleAddLocation} // Pass the callback here
              onDeleteLocation={(index) => this.handleDeleteLocation(material.id, index)} // Pass the material ID and index here
              />
          ))}
        </div>
      </div>
    );
  }
}

export default Home;