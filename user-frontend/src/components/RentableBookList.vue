<template>
  <v-layout>
    <v-layout>
      <v-flex>
        <v-row justify="center">
          <v-col cols="12" class="pb-0">
            <div class="d-flex justify-center mt-2">
              <v-chip-group
                v-model="selectedCityId"
                active-class="amber--text text--accent-4"
                column
                mandatory
              >
                <v-chip value="0" small>서울</v-chip>
                <v-chip value="1" small>대전</v-chip>
                <v-chip value="2" small>구미</v-chip>
                <v-chip value="3" small>광주</v-chip>
              </v-chip-group>
            </div>
          </v-col>
        </v-row>
        
        <v-text-field v-model="search" dense full-width append-icon="search" label="Search" single-line hide-details
              class="ma-0 pa-0"></v-text-field>
        <v-layout class="mt-1 mb-4 mx-0">
          <v-flex class="d-flex justify-end">
            <v-btn small text :color="bookButtonColor" @click="bookNameSort">
              <v-icon left small>{{ icons.mdiBookMultiple }}</v-icon>
              <span class="caption text-lowercase">도서명 순</span>
            </v-btn>
            <span>|</span>
            <v-btn small text :color="publishButtonColor" @click="publishDateSort">
              <v-icon left small>{{ icons.mdiCalendarRange }}</v-icon>
              <span class="caption text-lowercase">출판일 순</span>
            </v-btn>            
          </v-flex>
        </v-layout>
        <v-row>
          <v-col v-for="(book, index) in filteredBooks" :key="index" :cols="cardFlex">
            <v-card style="max-width: 150px; max-height: 241.6px;" flat>
              <router-link :to="{
                name: 'bookdetail',
                query: {
                  bookId: book.bookId,
                  bookISBN: book.bookISBN,
                  bookName: book.bookName,
                  bookPrice: book.bookPrice,
                  publisher: book.publisher,
                  publishDate: book.publishDate,
                  author: book.author,
                  bookImg: book.bookImg,
                  rentCnt: book.rentCnt,
                  categoryId: book.categoryId,
                  cityId: book.cityId,
                  classId: book.classId,
                }
              }" style="text-decoration: none;">
                <v-card outlined tile>
                <v-img :src="book.bookImg" alt="rentableBookImg" class="ma-auto" style="width: 150px; height: 200px">
                </v-img>
                </v-card>
                <v-card-text class="pa-0">
                  <v-col cols="auto" class="pa-0">
                    
                    <p class="cardContent black--text caption mb-0">{{ book.bookName }}</p>
                    <p class="cardContent grey--text caption mb-0">{{ book.author }}</p>
                  </v-col>
                </v-card-text>
              </router-link>
            </v-card>
          </v-col>
        </v-row>
      </v-flex>
    </v-layout>
  </v-layout>
</template>
<script>
  import VClamp from 'vue-clamp'
  import {
    mapGetters
  } from 'vuex'
  import {
    mdiBookMultiple,
    mdiCalendarRange,
  } from '@mdi/js';

  export default {
    name: "BookList",
    data() {
      return {
        search: '',
        rentableBooks: [],
        selectedCityId: "0",
        items: [{
            title: '제목 순'
          },
          {
            title: '출판일 순'
          },

        ],
        icons: {
          mdiBookMultiple,
          mdiCalendarRange,
        },
        bookButtonColor: 'grey',
        publishButtonColor: 'grey',
      }
    },
    components: {
      VClamp
    },
    watch: {
      selectedCityId: function() {
        this.getBookList()
      }
    },
    computed: {
      ...mapGetters([
        'requestHeader',
        'getAxios',
        'user',
      ]),
      filteredBooks: function () {
        return this.rentableBooks.filter(book => {
          return book.bookName.includes(this.search)
        })
      },
      cardFlex: function() {
        if (this.$vuetify.breakpoint.xs) {
          return 6
        }
        else if (this.$vuetify.breakpoint.sm) {
          return 4
        }
        else {
          return 3
        }
      }

    },
    methods: {
      rentableInit() {
        this.selectedCityId = this.user.cityId
        this.getBookList()
      },
      getBookList() {
        this.getAxios.get(`/book/rentable/${this.selectedCityId}`, this.requestHeader)
          .then(res => {
            this.rentableBooks = res.data.rentableBook
          })
          .catch(err => {
            console.log(err)
          })
      },
      bookNameSort() {
        this.bookButtonColor = 'black'
        this.publishButtonColor = 'grey'
        this.rentableBooks.sort(function (V1, V2) {
          return V1.bookName < V2.bookName ? -1 : V1.bookName > V2.bookName ? 1 : 0;
        })
      },
      publishDateSort() {
        this.publishButtonColor = 'black'
        this.bookButtonColor = 'grey'
        this.rentableBooks.sort(function (V1, V2) {
          return V1.publishDate < V2.publishDate ? 1 : V1.publishDate > V2.publishDate ? -1 : 0;
        })
      },
    },

    mounted() {
      this.rentableInit()

    }
  }
</script>

<style scoped>
  .cardContent {
		white-space:nowrap;
    overflow:hidden;
	}
</style>