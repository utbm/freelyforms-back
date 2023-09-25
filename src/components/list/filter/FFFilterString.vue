<template>
  <b-form-group>
    <b-form-radio-group
      size="sm"
      v-model="filterChosen"
      :options="filterOptions"
      name="radios-btn-default"
    ></b-form-radio-group>
    <b-form-input
      v-model="text"
      placeholder="Value..."
      class="my-2"
    ></b-form-input>
  </b-form-group>
</template>

<script>
export default {
  name: "FFFilterString",
  props: {
    value: {
      required: false,
    },
  },
  data: function () {
    return {
      filters: {
        input: ["==", "!=", "Ends with", "Starts With", "regex"],
        // list: ["in", "out"],
      },
      filterChosen: "==",
      text: "",
    };
  },
  computed: {
    filterOptions: function () {
      return Object.values(this.filters).flat();
    },
    filterValue: function () {
      return [this.filterChosen, this.text];
    },
  },
  watch: {
    value: function (newValue) {
      if (newValue === undefined) return;
      this.filterChosen = newValue[0];
      this.text = newValue[1];
    },
    filterValue: function (newValue) {
      this.$emit("input", newValue);
    },
  },
};
</script>
