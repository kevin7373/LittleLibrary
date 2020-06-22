<template>
  <v-container fluid grid-list-xl>
    <v-layout row wrap style="background-color: #f5f5f5">
      <v-flex d-flex xs12>
        <v-card width="100%">
          <v-card-title>
            대여 회원 현황
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
              showFirstLastPage: true,
              firstIcon: 'fa-angle-double-left',
              lastIcon: 'fa-angle-double-right',
              prevIcon: 'fa-chevron-left',
              nextIcon: 'fa-chevron-right',
              'items-per-page-options': [5, 10, 15, lastIndex]
            }"
            :sort-by="['rentFrom']"
          >
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
              >{{ item.studentId }}({{ item.cityName }}{{ item.className }})</v-flex>
            </template>
            <template v-slot:item.userName="{ item }">
              <v-flex
                d-flex
                class="text-left pa-0"
                @click="userDetail(item.userId)"
              >{{ item.userName }}</v-flex>
            </template>
            <template v-slot:item.state="{ item }">
              <v-chip
                v-if="item.state == '대여'"
                color="#ffc105"
                text-color="white"
                label
              >{{ item.state }}</v-chip>
              <v-chip
                v-else-if="item.state == '연체'"
                color="#ff0017"
                text-color="white"
                label
              >{{ item.state }}</v-chip>
            </template>
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
      userDefaultImg:
        "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__340.png",
      options: {
        itemsPerPage: 10
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
        },
        {
          text: "상태",
          value: "state"
        }
      ]
    };
  },
  methods: {
    userDetail(userId) {
      this.$router.push({ name: "userprofile", params: { userId: "" } });
      window.sessionStorage.setItem("userId", userId);
    }
  },
  mounted() {
    this.getAxios
      .get("/admin/user/rent/list", this.requestHeader)
      .then(res => {
        this.users = res.data.rentUserList;
        this.lastIndex = this.users.length
      })
      .catch(err => {
        console.log(err);
      });
  }
};
</script>