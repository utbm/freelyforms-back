import React from 'react';

type LocationType = {
  x: number;
  y: number;
  radius: number;
  address: string;
};

export default class Location extends React.Component<LocationType> {
  getType() {
    return this.props.radius > 50 ? 'Large' : 'Small';
  }
}
