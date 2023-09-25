<template>
  <b-form-tags
    v-model="value"
    class="ff-autocomplete mb-2"
    no-outer-focus
    :size="schemaTweaked.size"
  >
    <template v-slot="{ tags, disabled, addTag, removeTag }">
      <ul v-if="tags.length > 0" class="list-inline d-inline-block mb-2">
        <li v-for="value in tags" :key="value" class="list-inline-item">
          <b-form-tag
            @remove="removeTag(value)"
            :title="value"
            :disabled="disabled"
            variant="primary"
            >{{ getTextFromValue(value) }}</b-form-tag
          >
        </li>
      </ul>

      <b-form
        :disabled="disabled && inputDisabled"
        @submit.stop.prevent="() => {}"
      >
        <b-form-group
          class="mb-0"
          :label-size="schemaTweaked.size"
          :description="searchDesc"
          :disabled="disabled"
        >
          <b-form-input
            v-model="search"
            id="tag-search-input"
            type="search"
            ref="searchInput"
            :placeholder="schemaTweaked.placeholder"
            :debounce="schemaTweaked.debounce"
            :name="schemaTweaked.name"
            :size="schemaTweaked.size"
            autocomplete="off"
          ></b-form-input>
        </b-form-group>
      </b-form>

      <div class="ff-autocomplete-container">
        <div class="ff-autocomplete-content" v-show="!loadingSearch">
          <b-list-group>
            <b-list-group-item
              class="px-2 py-1"
              v-for="option in availableOptions"
              :key="option"
              @click="onOptionClick({ option, addTag })"
            >
              {{ option }}
            </b-list-group-item>
          </b-list-group>
        </div>
        <b-spinner
          class="text-center"
          variant="primary"
          small
          label="Spinning"
          v-show="loadingSearch"
        ></b-spinner>
      </div>
    </template>
  </b-form-tags>
</template>

<script>
import { abstractField } from "vue-form-generator";

export default {
  name: "ff-autocomplete",
  mixins: [abstractField],
  props: {
    loadingSearch: {
      required: false,
      type: Boolean,
      default: () => false,
    },
  },
  computed: {
    schemaTweaked() {
      const res = this.schema;

      res.values = res.values || [];
      res.name = res.name || undefined;
      res.size = res.size || "md";
      res["options-value"] = res["options-value"] || undefined; // value entered : value
      res["options-field"] = res["options-field"] || undefined; // value displayed: text
      res.multiple = res.multiple || false;
      res.placeholder = res.placeholder || "";
      res.autofocus = res.autofocus || false;
      res.debounce = res.debounce || 0;

      return this.schema;
    },
    criteria() {
      // Compute the search criteria
      return this.search.trim();
    },
    availableOptions() {
      // Filter out already selected options
      const options = this.schemaTweaked.values.filter((opt) => {
        const compared =
          this.schemaTweaked["options-value"] !== undefined
            ? opt[this.schemaTweaked["options-value"]]
            : opt;

        // Fixes bug if value is undefined or not an array
        return this.value && Array.isArray(this.value)
          ? this.value.indexOf(compared) === -1
          : true;
      });

      // get searched term
      const searchedTerm = this.criteria;

      // if something to compare
      if (searchedTerm) {
        // Show only options that match criteria
        return options.filter((opt) =>
          this.itemText(opt).includes(searchedTerm)
        );
      }
      // Show all options available
      return options;
    },
    searchDesc() {
      if (this.criteria && this.availableOptions.length === 0) {
        return "There are no tags matching your search criteria";
      }
      return "";
    },
    inputDisabled() {
      return !this.schemaTweaked.multiple && !!this.value;
    },
    valuesAvailable() {
      return this.schemaTweaked.values.map(this.itemValue);
    },
  },
  data: function () {
    return {
      search: "",
    };
  },
  watch: {
    value: function (n, o) {
      let vals = n;
      if (!this.schemaTweaked.multiple && !Array.isArray(n)) vals = [n];

      // check all values of in options available
      let valid = true;
      let i = 0;
      while (i < vals.length && valid) {
        valid = this.valuesAvailable.includes(vals[i]);
        ++i;
      }

      if (!valid) this.value = o;
      this.$emit("input", this.value);
    },
    search: function (n) {
      this.$emit("change", n, this.schemaTweaked["options-value"]);
    },
  },
  methods: {
    itemValue(item) {
      return this.schemaTweaked["options-value"] !== undefined
        ? item[this.schemaTweaked["options-value"]]
        : item;
    },
    itemText(item) {
      return this.schemaTweaked["options-field"] !== undefined
        ? item[this.schemaTweaked["options-field"]]
        : item;
    },
    onOptionClick({ option, addTag }) {
      addTag(option);
      this.search = "";
      this.$refs.searchInput.$refs.input.focus();
    },
    getTextFromValue: function (value) {
      return this.itemText(
        this.schemaTweaked.values.filter((e) => value === this.itemValue(e))[0]
      );
    },
  },
};
</script>

<style lang="scss">
.vue-form-generator .form-control.ff-autocomplete {
  border: 0px none;
  padding: 0;
  box-shadow: none;
  background: transparent;

  & .b-form-tag > button.b-form-tag-remove {
    padding: 0;
    opacity: 1;
    border: 0px none;
    text-shadow: unset;
    color: inherit !important;
    background: transparent;
  }
  & .ff-autocomplete-container {
    position: relative;
    max-height: 150px;
    overflow: auto;
    z-index: 100;
    margin-top: 0.75rem;
  }
}
</style>
