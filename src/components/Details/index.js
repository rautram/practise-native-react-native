import React, { Component } from "react";

import {
  Container,
  TypeTitle,
  TypeDescription,
  TypeImage,
  RequestButton,
  RequestButtonText
} from "./styles";

import uberx from "../../assets/uberx.png";

export default class Details extends Component {
  render() {
    return (
      <Container>
        <TypeTitle>Popular</TypeTitle>
        <TypeDescription>Your Destinations</TypeDescription>

        <TypeImage source={uberx} />
        <TypeTitle>Janata Cabs</TypeTitle>
        <TypeDescription>Rs.100</TypeDescription>

        <RequestButton onPress={() => {}}>
          <RequestButtonText>Request Ride</RequestButtonText>
        </RequestButton>
      </Container>
    );
  }
}
