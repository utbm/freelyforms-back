/* eslint-disable */
<template>
  <div>
    <div v-if="loading" class="text-center">
      <b-spinner small variant="primary" label="Loading data" /> Loading data...
    </div>
    <template v-else>
      <vue-form-generator
        :schema="finalSchema"
        :model="content"
        :options="formOptions"
        @validated="onValidated"
      ></vue-form-generator>
      <div class="text-right">
        <b-button
          variant="warning"
          v-on:click="reset"
          v-b-tooltip.hover
          class="mr-1"
          title="Resets form to original values"
        >
          Reset
        </b-button>
        <b-button
          variant="danger"
          v-on:click="clear"
          v-b-tooltip.hover="'Clears values to default values'"
          class="mx-1"
        >
          Clear </b-button
        ><b-button
          v-if="cancelButton"
          variant="secondary"
          v-on:click="cancel"
          v-b-tooltip.hover="'Returns to last page'"
          class="mx-1"
        >
          Cancel </b-button
        ><b-button
          variant="primary"
          @click="submit"
          v-b-tooltip.hover="'Submits and sends the result'"
          class="ml-1"
          :disabled="!isValid"
        >
          Submit
        </b-button>
      </div>
    </template>
    <div class="mt-3">
      <b-alert
        style="max-height: 220px; overflow: auto"
        v-model="alert.show"
        :variant="alert.variant"
        dismissible
      >
        {{ alert.message }}
      </b-alert>
    </div>
    <div class="mt-3">
      <button v-on:click="getDataList">Get Data</button>
      <div>{{ dataList }}</div>
    </div>
  </div>
</template>

<script>
import ToastMixin from "../../mixins/ToastMixin"; // mixin to show toasts for error for example
import CRUDMixin from "../../mixins/CRUDMixin"; // mixin to show make CRUD operations on Fast&Form data
import Vue from "vue";
import VueFormGenerator from "vue-form-generator";

Vue.use(VueFormGenerator);

import FFDatePicker from "./FFDatePicker.vue";

Vue.component("fieldDatePicker", FFDatePicker);
import FFAutocomplete from "./FFAutocomplete.vue";
Vue.component("fieldAutocomplete", FFAutocomplete);
import FFAutocompleteAsync from "./FFAutocompleteAsync.vue";
Vue.component("fieldAutocompleteAsync", FFAutocompleteAsync);

import { validators } from "vue-form-generator";
import axios from "axios";

import { cloneDeep } from "lodash";

