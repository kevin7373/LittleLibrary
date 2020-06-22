<template>
  <v-container fluid grid-list-xl>
    <v-layout row wrap style="background-color: #f5f5f5">
      <v-flex d-flex xs12>
        <v-card width="100%">
          <v-card-title>
            연체 회원 현황
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
            :items="users"
            :search="keyword"
            :options="options"
            :footer-props="{
              'items-per-page-options': [5, 10, 15, lastIndex]
            }"
            :sort-by="['rentFrom']"
          >
          </v-data-table>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  name: "UserRentPage",
  components: {},
  computed: {
    ...mapGetters(["requestHeader", "user", "getAxios"])
  },
  data() {
    return {
      users: [],
      keyword: "",
      options: {
        itemsPerPage: 10,
      },
      lastIndex: Number,
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
          text: "대여 도서",
          value: "bookId"
        },
        {
          text: "대여 일",
          value: "rentFrom"
        },
        {
          text: "반납 예정 일",
          value: "rentTo"
        }
      ]
    };
  },
  mounted() {
    this.getAxios
      .get("/admin/user/list", {}, this.requestHeader)
      .then(res => {
        this.users = res.data.userList;
        this.lastIndex = this.users.length
      })
      .catch(err => {
        console.log(err)
      });
  }
};
</script>