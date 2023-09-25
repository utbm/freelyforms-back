<template>
  <b-card
    class="ff-filter-card"
    :bg-variant="$store.ff.config.dark ? 'dark' : ''"
  >
    <h4 class="pl-3 pt-1">Filter zone</h4>
    <span style="position: absolute; top: 0; right: 0; opacity: 0.7">{{
      value
    }}</span>
    <div class="container">
      <div
        v-for="(propLine, index) in propertiesDivided"
        :key="index"
        class="row"
      >
        <div class="col-4" v-for="(prop, propKey) in propLine" :key="propKey">
          <FFToggableFilter
            :prop="prop"
            :propName="propKey"
            v-model="filters[propKey]"
          />
        </div>
      </div>
    </div>
  </b-card>
</template>

<script>
import Vue from "vue";
import FFToggableFilter from "./filter/FFToggableFilter.vue";

const DIVIDED = 3;

export default {
  name: "FFListFilterZone",
  components: { FFToggableFilter },
  props: {
    value: {
      required: false,
    },
    entityName: {
      required: true,
    },
  },
  data: function () {
    return {
      filters: {},
    };
  },
  computed: {
    properties: function () {
      return Vue.getEntity(this.entityName).properties || {};
    },
    propertiesDivided: function () {
      const result = [];
      const propKeys = Object.keys(this.properties);
      for (let i = 0; i < propKeys.length; ++i) {
        if (i % DIVIDED == 0) result.push({});
        result[result.length - 1][propKeys[i]] = this.properties[propKeys[i]];
      }
      return result;
    },
  },
  watch: {
    filters: function (newValue) {
      this.$emit("input", newValue);
    },
  },
};
</script>

<style>
.ff-filter-card {
  height: 100%;
  overflow: auto scroll;
}
.ff-filter-card > .card-body {
  padding: 0.4rem;
}
</style>
