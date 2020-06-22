<template>
  <v-container fluid grid-list-xl>
    <v-layout row wrap style="background-color: #f5f5f5">
      <v-flex xs12>
        <v-card>
          <v-card-title>
            내 정보
            <v-spacer></v-spacer>
          </v-card-title>
          <v-divider></v-divider>
          <v-list dense>
            <v-form @submit.prevent="updateUserInfo" enctype="multipart/form-data">
              <v-card-text>
                <v-divider v-if="$vuetify.breakpoint.mdAndUp"></v-divider>
                <v-row no-gutters>
                  <v-flex
                    sm2
                    d-flex
                    style="margin-top: auto; margin-bottom: auto;"
                    v-if="$vuetify.breakpoint.mdAndUp"
                  >사진</v-flex>
                  <v-divider v-if="$vuetify.breakpoint.mdAndUp" vertical style="margin-right: 2%"></v-divider>
                  <v-flex>
                    <v-flex class="ma-0 pa-0" v-if="$vuetify.breakpoint.smAndDown">사진</v-flex>
                    <v-row>
                      <v-col sm1 :class="makeSize()">
                        <v-img
                          :src="userImg || userDefaultImg"
                          width="112px"
                          height="112px"
                          max-width="112px"
                          max-height="112px"
                        />
                        <div v-if="this.user.userId == userInfo.userId">
                          <div class="mt-3 mb-0" v-if="$vuetify.breakpoint.mdAndUp">
                            <v-btn
                              type="file"
                              class="ml-3"
                              color="#ffffff"
                              style="border: 1px; border-style: solid"
                              depressed
                              tile
                              @click.prevent="$refs.userImg.click()"
                            >찾아보기</v-btn>
                            <input v-show="false" ref="userImg" type="file" @change="onFileChange" />
                            <v-btn
                              class="ml-3"
                              depressed
                              tile
                              color="#ffffff"
                              style="border: 1px; border-style: solid"
                              @click="deleteImg()"
                            >삭제</v-btn>
                            <span
                              v-if="$vuetify.breakpoint.mdAndUp"
                              class="mb-0 ml-3"
                            >사진은 20MB 이내의 jpg, png, gif 파일만 등록 가능합니다.</span>
                          </div>
                          <div class="mt-3 mb-0" v-else>
                            <div class="text-center">
                              <v-btn
                                type="file"
                                class="ml-3"
                                color="#ffffff"
                                depressed
                                tile
                                style="border: 1px; border-style: solid"
                                @click.prevent="$refs.userImg.click()"
                              >찾아보기</v-btn>
                              <input
                                v-show="false"
                                ref="userImg"
                                type="file"
                                @change="onFileChange"
                              />
                              <v-btn
                                class="ml-3"
                                color="#ffffff"
                                style="border: 1px; border-style: solid"
                                depressed
                                tile
                                @click="deleteImg()"
                              >삭제</v-btn>
                            </div>
                            <p class="mt-3 mb-0 ml-3">사진은 20MB 이내의 jpg, png, gif 파일만 등록 가능합니다.</p>
                          </div>
                        </div>
                      </v-col>
                    </v-row>
                    <v-flex class="red--text my-0 py-0" v-if="fileTypeErrMsg">{{ fileTypeErrMsg }}</v-flex>
                  </v-flex>
                </v-row>
                <v-divider v-if="$vuetify.breakpoint.mdAndUp"></v-divider>
                <v-row no-gutters>
                  <v-flex
                    sm2
                    d-flex
                    style="margin-top: auto; margin-bottom: auto;"
                    v-if="$vuetify.breakpoint.mdAndUp"
                  >아이디</v-flex>
                  <v-divider v-if="$vuetify.breakpoint.mdAndUp" vertical style="margin-right: 2%"></v-divider>
                  <v-col class="ma-3 mb-3 pa-0">
                    <v-flex class="ma-0 pa-0" v-if="$vuetify.breakpoint.smAndDown">아이디</v-flex>
                    <v-text-field
                      outlined
                      ref="userId"
                      v-model="userInfo.userId"
                      label="아이디"
                      required
                      single-line
                      hide-details
                      disabled
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
                  >비밀번호</v-flex>
                  <v-divider v-if="$vuetify.breakpoint.mdAndUp" vertical style="margin-right: 2%"></v-divider>
                  <v-col class="ma-3 mb-3 pa-0">
                    <v-flex class="ma-0 pa-0" v-if="$vuetify.breakpoint.smAndDown">비밀번호</v-flex>
                    <v-text-field
                      type="password"
                      outlined
                      ref="userPw"
                      v-model="userPw"
                      label="비밀번호"
                      required
                      single-line
                      hide-details="auto"
                      :disabled="this.user.userId != userInfo.userId"
                      autofocus
                      :rules="[rules.passwordCounter]"
                      :error-messages="userPwErrMsg"
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
                  >이름</v-flex>
                  <v-divider v-if="$vuetify.breakpoint.mdAndUp" vertical style="margin-right: 2%"></v-divider>
                  <v-col class="ma-3 mb-3 pa-0">
                    <v-flex class="ma-0 pa-0" v-if="$vuetify.breakpoint.smAndDown">이름</v-flex>
                    <v-text-field
                      outlined
                      ref="userName"
                      v-model="userInfo.userName"
                      label="이름"
                      required
                      single-line
                      hide-details="auto"
                      :disabled="this.user.userId != userInfo.userId"
                      :rules="[rules.nameRequired]"
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
                  >관리자 번호</v-flex>
                  <v-divider v-if="$vuetify.breakpoint.mdAndUp" vertical style="margin-right: 2%"></v-divider>
                  <v-col class="ma-3 mb-3 pa-0">
                    <v-flex class="ma-0 pa-0" v-if="$vuetify.breakpoint.smAndDown">관리자 번호</v-flex>
                    <v-text-field
                      outlined
                      ref="studentId"
                      v-model="studentId"
                      label="관리자 번호"
                      required
                      single-line
                      hide-details="auto"
                      :disabled="this.user.userRoleId == 2"
                      :rules="[rules.studentIdRequired, rules.studentIdCounter, rules.studentIdPattern]"
                      :error-messages="this.error"
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
                  >E-MAIL</v-flex>
                  <v-divider v-if="$vuetify.breakpoint.mdAndUp" vertical style="margin-right: 2%"></v-divider>
                  <v-col class="ma-3 mb-3 pa-0">
                    <v-flex class="ma-0 pa-0" v-if="$vuetify.breakpoint.smAndDown">E-MAIL</v-flex>
                    <v-text-field
                      outlined
                      ref="email"
                      v-model="userInfo.email"
                      label="E-MAIL"
                      required
                      single-line
                      hide-details="auto"
                      :disabled="this.user.userId !=  userInfo.userId"
                      :rules="[rules.emailRequired, rules.emailPattern]"
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
                  >휴대폰 번호</v-flex>
                  <v-divider v-if="$vuetify.breakpoint.mdAndUp" vertical style="margin-right: 2%"></v-divider>
                  <v-col class="ma-3 mb-3 pa-0">
                    <v-flex class="ma-0 pa-0" v-if="$vuetify.breakpoint.smAndDown">휴대폰 번호</v-flex>
                    <v-text-field
                      outlined
                      ref="phone"
                      v-model="userInfo.phone"
                      label="휴대폰 번호는 -를 빼고 입력해주세요."
                      required
                      single-line
                      hide-details="auto"
                      :disabled="this.user.userId != userInfo.userId"
                      :rules="[rules.phoneRequired, rules.phonePattern, rules.phoneCounter]"
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
                  >관심 분야</v-flex>
                  <v-divider v-if="$vuetify.breakpoint.mdAndUp" vertical style="margin-right: 2%"></v-divider>
                  <v-col class="ma-3 mb-3 pa-0">
                    <v-flex class="ma-0 pa-0" v-if="$vuetify.breakpoint.smAndDown">관심 분야</v-flex>
                    <v-select
                      outlined
                      v-model="userInfo.categoryId"
                      :items="category"
                      chips
                      multiple
                      item-text="name"
                      item-value="categoryId"
                      required
                      single-line
                      hide-details
                      :disabled="this.user.userId != userInfo.userId"
                    ></v-select>
                  </v-col>
                </v-row>
                <v-divider v-if="$vuetify.breakpoint.mdAndUp"></v-divider>

                <v-row no-gutters>
                  <v-flex
                    sm2
                    d-flex
                    style="margin-top: auto; margin-bottom: auto;"
                    v-if="$vuetify.breakpoint.mdAndUp"
                  >지역</v-flex>
                  <v-divider v-if="$vuetify.breakpoint.mdAndUp" vertical style="margin-right: 2%"></v-divider>
                  <v-col class="ma-3 mb-3 pa-0">
                    <v-flex class="ma-0 pa-0" v-if="$vuetify.breakpoint.smAndDown">지역</v-flex>
                    <v-select
                      outlined
                      v-model="userInfo.cityId"
                      :items="city"
                      item-text="name"
                      item-value="cityId"
                      required
                      single-line
                      hide-details
                      :disabled="this.user.userId != userInfo.userId"
                    ></v-select>
                  </v-col>
                </v-row>
                <v-divider v-if="$vuetify.breakpoint.mdAndUp"></v-divider>
                <v-row no-gutters>
                  <v-flex
                    sm2
                    d-flex
                    style="margin-top: auto; margin-bottom: auto;"
                    v-if="$vuetify.breakpoint.mdAndUp"
                  >연체 기간</v-flex>
                  <v-divider v-if="$vuetify.breakpoint.mdAndUp" vertical style="margin-right: 2%"></v-divider>
                  <v-col class="ma-3 mb-3 pa-0">
                    <v-flex class="ma-0 pa-0" v-if="$vuetify.breakpoint.smAndDown">연체 기간</v-flex>
                    <v-text-field
                      outlined
                      ref="overduePeriod"
                      v-model="userInfo.overduePeriod"
                      label="연체 기간"
                      required
                      single-line
                      hide-details="auto"
                      :disabled="this.user.userRoleId == 2"
                      :rules="[rules.overduePeriodPattern]"
                    ></v-text-field>
                  </v-col>
                </v-row>
                <v-divider v-if="$vuetify.breakpoint.mdAndUp"></v-divider>
                <v-row no-gutters>
                  <v-flex
                    sm2
                    style="margin-top: auto; margin-bottom: auto;"
                    v-if="$vuetify.breakpoint.mdAndUp"
                  >권한</v-flex>
                  <v-divider v-if="$vuetify.breakpoint.mdAndUp" vertical style="margin-right: 2%"></v-divider>
                  <v-col class="ma-3 mb-3 pa-0">
                    <v-flex class="ma-0 pa-0" v-if="$vuetify.breakpoint.smAndDown">권한</v-flex>
                    <v-select
                      outlined
                      v-model="userInfo.userRoleId"
                      :items="role"
                      item-text="name"
                      item-value="userRoleId"
                      required
                      single-line
                      hide-details
                      :disabled="this.user.userRoleId > 1 || this.user.userRoleId == 0"
                    ></v-select>
                  </v-col>
                </v-row>
                <v-divider v-if="$vuetify.breakpoint.mdAndUp"></v-divider>
              </v-card-text>
              <v-flex d-flex class="justify-center">
                <v-btn
                  min-width="120px"
                  height="50px"
                  color="#4f5a66"
                  dark
                  tile
                  depressed
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
  name: "UserMyInfo",
  components: {},
  computed: {
    ...mapGetters(["requestHeader", "user", "getAxios"])
  },
  props: ["userId"],
  data() {
    return {
      userInfo: [],
      userPw: "",
      userImg: "",
      uploadImg: "",
      studentId: "",
      error: "",
      errMessage: "",
      userPwErrMsg: "",
      fileTypeErrMsg: "",
      userDefaultImg:
        "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__340.png",
      category: [
        {
          name: "웹 / 모바일",
          categoryId: "000"
        },
        {
          name: "머신러닝",
          categoryId: "001"
        },
        {
          name: "DS / 빅데이터",
          categoryId: "002"
        },
        {
          name: "블록체인",
          categoryId: "003"
        },
        {
          name: "IoT",
          categoryId: "004"
        },
        {
          name: "프로그래밍 언어",
          categoryId: "005"
        },
        {
          name: "알고리즘",
          categoryId: "006"
        },
        {
          name: "애자일",
          categoryId: "007"
        },
        {
          name: "면접",
          categoryId: "008"
        },
        {
          name: "CI / CD",
          categoryId: "009"
        },
        {
          name: "VueJS",
          categoryId: "010"
        },
        {
          name: "ReactJS",
          categoryId: "011"
        },
        {
          name: "데이터베이스",
          categoryId: "012"
        },
        {
          name: "기타",
          categoryId: "013"
        }
      ],
      city: [
        {
          name: "서울",
          cityId: "0"
        },
        {
          name: "대전",
          cityId: "1"
        },
        {
          name: "구미",
          cityId: "2"
        },
        {
          name: "광주",
          cityId: "3"
        }
      ],
      role: [
        {
          name: "사용자",
          userRoleId: "0"
        },
        {
          name: "최상위 관리자",
          userRoleId: "1"
        },
        {
          name: "관리자",
          userRoleId: "2"
        }
      ],
      rules: {
        passwordRequired: value => !!value || "비밀번호를 입력해주세요.",
        passwordCounter: value => {
          if (value != null) {
            return value.length == 8 ? true : "비밀번호는 8자입니다.";
          } else {
            return true;
          }
        },
        nameRequired: value => !!value || "이름을 입력해주세요.",
        studentIdRequired: value => !!value || "관리자 번호를 입력해주세요.",
        studentIdCounter: value => {
          this.error = "";

          if (value != null) {
            return value.length == 7 ? true : "관리자 번호는 7자입니다.";
          } else {
            return true;
          }
        },
        studentIdPattern: value => {
          this.error = "";
          const studentIdPattern = /^[0-9]/gi;
          return studentIdPattern.test(value)
            ? true
            : "관리자 번호는 숫자만 가능합니다.";
        },
        emailRequired: value => {
          return value == null || value == "" ? "email을 입력해주세요." : true;
        },
        emailPattern: value => {
          const emailPattern = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
          return emailPattern.test(value) || "잘못된 이메일입니다.";
        },
        phoneRequired: value => !!value || "휴대폰 번호를 입력해주세요.",
        phonePattern: value => {
          const phonePattern = /^[0-9]/gi;
          return phonePattern.test(value)
            ? true
            : "휴대폰 번호는 숫자만 가능합니다.";
        },
        phoneCounter: value => {
          if (value != null) {
            return value.length == 10 || value.length == 11
              ? true
              : "잘못된 휴대폰 번호입니다.";
          } else {
            return true;
          }
        },
        overduePeriodPattern: value => {
          if (value == null || value == "") {
            return true;
          }
          const overduePeriodPattern = /^[0-9]/gi;
          return overduePeriodPattern.test(value)
            ? true
            : "연체 기간은 숫자만 가능합니다.";
        }
      }
    };
  },
  watch: {
    studentId(e) {
      if (e.length == 7) {
        this.getAxios("/admin/user/checkstudentid/" + e, this.requestHeader)
          .then(res => {
            if (res.data.message == "중복") {
              this.error = "중복된 관리자 번호입니다.";
            } else {
              this.error = "";
            }
          })
          .catch(err => {
            console.log(err);
          });
      }
    },
    userPw(e) {
      if (e != null || e != "") {
        this.userPwErrMsg = "";
      }
    }
  },
  methods: {
    deleteImg() {
      this.userImg = this.userDefaultImg;
      this.uploadImg = null;
    },
    updateUserInfo() {
      if (this.userPw == null || this.userPw == "") {
        this.userPwErrMsg = "비밀번호를 입력해주세요";
        return;
      }

      if (this.userPw.length != 8) {
        this.userPwErrMsg = "비밀번호는 8자입니다";
        return;
      }

      if (this.error == "중복된 관리자 번호입니다.") {
        return;
      }

      if (this.userInfo.userName == null || this.userInfo.userName == "") {
        return;
      }

      if (
        this.userInfo.studentId == null ||
        this.userInfo.studentId == "" ||
        this.userInfo.studentId.length != 7
      ) {
        return;
      }

      if (this.userInfo.email == null || this.userInfo.email == "") {
        return;
      }

      const emailPattern = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

      if (!emailPattern.test(this.userInfo.email)) {
        return;
      }

      if (
        this.userInfo.phone == null ||
        this.userInfo.phone == "" ||
        this.userInfo.phone.length < 10 ||
        this.userInfo.phone.length > 11
      ) {
        return;
      }

      const phonePattern = /^[0-9]/gi;

      if (!phonePattern.test(this.userInfo.phone)) {
        return;
      }

      const overduePeriodPattern = /^[0-9]/gi;

      if (!overduePeriodPattern.test(this.userInfo.overduePeriod)) {
        return;
      }

      if (this.fileTypeErrMsg != "") {
        return;
      }

      if (
        this.uploadImg == null ||
        this.uploadImg == "" ||
        this.uploadImg == this.userImg
      ) {
        var user = {
          userId: this.userInfo.userId,
          userPw: this.userPw,
          userName: this.userInfo.userName,
          studentId: this.userInfo.studentId,
          email: this.userInfo.email,
          categoryId: this.userInfo.categoryId,
          phone: this.userInfo.phone,
          cityId: this.userInfo.cityId,
          classId: this.userInfo.classId,
          overduePeriod: this.userInfo.overduePeriod,
          userRoleId: this.userInfo.userRoleId,
          userImg: this.userImg
        };

        this.getAxios
          .post("admin/user/update", user, this.requestHeader)
          .then(res => {
            if (res.data.message == "비밀번호 오류") {
              this.errMessage = "비밀번호가 틀렸습니다.";
            } else {
              this.$store.dispatch("login", res.headers.token);
              location.reload();
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
        return;
      } else {
        if (this.uploadImg.size >= 1024 * 1024 * 20) {
          this.fileTypeErrMsg = "20MB 이내의 파일을 올려주세요";
          return;
        }
        var user = new FormData();
        user.append("file", this.uploadImg);
        user.append("userId", this.userInfo.userId);
        user.append("userPw", this.userPw);
        user.append("userName", this.userInfo.userName);
        user.append("studentId", this.userInfo.studentId);
        user.append("email", this.userInfo.email);
        user.append("categoryId", this.userInfo.categoryId);
        user.append("phone", this.userInfo.phone);
        user.append("cityId", this.userInfo.cityId);
        user.append("classId", this.userInfo.classId);
        user.append("overduePeriod", this.userInfo.overduePeriod);
        user.append("userRoleId", this.userInfo.userRoleId);

        this.getAxios
          .post("admin/user/updatewithfile", user, this.requestHeader)
          .then(res => {
            if (res.data.message == "비밀번호 오류") {
              this.errMessage = "비밀번호가 틀렸습니다.";
            } else {
              this.$store.dispatch("login", res.headers.token);
              location.reload();
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
        return;
      }
    },
    onFileChange(e) {
      var files = e.target.files || e.dataTransfer.files;

      if (!files.length) {
        return;
      }
      var fileName = "";
      fileName = files[0].name;

      var reg = /(.*?)\.(jpg|JPG|jpeg|JPEG|png|PNG|gif|GIF)$/;

      if (!fileName.match(reg)) {
        this.fileTypeErrMsg = "jpg, jpeg, png, gif 중 하나만 올려주세요";
        return;
      } else if (fileName.match(reg)) {
        this.fileTypeErrMsg = "";
      }
      this.uploadImg = files[0];
      this.createImage(files[0]);
    },
    // 이미지 바꿔서 보여주는 부분
    createImage(file) {
      var image = new Image();
      var reader = new FileReader();
      var vm = this;

      reader.onload = e => {
        vm.userImg = e.target.result;
      };
      reader.readAsDataURL(file);
    },
    goBack() {
      this.$router.go(-1);
    },
    makeSize() {
      if (this.$vuetify.breakpoint.xs || this.$vuetify.breakpoint.sm) {
        return "ma-0 d-flex flex-column align-center";
      } else {
        return "ma-0 d-inline-flex align-end";
      }
    }
  },
  created() {
    this.uploadImg = this.userImg;

    this.getAxios("/admin/user/profile/" + this.user.userId, this.requestHeader)
      .then(res => {
        this.userImg = res.data.profile.userImg;
        this.userInfo = res.data.profile;
        this.studentId = this.userInfo.studentId;
      })
      .catch(err => {
        console.log(err);
      });
  }
};
</script>