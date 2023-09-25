import { BVToastPlugin } from "bootstrap-vue";
import Vue from "vue";

Vue.use(BVToastPlugin);

export default {
  methods: {
    /**
     * Appends toast to fixed space
     * @param {String} description Toast text content
     * @param {String} title Toast title
     * @param {String} variant Toast style variant, refet to theming reference section
     */
    showToast: function (description, title, variant) {
      return this.$bvToast.toast(description, {
        title: title,
        noAutoHide: false,
        appendToast: false,
        variant: variant,
        toaster: "b-toaster-bottom-right",
      });
    },

    /**
     * Shows success toast
     * @param {String} description Toast text content
     * @param {String} title Toast title
     */
    showToastSuccess(description, title) {
      return this.showToast(description, title, "success");
    },

    /**
     * Shows warning toast
     * @param {String} description Toast text content
     * @param {String} title Toast title
     */
    showToastWarning(description, title) {
      return this.showToast(description, title, "warning");
    },

    /**
     * Shows warning toast
     * @param {String} description Toast text content
     * @param {String} title Toast title
     */
    showToastDanger(description, title) {
      return this.showToast(description, title, "danger");
    },
  },
};
