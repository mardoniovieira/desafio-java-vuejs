import { reactive } from "vue";
import api from "@/services/api";

export default {
  install: (App) => {
    let overlayCounter = 0;
    let timeout = null;

    const { globalProperties } = App.config;

    globalProperties.$app = reactive({
      loading: false,
    });

    globalProperties.$showOverlay = function showOverlay() {
      overlayCounter += 1;

      if (overlayCounter > 0) {
        if (!timeout) {
          timeout = setTimeout(() => {
            globalProperties.$app.loading = true;
          }, 10);
        }
      }
    };

    globalProperties.$hideOverlay = function hideOverlay() {
      overlayCounter -= 1;

      if (overlayCounter <= 0) {
        if (timeout) {
          clearTimeout(timeout);
          timeout = null;
        }
        setTimeout(() => {
          globalProperties.$app.loading = false;
          overlayCounter = 0;
        }, 100);
      }
    };

    api.interceptors.request.use(
      (config) => {
        globalProperties.$showOverlay();
        return config;
      },
      (error) => {
        globalProperties.$hideOverlay();
        return Promise.reject(error.response);
      }
    );

    api.interceptors.response.use(
      (response) => {
        if (!response.config.dontShowOverlay) {
          globalProperties.$hideOverlay();
        }
        return response;
      },
      (error) => {
        globalProperties.$hideOverlay();
        return Promise.reject(error.response);
      }
    );
  },
};
