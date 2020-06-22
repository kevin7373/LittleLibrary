<template>
  <v-card class="card" width="100%">
    <v-card-text class="pa-0">
      <apexcharts width="100%" height="300" type="area" :options="options" :series="series"></apexcharts>
    </v-card-text>
  </v-card>
</template>

<script>
import VueApexCharts from "vue-apexcharts"

export default {
  name: "RentAreaChart",
  components: {
    apexcharts: VueApexCharts
  },
  props: {
    cate: {
      type: Array,
      required: false
    },
    cnt: {
      type: Array,
      required: true
    }
  },
  data() {
    return {
      options: {
        chart: {
          id: "RentAreaChart",
          animations: {
            enabled: false
          }
        },
        colors: ["#745af2"],
        xaxis: {
          categories: this.cate
        },
        title: {
          text: "월 별 도서 대여 현황",
          align: "center",
          margin: 10,
          style: {
            fontSize: "18px",
            fontWeight: "bold"
          }
        },
        noData: {
          text: "Loading..."
        }
      },
      series: [
        {
          name: "대여 수",
          data: this.cnt
        }
      ]
    };
  }
};
</script>