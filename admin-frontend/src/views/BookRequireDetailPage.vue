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
                <v-data-iterator :items="reqBookDetail" hide-default-footer>
                  <template v-slot:default="props">
                    <v-list dense v-for="item in props.items" :key="item.bookImg">
                      <v-flex sm3 style="margin-left: auto">
                        <v-img :src="reqBookImg || bookDefaultImg" />
                      </v-flex>
                      <v-list-item>
                        <v-list-item-subtitle>도서명:</v-list-item-subtitle>
                        <v-list-item-title class="text-right" id="bookName">{{ item.bookName }}</v-list-item-title>
                      </v-list-item>
                      <v-list-item>
                        <v-list-item-subtitle>ISBN 코드:</v-list-item-subtitle>
                        <v-list-item-title class="text-right">{{ item.bookISBN }}</v-list-item-title>
                      </v-list-item>
                      <v-list-item>
                        <v-list-item-subtitle>출판사:</v-list-item-subtitle>
                        <v-list-item-title class="text-right">{{ item.publisher }}</v-list-item-title>
                      </v-list-item>
                      <v-list-item>
                        <v-list-item-subtitle>가격:</v-list-item-subtitle>
                        <v-list-item-title class="text-right">{{ item.bookPrice }}</v-list-item-title>
                      </v-list-item>
                      <v-list-item>
                        <v-list-item-subtitle>신청 사유:</v-list-item-subtitle>
                        <v-list-item-title class="text-right">{{ item.reqReason }}</v-list-item-title>
                      </v-list-item>
                      <v-list-item>
                        <v-list-item-subtitle>위치:</v-list-item-subtitle>
                        <v-list-item-title class="text-right">{{ item.reqCity }}</v-list-item-title>
                      </v-list-item>
                      <v-list-item>
                        <v-list-item-subtitle>미니도서관 배치 여부:</v-list-item-subtitle>
                        <v-list-item-title class="text-right" v-if="item.have == 0">O</v-list-item-title>
                        <v-list-item-title class="text-right" v-else-if="item.have == 1">X</v-list-item-title>
                      </v-list-item>
                    </v-list>
                  </template>
                </v-data-iterator>
              </div>
            </v-col>
            <v-col cols="6">
              <div class="ma-3">
                <v-flex d-flex>신청 회원 정보</v-flex>
                <v-divider></v-divider>
                <v-data-iterator :items="userInfo" hide-default-footer>
                  <template v-slot:default="props">
                    <v-list dense v-for="item in props.items" :key="item.userImg">
                      <v-flex sm3 style="margin-left: auto">
                        <v-img :src="userImg || userDefaultImg" />
                      </v-flex>
                      <v-list-item>
                        <v-list-item-subtitle>회원 번호:</v-list-item-subtitle>
                        <v-list-item-title class="text-right">
                          {{ item.studentId }}
                          <span
                            v-if="item.reqCity && item.userClass"
                          >({{ item.reqCity }}{{ item.userClass }})</span>
                        </v-list-item-title>
                      </v-list-item>
                      <v-list-item v-if="reqBookDetail[0].consultant">
                        <v-list-item-subtitle>컨설턴트:</v-list-item-subtitle>
                        <v-list-item-title
                          class="text-right"
                          v-if="item.consultant"
                        >{{ reqBookDetail[0].consultant }}</v-list-item-title>
                      </v-list-item>
                      <v-list-item v-if="reqBookDetail[0].coach">
                        <v-list-item-subtitle>실습코치:</v-list-item-subtitle>
                        <v-list-item-title class="text-right">{{ reqBookDetail[0].coach }}</v-list-item-title>
                      </v-list-item>
                      <v-list-item v-if="reqBookDetail[0].pjtweek">
                        <v-list-item-subtitle>주차:</v-list-item-subtitle>
                        <v-list-item-title class="text-right">{{ reqBookDetail[0].pjtweek }}</v-list-item-title>
                      </v-list-item>
                      <v-list-item v-if="reqBookDetail[0].period">
                        <v-list-item-subtitle>기간:</v-list-item-subtitle>
                        <div class="text-right">
                          <v-list-item-title class="text-right">{{ reqBookDetail[0].period }}</v-list-item-title>
                        </div>
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
    </v-layout>
  </v-container>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  name: "BookRequireDetailPage",
  components: {},
  computed: {
    ...mapGetters(["requestHeader", "user", "getAxios"])
  },
  data() {
    return {
      userInfo: [],
      reqBookDetail: [],
      reqId: "",
      userDefaultImg:
        "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__340.png",
      bookDefaultImg:
        "https://dynamicmediainstitute.org/wp-content/themes/dynamic-media-institute/imagery/default-thesis-abstract.png",
      category: []
    };
  },
  created() {
    this.reqId = window.sessionStorage.getItem("reqId");

    this.getAxios(
      "/admin/book/require/detail/" + this.reqId,
      this.requestHeader
    )
      .then(res => {
        this.reqBookDetail = res.data.reqBookDetails;
        this.reqBookImg = res.data.reqBookDetails[0].bookImg;
        this.userInfo = res.data.userInfo;

        if (
          this.userInfo[0].userImg != null &&
          this.userInfo[0].userImg != ""
        ) {
          this.userImg =
            "https://i02b206.p.ssafy.io/image" + res.data.userInfo[0].userImg;
        }
        this.category = res.data.userInfo.categoryId;
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