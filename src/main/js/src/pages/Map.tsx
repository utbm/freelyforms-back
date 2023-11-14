import React from 'react';
import Material from './Material';
import Location from './Location';

export default class Map extends React.Component {
  materialList: Material[] = [];
  locationList: Location[] = [];

  addMaterial(material: Material) {
    this.materialList.push(material);
  }

  removeMaterial(material: Material) {
    const index = this.materialList.indexOf(material);
    if (index > -1) {
      this.materialList.splice(index, 1);
    }
  }

  addLocation(location: Location) {
    this.locationList.push(location);
  }

  removeLocation(location: Location) {
    const index = this.locationList.indexOf(location);
    if (index > -1) {
      this.locationList.splice(index, 1);
    }
  }
}
