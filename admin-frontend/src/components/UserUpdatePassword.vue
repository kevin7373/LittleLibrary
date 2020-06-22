<template>
  <v-container fluid grid-list-xl>
    <v-layout row wrap style="background-color: #f5f5f5">
      <v-flex xs12>
        <v-card>
          <v-card-title>
            비밀번호 변경
            <v-spacer></v-spacer>
          </v-card-title>
          <v-divider></v-divider>
          <v-flex d-flex sm6 class="pb-0 mb-0 my-0 ml-auto mr-auto">
            <v-flex d-flex class="my-0 py-0 ml-1">새로 사용할 비밀번호를 입력해 주세요</v-flex>
          </v-flex>
          <v-list dense>
            <v-form @submit.prevent="updatePassword">
              <v-card-text class="pt-0">
                <v-flex d-flex sm6 class="pa-0 ma-0 ml-auto mr-auto">
                  <v-row no-gutters>
                    <v-col class="ma-3 mb-3 pa-0">
                      <v-flex class="ma-0 pa-0">현재 비밀번호</v-flex>
                      <v-text-field
                        v-model="userPw"
                        type="password"
                        outlined
                        ref="userPw"
                        label="비밀번호"
                        required
                        single-line
                        hide-details="auto"
                        :disabled="this.user.userId != userInfo.userId"
                        :rules="[rules.passwordCounter]"
                        :error-messages="userPwErrMsg"
                      ></v-text-field>
                    </v-col>
                  </v-row>
                </v-flex>
                <v-flex d-flex sm6 class="pa-0 ma-0 ml-auto mr-auto">
                  <v-row no-gutters>
                    <v-col class="ma-3 mb-3 pa-0">
                      <v-flex class="ma-0 pa-0">새 비밀번호</v-flex>
                      <v-text-field
                        v-model="newUserPw"
                        type="password"
                        outlined
                        ref="newUserPw"
                        label="새 비밀번호"
                        required
                        single-line
                        hide-details="auto"
                        :disabled="this.user.userId != userInfo.userId"
                        :rules="[rules.newPwCounter]"
                        :error-messages="newUserPwErrMsg"
                      ></v-text-field>
                    </v-col>
                  </v-row>
                </v-flex>
                <v-flex d-flex sm6 class="pa-0 ma-0 ml-auto mr-auto">
                  <v-row no-gutters>
                    <v-col class="ma-3 mb-3 pa-0">
                      <v-flex class="ma-0 pa-0">새 비밀번호 확인</v-flex>
                      <v-text-field
                        type="password"
                        outlined
                        ref="newUserPwValid"
                        v-model="newUserPwValid"
                        label="새 비밀번호 확인"
                        required
                        single-line
                        hide-details="auto"
                        :disabled="this.user.userId != userInfo.userId"
                        :error-messages="this.newUserPwValidErrMsg"
                      ></v-text-field>
                    </v-col>
                  </v-row>
                </v-flex>
              </v-card-text>
              <v-flex d-flex class="justify-center">
                <v-btn
                  min-width="120px"
                  height="50px"
                  color="#4f5a66"
                  tile
                  depressed
                  dark
                  @click="goBack"
                >취소</v-btn>
                <v-btn
                  min-width="120px"
                  height="50px"
                  color="#3c90e2"
                  dark
                  tile
                  depressed
                  type="submit"
                  class="ml-3"
                >저장</v-btn>
              </v-flex>
            </v-form>
          </v-list>
          <v-card-text class="red--text text-center mt-0 pt-0" v-if="errMessage">{{ errMessage }}</v-card-text>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  name: "UserUpdatePassword",
  components: {},
  computed: {
    ...mapGetters(["requestHeader", "user", "getAxios"])
  },
  props: ["userId"],
  data() {
    return {
      userInfo: [],
      error: "",
      userPw: "",
      newUserPw: null,
      newUserPwValid: null,
      errMessage: "",
      userPwErrMsg: "",
      newUserPwErrMsg: "",
      newUserPwValidErrMsg: "",
      rules: {
        passwordCounter: value => {
          if (value != null) {
            return value.length == 8 ? true : "비밀번호는 8자입니다.";
          } else {
            return true;
          }
        },
        newPwCounter: value => {
          if (value != null && value != "") {
            return value.length == 8 ? true : "비밀번호는 8자입니다.";
          } else {
            return true;
          }
        }
      }
    };
  },
  watch: {
    userPw(e) {
      if (e != null || e != "") {
        this.userPwErrMsg = "";
      }
    },
    newUserPw(e) {
      if (e != null || e != "") {
        this.newUserPwErrMsg = "";
      }
    },
    newUserPwValid(e) {
      if (e != null || e != "") {
        this.newUserPwValidErrMsg = "";
      }

      if (this.newUserPw != e) {
        this.newUserPwValidErrMsg = "비밀번호가 다릅니다.";
      } else {
        this.newUserPwValidErrMsg = "";
      }
    }
  },
  methods: {
    updatePassword() {
      if (this.userPw == null || this.userPw == "") {
        this.userPwErrMsg = "비밀번호를 입력해주세요";
        return;
      }

      if (this.userPw.length != 8) {
        return;
      }

      if (this.newUserPw == null || this.newUserPw == "") {
        this.newUserPwErrMsg = "새 비밀번호를 입력해주세요";
        return;
      }

      if (this.newUserPw.length != 8) {
        return;
      }

      if (this.newUserPwValid == null || this.newUserPwValid == "") {
        this.newUserPwValidErrMsg = "새 비밀번호 확인을 입력해주세요";
        return;
      }

      if (this.newUserPw != this.newUserPwValid) {
        this.newUserPwValidErrMsg = "비밀번호가 다릅니다";
        return;
      }

      var user = {
        userId: this.userInfo.userId,
        userPw: this.userPw
      };

      var jsonObj = new Object();
      jsonObj.user = user;
      jsonObj.newUserPw = this.newUserPw;

      var object = JSON.stringify(jsonObj);

      this.getAxios
        .post(
          "/admin/user/updatepassword",
          {
            object,
            headers: {
              "Content-Type": "application/json"
            }
          },
          this.requestHeader
        )
        .then(res => {
          this.errMessage = "";

          if (res.data.message == "정상") {
            this.$store.dispatch("login", res.headers.token);
            location.reload();
          } else if (res.data.message == "비밀번호 오류") {
            this.errMessage = "비밀번호가 틀립니다.";
            this.userInfo.userPw = "";
            this.newUserPw = "";
            this.newUserPwValid = "";
          }

          const sleep = milliseconds => {
            return new Promise(resolve => setTimeout(resolve, milliseconds));
          };

          sleep(5000).then(() => {
            this.errMessage = "";
          });
        })
        .catch(err => {
          console.log(err);
        });
    },
    goBack() {
      this.$router.go(-1);
    }
  },
  mounted() {
    this.errMessage = "";

    this.getAxios("/admin/user/profile/" + this.user.userId, this.requestHeader)
      .then(res => {
        this.userInfo = res.data.profile;
        this.errMessage = "";
        this.newUserPw = "";
        this.newUserPwValid = "";
      })
      .catch(err => {
        console.log(err);
      });
  }
};
</script>