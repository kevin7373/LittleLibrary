<template>
  <v-container fluid grid-list-xl>
    <v-layout row wrap style="background-color: #f5f5f5">
      <v-flex xs12>
        <v-card>
          <v-card-title>
            도서 상세 정보
            <v-spacer></v-spacer>
          </v-card-title>
          <v-divider></v-divider>
          <v-row>
            <v-col cols="6">
              <div class="ma-3">
                <v-flex d-flex>도서 정보</v-flex>
                <v-divider></v-divider>
                <v-data-iterator :items="bookDetail" hide-default-footer>
                  <template v-slot:default="props">
                    <v-list dense v-for="item in props.items" :key="item.bookImg">
                      <v-flex sm3 style="margin-left: auto">
                        <v-img :src="bookImg || bookDefaultImg" />
                      </v-flex>
                      <v-list-item>
                        <v-list-item-subtitle>도서 ISBN:</v-list-item-subtitle>
                        <v-list-item-title class="text-right">{{ item.bookISBN }}</v-list-item-title>
                      </v-list-item>
                      <v-list-item>
                        <v-list-item-subtitle>도서 코드:</v-list-item-subtitle>
                        <v-list-item-title class="text-right">{{ item.bookId }}</v-list-item-title>
                      </v-list-item>
                      <v-list-item>
                        <v-list-item-subtitle>도서 제목:</v-list-item-subtitle>
                        <v-list-item-title class="text-right" id="bookName">{{ item.bookName }}</v-list-item-title>
                      </v-list-item>
                      <v-list-item v-if="item.rentFrom">
                        <v-list-item-subtitle>대여 일:</v-list-item-subtitle>
                        <v-list-item-title class="text-right">{{ item.rentFrom }}</v-list-item-title>
                      </v-list-item>
                      <v-list-item v-if="item.rentTo">
                        <v-list-item-subtitle>반납 예정 일:</v-list-item-subtitle>
                        <v-list-item-title class="text-right">{{ item.rentTo }}</v-list-item-title>
                      </v-list-item>
                      <v-list-item v-if="item.realReturn">
                        <v-list-item-subtitle>반납 일:</v-list-item-subtitle>
                        <v-list-item-title class="text-right">{{ item.realReturn }}</v-list-item-title>
                      </v-list-item>
                      <v-list-item>
                        <v-list-item-subtitle>출판사:</v-list-item-subtitle>
                        <v-list-item-title class="text-right">{{ item.publisher }}</v-list-item-title>
                      </v-list-item>
                      <v-list-item>
                        <v-list-item-subtitle>저자:</v-list-item-subtitle>
                        <v-list-item-title class="text-right">{{ item.author }}</v-list-item-title>
                      </v-list-item>
                      <v-list-item>
                        <v-list-item-subtitle>출판일:</v-list-item-subtitle>
                        <v-list-item-title class="text-right">{{ item.publishDate }}</v-list-item-title>
                      </v-list-item>
                      <v-list-item>
                        <v-list-item-subtitle>가격:</v-list-item-subtitle>
                        <v-list-item-title class="text-right">{{ item.bookPrice }}</v-list-item-title>
                      </v-list-item>
                      <v-list-item>
                        <v-list-item-subtitle>카테고리:</v-list-item-subtitle>
                        <v-list-item-title class="text-right">{{ item.categoryName }}</v-list-item-title>
                      </v-list-item>
                      <v-list-item>
                        <v-list-item-subtitle>대여 횟수:</v-list-item-subtitle>
                        <v-list-item-title class="text-right">{{ item.rentCnt }}</v-list-item-title>
                      </v-list-item>
                      <v-list-item>
                        <v-list-item-subtitle>위치:</v-list-item-subtitle>
                        <v-list-item-title
                          class="text-right"
                        >{{ item.cityName }}{{ item.className }}</v-list-item-title>
                      </v-list-item>
                      <v-list-item>
                        <v-list-item-subtitle>상태:</v-list-item-subtitle>
                        <v-list-item-title class="text-right">{{ item.state }}</v-list-item-title>
                      </v-list-item>
                    </v-list>
                  </template>
                </v-data-iterator>
              </div>
            </v-col>
            <v-col cols="6">
              <div class="ma-3">
                <v-flex d-flex>대여 회원 정보</v-flex>
                <v-divider></v-divider>
                <v-data-iterator
                  :items="userInfo"
                  hide-default-footer
                  :no-data-text="userNoDataText"
                >
                  <template v-slot:default="props">
                    <v-list dense v-for="item in props.items" :key="item.bookImg">
                      <v-flex sm3 style="margin-left: auto">
                        <v-img :src="userImg || userDefaultImg" />
                      </v-flex>
                      <v-list-item>
                        <v-list-item-subtitle>회원 번호:</v-list-item-subtitle>
                        <v-list-item-title
                          class="text-right"
                        >{{ item.studentId }} ({{ item.cityName }}{{ item.className }})</v-list-item-title>
                      </v-list-item>
                      <v-list-item>
                        <v-list-item-subtitle>이름:</v-list-item-subtitle>
                        <v-list-item-title class="text-right">{{ item.userName }}</v-list-item-title>
                      </v-list-item>
                      <v-list-item>
                        <v-list-item-subtitle>이메일:</v-list-item-subtitle>
                        <v-list-item-title class="text-right">{{ item.email }}</v-list-item-title>
                      </v-list-item>
                      <v-list-item>
                        <v-list-item-subtitle>전화번호:</v-list-item-subtitle>
                        <v-list-item-title class="text-right">{{ item.phone }}</v-list-item-title>
                      </v-list-item>
                      <v-list-item v-if="item.categoryName">
                        <v-list-item-subtitle>관심 분야:</v-list-item-subtitle>
                        <div class="text-right">
                          <v-list-item-title class="text-right">{{ item.categoryName }}</v-list-item-title>
                        </div>
                      </v-list-item>
                      <v-list-item>
                        <v-list-item-subtitle>연체 기간:</v-list-item-subtitle>
                        <v-list-item-title class="text-right">{{ item.overduePeriod }}</v-list-item-title>
                      </v-list-item>
                      <v-list-item>
                        <v-list-item-subtitle>권한:</v-list-item-subtitle>
                        <v-list-item-title class="text-right">{{ item.userRoleName }}</v-list-item-title>
                      </v-list-item>
                    </v-list>
                  </template>
                </v-data-iterator>
              </div>
            </v-col>
          </v-row>
        </v-card>
      </v-flex>
      <!-- <v-flex class="pt-0 text-right">
        <v-btn color="#3396f4" dark depressed class="ml-3 mt-0 mb-0 mr-0" @click="bookEdit()">도서 수정</v-btn>
      </v-flex> -->
    </v-layout>
  </v-container>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  name: "BookDetailPage",
  components: {},
  computed: {
    ...mapGetters(["requestHeader", "user", "getAxios"])
  },
  data() {
    return {
      userInfo: [],
      bookDetail: [],
      bookId: "",
      userDefaultImg:
        "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__340.png",
      bookDefaultImg:
        "https://dynamicmediainstitute.org/wp-content/themes/dynamic-media-institute/imagery/default-thesis-abstract.png",
      category: [],
      userNoDataText: "대여 기록이 없습니다"
    };
  },
  methods: {
    bookEdit() {
      this.$router.push({ name: "bookedit", params: { bookId: "" } });
      window.sessionStorage.setItem("editBookId", this.bookId);
    }
  },
  created() {
    this.bookId = window.sessionStorage.getItem("bookId");

    this.getAxios("/admin/book/detail/" + this.bookId, this.requestHeader)
      .then(res => {
        this.bookDetail = res.data.bookDetail;
        this.bookImg = res.data.bookDetail[0].bookImg;
        this.userInfo = res.data.userInfo;
        
        if (this.userInfo != null && this.userInfo != []) {
          this.category = res.data.userInfo.categoryId;
          this.userImg = res.data.userInfo[0].userImg;
        }
      })
      .catch(err => {
        console.log(err);
      });
  }
};
</script>
<style scoped>
#bookName {
  white-space: pre-line;
}
</style>