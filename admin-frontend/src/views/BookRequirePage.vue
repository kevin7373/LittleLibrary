<template>
  <v-container fluid grid-list-xl>
    <v-layout row wrap style="background-color: #f5f5f5">
      <v-flex d-flex xs12>
        <v-card width="100%">
          <v-card-title>
            도서 신청 현황
            <v-spacer></v-spacer>
            <v-text-field
              v-model="keyword"
              append-icon="search"
              label="Search"
              single-line
              hide-details
            ></v-text-field>
          </v-card-title>
          <v-data-table
            :headers="headers"
            :items="requireList"
            :search="keyword"
            :options="options"
            v-model="selected"
            show-select
            item-key="reqId"
            :footer-props="{
              'items-per-page-options': [5, 10, 15, lastIndex]
            }"
          >
            <template v-slot:header.data-table-select="{ on, props }">
              <v-simple-checkbox color="#3396f4" v-bind="props" v-on="on" :ripple="ripple"></v-simple-checkbox>
            </template>
            <template v-slot:item.data-table-select="{ isSelected, select }">
              <v-simple-checkbox
                :value="isSelected"
                @input="select($event)"
                color="#3396f4"
                :ripple="ripple"
              ></v-simple-checkbox>
            </template>
            <template v-slot:item.bookImg="{ item }">
              <v-img
                :src="item.bookImg || bookDefaultImg"
                width="30px"
                @click="bookDetail(item.reqId)"
              />
            </template>
            <template v-slot:item.bookISBN="{ item }">
              <v-flex
                d-flex
                class="pl-0 text-left"
                @click="bookDetail(item.reqId)"
              >{{ item.bookISBN }}</v-flex>
            </template>
            <template v-slot:item.bookName="{ item }">
              <v-flex d-flex class="pl-0 text-left" @click="bookDetail(item.reqId)" id="bookName">
                <v-clamp autoresize :max-lines="1">{{ item.bookName }}</v-clamp>
              </v-flex>
            </template>
            <template v-slot:item.userName="{ item }">
              <v-flex d-flex class="pl-0 text-left">({{ item.reqCity }}){{ item.userName }}</v-flex>
            </template>
            <template v-slot:item.publisher="{ item }">
              <v-flex d-flex class="pl-0 text-left">
                <v-clamp autoresize :max-lines="1">{{ item.publisher }}</v-clamp>
              </v-flex>
            </template>
            <template v-slot:item.have="{ item }">
              <v-chip
                v-if="item.have == 1"
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
            </template>
          </v-data-table>
        </v-card>
      </v-flex>
      <v-flex class="pt-0 text-right">
        <v-btn color="green" dark depressed class="ma-0" @click="makeXLSX()">엑셀로 내려받기</v-btn>
        <v-btn color="#3396f4" dark depressed class="ml-3 mt-0 mb-0 mr-0" @click="open()">승인 / 반려 알림</v-btn>
      </v-flex>
      <v-dialog v-model="selectedNullDialog" max-width="400">
        <v-card tile style="border-radius: 0px;">
          <v-card-title class="pt-0 pb-0">
            <v-flex
              d-flex
              class="mt-10 pt-0 justify-center"
              style="border-radius: 0px !important; font-size: 15px; font-weight: bold;"
            >알림 보낼 사람을 선택해주세요.</v-flex>
          </v-card-title>
          <v-card-actions class="pa-0">
            <v-flex d-flex class="mt-11 mb-10 justify-center">
              <v-btn
                min-width="100px"
                height="45px"
                color="#3c90e2"
                dark
                tile
                depressed
                @click="close()"
              >확인</v-btn>
            </v-flex>
          </v-card-actions>
        </v-card>
      </v-dialog>
      <v-dialog v-model="dialog" width="600px">
        <v-card>
          <v-flex
            d-flex
            dark
            style="background-color: #3396f4; color: white"
            class="pl-10 pt-5 pb-5"
          >알림작성</v-flex>
          <v-flex d-flex class="mt-5 mb-5 ml-10 mr-10">
            <v-select
              outlined
              v-model="titleId"
              :items="title"
              label="승인 / 반려"
              item-text="name"
              item-value="titleId"
              required
              single-line
              hide-details="auto"
              :error-messages="titleIdErrMsg"
            ></v-select>
          </v-flex>
          <v-flex d-flex class="ml-10 mr-10">
            <v-textarea
              outlined
              ref="content"
              v-model="content"
              label="알림 내용"
              required
              single-line
              hide-details="auto"
              auto-grow
              :error-messages="contentErrMsg"
            ></v-textarea>
          </v-flex>
          <v-flex d-flex class="pa-5 justify-center">
            <v-btn
              min-width="120px"
              height="50px"
              color="#4f5a66"
              dark
              tile
              depressed
              class="ma-0"
              @click="close()"
            >취소</v-btn>
            <v-btn
              min-width="120px"
              height="50px"
              color="#3c90e2"
              dark
              tile
              depressed
              class="ml-3 ma-0"
              @click="sendNotWindow()"
            >작성</v-btn>
          </v-flex>
        </v-card>
      </v-dialog>
    </v-layout>
  </v-container>
