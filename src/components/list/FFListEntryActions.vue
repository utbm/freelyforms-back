<template>
  <b-button-group :size="size">
    <b-button :disabled="read" v-b-tooltip.hover="'Edit'" @click="editButton">
      <b-icon icon="pencil-fill" aria-label="Edit"></b-icon>
    </b-button>
    <b-button :disabled="read" v-b-tooltip.hover="'Delete'">
      <b-icon icon="trash2-fill" aria-label="Delete"></b-icon>
    </b-button>
    <b-button
      v-b-tooltip.hover="'See full entry'"
      v-if="row"
      @click="row ? row.toggleDetails() : () => {}"
    >
      <b-icon
        :icon="row && row.detailsShowing ? 'eye-slash-fill' : 'eye-fill'"
        aria-label="See full entry"
      ></b-icon>
    </b-button>
    <b-button :disabled="true" v-b-tooltip.hover="'Add to favorite'">
      <b-icon icon="star-fill" aria-label="Add to favorite"></b-icon>
    </b-button>
  </b-button-group>
</template>

<script>
export default {
  name: "FFListEntryActions",
  props: {
    edit: {
      required: true,
      type: Boolean,
    },
    row: {
      required: false,
      type: Object,
    },
    size: {
      required: false,
      type: String,
      default: () => "sm",
    },
    editURL: {
      required: false,
      type: String,
      default: () => "",
    },
  },
  computed: {
    read: function () {
      return !this.edit;
    },
  },
  methods: {
    editButton: function () {
      if (!this.editURL) return;
      window.location.href = this.editURL;
    },
  },
};
</script>
