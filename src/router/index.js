import Vue from "vue";
import VueRouter from "vue-router";
import AppList from "../views/AppList.vue";
import AppForm from "../views/AppForm.vue";
import AppNotFound from "../views/AppNotFound.vue";
import BootstrapVue from "bootstrap-vue";

Vue.use(VueRouter);
Vue.use(BootstrapVue);

// bootstrap
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue/dist/bootstrap-vue.css";

const routes = [
  {
    path: "/",
    redirect: "/list/users",
  },
  {
    path: "/form/:entity/:id?",
    name: "Edit/Add",
    component: AppForm,
  },
  {
    path: "/list/:entity",
    name: "list",
    component: AppList,
  },
  {
    path: "*",
    component: AppNotFound,
  },
];

const router = new VueRouter({
  routes,
});

export default router;
