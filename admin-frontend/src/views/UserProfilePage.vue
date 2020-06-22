<template>
  <v-container fluid grid-list-xl>
    <v-layout row wrap style="background-color: #f5f5f5">
      <v-flex xs12>
        <v-card>
          <v-card-title>
            유저 상세 정보
            <v-spacer></v-spacer>
          </v-card-title>
          <v-divider></v-divider>
          <v-list dense>
            <v-form @submit.prevent="updateUserInfo">
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
                      </v-col>
                    </v-row>
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
                  >회원 번호</v-flex>
                  <v-divider v-if="$vuetify.breakpoint.mdAndUp" vertical style="margin-right: 2%"></v-divider>
                  <v-col class="ma-3 mb-3 pa-0">
                    <v-flex class="ma-0 pa-0" v-if="$vuetify.breakpoint.smAndDown">회원 번호</v-flex>
                    <v-text-field
                      outlined
                      ref="studentId"
                      v-model="studentId"
                      label="회원 번호"
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
                  >휴대폰 번호</v-flex>
                  <v-divider v-if="$vuetify.breakpoint.mdAndUp" vertical style="margin-right: 2%"></v-divider>
                  <v-col class="ma-3 mb-3 pa-0">
                    <v-flex class="ma-0 pa-0" v-if="$vuetify.breakpoint.smAndDown">휴대폰 번호</v-flex>
                    <v-text-field
                      outlined
                      ref="phone"
                      v-model="userInfo.phone"
                      label="휴대폰 번호"
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
                      disabled
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
                      hide-details
                      disabled
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
              <v-flex d-flex v-if="this.user.userRoleId == 1" class="justify-center">
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
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  name: "UserProfilePage",
  components: {},
  computed: {
    ...mapGetters(["requestHeader", "user", "getAxios"])
  },
  data() {
    return {
      userId: "",
      userInfo: [],
      cityName: "",
      className: "",
      userImg: "",
      studentId: "",
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
      ]
    };
  },
  methods: {
    deleteImg() {
      this.userImg = this.userDefaultImg;
    },
    updateUserInfo() {
      this.getAxios
        .post(
          "admin/user/update",
          {
            userImg: this.userInfo.userImg,
            userId: this.userInfo.userId,
            userPw: this.userInfo.userPw,
            userName: this.userInfo.userName,
            studentId: this.userInfo.studentId,
            email: this.userInfo.email,
            phone: this.userInfo.phone,
            categoryId: this.userInfo.categoryId,
            cityId: this.userInfo.cityId,
            classId: this.userInfo.classId,
            overduePeriod: this.userInfo.overduePeriod,
            userRoleId: this.userInfo.userRoleId
          },
          this.requestHeader
        )
        .then(res => {
          this.$store.dispatch("login", res.headers.token);
          location.reload();
        })
        .catch(err => {});
    },
    onFileChange(e) {
      var files = e.target.files || e.dataTransfer.files;

      if (!files.length) {
        return;
      }
      this.createImage(files[0]);
    },
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
    this.userId = sessionStorage.getItem("userId");

    this.getAxios("/admin/user/profile/" + this.userId, this.requestHeader)
      .then(res => {
        this.userImg = res.data.profile.userImg;
        this.userInfo = res.data.profile;
        this.studentId =
          this.userInfo.studentId +
          " (" +
          this.userInfo.cityName +
          this.userInfo.className +
          ")";
      })
      .catch(err => {
        console.log(err);
      });
  }
};
</script>