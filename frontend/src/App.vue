<template>
  <router-view />
  <Overlay v-if="$app.loading" />
</template>

<script>
import Overlay from "@/views/layout/Overlay";

export default {
  components: {
    Overlay,
  },
};
</script>

<style lang="scss">
@import "style/style.scss";
</style>
