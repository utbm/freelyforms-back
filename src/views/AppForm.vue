<template>
  <div>
    <h2>{{ "Edit/Add " + this.$route.params.entity }}</h2>
    <FFForm
      :entityName="this.$route.params.entity"
      :id="this.$route.params.id"
      :formName="this.$route.params.entity"
    />
  </div>
</template>

<script>
import FFForm from "../components/form/FFForm.vue";

export default {
  name: "app-list",
  components: { FFForm },
};
</script>
