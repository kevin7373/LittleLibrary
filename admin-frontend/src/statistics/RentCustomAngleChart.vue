<template>
  <v-card class="card" width="100%">
    <v-card-text class="pa-0">
      <apexcharts width="100%" height="300" type="radialBar" :options="options" :series="value"></apexcharts>
    </v-card-text>
  </v-card>
</template>

<script>
import VueApexCharts from "vue-apexcharts";

export default {
  name: "RentCustomAngleChart",
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
      tmpX: Number,
      returnRate: [],
      options: {
        chart: {
          id: "RentCustomAngleChart",
          animations: {
            enabled: false
          }
        },
        plotOptions: {
          radialBar: {
            offsetY: 0,
            startAngle: 0,
            endAngle: 270,
            hollow: {
              margin: 5,
              size: "30%",
              background: "transparent",
              image: undefined
            },
            dataLabels: {
              name: {
                show: true
              },
              value: {
                show: true
              }
            }
          }
        },
        title: {
          text: "지역별 반납 현황",
          align: "center",
          margin: 10,
          style: {
            fontSize: "18px",
            fontWeight: "bold"
          }
        },
        colors: ["#258df2", "#24E5A4", "#FCBA39", "#ff0017"],
        labels: ["서울", "대전", "구미", "광주"],
        noData: {
          text: "오늘까지 반납해야 할 책이 없습니다"
        },
        legend: {
          show: true,
          floating: true,
          fontSize: "12px",
          offsetX: 30,
          offsetY: 37,
          labels: {
            useSeriesColors: true
          },
          markers: {
            size: 0
          },
          formatter: function(seriesName, opts) {
            if (opts.w.globals.series.length == 0) {
              return;
            }
            return seriesName + ":  " + opts.w.globals.series[opts.seriesIndex];
          }
        }
      },
      series: [],
      responsive: [
        {
          breakpoint: this.$vuetify.breakpoint.xsOnly,
          options: {
            legend: {
              show: false
            }
          }
        }
      ]
    };
  },
  beforeCreate() {
    if (window.innerWidth <= 320) {
      this.tmpX = 45;
    } else if (window.innerWidth <= 375) {
      this.tmpX = 55;
    }
  }
};
</script>
<style>
.apexcharts-legend {
  right: 49% !important;
  /* padding: 20px !important; */
  /* margin: 100px !important; */
}
</style>