<template>
  <v-container fluid grid-list-xl>
    <v-layout row wrap style="background-color: #f5f5f5">
      <v-flex d-flex xs12>
        <v-card width="100%">
          <v-card-title>
            회원 현황
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
            v-model="selected"
            item-key="studentId"
            :headers="headers"
            :items="users"
            :search="keyword"
            show-select
            :options="options"
            :footer-props="{
              showFirstLastPage: true,
              firstIcon: 'fa-angle-double-left',
              lastIcon: 'fa-angle-double-right',
              prevIcon: 'fa-chevron-left',
              nextIcon: 'fa-chevron-right',
              'items-per-page-options': [5, 10, 15, lastIndex]
            }"
            :sort-by="['userRoleId', 'studentId']"
          >
            <template v-slot:item.data-table-select="{ isSelected, select }">
              <v-simple-checkbox
                :value="isSelected"
                @input="select($event)"
                color="#3396f4"
                :ripple="ripple"
              ></v-simple-checkbox>
            </template>
            <template v-slot:item.userImg="{ item }">
              <v-img
                :src="item.userImg || userDefaultImg"
                width="30px"
                @click="userDetail(item.userId)"
              />
            </template>
            <template v-slot:item.studentId="{ item }">
              <v-flex
                d-flex
                class="text-left pa-0"
                @click="userDetail(item.userId)"
              >{{ item.studentId }} ({{ item.cityName }}{{ item.className }})</v-flex>
            </template>
            <template v-slot:item.userName="{ item }">
              <v-flex
                d-flex
                class="text-left pa-0"
                @click="userDetail(item.userId)"
              >{{ item.userName }}</v-flex>
            </template>
          </v-data-table>
        </v-card>
      </v-flex>
      <v-flex class="pt-0 text-right">
        <v-btn color="green" dark depressed class="ma-0" @click="makeXLSX()">엑셀로 내려받기</v-btn>
        <v-btn color="#3396f4" dark depressed class="ml-3 mt-0 mb-0 mr-0" @click="open()">알림 보내기</v-btn>
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
          <v-flex
            d-flex
            class="mt-5 mb-5 ml-10 mr-10"
          >
            <v-text-field
              outlined
              ref="title"
              label="알림 제목"
              v-model="title"
              required
              single-line
              hide-details="auto"
              :error-messages="titleErrMsg"
            ></v-text-field>
          </v-flex>
          <v-flex
            d-flex
            class="ml-10 mr-10"
          >
            <v-textarea
              outlined
              ref="content"
              v-model="content"
              label="알림 내용"
              required
              single-line
              hide-details="auto"
              auto-grow
              :error-messages="contentErMsg"
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
import XLSX from "xlsx";

export default {
  name: "UserInfoPage",
  components: {},
  computed: {
    ...mapGetters(["requestHeader", "user", "getAxios"])
  },
  data() {
    return {
      users: [],
      keyword: "",
      selected: [],
      receiver: [],
      singleSelect: false,
      selectedNullDialog: false,
      titleErrMsg: "",
      contentErMsg: "",
      lastIndex: Number,
      userDefaultImg:
        "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__340.png",
      options: {
        itemsPerPage: 10
      },
      ripple: false,
      title: "",
      content: "",
      dialog: false,
      exportData: [
        [
          "회원 번호",
          "이름",
          "이메일",
          "휴대폰 번호",
          "연체 기간",
          "지역",
          "반",
          "권한"
        ]
      ],
      headers: [
        {
          text: "이미지",
          value: "userImg",
          sortable: false
        },
        {
          text: "학번",
          value: "studentId"
        },
        {
          text: "이름",
          value: "userName"
        },
        {
          text: "핸드폰 번호",
          value: "phone"
        },
        {
          text: "연체 기간",
          value: "overduePeriod"
        },
        {
          text: "권한",
          value: "userRoleName"
        }
      ]
    };
  },
  watch: {
    title(e) {
      if (e != null || e != "") {
        this.titleErrMsg = "";
      }
    },
    content(e) {
      if (e != null || e != "") {
        this.contentErrMsg = "";
      }
    }
  },
  methods: {
    userDetail(userId) {
      this.$router.push({ name: "userprofile", params: { userId: "" } });
      window.sessionStorage.setItem("userId", userId);
    },
    makeXLSX() {
      if (this.selected.length > 0) {
        for (let i = 0; i < this.selected.length; i++) {
          this.exportData.push([
            this.selected[i].studentId,
            this.selected[i].userName,
            this.selected[i].email,
            this.selected[i].phone,
            this.selected[i].overduePeriod,
            this.selected[i].cityName,
            this.selected[i].className,
            this.selected[i].userRoleName
          ]);
        }
      } else {
        for (let i = 0; i < this.users.length; i++) {
          this.exportData.push([
            this.users[i].studentId,
            this.users[i].userName,
            this.users[i].email,
            this.users[i].phone,
            this.users[i].overduePeriod,
            this.users[i].cityName,
            this.users[i].className,
            this.users[i].userRoleName
          ]);
        }
      }
      if (typeof XLSX == "undefined") {
        XLSX = require("xlsx");
      }
      var ws = XLSX.utils.aoa_to_sheet(this.exportData);
      var wb = XLSX.utils.book_new();
      XLSX.utils.book_append_sheet(wb, ws, "Sheet1");
      XLSX.writeFile(wb, "회원현황목록.xlsx");
      this.exportData = [
        [
          "회원 번호",
          "이름",
          "이메일",
          "휴대폰 번호",
          "연체 기간",
          "지역",
          "반",
          "권한"
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
      this.title = "";
      this.content = "";
      this.dialog = false;
      this.selectedNullDialog = false;
      this.selected = [];
    },
    sendNotWindow() {
      if (this.title == null || this.title == "") {
        this.titleErrMsg = "제목을 입력해주세요";
        return;
      }

      if (this.content == null || this.content == "") {
        this.contentErMsg = "내용을 입력해주세요";
        return;
      }

      var notwindow = {
        writer: this.user.userId,
        title: this.title,
        content: this.content
      };

      for (let i = 0; i < this.selected.length; i++) {
        this.receiver.push(this.selected[i].userId);
      }
      var jsonObj = new Object();

      jsonObj.notwindow = notwindow;
      jsonObj.receiver = this.receiver;

      var object = JSON.stringify(jsonObj);

      this.getAxios
        .post(
          "/admin/notwindow/register",
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
          this.title = "";
          this.content = "";
        })
        .catch(err => {
          console.log(err);
        });
    }
  },
  mounted() {
    this.getAxios
      .get("/admin/user/list", this.requestHeader)
      .then(res => {
        this.users = res.data.userList;
        this.lastIndex = this.users.length;
      })
      .catch(err => {
        console.log(err);
      });
  }
};
</script>