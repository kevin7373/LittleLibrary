<template>
  <v-container fluid grid-list-xl>
    <v-layout row wrap style="background-color: #f5f5f5">
      <v-flex d-flex xs12>
        <v-card width="100%">
          <v-card-title>
            QNA
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
            :items="qnaList"
            :search="keyword"
            :options="options"
            :footer-props="{
              'items-per-page-options': [5, 10, 15, lastIndex]
            }"
          >
            <template
              v-slot:item.qnaId="{ item }"
            >{{ qnaList.map(function(x) { return x.qnaId; }).length - qnaList.map(function(x) { return x.qnaId; }).indexOf(item.qnaId) }}</template>
            <template v-slot:item.qnaTitle="{ item }">
              <v-flex d-flex class="pl-0 text-left" @click="goEdit(item.qnaId)">
                <v-clamp autoresize :max-lines="1">{{ item.qnaTitle }}</v-clamp>
              </v-flex>
            </template>
            <template v-slot:item.action="{ item }">
              <div>
                <v-icon small @click="deleteQNAButton(item)">delete</v-icon>
              </div>
            </template>
          </v-data-table>
        </v-card>
      </v-flex>
      <v-dialog v-model="deleteDialog" max-width="420">
        <v-card>
          <v-card-title class="pt-0 pb-0">
            <v-flex
              d-flex
              class="mt-10 pt-0 justify-center text-center"
              style="font-size: 15px; line-height: 22px; font-weight: bold;"
            >
              게시물 삭제 시 댓글도 함께 삭제됩니다.
              <br />삭제하시겠습니까?
            </v-flex>
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
                @click="deleteQNA()"
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
  name: "BoardQNAPage",
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
      qnaList: [],
      keyword: "",
      options: {
        itemsPerPage: 10
      },
      lastIndex: Number,
      headers: [
        {
          text: "글번호",
          value: "qnaId"
        },
        {
          text: "제목",
          value: "qnaTitle",
          sortable: false
        },
        {
          text: "작성자",
          value: "userName",
          sortable: false
        },
        {
          text: "작성일자",
          value: "qnaWriteDate",
          align: "right",
          sortable: false
        },
        {
          text: "조회수",
          value: "qnaViewCnt",
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
    newQNAButton() {
      this.$router.push("/admin/board/qna/write");
    },
    deleteQNAButton(item) {
      this.editItem = item;
      this.deleteDialog = true;
    },
    goEdit(qnaId) {
      this.$router.push({ name: "qnaedit", params: { qnaId: "" } });
      window.sessionStorage.setItem("qnaId", qnaId);
    },
    deleteQNAClose() {
      this.deleteDialog = false;
    },
    deleteQNA() {
      this.getAxios
        .post(
          `/admin/board/qna/delete/${this.editItem.qnaId}`,
          {},
          this.requestHeader
        )
        .then(res => {
          const index = this.qnaList.indexOf(this.editItem);
          this.qnaList.splice(index, 1);
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
      .get("/admin/board/qna/list", this.requestHeader)
      .then(res => {
        this.qnaList = res.data.qnaList;
        this.lastIndex = this.qnaList.length;
      })
      .catch(err => {
        console.log(err);
      });
  }
};
</script>
