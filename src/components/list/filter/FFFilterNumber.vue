<template>
  <b-form-group>
    <b-form-radio-group
      size="sm"
      v-model="filterChosen"
      :options="filterOptions"
      name="radios-btn-default"
    ></b-form-radio-group>
    <b-form-input
      v-model="number"
      @keypress="isNumber"
      placeholder="Value..."
      type="number"
      class="my-2"
    ></b-form-input>
  </b-form-group>
</template>

<script>
import Vue from "vue";

export default {
  name: "FFFilterNumber",
  props: {
    value: {
      required: false,
    },
  },
  data: function () {
    return {
      filters: {
        input: ["==", "!=", ">", "<"],
      },
      filterChosen: "==",
      number: 0,
    };
  },
  computed: {
    filterOptions: function () {
      return Object.values(this.filters).flat();
    },
    filterValue: function () {
      return [this.filterChosen, this.number];
    },
  },
  watch: {
    number: function (n, o) {
      if (n === "") {
        this.number = o;
      }
    },
    value: function (newValue) {
      if (newValue === undefined) return;
      this.filterChosen = newValue[0];
      this.number = newValue[1];
    },
    filterValue: function (newValue) {
      this.$emit("input", newValue);
    },
  },
  methods: {
    isNumber(evt) {
      // number regex
      // -.5
      // +2.5
      // 5
      Vue.nextTick(() => {
        const number = evt.target.value;
        console.log(number, this.number);
        if (this.number != number) {
          //frferf
        }
      });
    },
  },
};
</script>
