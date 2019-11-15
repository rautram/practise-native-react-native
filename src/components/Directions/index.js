import React from "react";
import MapViewDirections from "react-native-maps-directions";

const Directions = ({ destination, origin, onReady }) => (
  <MapViewDirections
    destination={destination}
    origin={origin}
    onReady={onReady}
    apikey="AIzaSyCrwtzPXUHUiD2gDZwK9gbWL19ofgF8MOU"
    strokeWidth={3}
    strokeColor="#222"
  />
);

export default Directions;
