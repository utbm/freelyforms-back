<div align="center">
  <img src="public/favicon.ico" height="64">
  <h1>Fast & Form Vue</h1>

  <div>
    <i>Vue.js implementation of the Fast&Form frontend components</i>
  </div>
</div>

<br>

## User install

Just a few lines to insall the module:
```
npm install fast-and-form-vue
```

And insert in your favorite application the following lines:

```js
// app.js
import FastAndForm from "./FastAndForm";

Vue.use(FastAndForm, {
  dark: false,
  baseURL: "http://<fastandformbackend.com>/",
  edit: true,
  parameters: [require('path/to/parameterFile.json')]
});
```

---

## Project setup
```
npm install
```

### Compiles and hot-reloads for development
```
npm run serve
npm run json-serverX
```