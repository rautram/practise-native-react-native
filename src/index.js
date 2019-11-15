// import React from "react";
// import Map from "./components/Map";

// const App = () => <Map />;

// export default App;

import React from "react";
import { Text, View, StyleSheet, NativeModules, Button } from "react-native";

class App extends React.Component {
  render() {
    return (
      <View style={{ flex: 1 }}>
        <Button
          title="Click Me"
          onPress={() => NativeModules.ActivityStarter.navigateTo()}
        />
      </View>
    );
  }
}

export default App;
