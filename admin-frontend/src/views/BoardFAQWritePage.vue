<template>
  <v-container fluid grid-list-xl>
    <v-layout row wrap style="background-color: #f5f5f5">
      <v-flex xs12>
        <v-card>
          <v-card-title>
            FAQ 작성
            <v-spacer></v-spacer>
          </v-card-title>
          <v-list dense class="pt-0">
            <v-form @submit.prevent="writeFAQ">
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
                      v-model="title"
                      label="제목"
                      required
                      single-line
                      hide-details="auto"
                      :error-messages="titleErrMsg"
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
                      v-model="content"
                      label="내용"
                      required
                      single-line
                      hide-details="auto"
                      auto-grow
                      :error-messages="contentErrMsg"
                    ></v-textarea>
                  </v-col>
                </v-row>
                <v-divider v-if="$vuetify.breakpoint.mdAndUp"></v-divider>
              </v-card-text>
              <v-flex d-flex class="pt-0 justify-center">
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
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  name: "BoardFAQWritePage",
  components: {},
  computed: {
    ...mapGetters(["requestHeader", "user", "getAxios"])
  },
  data() {
    return {
      dialog: false,
      title: "",
      content: "",
      titleErrMsg: "",
      contentErrMsg: ""
    };
  },
  watch: {
    title(e) {
      if (e != null || e != "") {
        this.titleErrMsg = "";
      }
    },
    content(e) {
      if (e != null || e != "") {
        this.contentErrMsg = "";
      }
    }
  },
  methods: {
    goBack() {
      this.$router.go(-1);
    },
    writeFAQ() {
      if (this.title == null || this.title == "") {
        this.titleErrMsg = "제목은 필수항목입니다";
        return;
      }

      if (this.content == null || this.content == "") {
        this.contentErrMsg = "내용은 필수항목입니다";
        return;
      }

      this.getAxios
        .post(
          "/admin/board/faq/register",
          {
            faqTitle: this.title,
            faqContent: this.content
          },
          this.requestHeader
        )
        .then(res => {
          this.$router.push("/admin/board/faq");
        })
        .catch(err => {
          console.log(err);
        });
    }
  },
  created() {}
};
</script>