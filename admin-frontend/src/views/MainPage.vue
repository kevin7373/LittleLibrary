<template>
  <v-container fluid grid-list-xl>
    <v-layout row wrap style="background-color: #f5f5f5">
      <v-flex d-flex lg3 sm6 xs12>
        <v-btn
          style="width: 100%; height: 100%; text-decoration: none"
          class="pa-0"
          tile
          depressed
          @click="goBookList(listKeyword)"
        >
          <widget
            icon="local_library"
            :title="bookCnt + '권'"
            subTitle="13% higher yesterday"
            supTitle="총 도서 현황"
            color="#258df2"
          ></widget>
        </v-btn>
      </v-flex>
      <v-flex d-flex lg3 sm6 xs12>
        <v-btn
          style="width: 100%; height: 100%; text-decoration: none"
          class="pa-0"
          tile
          depressed
          @click="goBookList(haveKeyword)"
        >
          <widget
            icon="import_contacts"
            :title="currentBookCnt + '권'"
            subTitle="$117,212 before tax"
            supTitle="현재 보유 도서 현황"
            color="#40c741"
            iconColor="#dc3545"
          />
        </v-btn>
      </v-flex>
      <v-flex d-flex lg3 sm6 xs12>
        <v-btn
          style="width: 100%; height: 100%; text-decoration: none"
          class="pa-0"
          tile
          depressed
          @click="goRentList(rentKeyword)"
        >
          <widget
            icon="move_to_inbox"
            :title="rentCnt + '권'"
            subTitle="13% average duration"
            supTitle="대여 도서 현황"
            color="#fdba2c"
            iconColor="#0866C6"
            path="/admin/book/rent"
          />
        </v-btn>
      </v-flex>
      <v-flex d-flex lg3 sm6 xs12>
        <v-btn
          style="width: 100%; height: 100%; text-decoration: none"
          class="pa-0"
          tile
          depressed
          @click="goRentList(expireKeyword)"
        >
          <widget
            icon="watch_later"
            :title="expireCnt + '권'"
            subTitle="17.25% on average time"
            supTitle="연체 도서 현황"
            color="#ff0017"
            iconColor="#1D2939"
            path="/admin/book/rent"
          />
        </v-btn>
      </v-flex>
      <v-flex d-flex sm6 xs12 justify-center>
        <RentAreaChart :cate="monthRents" :cnt="monthRentsCnt" />
      </v-flex>
      <v-flex d-flex sm6 xs12 justify-center v-if="this.user.userRoleId != 1">
        <RentRadialBarChart :value="returnRate" :returnStatus="returnStatus" />
      </v-flex>
      <v-flex d-flex sm6 xs12 justify-center v-if="this.user.userRoleId == 1">
        <RentCustomAngleChart :value="rtRateList" />
      </v-flex>
      <v-flex d-flex xs12 sm6>
        <v-card width="100%">
          <v-flex d-flex class="justify-center">많이 대여한 도서</v-flex>
          <v-simple-table>
            <template>
              <thead>
                <tr>
                  <th class="text-left">이미지</th>
                  <th class="text-left">책 제목</th>
                  <th class="text-center">대여 횟수</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(bestBook) in bestBookList" :key="bestBook.bookRentId">
                  <td>
                    <v-img :src="bestBook.bookImg" width="30px" />
                  </td>
                  <td class="text-left">
                    <v-clamp autoresize :max-lines="1">{{ bestBook.bookName }}</v-clamp>
                  </td>
                  <td class="text-center">{{ bestBook.rentCnt }}</td>
                </tr>
              </tbody>
            </template>
          </v-simple-table>
        </v-card>
      </v-flex>
      <v-flex d-flex xs12 sm6>
        <v-card width="100%">
          <v-flex d-flex class="justify-center">많이 신청한 도서</v-flex>
          <v-simple-table>
            <template v-slot:default>
              <thead>
                <tr>
                  <th class="text-left">ISBN</th>
                  <th class="text-left">책 제목</th>
                  <th class="text-center">신청 횟수</th>
                  <th class="text-center">보유</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(reqBook) in reqBookMain" :key="reqBook.reqBookId">
                  <td class="text-left">{{ reqBook.bookISBN}}</td>
                  <td class="text-left">
                    <v-clamp autoresize :max-lines="1">{{ reqBook.bookName }}</v-clamp>
                  </td>
                  <td class="text-center">{{ reqBook.cnt }}</td>
                  <td class="text-center">
                    <v-chip
                      v-if="reqBook.have == 1"
                      color="#58d8a3"
                      class="justify-center"
                      style="width: 48px;"
                      text-color="white"
                      label
                    >YES</v-chip>
                    <v-chip
                      v-else
                      color="#ff0017"
                      class="justify-center"
                      style="width: 48px;"
                      text-color="white"
                      label
                    >NO</v-chip>
                  </td>
                </tr>
              </tbody>
            </template>
          </v-simple-table>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import { mapGetters } from "vuex";
