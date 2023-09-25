<template>
  <b-form-group>
    <b-form-radio-group
      size="sm"
      v-model="filterChosen"
      :options="filterOptions"
      name="radios-btn-default"
    ></b-form-radio-group>
    <FFDatePicker :schema="schema" v-model="filterValue" class="mt-2" />
  </b-form-group>
</template>

<script>
import FFDatePicker from "../../form/FFDatePicker.vue";
export default {
  name: "FFFilterDate",
  components: {
    FFDatePicker,
  },
  props: {
    value: {
      required: false,
    },
  },
  data: function () {
    return {
      filters: {
        input: ["==", "!=", "<", ">"],
      },
      filterChosen: "==",
      filterValue: undefined,
      schema: {
        dark: this.$store.ff.config.dark,
        size: "sm",
        format: "timestamp",
      },
    };
  },
  computed: {
    filterOptions: function () {
      return Object.values(this.filters).flat();
    },
    outValue: function () {
      return [this.filterChosen, this.filterValue];
    },
  },
  watch: {
    value: function (newValue) {
      if (newValue === undefined) return;
      this.filterChosen = newValue[0];
      this.filterValue = newValue[1];
    },
    outValue: function (newValue, oldValue) {
      if (newValue != oldValue) {
        this.$emit(
          "input",
          !this.filterValue || isNaN(this.filterValue) ? undefined : newValue // fixes bug where filterValue is not outed correctly
        );
      }
    },
  },
};
</script>
