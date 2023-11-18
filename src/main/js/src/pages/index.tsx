import React from 'react';
import { MapContainer, TileLayer } from 'react-leaflet';
import Material from './Material';
import './leaflet.css';
import './styles.css';

type MaterialType = {
  id: string;
  type: string;
  location: string;
  fields: { name: string; type: string; data: any }[];
};

type AppState = {
  materials: MaterialType[];
};

class Home extends React.Component<{}, AppState> {
  constructor(props: {}) {
    super(props);
    this.state = {
      materials: [
        {
          id: '1',
          type: 'Type 1',
          location: 'Location 1',
          fields: [
            { name: 'Field 1', type: 'string', data: 'Data 1' },
            { name: 'Field 2', type: 'number', data: 42 },
          ],
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
          { name: 'Field 1', type: 'string', data: 'abcsz' },
          { name: 'Field 2', type: 'number', data: 42 },
        ] },
      ],
    };
  }

  render() {
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
