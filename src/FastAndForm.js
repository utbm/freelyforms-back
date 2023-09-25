import { BootstrapVue, IconsPlugin } from "bootstrap-vue";
import merge from "deepmerge";
// Import Bootstrap an BootstrapVue CSS files (order is important)
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue/dist/bootstrap-vue.css";
import "vue-form-generator/dist/vfg-core.css"; // optional core css additions

// import components
import FFList from "./components/list/FFList.vue";
import FFForm from "./components/form/FFForm.vue";

/**
 * @typedef {Object} FFOptions
 * @property {Boolean} dark Puts fast and form components in dark mode
 * @property {Boolean} edit Sets in edit mode
 * @property {String} database Default database name
 * @property {String} baseURL Backend root url for data manipulation
 * @property {Object[]} parameters Parameter file content objects
 * @property {Object} entities Entities objects
 */

const defaultOptions = {
  dark: false,
  edit: true,
  baseURL: undefined,
  database: "fastandform",
  parameters: [],
  entities: {},
};

const ffVue = {
  /**
   *
   * @param {import('vue')} Vue Vue instance
   * @param {FFOptions} options Install options
   */
  install(Vue, options) {
    if (!options) options = defaultOptions;
    else options = merge(defaultOptions, options);
    // Make BootstrapVue available throughout your project
    Vue.use(BootstrapVue);
    // Optionally install the BootstrapVue icon components plugin
    Vue.use(IconsPlugin);

    // add both components
    Vue.component(FFForm);
    Vue.component(FFList);

    // add a global fast and form variable
    Vue.ff = {
      data: {
        entities: {},
        forms: {},
        locales: {},
        rules: {},
      },
      config: {
        edit: options.edit || defaultOptions.edit,
        dark: options.dark || defaultOptions.dark,
        baseURL: options.baseURL || defaultOptions.baseURL,
        database: options.database || defaultOptions.database,
        jsonserver: true, // TODO: Remove this line when switching to fast and form and baseURL
      },
    };

    // add methods
    /**
     * Entity setter
     * @param {String} name Entity name
     * @param {Object} entity Entity object
     */
    Vue.loadEntity = function (name, entity) {
      if (!name && !entity) return; // if non true, it's a safe return

      // else throw error if missing
      if (!name && entity.name) name = entity.name;
      if (!name) throw new Error("No name provided, got " + name);
      if (!entity) throw new Error("No object provided for " + name);

      this.ff.data.entities[name] = entity;
    };

    /**
     * Entity getter
     * @param {String} name Entity name searched
     * @returns Given entity with complete rules
     */
    Vue.getEntity = function (name) {
      if (!this.ff.data.entities[name])
        throw new Error("No object found for " + name);
      return this.ff.data.entities[name];
    };

    // add methods
    /**
     * Form setter
     * @param {String} name Form name
     * @param {Object} form Form object
     */
    Vue.loadForm = function (name, form) {
      if (!name && !form) return; // if non true, it's a safe return

      // else throw error if missing
      if (!name && form.name) name = form.name;
      if (!name) throw new Error("No name provided, got " + name);
      if (!form) throw new Error("No object provided for " + name);

      this.ff.data.forms[name] = form;
    };

    /**
     * Form getter
     * @param {String} name Form name searched
     * @returns Given Form
     */
    Vue.getForm = function (name) {
      if (!this.ff.data.forms[name])
        throw new Error("No form found for " + name);
      return this.ff.data.forms[name];
    };

    /**
     *
     * @param {Object} parameters Parameters object
     */
    Vue.loadParameters = function (parameters) {
      let keys;
      if (parameters.entities) {
        keys = Object.keys(parameters.entities);
        keys.forEach((k) => {
          Vue.loadEntity(k, parameters.entities[k]);
        });
      }
      if (parameters.forms) {
        keys = Object.keys(parameters.forms);
        keys.forEach((k) => {
          Vue.loadForm(k, parameters.forms[k]);
        });
      }
    };

    options.parameters.forEach((par) => {
      Vue.loadParameters(par);
    });

    Object.keys(options.entities).forEach((k) => {
      Vue.loadEntity(k, options.entities[k]);
    });

    const ff = Vue.observable({ ff: Vue.ff });
    Vue.prototype.$store = ff;
  },
};

// Automatic installation if Vue has been added to the global scope.
if (typeof window !== "undefined" && window.Vue) {
  window.Vue.use(ffVue);
}

export default ffVue;
