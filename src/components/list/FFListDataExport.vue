<template>
  <b-button :disabled="true" v-b-tooltip.hover="'Export data'">
    <b-icon icon="box-arrow-down" aria-label="Export data"></b-icon>
    Export
  </b-button>
</template>

<script>
export default {
  name: "FFList-data-export",
};
</script>
