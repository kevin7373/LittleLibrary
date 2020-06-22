<template>
  <v-layout>
    <v-layout>
      <v-flex>
        <v-row justify="center">
          <v-col cols="12" class="pb-0">
            <v-chip-group
              v-model="selectedCategoryId"
              active-class="amber--text text--accent-4"
              class="mt-2"
              mandatory
              next-icon="fa-chevron-right"
              prev-icon="fa-chevron-left"
              mobile-break-point="600"
            >
              <v-chip small v-for="category in categories" :key="category.categoryId" :value="category.categoryId">{{ category.name }}</v-chip>
            </v-chip-group>
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
                <v-img :src="book.bookImg" alt="categoryBookImg" class="ma-auto" style="width: 150px; height: 200px">
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
        categoryBooks: [],
        selectedCategoryId: "000",
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
        categories: [
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
      }
    },
    components: {
      VClamp
    },
    watch: {
      selectedCategoryId: function() {
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
        return this.categoryBooks.filter(book => {
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
      fieldBookInit() {
        if (this.$route.query.selectedFieldId) {
          this.selectedCategoryId = this.$route.query.selectedFieldId
        }
        this.getBookList()
      },
      getBookList() {        
        this.getAxios.get(`/book/category/${this.selectedCategoryId}`, this.requestHeader)
          .then(res => {
            this.categoryBooks = res.data.categoryBook
          })
          .catch(err => {
            console.log(err)
          })
      },
      bookNameSort() {
        this.bookButtonColor = 'black'
        this.publishButtonColor = 'grey'
        this.categoryBooks.sort(function (V1, V2) {
          return V1.bookName < V2.bookName ? -1 : V1.bookName > V2.bookName ? 1 : 0;
        })
      },
      publishDateSort() {
        this.publishButtonColor = 'black'
        this.bookButtonColor = 'grey'
        this.categoryBooks.sort(function (V1, V2) {
          return V1.publishDate < V2.publishDate ? 1 : V1.publishDate > V2.publishDate ? -1 : 0;
        })
      },
    },

    mounted() {
      this.fieldBookInit()

    }
  }
</script>

<style scoped>
  .cardContent {
		white-space:nowrap;
    overflow:hidden;
	}
</style>