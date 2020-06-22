<template>
  <v-container>
    <v-flex sm8 offset-sm-2>
      <v-card outlined>
        <v-list>
          
          <v-subheader class="font-weight-bold">대여내역 {{ mybooks.length }}건</v-subheader>
          <v-list-item-group color="primary">
            <template v-for="(mybook, idx) in mybooks">
              <router-link :to="{
                  name: 'bookdetail',
                  query: {
                    bookId: rentedBookInfo[idx].bookId,
                    bookISBN: rentedBookInfo[idx].bookISBN,
                    bookName: rentedBookInfo[idx].bookName,
                    bookPrice: rentedBookInfo[idx].bookPrice,
                    publisher: rentedBookInfo[idx].publisher,
                    publishDate: rentedBookInfo[idx].publishDate,
                    author: rentedBookInfo[idx].author,
                    bookImg: rentedBookInfo[idx].bookImg,
                    rentCnt: rentedBookInfo[idx].rentCnt,
                    categoryId: rentedBookInfo[idx].categoryId,
                    cityId: rentedBookInfo[idx].cityId,
                    classId: rentedBookInfo[idx].classId,
                  }
                }" style="width: 100%; text-decoration: none;" :key="mybook.rentFrom">
                <v-list-item>
                  <v-card flat tile style="margin: 5% 5% 5% 5%">
                  <v-row justify="center" align="center">
                  <v-card tile :elevation="8">
                  <v-img
                  max-width="80px"
                  max-height="100px"
                  min-width="80px"
                  min-height="100px"
                    c
                    :src="rentedBookInfo[idx].bookImg"
                    alt="rentedBookImg"
                  ></v-img>
                  </v-card>
                
                <v-list-item-content class="d-flex justify-center; ml-5 ; mr-5">
                    <v-list-item-title class="black--text">{{rentedBookInfo[idx].bookName}}</v-list-item-title>
                    <v-list-item-subtitle>{{rentedBookInfo[idx].author}} | {{ rentedBookInfo[idx].publisher }}</v-list-item-subtitle>
                    <div v-if="$vuetify.breakpoint.smAndUp">
                      <v-list-item-subtitle>{{mybook.rentFrom}} ~ {{ mybook.realReturn }}</v-list-item-subtitle>
                    </div>
                    <div v-else>
                      <v-list-item-subtitle>{{mybook.rentFrom}} ~</v-list-item-subtitle>
                      <v-list-item-subtitle>{{ mybook.realReturn }}</v-list-item-subtitle>
                    </div>
                  </v-list-item-content>
                  </v-row>
                  </v-card>
                </v-list-item>
              </router-link>
              <v-divider :key="idx"></v-divider>
            </template>
          </v-list-item-group>
        </v-list>
      </v-card>
    </v-flex>
  </v-container>
</template>

<script>
  import { mapGetters } from 'vuex'
  export default {
    name: "MybookList",
    data() {
      return {
        mybooks: [],
        rentedBookInfo: [],
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
      mybookInit() {
        this.getAxios.get('/book/rentedbooks', this.requestHeader)
        .then(res => {
          this.mybooks = res.data.rentedBooks
          this.rentedBookInfo = res.data.rentedBookInfo
        })
        .catch(err => {
          console.log(err)
        })
      }
    },
    mounted() {
      this.mybookInit()
    }
  }
</script>