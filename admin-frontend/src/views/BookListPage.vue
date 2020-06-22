<template>
  <v-container fluid grid-list-xl>
    <v-layout row wrap style="background-color: #f5f5f5">
      <v-flex d-flex xs12>
        <v-card width="100%">
          <v-card-title>
            도서 현황
            <v-spacer></v-spacer>
            <v-text-field
              v-model="keyword"
              append-icon="search"
              label="검색"
              single-line
              hide-details
            ></v-text-field>
          </v-card-title>
          <v-data-table
            :headers="headers"
            :items="books"
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
              'items-per-page-options': [10, 20, 30, 40, lastIndex]
            }"
            :sort-by="['bookId']"
            class="elevation-1"
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
              <v-img :src="item.bookImg || bookDefaultImg" width="30px" />
            </template>
            <template v-slot:item.bookId="{ item }">
              <v-flex d-flex class="pl-0 text-left" @click="bookDetail(item.bookId)">
                <v-clamp autoresize :max-lines="1">{{ item.bookId }}</v-clamp>
              </v-flex>
            </template>
            <template v-slot:item.bookName="{ item }">
              <v-flex class="pl-0 text-left ma-0" @click="bookDetail(item.bookId)">
                <v-clamp autoresize :max-lines="1">{{ item.bookName }}</v-clamp>
              </v-flex>
            </template>
            <template v-slot:item.bookPrice="{ item }">{{ item.position }}</template>
            <template v-slot:item.state="{ item }">
              <v-chip
                v-if="item.state == '보유'"
                color="#58d8a3"
                text-color="white"
                label
              >{{ item.state }}</v-chip>
              <v-chip
                v-else-if="item.state.split(' ')[0] == '대여'"
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
        <v-btn color="green" dark depressed class="ma-0" @click="makeXLSX()">엑셀로 내려받기</v-btn>
        <v-btn color="#ff0017" dark depressed class="ma-0 ml-3" @click="deleteDialog()">책 삭제</v-btn>
      </v-flex>
      <v-flex xs12 v-if="deleteRejectList.length > 0">
        <v-card width="100%">
          <v-flex d-flex style="color: #ff0017;">다음의 책은 삭제 되지 않았습니다.</v-flex>
          <v-simple-table>
            <template v-slot:default>
              <thead>
                <tr>
                  <th class="text-center">번호</th>
                  <th class="text-center">이유</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(item, index) in deleteRejectList" :key="item">
                  <td class="text-center">{{ index + 1 }}</td>
                  <td class="text-center">{{ deleteRejectList[index] }}</td>
                </tr>
              </tbody>
            </template>
          </v-simple-table>
        </v-card>
      </v-flex>
      <v-dialog v-model="selectedNullDialog" max-width="400">
        <v-card tile style="border-radius: 0px;">
          <v-card-title class="pt-0 pb-0">
            <v-flex
              d-flex
              class="mt-10 pt-0 justify-center"
              style="border-radius: 0px !important; font-size: 15px; font-weight: bold;"
            >삭제할 책을 선택해주세요.</v-flex>
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
      <v-dialog v-model="deleteBookDialog" max-width="400">
        <v-card>
          <v-card-title class="pt-0 pb-0">
            <v-flex
              d-flex
              class="mt-10 pt-0 justify-center text-center"
              style="font-size: 15px; line-height: 22px; font-weight: bold;"
            >선택한 도서를 삭제하시겠습니까?</v-flex>
          </v-card-title>
          <v-card-actions class="pa-0">
            <v-flex d-flex class="mt-11 mb-10 justify-center">
              <v-btn
                min-width="100px"
                height="46px"
                color="#4f5a66"
                dark
                tile
                depressed
                @click="close()"
              >취소</v-btn>
              <v-btn
                min-width="100px"
                height="45px"
                class="ml-3"
                color="#3c90e2"
                dark
                tile
                depressed
                @click="deleteBook()"
              >삭제</v-btn>
            </v-flex>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-layout>
  </v-container>
</template>

<script>
import { mapGetters } from "vuex";
import XLSX from "xlsx";
import VClamp from "vue-clamp";

