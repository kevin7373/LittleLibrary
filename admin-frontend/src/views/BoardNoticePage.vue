<template>
  <v-container fluid grid-list-xl>
    <v-layout row wrap style="background-color: #f5f5f5">
      <v-flex d-flex xs12>
        <v-card width="100%">
          <v-card-title>
            공지사항
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
            :items="noticeList"
            :search="keyword"
            :options="options"
            :footer-props="{
              'items-per-page-options': [5, 10, 15, lastIndex]
            }"
          >
            <template
              v-slot:item.noticeId="{ item }"
            >{{ noticeList.map(function(x) { return x.noticeId; }).length - noticeList.map(function(x) { return x.noticeId; }).indexOf(item.noticeId) }}</template>
            <template v-slot:item.noticeTitle="{ item }">
              <v-flex d-flex class="pl-0 text-left" @click="goEdit(item.noticeId)">
                <v-clamp autoresize :max-lines="1">{{ item.noticeTitle }}</v-clamp>
              </v-flex>
            </template>
            <template v-slot:item.action="{ item }">
              <div>
                <v-icon small @click="deleteNoticeButton(item)">delete</v-icon>
              </div>
            </template>
          </v-data-table>
        </v-card>
      </v-flex>
      <v-flex class="pt-0 text-right">
        <v-btn color="#3396f4" dark depressed class="ma-0" @click="newNoticeButton()">새 공지사항 작성</v-btn>
      </v-flex>
      <v-dialog v-model="deleteDialog" persistent max-width="420">
        <v-card>
          <v-card-title class="pt-0 pb-0">
            <v-flex
              d-flex
              class="mt-10 pt-0 justify-center text-center"
              style="font-size: 15px; font-weight: bold;"
            >게시물을 삭제하시겠습니까?</v-flex>
          </v-card-title>
          <v-card-actions class="pa-0">
            <v-flex d-flex class="mt-11 mb-10 justify-center">
              <v-btn
                min-width="100px"
                height="45px"
                color="#4f5a66"
                dark
                tile
                depressed
                @click="deleteDialog = false"
              >취소</v-btn>
              <v-btn
                min-width="100px"
                height="45px"
                class="ml-3"
                color="#3c90e2"
                dark
                tile
                depressed
                @click="deleteNotice()"
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
import VClamp from "vue-clamp";

export default {
  name: "BoardNoticePage",
  components: {
    VClamp
  },
  computed: {
    ...mapGetters(["requestHeader", "user", "getAxios"])
  },
  data() {
    return {
      editItem: {},
      dialog: false,
      deleteDialog: false,
      noticeList: [],
      keyword: "",
      options: {
        itemsPerPage: 10
      },
      lastIndex: Number,
      headers: [
        {
          text: "글번호",
          value: "noticeId"
        },
        {
          text: "제목",
          value: "noticeTitle",
          sortable: false
        },
        {
          text: "작성자",
          value: "userName",
          sortable: false
        },
        {
          text: "작성일자",
          value: "noticeWriteDate",
          align: "right",
          sortable: false
        },
        {
          text: "조회수",
          value: "noticeViewCnt",
          align: "right",
          sortable: false
        },
        {
          text: "삭제",
          value: "action",
          align: "right",
          sortable: false
        }
      ]
    };
  },
  methods: {
    newNoticeButton() {
      this.$router.push("/admin/board/notice/write");
    },
    editNoticeButton(noticeId) {},
    deleteNoticeButton(item) {
      this.editItem = item;
      this.deleteDialog = true;
    },
    goEdit(noticeId) {
      this.$router.push({ name: "noticeedit", params: { noticeId: "" } });
      window.sessionStorage.setItem("noticeId", noticeId);
    },
    deleteNoticeClose() {
      this.deleteDialog = false;
    },
    deleteNotice() {
      this.getAxios
        .post(
          `/admin/board/notice/delete/${this.editItem.noticeId}`,
          {},
          this.requestHeader
        )
        .then(res => {
          const index = this.noticeList.indexOf(this.editItem);
          this.noticeList.splice(index, 1);
          this.editItem = {};
        })
        .catch(err => {
          console.log(err);
        });
      this.deleteDialog = false;
    }
  },
  mounted() {
    this.getAxios
      .get("/admin/board/notice", this.requestHeader)
      .then(res => {
        this.noticeList = res.data.noticeList;
        this.lastIndex = this.noticeList.length;
      })
      .catch(err => {
        console.log(err);
      });
  }
};
</script>
