<template>
  <v-container fluid grid-list-xl>
    <v-layout row wrap style="background-color: #f5f5f5">
      <v-flex d-flex xs12>
        <v-card width="100%">
          <v-card-title>
            대여 현황
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
            :items="rentBookList"
            :search="keyword"
            :options="options"
            v-model="selected"
            show-select
            item-key="bookId"
            :footer-props="{
              showFirstLastPage: true,
              firstIcon: 'fa-angle-double-left',
              lastIcon: 'fa-angle-double-right',
              prevIcon: 'fa-chevron-left',
              nextIcon: 'fa-chevron-right',
              'items-per-page-options': [5, 10, 15, lastIndex]
            }"
            :sort-by="['rentFrom']"
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
                @click="bookDetail(item.bookId)"
              />
            </template>
            <template v-slot:item.bookId="{ item }">
              <v-flex
                d-flex
                class="pl-0 text-left"
                @click="bookDetail(item.bookId)"
              >{{ item.bookId }}</v-flex>
            </template>
            <template v-slot:item.userId="{ item }">
              <v-flex
                d-flex
                class="pl-0 text-left"
                @click="bookDetail(item.bookId)"
              >{{ item.userName }}</v-flex>
            </template>
            <template v-slot:item.state="{ item }">
              <v-chip
                v-if="item.state.split(' ')[0] == '대여'"
                color="#ffc105"
                text-color="white"
                label
              >{{ item.state }}</v-chip>
              <v-chip
                v-else-if="item.state.split(' ')[0] == '연체'"
                color="#ff0017"
                text-color="white"
                label
              >{{ item.state }}</v-chip>
            </template>
          </v-data-table>
        </v-card>
      </v-flex>
      <v-flex class="pt-0 text-right">
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
            <v-select
              outlined
              v-model="titleId"
              :items="title"
              label="대여 / 연체"
              item-text="name"
              item-value="titleId"
              required
              single-line
              hide-details="auto"
              :error-messages="titleIdErrMsg"
            ></v-select>
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

export default {
  name: "BookRent",
  components: {},
  computed: {
    ...mapGetters(["requestHeader", "user", "getAxios"])
  },
  data() {
    return {
      rentBookList: [],
      keyword: "",
      color: "#3396f4",
      bookDefaultImg:
        "https://dynamicmediainstitute.org/wp-content/themes/dynamic-media-institute/imagery/default-thesis-abstract.png",
      options: {
        itemsPerPage: 10
      },
      ripple: false,
      selected: [],
      receiver: [],
      titleId: "",
      content: "",
      dialog: false,
      selectedNullDialog: false,
      titleIdErrMsg: "",
      contentErrMsg: "",
      lastIndex: Number,
      title: [
        {
          name: "대여",
          titleId: "0"
        },
        {
          name: "연체",
          titleId: "1"
        }
      ],
      headers: [
        {
          text: "이미지",
          value: "bookImg"
        },
        {
          text: "도서 번호",
          value: "bookId"
        },
        {
          text: "빌린 사람",
          value: "userId"
        },
        {
          text: "대여 일",
          value: "rentFrom"
        },
        {
          text: "반납 예정 일",
          value: "rentTo"
        },
        {
          text: "상태",
          value: "state"
        }
      ]
    };
  },
  watch: {
    selected(e) {},
    keyword(e) {
      if (e == "") {
        window.sessionStorage.removeItem("rentListKeyword");
      }
    },
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
    bookDetail(bookId) {
      this.$router.push({ name: "bookdetail", params: { bookId: "" } });
      window.sessionStorage.setItem("bookId", bookId);
    },
    open() {
      if (this.selected == null || this.selected.length <= 0) {
        this.selectedNullDialog = true;
        return;
      }
      this.dialog = true;
    },
    close() {
      this.dialog = false;
      this.selectedNullDialog = false;
      this.selected = [];
      this.receiver = [];
    },
    sendNotWindow() {
      if (this.titleId == null || this.titleId == "") {
        this.titleIdErrMsg = "제목을 선택해주세요"
        return;
      }

      if (this.content == null || this.content == "") {
        this.contentErrMsg = "내용을 입력해주세요"
        return;
      }

      var notwindow = {
        writer: this.user.userId,
        title: this.titleId == 0 ? "대여" : "연체",
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
          this.titleId = "";
          this.content = "";
        })
        .catch(err => {
          console.log(err);
        });
    }
  },
  mounted() {
    this.keyword = window.sessionStorage.getItem("rentListKeyword");

    this.getAxios
      .get("/admin/book/rent/list", this.requestHeader)
      .then(res => {
        this.rentBookList = res.data.rentBookList;
        this.lastIndex = this.rentBookList.length;
      })
      .catch(err => {
        console.log(err);
      });
  }
};
</script>