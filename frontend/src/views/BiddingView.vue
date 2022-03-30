<template>
  <div>
    <BiddingTable />
  </div>
</template>

<script>
// @ is an alias to /src
import BiddingTable from "@/components/BiddingTable.vue";

export default {
  name: "BiddingView",
  components: {
    BiddingTable,
  },
};
</script>
