<template>
  <v-container fluid grid-list-xl>
    <v-layout row wrap style="background-color: #f5f5f5">
      <v-flex d-flex xs12>
        <v-card width="100%">
          <v-card-title>
            FAQ
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
            :items="faqList"
            :search="keyword"
            :options="options"
            :footer-props="{
              'items-per-page-options': [5, 10, 15, lastIndex]
            }"
          >
            <template
              v-slot:item.faqId="{ item }"
            >{{ faqList.map(function(x) { return x.faqId; }).length - faqList.map(function(x) { return x.faqId; }).indexOf(item.faqId) }}</template>
            <template v-slot:item.faqTitle="{ item }">
              <v-flex d-flex class="pl-0 text-left" @click="goEdit(item.faqId)">
                <v-clamp autoresize :max-lines="1">{{ item.faqTitle }}</v-clamp>
              </v-flex>
            </template>
            <template v-slot:item.action="{ item }">
              <div>
                <v-icon small @click="deleteFAQButton(item)">delete</v-icon>
              </div>
            </template>
          </v-data-table>
        </v-card>
      </v-flex>
      <v-flex class="pt-0 text-right">
        <v-btn color="#3396f4" dark depressed class="ma-0" @click="newFAQButton()">새 FAQ 작성</v-btn>
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
                @click="deleteFAQ()"
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
  name: "BoardFAQPage",
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
      faqList: [],
      keyword: "",
      lastIndex: Number,
      options: {
        itemsPerPage: 10
      },
      headers: [
        {
          text: "글번호",
          value: "faqId"
        },
        {
          text: "제목",
          value: "faqTitle",
          sortable: false
        },
        {
          text: "작성자",
          value: "userName",
          sortable: false
        },
        {
          text: "작성일자",
          value: "faqWriteDate",
          align: "right",
          sortable: false
        },
        {
          text: "조회수",
          value: "faqViewCnt",
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
    newFAQButton() {
      this.$router.push("/admin/board/faq/write");
    },
    editFAQButton(faqId) {},
    deleteFAQButton(item) {
      this.editItem = item;
      this.deleteDialog = true;
    },
    goEdit(faqId) {
      this.$router.push({ name: "faqedit", params: { faqId: "" } });
      window.sessionStorage.setItem("faqId", faqId);
    },
    deleteFAQClose() {
      this.deleteDialog = false;
    },
    deleteFAQ() {
      this.getAxios
        .post(
          `/admin/board/faq/delete/${this.editItem.faqId}`,
          {},
          this.requestHeader
        )
        .then(res => {
          const index = this.faqList.indexOf(this.editItem);
          this.faqList.splice(index, 1);
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
      .get("/admin/board/faq/list", this.requestHeader)
      .then(res => {
        this.faqList = res.data.faqList;
        this.lastIndex = this.faqList.length;
      })
      .catch(err => {
        console.log(err);
      });
  }
};
</script>
