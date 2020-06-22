<template>
  <v-card class="card" width="100%">
    <v-card-text class="pa-0">
      <apexcharts width="100%" height="300" type="radialBar" :options="options" :series="series"></apexcharts>
    </v-card-text>
  </v-card>
</template>

<script>
import VueApexCharts from "vue-apexcharts";

export default {
  name: "RentRadialBarChart",
  components: {
    apexcharts: VueApexCharts
  },
  props: {
    value: {
      type: Array
    },
    color: {
      type: Array
    }
  },
  computed: {},
  data() {
    return {
      returnRate: [],
      options: {
        chart: {
          id: "RentRadialBarChart",
          animations: {
            enabled: false
          }
        },
        labels: ["반납 현황"],
        title: {
          text: "반납 현황",
          align: "center",
          margin: 10,
          style: {
            fontSize: "18px",
            fontWeight: "bold"
          }
        },
        colors: [
          function({ value }) {
            if (value > 70) {
              return "#008ffb";
            } else if (value > 30) {
              return "#ffc105";
            } else {
              return "#ff4560";
            }
          }
        ],
        noData: {
          text: "오늘까지 반납해야 할 책이 없습니다"
        }
      },
      series: [],
      plotOptions: {
        radialBar: {
          hollow: {
            size: "10%"
          }
        }
      }
    };
  },
  created() {
    if (this.value.values(0) == -1) {
      this.series = []
    } else {
      this.series = this.value
    }
  }
};
</script>