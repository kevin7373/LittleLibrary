<template>
  <v-layout>
    <v-card-title style="color: black">
      총
      <span style="color: #3c90e2" class="ml-1">{{qnaCommentList.length}}</span>건의 댓글이 있습니다
    </v-card-title>
    <v-flex d-flex class="ma-0 pa-0">
      <v-row no-gutters>
        <v-col cols="12">
          <v-textarea
            outlined
            full-width
            ref="content"
            v-model="qnaComment"
            label="댓글"
            required
            single-line
            hide-details
            no-resize
            auto-grow
          ></v-textarea>
        </v-col>
      </v-row>
    </v-flex>
    <v-flex d-flex class="justify-end pr-0">
      <v-btn color="#3c90e2" tile depressed dark class="ma-0">등록</v-btn>
    </v-flex>
  </v-layout>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  name: "QnaComment",
  props: ["qnaId"],
  data() {
    return {
      qnaCommentContent: "",
      editItem: {},
      deleteDialog: false,
      commentheaders: [
        { text: "댓글 내용", sortable: false, value: "qnaCommentContent" },
        { text: "작성일자", sortable: false, value: "qnaCommentDate" },
        { text: "작성자", sortable: false, value: "qnaCommentWriter" },
        { text: "삭제", sortable: false, value: "action" }
      ],
      comments: [],
      commentCheck: false,
      editCheck: false
    };
  },
  computed: {
    ...mapGetters(["requestHeader", "user", "getAxios"])
  },
  methods: {
    checkuser(item) {
      if (this.user.userId === item.qnaCommentWriter) {
        return true;
      }
    },
    commentSubmit() {
      if (this.editCheck) {
        if (this.checkuser(this.editItem)) {
          this.getAxios
            .post(
              `/admin/qna/${this.qnaId}/update/${this.editItem.qnaCommentId}`,
              {
                qnaCommentContent: this.qnaCommentContent
              },
              this.requestHeader
            )
            .then(res => {
              this.editItem.qnaCommentContent = this.qnaCommentContent;
              const index = this.comments.indexOf(this.editItem);
              Object.assign(this.comments[index], this.editItem);
              this.editItem = {};
              this.qnaCommentContent = "";
            })
            .catch(err => {
              console.log(err);
            });
        }
        this.editCheck = false;
      } else {
        this.getAxios
          .post(
            `/admin/qna/${this.qnaId}/register`,
            {
              qnaCommentContent: this.qnaCommentContent
            },
            this.requestHeader
          )
          .then(res => {
            this.commentCheck = true;
            this.$emit("commentCheck", this.commentCheck);
            this.commentCheck = false;
          })
          .catch(err => {
            console.log(err);
          });
      }
    },
    editCommentButton(item) {
      this.qnaCommentContent = item.qnaCommentContent;
      this.editItem = item;
      this.editCheck = true;
    },
    deleteCommentButton(item) {
      this.deleteDialog = true;
      this.editItem = item;
    },
    deleteComment() {
      if (this.checkuser(this.editItem)) {
        this.getAxios
          .post(
            `/admin/qna/${this.qnaId}/delete/${this.editItem.qnaCommentId}`,
            this.requestHeader
          )
          .then(res => {
            const index = this.comments.indexOf(this.editItem);
            this.comments.splice(index, 1);
            this.editItem = {};
          })
          .catch(err => {
            console.log(err);
          });
        this.deleteDialog = false;
      }
    },
    cancel() {
      this.editItem = {};
      this.editCheck = false;
      this.qnaCommentContent = "";
      this.deleteDialog = false;
    },
    init() {
      this.getAxios
        .get(`/admin/qna/${this.qnaId}/list`, this.requestHeader)
        .then(res => {
          this.comments = res.data.qnaCommentList;
        })
        .catch(err => {
          console.log(err);
        });
    }
  },
  mounted() {
    this.init();
  }
};
</script>