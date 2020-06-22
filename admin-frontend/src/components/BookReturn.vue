<template>
  <v-container fluid grid-list-xl>
    <v-layout row wrap style="background-color: #f5f5f5">
      <v-flex d-flex xs12>
        <v-card width="100%">
          <v-card-title>
            반납 현황
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
            :items="returnBookList"
            :search="keyword"
            :options="options"
            :footer-props="{
              showFirstLastPage: true,
              firstIcon: 'fa-angle-double-left',
              lastIcon: 'fa-angle-double-right',
              prevIcon: 'fa-chevron-left',
              nextIcon: 'fa-chevron-right',
              'items-per-page-options': [5, 10, 15, lastIndex]
            }"
            :sort-by="['rentFrom']"
            sort-desc
          >
            <template v-slot:item.bookImg="{ item }">
              <v-img
                :src="item.bookImg || bookDefaultImg"
                width="30px"
                @click="returnBookDetail(item.bookRentId)"
              />
            </template>
            <template v-slot:item.bookId="{ item }">
              <v-flex
                d-flex
                class="pl-0 text-left"
                @click="returnBookDetail(item.bookRentId)"
              >{{ item.bookId }}</v-flex>
            </template>
            <template v-slot:item.userId="{ item }">
              <v-flex
                d-flex
                class="pl-0 text-left"
                @click="returnBookDetail(item.bookRentId)"
              >{{ item.userName }}</v-flex>
            </template>
          </v-data-table>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>
​
<script>
import { mapGetters } from "vuex";

export default {
  name: "BookReturn",
  components: {},
  computed: {
    ...mapGetters(["requestHeader", "user", "getAxios"])
  },
  data() {
    return {
      rentBookList: [],
      returnBookList: [],
      expiredBookList: [],
      keyword: "",
      bookDefaultImg:
        "https://dynamicmediainstitute.org/wp-content/themes/dynamic-media-institute/imagery/default-thesis-abstract.png",
        lastIndex: Number,
      options: {
        itemsPerPage: 10
      },
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
          value: "userName"
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
          text: "실제 반납 일",
          value: "realReturn"
        }
      ]
    };
  },
  methods: {
    returnBookDetail(bookRentId) {
      window.sessionStorage.removeItem("bookListKeyword");
      window.sessionStorage.removeItem("rentListKeyword");
      window.sessionStorage.setItem("bookRentId", bookRentId);
      this.$router.push({ name: "returnbookdetail", params: { bookRentId: "" } });
    }
  },
  mounted() {
    this.getAxios
      .get("/admin/book/return/list", this.requestHeader)
      .then(res => {
        this.returnBookList = res.data.returnBookList;
        this.lastIndex = this.returnBookList.length
      });
  }
};
</script>
