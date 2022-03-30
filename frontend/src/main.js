import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import PrimeVue from "primevue/config";
import appPlugin from "./plugins/appPlugin";

createApp(App).use(router).use(PrimeVue).use(appPlugin).mount("#app");
