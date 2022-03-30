import { createRouter, createWebHashHistory } from "vue-router";
import BiddingView from "../views/BiddingView.vue";

const routes = [
  {
    path: "/",
    name: "home",
    component: BiddingView,
  },
];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

export default router;
