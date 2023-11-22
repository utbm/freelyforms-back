import React from 'react';

type LocationType = {
  x: number;
  y: number;
  radius: number;
  address: string;
};

export default class Location extends React.Component<LocationType> {
  render() {
    return (
      <div>
        <div>X: {this.props.x}</div>
        <div>Y: {this.props.y}</div>
        <div>Rayon : {this.props.radius}</div>
        <div>Adresse: {this.props.address}</div>
      </div>
    );
  }
}
