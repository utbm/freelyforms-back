<template>
  <b-form-datepicker
    class="ff-date-picker"
    v-model="rawModel"
    :disabled="disabled"
    today-button
    value-as-date
    :size="schema.size || ''"
    :min="schema.min"
    boundary="window"
    :max="schema.max"
    :placeholder="schema.placeholder"
    :locale="schema.locale || 'en'"
    :dark="schema.dark || false"
    :state="errors.length == 0 ? null : false"
    :date-disabled-fn="dateDisabled"
  />
</template>

<script>
import { abstractField } from "vue-form-generator";

export default {
  name: "ff-date-picker",
  mixins: [abstractField],
  data: function () {
    return {
      rawModel: new Date(),
    };
  },
  mounted: function () {
    this.rawModel = new Date(this.value);
  },
  watch: {
    value: function (currentValue) {
      this.rawModel = new Date(currentValue);
    },
    outModel: function (currentValue) {
      this.value = currentValue;
      this.$emit("input", currentValue);
    },
  },
  computed: {
    outModel: function () {
      if (!this.schema.format) return this.rawModel;

      const parsed_date = this.rawModel;

      if (!(parsed_date instanceof Date)) {
        throw new Error("Not a date");
      }
      // number of milliseconds since January 1st, 1970 at UTC
      if (this.schema.format === "timestamp") return parsed_date.getTime();
      // number of seconds since January 1st, 1970 at UTC
      if (this.schema.format === "unix") return parsed_date.getTime() / 1000;
      // Coordinated Universal Time string
      else if (this.schema.format === "utc") return parsed_date.toUTCString();
      // ISO 8601 extended format : YYYY-MM-DDTHH:mm:ss.sssZ
      else if (this.schema.format === "iso") return parsed_date.toISOString();

      return this.rawModel;
    },
  },
  methods: {
    /**
     * @param {String} _yyyymmdd date as YYYY-MM-DD format string
     * @param {Date} actualDate date as Date instance
     */
    dateDisabled(_yyyymmdd, actualDate) {
      // not disabled is no option
      if (!this.schema.dateDisabled) return false;

      // recover to array
      let date_disabled = this.schema.dateDisabled;
      if (!Array.isArray(date_disabled)) date_disabled = [date_disabled];

      // we are on a logic AND so we end
      let uncorrect_date = false;
      let el,
        dateToCheck,
        var_type,
        i = 0;
      while (i < date_disabled.length && !uncorrect_date) {
        el = date_disabled[i];
        var_type = typeof el;

        if (var_type === "string" || var_type === "number") {
          dateToCheck = new Date(el);

          if (dateToCheck instanceof Date && !!dateToCheck.getDate()) {
            uncorrect_date =
              dateToCheck.getDate() === actualDate.getDate() &&
              dateToCheck.getMonth() === actualDate.getMonth() &&
              dateToCheck.getFullYear() === actualDate.getFullYear();
          } else {
            console.warn("Failed to parse date", el);
          }
        } else if (var_type === "function") {
          uncorrect_date = el();
        } else if (var_type === "boolean") {
          uncorrect_date = el;
        }
        i++;
      }

      return uncorrect_date;
    },
  },
};
</script>

<style lang="scss">
.ff-date-picker.form-control {
  position: relative;
  padding: 0;

  & > button.btn {
    position: absolute;
    height: 33px !important;
  }

  & > label {
    margin-left: 41px !important;
  }
}
</style>
