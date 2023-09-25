<template>
  <b-button :disabled="true" v-b-tooltip.hover="'Import data'">
    <b-icon icon="box-arrow-in-down" aria-label="Import data"></b-icon>
    Import
  </b-button>
</template>

<script>
export default {
  name: "FFList-data-import",
};
</script>
