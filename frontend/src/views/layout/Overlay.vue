<template lang="html">
  <div class="overlay-container">
    <ProgressSpinner stroke-width="4" />
  </div>
</template>

<script type="text/javascript">
import ProgressSpinner from "primevue/progressspinner";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Overlay",
  components: {
    ProgressSpinner,
  },
};
</script>

<style lang="scss">
.overlay-container {
  position: fixed;
  top: 0;
  left: 0;
  z-index: 2147483647;
  box-sizing: border-box;
  display: flex;
  flex-flow: column nowrap;
  align-items: center;
  justify-content: space-around;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  opacity: 1;
}

@keyframes p-progress-spinner-color {
  0%,
  25% {
    stroke: var(--blue-700);
  }
  50%,
  100% {
    stroke: var(--blue-800);
  }
}
</style>
