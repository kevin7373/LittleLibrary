<template>
  <v-card class="card" width="100%">
    <v-card-text class="pa-0">
      <apexcharts width="100%" height="300" type="pie" :options="options" :series="series"></apexcharts>
    </v-card-text>
  </v-card>
</template>

<script>
import VueApexCharts from "vue-apexcharts";

export default {
  name: "BookPieChart",
  components: {
    apexcharts: VueApexCharts
  },
  data() {
    return {
      options: {
        chart: {
          id: "BookPieChart",
          animations: {
            enabled: false
          }
        },
        labels: ["웹", "모바일", "인공지능", "빅데이터", "블록체인"],
        title: {
          text: "카테고리별 대여 현황",
          align: "center",
          margin: 10,
          style: {
            fontSize: "18px",
            fontWeight: "bold"
          }
        }
      },
      series: [3, 2, 2, 1, 3]
    };
  }
};
</script>