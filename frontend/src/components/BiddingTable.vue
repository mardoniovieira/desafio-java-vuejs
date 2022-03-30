<template>
  <div>
    <DataTable :value="biddings" stripedRows responsiveLayout="scroll">
      <Column field="publicationNumber" header="N° da Publicação"></Column>
      <Column field="status" header="Status"></Column>
      <Column field="viprocNumber" header="N° VIPROC"></Column>
      <Column field="contractObject" header="Objeto da Contratação"></Column>
      <Column
        field="noticeNumberAndContractorAndDelivery"
        header="N° Edital - Contratente - Entrega"
      ></Column>
      <Column
        field="systematicsAndAcquisitionForm"
        header="Sistemática - Forma de Aquisição"
      ></Column>
      <Column
        field="arrivalAndOpening"
        header="Acolhimento - Abertura"
      ></Column>
      <Column header="Visualizada">
        <template #body="slotProps">
          <Button
            class="p-button-text"
            :label="slotProps.data.visualized ? 'Sim' : 'Não'"
            @click="checkBidding(slotProps.data)"
          />
        </template>
      </Column>
    </DataTable>
    <div class="p-mt-3 p-d-flex p-jc-between">
      <Button
        type="button"
        icon="pi pi-arrow-left"
        class="p-button-rounded p-button-info"
        :disabled="page === 1"
        @click="prevPage()"
      />
      Página {{ this.page }}
      <Button
        type="button"
        icon="pi pi-arrow-right"
        class="p-button-rounded p-button-info"
        @click="nextPage()"
      />
    </div>
  </div>
</template>

<script>
import biddingService from "@/services/biddingService";
import DataTable from "primevue/datatable";
import Column from "primevue/column";
import Button from "primevue/button";

export default {
  name: "BiddingTable",
  components: {
    DataTable,
    Column,
    Button,
  },
  data() {
    return {
      page: 1,
      biddings: [],
    };
  },
  mounted() {
    biddingService.getByPage(this.page).then((resp) => {
      this.biddings = resp.data;
    });
  },
  methods: {
    checkBidding(bidding) {
      bidding.visualized = !bidding.visualized;
      biddingService.checkBidding(bidding).then(() => {});
    },
    nextPage() {
      biddingService.getByPage(this.page + 1).then((resp) => {
        this.biddings = resp.data;
        this.page++;
      });
    },
    prevPage() {
      biddingService.getByPage(this.page - 1).then((resp) => {
        this.biddings = resp.data;
        this.page--;
      });
    },
  },
};
</script>
