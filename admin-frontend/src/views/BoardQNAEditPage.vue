<template>
  <v-container fluid grid-list-xl>
    <v-layout row wrap style="background-color: #f5f5f5">
      <v-flex xs12>
        <v-card>
          <v-card-title class="pt-0">
            QNA 수정
            <v-spacer></v-spacer>
            <v-card-subtitle class="pb-0">
              <v-col class="pt-0">{{qna.qnaWriteDate}}</v-col>
            </v-card-subtitle>
          </v-card-title>
          <v-card-subtitle class="pb-0">
            <v-row>
              <v-col class="pt-0">{{qna.userName}}</v-col>
            </v-row>
          </v-card-subtitle>
          <v-list dense class="pt-0 pb-0">
            <v-form @submit.prevent="writeQNA">
              <v-card-text class="pt-0">
                <v-divider v-if="$vuetify.breakpoint.mdAndUp" style="border-color: #6c8093"></v-divider>
                <v-row no-gutters>
                  <v-flex
                    sm2
                    d-flex
                    style="margin-top: auto; margin-bottom: auto;"
                    v-if="$vuetify.breakpoint.mdAndUp"
                  >제목</v-flex>
                  <v-divider v-if="$vuetify.breakpoint.mdAndUp" vertical style="margin-right: 2%"></v-divider>
                  <v-col class="ma-3 mb-3 pa-0">
                    <v-flex class="ma-0 pa-0" v-if="$vuetify.breakpoint.smAndDown">제목</v-flex>
                    <v-text-field
                      outlined
                      ref="title"
                      v-model="qnaTitle"
                      label="제목"
                      required
                      single-line
                      hide-details="auto"
                      :error-messages="qnaTitleErrMsg"
                    ></v-text-field>
                  </v-col>
                </v-row>
                <v-divider v-if="$vuetify.breakpoint.mdAndUp"></v-divider>
                <v-row no-gutters>
                  <v-flex
                    sm2
                    d-flex
                    style="margin-top: auto; margin-bottom: auto;"
                    v-if="$vuetify.breakpoint.mdAndUp"
                  >내용</v-flex>
                  <v-divider v-if="$vuetify.breakpoint.mdAndUp" vertical style="margin-right: 2%"></v-divider>
                  <v-col class="ma-3 mb-3 pa-0">
                    <v-flex class="ma-0 pa-0" v-if="$vuetify.breakpoint.smAndDown">내용</v-flex>
                    <v-textarea
                      outlined
                      ref="content"
                      v-model="qnaContent"
                      label="내용"
                      required
                      single-line
                      hide-details="auto"
                      :error-messages="qnaContentErrMsg"
                      auto-grow
                    ></v-textarea>
                  </v-col>
                </v-row>
                <v-divider v-if="$vuetify.breakpoint.mdAndUp"></v-divider>
              </v-card-text>
              <v-flex d-flex class="pt-0 pb-0 justify-center">
                <v-btn
                  min-width="120px"
                  height="50px"
                  color="#4f5a66"
                  dark
                  tile
                  depressed
                  class="ma-0"
                  @click="goBack()"
                >취소</v-btn>
                <v-btn
                  min-width="120px"
                  height="50px"
                  color="#3c90e2"
                  dark
                  tile
                  depressed
                  class="ml-3"
                  type="submit"
                >저장</v-btn>
              </v-flex>
            </v-form>
          </v-list>
          <v-card-text class="pb-0">
            <v-divider></v-divider>
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
                    v-model="qnaCommentContent"
                    label="댓글"
                    required
                    single-line
                    hide-details="auto"
                    :error-messages="qnaCommentContentErrMsg"
                    no-resize
                    auto-grow
                  ></v-textarea>
                </v-col>
              </v-row>
            </v-flex>
            <v-flex d-flex class="justify-end pr-0">
              <v-btn color="#3c90e2" dark tile depressed class="ma-0" @click="regComment(qnaId)">등록</v-btn>
            </v-flex>
          </v-card-text>
          <v-card-text>
            <v-divider></v-divider>
            <v-list two-line class="pb-0">
              <template v-for="(item, index) in qnaCommentList">
                <v-list-item :key="item.qnaCommentId" dense>
                  <v-list-item-content class="pb-5">
                    <v-flex d-flex class="pa-0 pb-0 mb-0" style="height: 36px">
                      <v-avatar>
                        <v-img
                          :src="item.userImg || userDefaultImg"
                          max-width="36px"
                          max-height="36px !important"
                        />
                      </v-avatar>
                      <v-list-item-title
                        class="pl-1"
                      >{{ item.userName }}({{ item.cityName }}{{ item.className }})</v-list-item-title>
                    </v-flex>
                    <div class="px-9 mx-4 pt-0 mt-0 d-flex justify-space-between">
                      <v-list-item-title>{{ item.qnaCommentContent }}</v-list-item-title>
                      <div class="d-flex">
                        <v-icon small class="mr-2" @click="editCommentButton(item)">edit</v-icon>
                        <v-icon small @click="deleteCommentButton(item)">delete</v-icon>
                      </div>
                    </div>
                  </v-list-item-content>
                </v-list-item>
                <v-divider v-if="index + 1 < qnaCommentList.length" :key="index"></v-divider>
              </template>
            </v-list>
            <v-divider v-if="qnaCommentList.length > 0"></v-divider>
          </v-card-text>
        </v-card>
      </v-flex>
    </v-layout>
    <v-dialog v-model="editDialog" persistent width="600px">
      <v-card>
        <v-flex
          d-flex
          dark
          style="background-color: #3396f4; color: white"
          class="pl-10 pt-5 pb-5"
        >댓글 수정</v-flex>
        <v-flex
          d-flex
          class="mt-10 ml-10 mr-10 mb-5"
          style="border-style: solid; border-width: 1px; border-color: #eceef1"
        >
          <v-card-title>
            <v-flex d-flex>
              <v-avatar class="mt-2">
                <v-img :src="userImg || userDefaultImg" width="10px" />
              </v-avatar>
              <v-flex d-flex class="flex-column">
                <span class="pl-3 mt-auto mb-auto">
                  <span style="color: #777f88; font-size: 15px">보낸사람</span>
                  <span class="pl-3" style="font-size: 15px">{{ userName }}</span>
                  <span
                    style="font-size: 15px"
                    v-if="cityName && className"
                  >({{ cityName }}{{ className }})</span>
                </span>
                <span class="pl-3 mt-auto mb-auto">
                  <span style="color: #777f88; font-size: 15px">보낸날짜</span>
                  <span class="pl-3" style="font-size: 15px">{{ qnaCommentDate }}</span>
                </span>
              </v-flex>
            </v-flex>
          </v-card-title>
        </v-flex>
        <v-flex
          d-flex
          class="ml-10 mr-10"
          style="border-style: solid; border-width: 1px; border-color: #eceef1;"
        >
          <v-textarea
            outlined
            ref="userQnaComment"
            v-model="userQnaComment"
            label="댓글 내용"
            required
            single-line
            hide-details="auto"
            auto-grow
            :error-messages="userQnaCommentErrMsg"
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
            @click="cancel()"
          >취소</v-btn>
          <v-btn
            min-width="120px"
            height="50px"
            color="#3c90e2"
            dark
            tile
            depressed
            class="ml-3 ma-0"
            @click="editUserComment()"
          >작성</v-btn>
        </v-flex>
      </v-card>
    </v-dialog>
    <v-dialog v-model="deleteDialog" max-width="400">
      <v-card tile style="border-radius: 0px;">
        <v-card-title class="pt-0 pb-0">
          <v-flex
            d-flex
            class="mt-10 pt-0 justify-center"
            style="border-radius: 0px !important; font-size: 15px; font-weight: bold;"
          >댓글을 삭제하시겠습니까?</v-flex>
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
              @click="cancel()"
            >취소</v-btn>
            <v-btn
              min-width="100px"
              height="45px"
              class="ml-3"
              color="#3c90e2"
              dark
              tile
              depressed
              @click="deleteComment()"
            >삭제</v-btn>
          </v-flex>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script>