import widget from "../components/Widget";
import RentAreaChart from "../statistics/RentAreaChart";
import RentRadialBarChart from "../statistics/RentRadialBarChart";
import RentCustomAngleChart from "../statistics/RentCustomAngleChart";
import VClamp from "vue-clamp";

export default {
  name: "MainPage",
  components: {
    widget,
    RentAreaChart,
    RentRadialBarChart,
    RentCustomAngleChart,
    VClamp
  },
  computed: {
    ...mapGetters(["requestHeader", "user", "getAxios"])
  },
  data() {
    return {
      bookCnt: "",
      currentBookCnt: "",
      rentCnt: "",
      expireCnt: "",
      bestBookList: [],
      rentCntListByPeriod: [],
      monthRents: [],
      monthRentsCnt: [0, 0, 0, 0, 0, 0],
      returnRate: [],
      reqBookMain: [],
      listKeyword: "",
      haveKeyword: "보유",
      rentKeyword: "대여",
      expireKeyword: "연체",
      rtRateList: [],
      returnStatus: [],
      returnStatusList: [],
      data: []
    };
  },
  methods: {
    init() {
      this.getAxios
        .post(`/admin/main`, {}, this.requestHeader)
        .then(res => {
          this.bookCnt = res.data.bookCnt;
          this.currentBookCnt = res.data.currentBookCnt;
          this.rentCnt = res.data.rentCnt;
          this.expireCnt = res.data.expireCnt;
          this.bestBookList = res.data.bestBookList;
          this.rentCntListByPeriod = res.data.rentCntListByPeriod;

          if (this.user.userRoleId != "1") {
            this.returnStatus = [[this.user.cityName, res.data.returnStatus]];

            if (res.data.returnRate != "NaN") {
              if (res.data.returnRate.toFixed(4) * 100 >= 100) {
                this.returnRate[0] = res.data.returnRate.toFixed(1) * 100;
              } else {
                this.returnRate[0] = res.data.returnRate.toFixed(4) * 100;
              }
            }
          }
          this.reqBookMain = res.data.reqBookMain;

          if (this.user.userRoleId == "1") {
            this.rtRateList = res.data.rtRateList;

            for (let i = 0; i < this.rtRateList.length; i++) {
              if (this.rtRateList[i] != "NaN") {
                if (this.rtRateList[i].toFixed(4) * 100 >= 100) {
                  this.rtRateList[i] = this.rtRateList[i].toFixed(1) * 100;
                } else {
                  this.rtRateList[i] = this.rtRateList[i].toFixed(4) * 100;
                }
              } else {
                this.rtRateList[i] = "0";
              }
            }
          }
          this.makeRentAreaChart(res);
        })
        .catch(err => {
          console.log(err);
        });
    },
    makeRentAreaChart(res) {
      for (let i = 0; i < this.monthRents.length; i++) {
        for (let j = 0; j < res.data.rentCntListByPeriod.length; j++) {
          if (this.monthRents[i] == res.data.rentCntListByPeriod[j].month) {
            this.monthRentsCnt[i] = res.data.rentCntListByPeriod[j].cnt;
          }
        }
      }
    },
    goBookList(param) {
      window.sessionStorage.setItem("bookListKeyword", param);
      this.$router.push("/admin/book/list");
    },
    goRentList(param) {
      window.sessionStorage.setItem("rentListKeyword", param);
      window.sessionStorage.setItem("activeTab", 0);
      this.$router.push("/admin/book/rent");
    }
  },
  created() {
    for (let i = 5; i >= 0; i--) {
      var today = new Date();
      today.setMonth(today.getMonth() - i);
      this.monthRents[5 - i] = today.toJSON().slice(0, 7);
    }
    this.init();
  }
};
</script>