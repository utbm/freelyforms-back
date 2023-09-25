<template>
  <div>
    <div class="text-right">
      <span>Results per page: </span>
      <b-form-radio-group
        id="btn-radios-1"
        size="sm"
        v-model="rows"
        :options="options"
        name="radios-btn-default"
        buttons
      ></b-form-radio-group>
    </div>
    <b-table
      :items="data"
      :fields="fields"
      select-mode="range"
      responsive="sm"
      striped
      :outlined="!$store.ff.config.dark"
      hover
      ref="selectableTable"
      class="my-2"
      :dark="$store.ff.config.dark"
      selectable
      @row-selected="onRowSelected"
    >
      <!-- Example scoped slot for select state illustrative purposes -->
      <template v-slot:cell(ff_selected)="{ rowSelected }"
        ><div class="form-check">
          <input
            class="form-check-input"
            type="checkbox"
            :checked="rowSelected ? true : false"
            @click="clickParent($event)"
          />
        </div>
      </template>

      <template v-slot:cell(ff_actions)="row">
        <FFListEntryActions
          class="ff-entries-actions"
          :edit="edit"
          :row="row"
          :editURL="
            'http://localhost:8090/#/form/' + entity.name + '/' + row.item.id
          "
        />
      </template>

      <template #row-details="row">
        <b-card :bg-variant="$store.ff.config.dark ? 'dark' : ''">
          <ul>
            <template v-for="(value, key) in row.item">
              <li :key="key" v-if="key !== '_showDetails'">
                {{ key }}: {{ value }}
              </li>
            </template>
          </ul>
        </b-card>
      </template>

      <!-- default data cell scoped slot -->
      <template #cell()="data">
        <template v-if="entity.properties[data.field.key].type === 'date'">
          {{ new Date(data.value).toISOString().split("T")[0] }}
        </template>
        <template
          v-else-if="entity.properties[data.field.key].type === 'array'"
        >
          <template v-for="(el, i) in data.value">
            <b-badge :key="i" no-remove class="mr-1">{{ el }}</b-badge>
          </template>
        </template>
        <template v-else>{{ data.value }}</template>
      </template>
    </b-table>
    <!-- <b-pagination
      v-model="currentPage"
      :total-rows="rows"
      :per-page="perPage"
      align="right"
      size="sm"
      first-number
    ></b-pagination> -->
  </div>
</template>

<script>
import Vue from "vue";
import FFListEntryActions from "../FFListEntryActions.vue";

export default {
  name: "FFTable",
  components: {
    FFListEntryActions,
  },
  props: {
    entity: {
      required: true,
      type: Object,
    },
    data: {
      required: true,
      type: Array,
    },
    columns: {
      required: true,
      type: Array,
    },
  },
  data: function () {
    return {
      options: [
        { text: "25", value: 25 },
        { text: "50", value: 50 },
        { text: "100", value: 100 },
      ],
      rows: 25,
      perPage: 1,
      currentPage: 5,
      selected: [],
    };
  },
  computed: {
    displayedColumns: function () {
      return this.columns.length > 0
        ? this.columns
        : this.data.length > 0
        ? Object.keys(this.data[0])
        : [];
    },
    displayedData: function () {
      const result = [];
      const data = this.data || [];

      let tmp;
      data.forEach((entry) => {
        tmp = {};

        this.displayedColumns.forEach((col) => {
          tmp[col] = entry[col];
        });

        tmp.ff_selected = false;

        result.push(tmp);
      });

      return result;
    },
    fields: function () {
      const prop_keys = Object.keys(this.entity.properties);
      const result = [];
      let tmp;
      prop_keys.forEach((prop) => {
        tmp = {
          key: prop,
        };
        if (this.entity.properties[prop].displayName) {
          tmp.label = this.entity.properties[prop].displayName;
        }

        if (
          ["number", "string", "float", "date"].includes(
            this.entity.properties[prop].type
          )
        ) {
          tmp.sortable = true;
        }

        result.push(tmp);
      });

      // Add selection field
      result.unshift({
        key: "ff_selected",
        label: "Selected",
      });

      // Add actions field
      result.push({
        key: "ff_actions",
        label: "Actions",
      });

      return result;
    },
    edit: function () {
      return Vue.ff.config.edit;
    },
  },
  methods: {
    hash: function (obj, index) {
      return JSON.stringify(obj) + "-" + index;
    },
    onRowSelected(items) {
      this.selected = items.map((e) => e.id);
    },
    clickParent(event) {
      event.target.parentElement.click();
    },
  },
};
</script>
