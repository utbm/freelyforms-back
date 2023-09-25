<template>
  <div class="ff-toggable-filter">
    <b-form-checkbox v-model="status">
      {{ prop.displayName }}
    </b-form-checkbox>
    <FFFilterString
      v-if="prop.type === 'string'"
      :prop="prop"
      :disabled="!status"
      v-model="filterValue"
      class="mt-2"
    />
    <FFFilterNumber
      v-if="prop.type === 'number'"
      :prop="prop"
      :disabled="!status"
      v-model="filterValue"
      class="mt-2"
    />
    <FFFilterArray
      v-if="prop.type === 'array'"
      :prop="prop"
      :disabled="!status"
      v-model="filterValue"
      class="mt-2"
    />
    <FFFilterDate
      v-if="prop.type === 'date'"
      :prop="prop"
      :disabled="!status"
      v-model="filterValue"
      class="mt-2"
    />
  </div>
</template>

<script>
import FFFilterArray from "./FFFilterArray.vue";
import FFFilterString from "./FFFilterString.vue";
import FFFilterDate from "./FFFilterDate.vue";
import FFFilterNumber from "./FFFilterNumber.vue";

export default {
  name: "FFToggableFilter",
  components: { FFFilterString, FFFilterArray, FFFilterDate, FFFilterNumber },
  props: {
    propName: {
      type: String,
      required: true,
    },
    prop: {
      type: Object,
      required: true,
    },
    value: {
      required: false,
    },
  },
  data: function () {
    return {
      filterValue: this.value,
      status: false,
    };
  },
  watch: {
    value: function (newValue) {
      if (newValue === undefined) return;
      this.filterValue = newValue;
    },
    filterValue: function (newValue) {
      this.$emit("input", newValue);
    },
    status: function (newValue) {
      this.$emit("input", newValue ? this.filterValue : undefined);
      this.$forceUpdate();
    },
  },
};
</script>

<style>
.ff-toggable-filter .btn-group-toggle {
  width: 100%;
}
</style>
