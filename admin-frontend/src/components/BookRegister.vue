<template>
  <v-container fluid grid-list-xl>
    <v-layout row wrap style="background-color: #f5f5f5">
      <v-flex xs12>
        <v-card>
          <v-card-title>
            한 권 등록
            <v-spacer></v-spacer>
          </v-card-title>
          <v-divider></v-divider>
          <v-card-subtitle class="pb-0">직접 입력</v-card-subtitle>
          <v-list dense class="pt-0">
            <v-form @submit.prevent="registerBook" enctype="multipart/form-data">
              <v-card-text>
                <v-divider v-if="$vuetify.breakpoint.mdAndUp"></v-divider>
                <v-row no-gutters>
                  <v-flex
                    sm2
                    d-flex
                    style="margin-top: auto; margin-bottom: auto;"
                    v-if="$vuetify.breakpoint.mdAndUp"
                  >도서 ID</v-flex>
                  <v-divider v-if="$vuetify.breakpoint.mdAndUp" vertical style="margin-right: 2%"></v-divider>
                  <v-col class="ma-3 mb-3 pa-0">
                    <v-flex class="ma-0 pa-0" v-if="$vuetify.breakpoint.smAndDown">도서 ID</v-flex>
                    <v-text-field
                      class="input-field validate"
                      outlined
                      ref="bookId"
                      v-model="bookId"
                      label="도서 ID"
                      required
                      single-line
                      hide-details="auto"
                      :error-messages="bookIdErrMsg"
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
                  >ISBN 코드</v-flex>
                  <v-divider v-if="$vuetify.breakpoint.mdAndUp" vertical style="margin-right: 2%"></v-divider>
                  <v-col class="ma-3 mb-3 pa-0">
                    <v-flex class="ma-0 pa-0" v-if="$vuetify.breakpoint.smAndDown">ISBN 코드</v-flex>
                    <v-text-field
                      outlined
                      ref="bookISBN"
                      v-model="bookISBN"
                      label="ISBN 코드 13자리"
                      required
                      single-line
                      hide-details="auto"
                      :error-messages="bookISBNErrMsg"
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
                  >카테고리</v-flex>
                  <v-divider v-if="$vuetify.breakpoint.mdAndUp" vertical style="margin-right: 2%"></v-divider>
                  <v-col class="ma-3 mb-3 pa-0">
                    <v-flex class="ma-0 pa-0" v-if="$vuetify.breakpoint.smAndDown">카테고리</v-flex>
                    <v-select
                      outlined
                      v-model="categoryId"
                      :items="category"
                      item-text="name"
                      item-value="categoryId"
                      label="카테고리"
                      required
                      single-line
                      hide-details="auto"
                      :error-messages="bookCategoryErrMsg"
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
                      v-model="cityId"
                      :items="city"
                      item-text="name"
                      item-value="cityId"
                      required
                      single-line
                      hide-details="auto"
                      :disabled="this.user.userRoleId != 1"
                      :error-messages="bookCityErrMsg"
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
                  >반</v-flex>
                  <v-divider v-if="$vuetify.breakpoint.mdAndUp" vertical style="margin-right: 2%"></v-divider>
                  <v-col class="ma-3 mb-3 pa-0">
                    <v-flex class="ma-0 pa-0" v-if="$vuetify.breakpoint.smAndDown">반</v-flex>
                    <v-select
                      outlined
                      v-model="classId"
                      :items="classInfo"
                      item-text="name"
                      item-value="classId"
                      label="반"
                      required
                      single-line
                      hide-details="auto"
                      :error-messages="bookClassErrMsg"
                    ></v-select>
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
                  color="#3C90E2"
                  dark
                  tile
                  depressed
                  class="ml-3"
                  type="submit"
                >저장</v-btn>
              </v-flex>
            </v-form>
          </v-list>
          <v-card-text class="red--text text-center mt-0" v-if="errMessage">{{ errMessage }}</v-card-text>
          <v-card-text class="green--text text-center mt-0" v-if="registerMsg">{{ registerMsg }}</v-card-text>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  name: "BookRegister",
  computed: {
    ...mapGetters(["requestHeader", "user", "getAxios"])
  },
  data() {
    return {
      bookId: "",
      bookISBN: "",
      categoryId: "",
      cityId: "",
      classId: "",
      uploadFile: "",
      importBook: {},
      errMessage: "",
      registerMsg: "",
      bookIdErrMsg: "",
      bookISBNErrMsg: "",
      bookCategoryErrMsg: "",
      bookCityErrMsg: "",
      bookClassErrMsg: "",
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
      classInfo: [
        {
          name: "1반",
          classId: "0"
        },
        {
          name: "2반",
          classId: "1"
        },
        {
          name: "3반",
          classId: "2"
        },
        {
          name: "4반",
          classId: "3"
        },
        {
          name: "5반",
          classId: "4"
        },
        {
          name: "6반",
          classId: "5"
        },
        {
          name: "7반",
          classId: "6"
        },
        {
          name: "8반",
          classId: "7"
        },
        {
          name: "9반",
          classId: "8"
        },
        {
          name: "10반",
          classId: "9"
        }
      ],
      rules: {
        required: value => {
          if (value == null || value == "") {
            return "required";
          } else {
            return true;
          }
        }
      }
    };
  },
  watch: {
    bookId(e) {
      if (e != null || e != "") {
        this.bookIdErrMsg = "";
      }
    },
    bookISBN(e) {
      if (e != null || e != "") {
        this.bookISBNErrMsg = "ISBN 코드 13자리를 입력해주세요";

        if (e.length == 13) {
          this.bookISBNErrMsg = "";
        }
      }
    },
    categoryId(e) {
      if (e != null || e != "") {
        this.bookCategoryErrMsg = "";
      }
    },
    cityId(e) {
      if (e != null || e != "") {
        this.bookCityErrMsg = "";
      }
    },
    classId(e) {
      if (e != null || e != "") {
        this.bookClassErrMsg = "";
      }
    }
  },
  methods: {
    goBack() {
      this.$router.go(-1);
    },
    registerBook() {
      if (this.bookId == null || this.bookId == "") {
        this.bookIdErrMsg = "도서 ID를 입력해주세요";
        return;
      }

      if (this.bookISBN == null || this.bookISBN == "") {
        this.bookISBNErrMsg = "ISBN 코드를 입력해주세요";
        return;
      }

      if (this.bookISBN.length < 13) {
        this.bookISBNErrMsg = "ISBN 코드 13자리를 입력해주세요";
        return;
      }
      this.bookISBN = this.bookISBN.replace(/-/gi, "");

      if (this.categoryId == null || this.categoryId == "") {
        this.bookCategoryErrMsg = "카테고리를 선택해주세요";
        return;
      }

      if (this.cityId == null || this.cityId == "") {
        this.bookCityErrMsg = "지역을 선택해주세요";
        return;
      }

      if (this.classId == null || this.classId == "") {
        this.bookClassErrMsg = "반을 선택해주세요";
        return;
      }

      var book = {
        bookId: this.bookId,
        bookISBN: this.bookISBN,
        categoryId: this.categoryId,
        cityId: this.cityId,
        classId: this.classId
      };

      this.getAxios
        .post("/admin/book/register", book, this.requestHeader)
        .then(res => {
          if (res.data.errorMsg.length > 0) {
            this.errMessage = res.data.errorMsg;
            return;
          }
          this.errMessage = "";
          this.bookId = "";
          this.bookISBN = "";
          this.categoryId = "";
          this.classId = "";
          this.registerMsg = res.data.registerMsg;

          // const sleep = milliseconds => {
          //     return new Promise(resolve => setTimeout(resolve, milliseconds));
          //   };

          //   sleep(3000).then(() => {
          //     this.registerMsg = "";
          //   });
        })
        .catch(err => {
          console.log(err);
        });
    }
  },
  mounted() {
    if (this.user.userRoleId != 1) {
      this.cityId = this.user.cityId;

      if (this.user.cityId == 1) {
        this.classInfo = [
          {
            name: "1반",
            classId: "0"
          },
          {
            name: "2반",
            classId: "1"
          },
          {
            name: "3반",
            classId: "2"
          },
          {
            name: "4반",
            classId: "3"
          }
        ];
      } else if (this.user.cityId == 2) {
        this.classInfo = [
          {
            name: "1반",
            classId: "0"
          },
          {
            name: "2반",
            classId: "1"
          }
        ];
      } else if (this.user.cityId == 3) {
        this.classInfo = [
          {
            name: "1반",
            classId: "0"
          },
          {
            name: "2반",
            classId: "1"
          }
        ];
      }
    }
  }
};
</script>