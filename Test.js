import React from "react";
import { Text, View, StyleSheet, requireNativeComponent } from "react-native";

const FrameLayoutView = requireNativeComponent("FrameLayoutView");

class Test extends React.Component {
  render() {
    return (
      <View style={styles.main}>
        <FrameLayoutView>
          <Text style={{ fontSize: 40 }}>Hello World</Text>
        </FrameLayoutView>
      </View>
    );
  }
}

export default Test;

const styles = StyleSheet.create({
  main: {
    flex: 1
  }
});
