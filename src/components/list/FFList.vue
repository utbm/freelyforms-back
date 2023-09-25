<template>
  <div class="ff-list">
    <div class="ff-list-top row">
      <div class="col-3">
        <FFListColumnsDisplayed :columns="columns" />
      </div>
      <div class="col-9">
        <FFListFilterZone :entityName="entityName" v-model="filters" />
      </div>
    </div>
    <b-button-toolbar key-nav class="ff-list-actions text-right my-2">
      <div class="ff-data-actions">
        <FFListDataImport class="mr-1" />
        <FFListDataExport />
      </div>

      <div class="filler"></div>
      <b-button variant="outline-secondary" class="ml-2" @click="refresh">
        <b-icon icon="arrow-clockwise" aria-label="Refresh data" />
        Refresh
      </b-button>
      <div class="filler"></div>

      <FFListEntryActions class="ff-entries-actions" :edit="edit" size="" />
    </b-button-toolbar>
    <div class="ff-list-main">
      <div v-if="loading" class="text-center my-5">
        <b-spinner small variant="primary" label="Loading data" />
        Loading data...
      </div>
      <FFTable v-else :data="data" :columns="columns" :entity="entity" />
    </div>
    <div class="ff-list-bottom text-right">
      <FFListEntryNew :url="newEntryURL" />
    </div>
  </div>
</template>

<script>
import Vue from "vue";

import FFListFilterZone from "./FFListFilterZone.vue";
import FFListColumnsDisplayed from "./FFListColumnsDisplayed.vue";
import FFListEntryNew from "./FFListEntryNew.vue";
import FFTable from "./table/FFTable.vue";
import FFListEntryActions from "./FFListEntryActions.vue";
import FFListDataExport from "./FFListDataExport.vue";
import FFListDataImport from "./FFListDataImport.vue";
import ToastMixin from "../../mixins/ToastMixin";
import CRUDMixin from "../../mixins/CRUDMixin";

export default {
  mixins: [ToastMixin, CRUDMixin],
  components: {
    FFListDataExport,
    FFListDataImport,
    FFListEntryActions,
    FFTable,
    FFListEntryNew,
    FFListColumnsDisplayed,
    FFListFilterZone,
  },
  name: "FFList",
  props: {
    newEntryURL: {
      required: true,
      type: String,
    },
    entityName: {
      required: true,
      type: String,
    },
  },
  data: function () {
    return {
      data: [],
      list: [],
      filters: [],
      loading: true,
      columns: [],
    };
  },
  computed: {
    entity: function () {
      return Vue.getEntity(this.entityName);
    },
    edit: function () {
      return Vue.ff.config.edit;
    },
  },
  created: function () {
    this.read(this.entityName);
  },
  watch: {
    entityName: function (newValue) {
      this.read(newValue);
    },
  },
  methods: {
    refresh: function () {
      this.read();
    },
    read: function () {
      this.loading = true;
      return this.ff_read(
        this.entityName,
        undefined,
        this.columns,
        this.filters
      )
        .then((res) => {
          const json = res.data;

          this.data = json;
        })
        .catch((err) => {
          const message = err.message || "List read failed";
          this.showToastDanger(
            "Error while reading list data for entity " + this.entityName,
            message
          );

          return Promise.reject(err);
        })
        .finally(() => {
          this.loading = false;
        });
    },
  },
};
</script>

<style lang="scss">
.filler {
  flex-grow: 1;
  text-align: center;
}
.ff-list-top {
  height: 220px;

  & > * {
    height: 100%;
  }
}
</style>