export default {
  mixins: [ToastMixin, CRUDMixin],
  name: "ff-form",
  props: {
    id: {
      required: false,
      default: function () {
        return undefined;
      },
    },
    entityName: {
      required: true,
      type: String,
    },
    formName: {
      required: true,
      type: String,
    },
    cancelButton: {
      required: false,
      type: Boolean,
      default: function () {
        return true;
      },
    },
    cancelURL: {
      required: false,
      type: String,
      default: function () {
        return undefined;
      },
    },
  },
  data: function () {
    return {
      dataList: [],

      content: {},

      formOptions: {
        validateAfterLoad: true,
        validateAfterChanged: true,
        validateAsync: true,
      },
      isValid: false,
      errors: [],
      alert: {
        show: false,
        variant: "primary",
        message: "",
      },
      loading: true,

      original: undefined,
    };
  },
  computed: {
    model: function () {
      return 1;
    },
    byDefault: function () {
      return this.schema.byDefault;
    },
    finalByDefault: function () {
      const entity = Vue.getEntity(this.entityName);

      let copy = cloneDeep(this.byDefault);

      let prop, val;
      Object.keys(copy).forEach((property) => {
        prop = entity.properties[property];

        // happens with id for example
        if (prop === undefined) return;

        val = copy[property];

        if (prop.type === "date" && val === "today") {
          copy[property] = new Date();

          if (prop.format === "timestamp") {
            copy[property] = copy[property].getTime();
          } else if (prop.format === "unix") {
            copy[property] = copy[property].getTime() / 1000;
          } else if (prop.format === "utc") {
            copy[property] = copy[property].toUTCString();
          } else if (prop.format === "iso") {
            copy[property] = copy[property].toISOString();
          }
        }
      });

      return copy;
    },
    finalSchema: function () {
      let result = cloneDeep(this.schema);
      result.fields = this.toArr(result.fields);

      for (let i = 0; i < result.fields.length; ++i) {
        result.fields[i] = this.addValidator(result.fields[i]);
      }

      result.groups = this.toArr(result.groups);

      let group;
      for (let i = 0; i < result.groups.length; ++i) {
        group = result.groups[i];
        for (let j = 0; j < group.length; ++j) {
          group.fields[j] = this.addValidator(group.fields[j]);
        }
        result.groups[i] = group;
      }

      return result;
    },
    isNewForm: function () {
      return !this.id;
    },
    schema: function () {
      return Vue.getForm(this.formName);
    },
  },
  methods: {
    /**
     * Reset changes fields to their original values, the ones before being saved
     */
    reset: function () {
      let copy = cloneDeep(this.content);
      for (let x in copy) {
        copy[x] = this.original[x];
      }
      Vue.set(this, "content", copy);
    },
    /**
     * Clear erases all the values to the default model chosen
     */
    clear: function () {
      let copy = cloneDeep(this.content);
      for (let x in copy) {
        copy[x] = this.finalByDefault[x];
      }
      Vue.set(this, "content", copy);
    },

    cancel: function () {
      if (this.cancelURL) {
        window.location.href = this.cancelURL;
      } else {
        window.history.back();
      }
    },
    onValidated: function (isValid, errors) {
      this.isValid = isValid; // disables submit button
      this.errors = errors; // groups errors where we can access them
    },
    toArr: function (el) {
      if (el === undefined) return [];
      if (Array.isArray(el)) return el;
      return [el];
    },
    append: function (el, cur) {
      const arr = this.toArr(el);
      if (arr.indexOf(cur) == -1) arr.push(cur);
      return arr;
    },
    addValidator(field) {
      let result = [];
      // fix select required tag
      if (field.required) {
        if (field.type !== "input") {
          result = this.append(result, validators.required);
        }
        if (field.type.toLowerCase() === "datepicker") {
          result = this.append(result, validators.date);
        } else if (field.type.toLowerCase() === "checklist") {
          result = this.append(result, validators.array);
        }
      }

      // validators are stored as strings

      if (field.validator) {
        if (typeof field.validator === "string") {
          const vfg_validator = VueFormGenerator.validators[field.validator];
          if (vfg_validator) result = this.append(result, vfg_validator);
        } else {
          result = this.append(result, field.validator);
        }

        if (Array.isArray(field.validator)) {
          field.validator.forEach((validator) => {
            if (typeof validator === "string") {
              const vfg_validator = VueFormGenerator.validators[validator];
              if (vfg_validator) result = this.append(result, vfg_validator);
            } else {
              result = this.append(result, validator);
            }
          });
        }
      }

      // apply validator
      field.validator = result;
      return field;
    },
    submit: function () {
      if (!this.isValid) return;

      const submitPromise = this.isNewForm
        ? this.ff_create(this.entityName, this.content)
        : this.ff_update(this.entityName, this.id, this.content);

      submitPromise
        .then(() => {
          this.showToastSuccess(
            JSON.stringify(this.content),
            "Success : The entry has been saved:"
          );
        })
        .catch((err) => {
          console.error(err);
          this.showToastDanger(
            err.message,
            "Error : The form has not been saved"
          );
        });
    },
    getDataList: function () {
      return axios
        .get(this.url)
        .then((response) => (this.dataList = response.data));
    },
    changeContent: function () {
      let copy;
      // if I have no ID it means it is a new entry so put default value
      if (this.id === undefined) {
        copy = cloneDeep(this.finalByDefault);
        Vue.set(this, "content", copy);
        Vue.set(this, "original", copy);

        this.loading = false;
        this.$forceUpdate();
      } else {
        this.getOriginal()
          .catch((err) => {
            console.error(err);
            this.showToastDanger(
              err.message,
              "Error : The form could not get the original value"
            );
          })
          .finally(() => {
            this.$forceUpdate();
          });
      }
    },
    getOriginal: function () {
      return this.ff_read(this.entityName, this.id)
        .then((res) => {
          if (typeof res !== "object") return Promise.reject(res.data);

          let data = res.data;
          console.log(data);
          Vue.set(this, "content", data);
          Vue.set(this, "original", data);
        })
        .catch((err) => {
          this.alert = {
            variant: "danger",
            message: JSON.stringify(err),
            show: true,
          };

          return Promise.reject(err);
        })
        .finally(() => {
          this.loading = false;
        });
    },
  },
  mounted: function () {
    this.changeContent();
  },
  watch: {
    content: {
      handler: function () {
        this.$emit("input", this.content);
      },
      deep: true,
      immediate: true,
    },
    id: {
      handler: function () {
        this.changeContent();
      },
      immediate: true,
    },
    title: {
      handler: function () {
        //changement de page de formulaire
        this.clear();
        this.getDataList();
        this.showAlert = false;
      },
    },
  },
};
</script>

<style>
.listbox.form-control,
.combobox.form-control {
  height: auto;
}
</style>
