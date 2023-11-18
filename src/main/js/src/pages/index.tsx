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
          locationDetails: {
            x: 51.52,
            y: -0.09,
            radius: 10,
            address: 'Location 1 Address',
          },
        },
        {
          id: '2',
          type: 'Type 2',
          location: 'Location 2',
          fields: [
            { name: 'Field 1', type: 'boolean', data: "true" },
            { name: 'Field 2', type: 'string', data: 'Data 2' },
          ],
        },
        { id: '3', type: 'Type 3', location: 'Location 3', fields: [
          { name: 'Field 1', type: 'string', data: 'Data 3' },
          { name: 'Field 2', type: 'number', data: 48 },
        ] },
      ],
    };
  }

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
          {this.state.materials.map((material) => (
            <Marker
              key={material.id}
              position={[material.locationDetails?.x || 0, material.locationDetails?.y || 0]}
              icon={blueIcon}
            >
              
              <Popup>{material.id}</Popup>
            </Marker>

            
          ))}
        </MapContainer>
        <div style={{ width: '50%' }}>
          {this.state.materials.map((material) => (
            <Material key={material.id} material={material} />
          ))}
        </div>
      </div>
    );
  }
}

export default Home;
