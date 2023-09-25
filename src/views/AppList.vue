<template>
  <div>
    <div class="text-right">
      <button class="btn btn-secondary mb-1" @click="switchEditMode">
        <b-icon :icon="edit ? 'pencil-fill' : 'book-fill'"></b-icon>
        <span> {{ (edit ? "Edit" : "Read") + " mode" }}</span>
      </button>
    </div>
    <FFList
      :newEntryURL="'/#/form/' + this.$route.params.entity"
      :entityName="this.$route.params.entity"
    />
  </div>
</template>

<script>
import Vue from "vue";
import FFList from "../components/list/FFList.vue";

export default {
  name: "app-list",
  components: { FFList },
  computed: {
    edit: function () {
      return Vue.ff.config.edit;
    },
  },
  methods: {
    switchEditMode: function () {
      Vue.ff.config.edit = !Vue.ff.config.edit;
    },
  },
};
</script>

<style>
body {
  overflow-y: scroll;
}
</style>
