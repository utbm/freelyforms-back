<template>
  <div class="px-4 d-inline-block text-center">
    <div>Add new entry</div>
    <b-button
      :disabled="!edit"
      class="my-2 ff-add-icon-button"
      @click="goToNewEntry"
    >
      <b-icon icon="plus" aria-label="Add new entry"></b-icon>
    </b-button>
  </div>
</template>

<script>
import Vue from "vue";

export default {
  name: "FFListEntryNew",
  props: {
    url: {
      type: String,
      required: true,
    },
  },
  methods: {
    goToNewEntry: function () {
      window.location.href = this.url;
    },
  },
  computed: {
    edit: function () {
      return Vue.ff.config.edit;
    },
  },
};
</script>

<style>
.ff-add-icon-button {
  width: 42px;
  height: 42px;
  line-height: 42px !important;
  padding: 0 !important;
  text-align: center !important;
  border-radius: 50% !important;
}
</style>
