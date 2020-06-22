<template>
  <div style="margin: 15vh 0px">
    <v-card outlined tile>
    <v-container>
      <v-flex sm8 offset-sm-2>
        
        <form @submit.prevent="getBook">
          <v-text-field
            v-model="user.userName"
            label="신청자"
            disabled
          ></v-text-field>
          <v-text-field
            v-model="user.className"
            label="팀명(반)"
            disabled
          ></v-text-field>
          <v-text-field
            v-model="bookName"
            label="도서명"
            required
          ></v-text-field>
          <v-text-field
            v-model="reason"
            label="신청 사유"
            required
          ></v-text-field>
          <div class="d-flex justify-end">
            <v-btn tile depressed type="submit" color="blue lighten-1" class="white--text ml-2">도서 검색</v-btn>
            <v-btn tile depressed color="black" class="white--text ml-2" @click="$router.go(-1)">취소</v-btn>
          </div>
        </form>
        <v-dialog v-model="bookConfirmDialog" persistent width="500">
          <v-card
            style="max-height: 80vh"
            class="overflow-y-auto"
            >
            <v-container>
              <div v-if="bookList">
                <v-list>
                  <v-subheader class="font-weight-bold">{{ message }}</v-subheader>
                  <v-list-item-group color="primary">
                    <template v-for="(book, idx) in bookList">
                      <v-list-item :key="book.bookISBN">
                        <v-card flat tile >
                          <v-row justify="center" align="center">
                          <v-card tile :elevation="8">
                          <v-img 
                            max-width="80px"
                            max-height="100px"
                            min-width="80px"
                            min-height="100px"
                            
                            :src="book.bookImg"
                            alt="searchedBookImg"
                          ></v-img>
                          </v-card>
                        
                        <v-list-item-content class="d-flex justify-center; ml-3">
                          <v-list-item-title class="black--text">{{ book.bookName }}</v-list-item-title>
                          <v-list-item-subtitle>{{ book.author }} | {{ book.publisher }}</v-list-item-subtitle>
                          <v-list-item-subtitle>{{ book.publishDate }} | {{ book.bookPrice }}원</v-list-item-subtitle>
                          <v-btn tile depressed color="blue lighten-1" class="white--text" @click="requestBook(book)">신청</v-btn>
                        </v-list-item-content>
                          </v-row>
                        </v-card>
                      </v-list-item>
                      <v-divider :key="idx"></v-divider>
                    </template>
                  </v-list-item-group>
                </v-list>
                <p class="caption mb-2 text-end">{{ apiFrom }}</p>
                <v-btn tile depressed color="black" block class="white--text" @click="bookConfirmDialog = false">닫기</v-btn>
              </div>
            </v-container>
          </v-card>
        </v-dialog>
      </v-flex>
    </v-container>
    </v-card>
  </div>
</template>

<script>
  import { mapGetters } from 'vuex'
  export default {
    name: "NeedBookPage",
    data() {
      return {
        reason: '',
        bookName: '',
        bookConfirmDialog: false,
        message: '원하는 책을 선택해 주세요',
        apiFrom: '도서 DB 제공 네이버 인터넷서점(book.naver.com)',
        bookList: [],
      }
    },
    computed: {
      ...mapGetters([
        'requestHeader',
        'user',
        'getAxios',
      ])
    },
    methods: {
      getBook() {
        this.message = '원하는 책을 선택해 주세요'
        this.apiFrom = '도서 DB 제공 네이버 인터넷서점(book.naver.com)'
        this.getAxios.get(`/book/request/info/${this.bookName}`, this.requestHeader)
        .then(res => {
          this.bookList = res.data.bookInfoList
          this.bookConfirmDialog = true
        })
        .catch(err => {
          console.log(err)
        })        
      },
      requestBook(book) {
        this.getAxios.post('/book/request/', {
          userTeam: this.user.classId,
          bookName: book.bookName,
          bookISBN: book.bookISBN,
          publisher: book.publisher,
          bookPrice: book.bookPrice,
          reqReason: this.reason,
          bookImg: book.bookImg,
        }, this.requestHeader)
        .then(res => {
          this.message = "신청이 완료되었습니다."
          this.apiFrom = ""
        })
        .catch(err => {
          console.log(err)
        })
        this.bookList = []
      }
    },
  }
</script>