export default {
  name: "BookListPage",
  components: {
    VClamp
  },
  computed: {
    ...mapGetters(["requestHeader", "user", "getAxios"])
  },
  data() {
    return {
      books: [],
      keyword: "",
      param: "",
      selected: [],
      selectAll: false,
      deleteBookDialog: false,
      selectedNullDialog: false,
      deleteRejectList: [],
      bookDefaultImg:
        "https://dynamicmediainstitute.org/wp-content/themes/dynamic-media-institute/imagery/default-thesis-abstract.png",
      options: {
        itemsPerPage: 20
      },
      exportData: [
        [
          "도서 ID",
          "ISBN 코드",
          "도서 제목",
          "출판사",
          "출판일",
          "저자",
          "가격",
          "카테고리",
          "지역",
          "반",
          "빌린 횟수",
          "이미지 URL"
        ]
      ],
      ripple: false,
      lastIndex: Number,
      headers: [
        {
          text: "이미지",
          value: "bookImg",
          sortable: false
        },
        {
          text: "도서 번호",
          value: "bookId"
        },
        {
          text: "도서 제목",
          value: "bookName"
        },
        {
          text: "도서 위치",
          value: "position"
        },
        {
          text: "상태",
          value: "state"
        },
        {
          text: "대여 횟수",
          value: "rentCnt",
          align: "center"
        }
      ]
    };
  },
  watch: {
    keyword(e) {
      if (e == "") {
        window.sessionStorage.removeItem("bookListKeyword");
      }
    }
  },
  methods: {
    bookDetail(bookId) {
      window.sessionStorage.removeItem("bookListKeyword");
      window.sessionStorage.removeItem("rentListKeyword");
      this.$router.push({ name: "bookdetail", params: { bookId: "" } });
      window.sessionStorage.setItem("bookId", bookId);
    },
    select() {
      this.selected = [];
    },
    makeXLSX() {
      if (this.selected.length > 0) {
        for (let i = 0; i < this.selected.length; i++) {
          this.exportData.push([
            this.selected[i].bookId,
            this.selected[i].bookISBN,
            this.selected[i].bookName,
            this.selected[i].publisher,
            this.selected[i].publishDate,
            this.selected[i].author,
            this.selected[i].bookPrice,
            this.selected[i].categoryName,
            this.selected[i].cityName,
            this.selected[i].className,
            this.selected[i].rentCnt,
            this.selected[i].bookImg
          ]);
        }
      } else {
        for (let i = 0; i < this.books.length; i++) {
          this.exportData.push([
            this.books[i].bookId,
            this.books[i].bookISBN,
            this.books[i].bookName,
            this.books[i].publisher,
            this.books[i].publishDate,
            this.books[i].author,
            this.books[i].bookPrice,
            this.books[i].categoryName,
            this.books[i].cityName,
            this.books[i].className,
            this.books[i].rentCnt,
            this.books[i].bookImg
          ]);
        }
      }

      if (typeof XLSX == "undefined") {
        XLSX = require("xlsx");
      }
      var ws = XLSX.utils.aoa_to_sheet(this.exportData);
      var wb = XLSX.utils.book_new();
      XLSX.utils.book_append_sheet(wb, ws, "Sheet1");
      XLSX.writeFile(wb, "도서현황목록.xlsx");
      this.exportData = [
        [
          "도서 ID",
          "ISBN 코드",
          "도서 제목",
          "출판사",
          "출판일",
          "저자",
          "가격",
          "카테고리",
          "지역",
          "반",
          "빌린 횟수",
          "이미지 URL"
        ]
      ];
    },
    deleteDialog() {
      if (this.selected.length <= 0) {
        this.selectedNullDialog = true;
        return;
      }
      this.deleteBookDialog = true;
    },
    deleteBook() {
      this.deleteBookDialog = false;
      var tmpBooks = this.selected;

      this.getAxios
        .post("/admin/book/delete", this.selected, this.requestHeader)
        .then(res => {
          for (let i = 0; i < tmpBooks.length; i++) {
            if (res.data.deleteRejectBookId.indexOf(tmpBooks[i].bookId) == 0) {
              continue;
            }
            const index = this.books.indexOf(tmpBooks[i]);
            this.books.splice(index, 1);
          }
          this.deleteRejectList = res.data.deleteRejectList;
          this.selected = [];
        })
        .catch(err => {
          console.log(err);
        });
    },
    close() {
      this.deleteBookDialog = false;
      this.selectedNullDialog = false;
      this.selected = [];
    }
  },
  mounted() {
    this.keyword = window.sessionStorage.getItem("bookListKeyword");
    this.getAxios
      .get("/admin/book/list", this.requestHeader)
      .then(res => {
        this.books = res.data.books;
        this.lastIndex = this.books.length;

        for (let i = 0; i < this.books.length; i++) {
          const position = this.books[i].cityName + this.books[i].className
          this.books[i].position = position
        }
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