</template>

<script>
import { mapGetters } from "vuex";
import JsonExcel from "vue-json-excel";
import XLSX from "xlsx";
import VClamp from "vue-clamp";

export default {
  name: "BookRequirePage",
  components: {
    JsonExcel,
    VClamp
  },
  computed: {
    ...mapGetters(["requestHeader", "user", "getAxios"])
  },
  data() {
    return {
      requireList: [],
      keyword: "",
      selected: [],
      receiver: [],
      selectAll: false,
      dialog: false,
      selectedNullDialog: false,
      titleIdErrMsg: "",
      contentErrMsg: "",
      bookDefaultImg:
        "https://dynamicmediainstitute.org/wp-content/themes/dynamic-media-institute/imagery/default-thesis-abstract.png",
      ripple: false,
      jsonData: [],
      options: {
        itemsPerPage: 10
      },
      lastIndex: Number,
      titleId: "",
      content: "",
      title: [
        {
          name: "승인",
          titleId: "2"
        },
        {
          name: "반려",
          titleId: "3"
        }
      ],
      exportData: [
        ["(한 주간의 신청 도서를 매주 목요일 김초롱 프로에게 송부)"],
        ["목록 작성 시 ★참고 시트 확인 必"],
        [
          "지역",
          "반",
          "컨설턴트",
          "실습코치",
          "주차",
          "기간",
          "성명",
          "팀명",
          "도서명",
          "ISBN코드",
          "출판사",
          "가격",
          "신청 사유",
          "미니도서관 배치 여부"
        ]
      ],
      jsonFields: {
        지역: "지역",
        반: "반",
        컨설턴트: "컨설턴트",
        실습코치: "실습코치",
        PJT: "PJT",
        주차: "주차",
        기간: "기간",
        성명: "성명",
        팀명: "팀명",
        도서명: "도서명",
        ISBN코드: "ISBN코드",
        출판사: "출판사",
        가격: "가격",
        "신청 사유": "신청 사유",
        "미니도서관 배치 여부": "미니도서관 배치 여부"
      },
      headers: [
        {
          text: "이미지",
          value: "bookImg",
          sortable: false
        },
        {
          text: "도서 ISBN",
          value: "bookISBN"
        },
        {
          text: "도서 제목",
          value: "bookName"
        },
        {
          text: "신청자",
          value: "userName"
        },
        {
          text: "출판사",
          value: "publisher"
        },
        {
          text: "가격",
          value: "bookPrice"
        },
        {
          text: "보유 여부",
          value: "have"
        }
      ]
    };
  },
  watch: {
    titleId(e) {
      if (e != null || e != "") {
        this.titleIdErrMsg = "";
      }
    },
    content(e) {
      if (e != null || e != "") {
        this.contentErrMsg = "";
      }
    }
  },
  methods: {
    bookDetail(reqId) {
      window.sessionStorage.removeItem("bookListKeyword");
      window.sessionStorage.removeItem("rentListKeyword");
      this.$router.push({ name: "reqbookdetail", params: { reqId: "" } });
      window.sessionStorage.setItem("reqId", reqId);
    },
    makeXLSX() {
      if (this.selected.length > 0) {
        for (let i = 0; i < this.selected.length; i++) {
          var strHave = "";

          if (this.selected[i].have == 0) {
            strHave = "X";
          } else if (this.selected[i].have == 1) {
            strHave = "O";
          }

          this.exportData.push([
            this.selected[i].reqCity,
            this.selected[i].userClass,
            this.selected[i].consultant,
            this.selected[i].coach,
            this.selected[i].pjtWeek,
            this.selected[i].period,
            this.selected[i].userName,
            this.selected[i].userTeam,
            this.selected[i].bookName,
            this.selected[i].bookISBN,
            this.selected[i].publisher,
            this.selected[i].bookPrice,
            this.selected[i].reqReason,
            strHave
          ]);
        }
      } else {
        for (let i = 0; i < this.requireList.length; i++) {
          var strHave = "";

          if (this.requireList[i].have == 0) {
            strHave = "X";
          } else if (this.requireList[i].have == 1) {
            strHave = "O";
          }

          this.exportData.push([
            this.requireList[i].reqCity,
            this.requireList[i].userClass,
            this.requireList[i].consultant,
            this.requireList[i].coach,
            this.requireList[i].pjtWeek,
            this.requireList[i].period,
            this.requireList[i].userName,
            this.requireList[i].userTeam,
            this.requireList[i].bookName,
            this.requireList[i].bookISBN,
            this.requireList[i].publisher,
            this.requireList[i].bookPrice,
            this.requireList[i].reqReason,
            strHave
          ]);
        }
      }

      if (typeof XLSX == "undefined") {
        XLSX = require("xlsx");
      }
      var ws = XLSX.utils.aoa_to_sheet(this.exportData);
      var wb = XLSX.utils.book_new();
      XLSX.utils.book_append_sheet(wb, ws, "Sheet1");
      XLSX.writeFile(wb, "희망도서목록.xlsx");
      this.exportData = [
        ["(한 주간의 신청 도서를 매주 목요일 김초롱 프로에게 송부)"],
        ["목록 작성 시 ★참고 시트 확인 必"],
        [
          "지역",
          "반",
          "컨설턴트",
          "실습코치",
          "주차",
          "기간",
          "성명",
          "팀명",
          "도서명",
          "ISBN코드",
          "출판사",
          "가격",
          "신청 사유",
          "미니도서관 배치 여부"
        ]
      ];
    },
    open() {
      if (this.selected == null || this.selected.length <= 0) {
        this.selectedNullDialog = true;
        return;
      }
      this.dialog = true;
    },
    close() {
      this.titleId = null;
      this.dialog = false;
      this.selectedNullDialog = false;
      this.selected = [];
    },
    sendNotWindow() {
      if (this.titleId == null || this.titleId == "") {
        this.titleIdErrMsg = "제목을 선택해주세요";
        return;
      }

      if (this.content == null || this.content == "") {
        this.contentErrMsg = "내용을 입력해주세요";
        return;
      }

      if (this.titleId == 2) {
        this.sendAccept();
      } else if (this.titleId == 3) {
        this.sendReject();
      }
    },
    sendAccept() {
      var notwindow = {
        writer: this.user.userId,
        title: this.titleId == 2 ? "승인" : "반려",
        content: this.content
      };

      for (let i = 0; i < this.selected.length; i++) {
        this.receiver.push(this.selected[i]);
      }

      var jsonObj = new Object();

      jsonObj.notwindow = notwindow;
      jsonObj.receiver = this.receiver;

      var object = JSON.stringify(jsonObj);

      this.getAxios
        .post(
          "/admin/notwindow/register/accept",
          {
            object,
            headers: {
              "Content-Type": "application/json"
            }
          },
          this.requestHeader
        )
        .then(res => {
          this.dialog = false;
          this.selected = [];
          this.receiver = [];
          this.titleId = "";
          this.content = "";
        })
        .catch(err => {
          console.log(err);
        });
    },
    sendReject() {
      var notwindow = {
        writer: this.user.userId,
        title: this.titleId == 2 ? "승인" : "반려",
        content: this.content
      };

      for (let i = 0; i < this.selected.length; i++) {
        this.receiver.push(this.selected[i]);
      }
      var jsonObj = new Object();

      jsonObj.notwindow = notwindow;
      jsonObj.receiver = this.receiver;

      var object = JSON.stringify(jsonObj);

      this.getAxios
        .post(
          "/admin/notwindow/register/reject",
          {
            object,
            headers: {
              "Content-Type": "application/json"
            }
          },
          this.requestHeader
        )
        .then(res => {
          this.dialog = false;
          this.selected = [];
          this.receiver = [];
          this.titleId = "";
          this.content = "";
        })
        .catch(err => {
          console.log(err);
        });
    }
  },
  mounted() {
    this.getAxios
      .get("/admin/book/require/list", this.requestHeader)
      .then(res => {
        this.requireList = res.data.reqBookList;
        this.lastIndex = this.requireList.length;
      })
      .catch(err => {
        console.log(err);
      });
  }
};
</script>
<style>
th.text-start {
  padding-right: 0px;
}
</style>