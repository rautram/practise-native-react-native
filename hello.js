import React from "react";
import { Text, View, StyleSheet, AppRegistry } from "react-native";

class Hello extends React.Component {
  render() {
    return (
      <View style={{ flex: 1 }}>
        <View style={styles.header}>
          <Text style={styles.text}>Hello from React Native Code</Text>
        </View>
      </View>
    );
  }
}

export default Hello;

const styles = StyleSheet.create({
  header: {
    height: 60,
    backgroundColor: "blue",
    justifyContent: "center",
    elevation: 3
  },
  text: {
    fontFamily: "Roboto",
    color: "white",
    fontWeight: "bold"
  }
});