import { mapGetters } from "vuex";
import QNAComment from "../components/QNAComment";

export default {
  name: "BoardQNAEditPage",
  components: {
    QNAComment
  },
  computed: {
    ...mapGetters(["requestHeader", "user", "getAxios"])
  },
  data() {
    return {
      dialog: false,
      qna: [],
      qnaCommentId: "",
      qnaCommentContent: "",
      qnaCommentList: [],
      editComment: {},
      editDialog: false,
      deleteDialog: false,
      userImg: "",
      cityName: "",
      className: "",
      userName: "",
      qnaCommentDate: "",
      userQnaComment: "",
      comment: {},
      qnaTitle: "",
      qnaContent: "",
      qnaTitleErrMsg: "",
      qnaContentErrMsg: "",
      qnaCommentContentErrMsg: "",
      userQnaCommentErrMsg: "",
      qnaId: "",
      userDefaultImg:
        "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__340.png"
    };
  },
  watch: {
    qnaTitle(e) {
      if (e != null || e != "") {
        this.qnaTitleErrMsg = "";
      }
    },
    qnaContent(e) {
      if (e != null || e != "") {
        this.qnaContentErrMsg = "";
      }
    },
    qnaCommentContent(e) {
      if (e != null || e != "") {
        this.qnaCommentContentErrMsg = "";
      }
    },
    userQnaComment(e) {
      if (e != null || e != "") {
        this.userQnaCommentErrMsg = "";
      }
    }
  },
  methods: {
    goBack() {
      this.$router.go(-1);
    },
    writeQNA() {
      if (this.qnaTitle == null || this.qnaTitle == "") {
        this.qnaTitleErrMsg = "제목은 필수항목입니다";
        return;
      }

      if (this.qnaContent == null || this.qnaContent == "") {
        this.qnaContentErrMsg = "내용은 필수항목입니다";
        return;
      }

      this.getAxios
        .post(
          `/admin/board/qna/update/${this.qnaId}`,
          {
            qnaTitle: this.qnaTitle,
            qnaContent: this.qnaContent
          },
          this.requestHeader
        )
        .then(res => {
          this.$router.push("/admin/board/qna");
        })
        .catch(err => {
          console.log(err);
        });
    },
    regComment(reqId) {
      if (this.qnaCommentContent == null || this.qnaCommentContent == "") {
        this.qnaCommentContentErrMsg = "댓글을 입력해주세요";
        return;
      }

      var qnaComment = {
        qnaCommentContent: this.qnaCommentContent,
        qnaCommentWriter: this.user.userId,
        qnaId: this.qnaId
      };

      this.editComment = {
        qnaCommentContent: this.qnaCommentContent,
        qnaCommentWriter: this.user.userId,
        qnaId: this.qnaId,
        userName: this.user.userName,
        userImg: "https://i02b206.p.ssafy.io/image" + this.user.userImg,
        cityName: this.user.cityName,
        className: this.user.className
      };

      this.getAxios
        .post(
          `/admin/board/qna/${this.qnaId}/register`,
          qnaComment,
          this.requestHeader
        )
        .then(res => {
          location.reload();
        })
        .catch(err => {
          console.log(err);
        });
    },
    editCommentButton(comment) {
      this.comment = comment;
      this.userQnaComment = comment.qnaCommentContent;
      this.userImg = comment.userImg;
      this.cityName = comment.cityName;
      this.className = comment.className;
      this.userName = comment.userName;
      this.qnaCommentDate = comment.qnaCommentDate;
      this.editDialog = true;
    },
    editUserComment() {
      if(this.userQnaComment == null || this.userQnaComment == "") {
        this.userQnaCommentErrMsg = "댓글을 입력해주세요"
        return;
      }
      this.comment.qnaCommentContent = this.userQnaComment;
      const index = this.qnaCommentList.indexOf(this.comment);
      this.getAxios
        .post(
          `/admin/board/qna/${this.qnaId}/update/${this.comment.qnaCommentId}`,
          this.comment,
          this.requestHeader
        )
        .then(res => {
          Object.assign(this.qnaCommentList[index], this.comment);
          this.deleteDialog = false;
          this.editDialog = false;
        })
        .catch(err => {
          console.log(err);
        });
      this.cancel();
    },
    deleteCommentButton(comment) {
      this.comment = comment;
      this.deleteDialog = true;
    },
    deleteComment() {
      const index = this.qnaCommentList.indexOf(this.comment);

      this.getAxios
        .post(
          `/admin/board/qna/${this.qnaId}/delete/${this.comment.qnaCommentId}`,
          {},
          this.requestHeader
        )
        .then(res => {
          this.qnaCommentList.splice(index, 1);
          this.deleteDialog = false;
          this.editDialog = false;
        })
        .catch(err => {
          console.log(err);
        });
      this.cancel();
    },
    cancel() {
      this.comment = {};
      this.qnaCommentContent = "";
      this.userQnaComment = "";
      this.deleteDialog = false;
      this.editDialog = false;
    }
  },
  created() {
    this.qnaId = window.sessionStorage.getItem("qnaId");

    this.getAxios
      .get("/admin/board/qna/read/" + this.qnaId, this.requestHeader)
      .then(res => {
        this.qna = res.data.qna;
        this.qnaTitle = res.data.qna.qnaTitle;
        this.qnaContent = res.data.qna.qnaContent;

        this.getAxios
          .get(`/admin/board/qna/${this.qnaId}/list`, this.requestHeader)
          .then(res => {
            this.qnaCommentList = res.data.qnaCommentList;
          })
          .catch(err => {
            console.log(err);
          });
      })
      .catch(e => {
        console.log(err);
      });
  }
};
</script>