// pour faire tourner la machine

// npm install
// npm install vue-form-generator
// npm run serve

import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import FastAndForm from "./FastAndForm";

const THEME_KEY = "theme_key";

Vue.use(FastAndForm, {
  dark: localStorage.getItem(THEME_KEY, false) === "true",
  baseURL: "http://localhost:3004/Form_",
  edit: true,
  parameters: [require("./assets/parameters.json")],
});

Vue.config.productionTip = false;

// eslint-disable-next-line no-unused-vars
const v = new Vue({
  router,
  render: (h) => h(App),
}).$mount("#app");
