<template>
  <b-form-group>
    <b-form-select
      v-model="selected"
      :options="filterOptions"
      multiple
      :select-size="4"
    ></b-form-select>
  </b-form-group>
</template>

<script>
import Vue from "vue";

export default {
  name: "FFFilterArray",
  props: {
    value: {
      required: false,
    },
    prop: {
      required: true,
    },
  },
  data: function () {
    return {
      selected: [],
    };
  },
  computed: {
    filterOptions: function () {
      let res = [];

      try {
        res = this.prop.rules.filter((e) => (e.type = "values"))[0].options
          .values;
      } catch (err) {
        // console.error(err);
      }

      return res;
    },
    filterValue: function () {
      return this.selected;
    },
    dark: function () {
      return Vue.ff.config.dark;
    },
  },
  watch: {
    value: function (newValue) {
      if (newValue === undefined) return;
      this.selected = newValue;
    },
    filterValue: function (newValue) {
      this.$emit("input", newValue);
    },
  },
};
</script>
