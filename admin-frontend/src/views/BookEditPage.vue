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
                  >도서 사진</v-flex>
                  <v-divider v-if="$vuetify.breakpoint.mdAndUp" vertical style="margin-right: 2%"></v-divider>
                  <v-flex>
                    <v-flex class="ma-0 pa-0" v-if="$vuetify.breakpoint.smAndDown">도서 사진</v-flex>
                    <v-row>
                      <v-col sm1 :class="makeSize()">
                        <v-img
                          :src="bookImg || bookDefaultImg"
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
                  >도서 코드</v-flex>
                  <v-divider v-if="$vuetify.breakpoint.mdAndUp" vertical style="margin-right: 2%"></v-divider>
                  <v-col class="ma-3 mb-3 pa-0">
                    <v-flex class="ma-0 pa-0" v-if="$vuetify.breakpoint.smAndDown">도서 코드</v-flex>
                    <v-text-field
                      outlined
                      ref="editBookId"
                      v-model="editBookId"
                      label="도서 코드"
                      required
                      single-line
                      hide-details="auto"
                      :error-messages="editBookIdErrMsg"
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
                  >도서 ISBN</v-flex>
                  <v-divider v-if="$vuetify.breakpoint.mdAndUp" vertical style="margin-right: 2%"></v-divider>
                  <v-col class="ma-3 mb-3 pa-0">
                    <v-flex class="ma-0 pa-0" v-if="$vuetify.breakpoint.smAndDown">도서 ISBN</v-flex>
                    <v-text-field
                      outlined
                      ref="bookISBN"
                      v-model="bookInfo.bookISBN"
                      label="도서 ISBN"
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
                  >도서 제목</v-flex>
                  <v-divider v-if="$vuetify.breakpoint.mdAndUp" vertical style="margin-right: 2%"></v-divider>
                  <v-col class="ma-3 mb-3 pa-0">
                    <v-flex class="ma-0 pa-0" v-if="$vuetify.breakpoint.smAndDown">도서 제목</v-flex>
                    <v-text-field
                      outlined
                      ref="bookName"
                      v-model="bookInfo.bookName"
                      label="도서 제목"
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
                  >출판사</v-flex>
                  <v-divider v-if="$vuetify.breakpoint.mdAndUp" vertical style="margin-right: 2%"></v-divider>
                  <v-col class="ma-3 mb-3 pa-0">
                    <v-flex class="ma-0 pa-0" v-if="$vuetify.breakpoint.smAndDown">출판사</v-flex>
                    <v-text-field
                      outlined
                      ref="publisher"
                      v-model="bookInfo.publisher"
                      label="출판사"
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
                  >저자</v-flex>
                  <v-divider v-if="$vuetify.breakpoint.mdAndUp" vertical style="margin-right: 2%"></v-divider>
                  <v-col class="ma-3 mb-3 pa-0">
                    <v-flex class="ma-0 pa-0" v-if="$vuetify.breakpoint.smAndDown">저자</v-flex>
                    <v-text-field
                      outlined
                      ref="author"
                      v-model="bookInfo.author"
                      label="저자"
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
                  >출판일</v-flex>
                  <v-divider v-if="$vuetify.breakpoint.mdAndUp" vertical style="margin-right: 2%"></v-divider>
                  <v-col class="ma-3 mb-3 pa-0">
                    <v-flex class="ma-0 pa-0" v-if="$vuetify.breakpoint.smAndDown">출판일</v-flex>
                    <v-text-field
                      outlined
                      ref="publishDate"
                      v-model="bookInfo.publishDate"
                      label="출판일"
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
                  >가격</v-flex>
                  <v-divider v-if="$vuetify.breakpoint.mdAndUp" vertical style="margin-right: 2%"></v-divider>
                  <v-col class="ma-3 mb-3 pa-0">
                    <v-flex class="ma-0 pa-0" v-if="$vuetify.breakpoint.smAndDown">가격</v-flex>
                    <v-text-field
                      outlined
                      ref="bookPrice"
                      v-model="bookInfo.bookPrice"
                      label="가격"
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
                      required
                      single-line
                      hide-details
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
                      hide-details
                      :disabled="this.user.userRoleId != 1"
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
                      required
                      single-line
                      hide-details
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
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  name: "BookEditPage",
  components: {},
  computed: {
    ...mapGetters(["requestHeader", "user", "getAxios"])
  },
  data() {
    return {
      bookId: "",
      editBookId: "",
      bookInfo: [],
      categoryId: "",
      cityId: "",
      classId: "",
      bookImg: "",
      editBookIdErrMsg: "",
      bookDefaultImg:
        "https://dynamicmediainstitute.org/wp-content/themes/dynamic-media-institute/imagery/default-thesis-abstract.png",
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
      ]
    };
  },
  watch: {
    editBookId(e) {
      if (e == null || e == "") {
        this.editBookIdErrMsg = "도서 코드를 입력해주세요";
      } else {
        if (e == this.bookInfo.bookId) {
          this.editBookIdErrMsg = "";
          return;
        }

        var jsonObj = new Object();
        jsonObj.editBookId = this.editBookId;
        jsonObj.cityId = this.cityId;

        var object = JSON.stringify(jsonObj);

        this.getAxios("/admin/book/checkbookid/" + object, this.requestHeader)
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
    }
  },
  methods: {
    updateBookInfo() {
      this.getAxios
        .post(
          "admin/book/update",
          {
            bookId: this.editBookId,
            bookISBN: this.bookInfo.bookISBN,
            categoryId: this.categoryId,
            cityId: this.cityId,
            classId: this.classId
          },
          this.requestHeader
        )
        .then(res => {})
        .catch(err => {});
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
    this.bookId = sessionStorage.getItem("editBookId");

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

    this.getAxios("/admin/book/info/" + this.bookId, this.requestHeader)
      .then(res => {
        this.bookInfo = res.data.bookDetail;
        this.bookImg = res.data.bookDetail.bookImg;
        this.editBookId = res.data.bookDetail.bookId;
        this.categoryId = res.data.bookDetail.categoryId;
        this.cityId = res.data.bookDetail.cityId;
        this.classId = res.data.bookDetail.classId;
      })
      .catch(err => {
        console.log(err);
      });
  }
};
</script>