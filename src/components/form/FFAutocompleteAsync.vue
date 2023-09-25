<template>
  <FFAutocomplete
    v-bind="$props"
    @change="onSearchChanged"
    :loadingSearch="loadingSearch"
    @validated="onValidate"
  />
</template>

<script>
import Vue from "vue";
import FFAutocomplete from "./FFAutocomplete.vue";
import { abstractField } from "vue-form-generator";
import axios from "axios";

export default {
  inheritAttrs: false,
  mixins: [abstractField],
  name: "ff-autocomplete-async",
  components: { FFAutocomplete },
  data: function () {
    return {
      loadingSearch: false,
    };
  },
  watch: {
    schema: {
      handler(sch) {
        if (!sch.debounce) {
          sch.debounce = sch.debounce || 300;
          this.schema = sch;
        }
      },
      deep: true,
      immediate: true,
    },
  },
  methods: {
    onSearchChanged: function (search, key) {
      if (search === "") return;
      // if(!key) return;
      this.loadingSearch = true;

      const baseURL = Vue.ff.config.baseURL;
      const database = "fastandform";
      const entityName = "contributor";

      axios
        .get(
          `${baseURL}/${database}/${entityName}?filter=${key}.includes.${search}`
        )
        .then((res) => {
          Vue.set(this.schema, "options", [
            ...this.schema.options,
            ...res.data,
          ]);
        })
        .catch((err) => {
          console.error(err);
        })
        .finally(() => {
          this.loadingSearch = false;
        });
    },
    onValidate: function (...args) {
      this.$emit("validated", args[0], args[1], args[2]);
    },
  },
};
</script>
