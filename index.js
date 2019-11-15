/** @format */

import { AppRegistry } from "react-native";
import App from "./src";
import Test from "./Test";
import { name as appName } from "./app.json";
import Hello from "./hello";

AppRegistry.registerComponent(appName, () => App);
AppRegistry.registerComponent("Hello", () => Hello);
