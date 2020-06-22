<template>
  <div>
    <v-container>
      <v-flex sm8 offset-sm-2>
        <v-carousel
          :show-arrows="false"
          hide-delimiter-background
          delimiter-icon="fa-circle"
          height="100%"
          class="mb-5"
        >
          <v-carousel-item>
            <v-sheet
              color="blue"
              height="100%"
              min-height="250px"
              tile
            >
              <v-container fill-height>
                <div class="headline" v-if="$vuetify.breakpoint.smAndUp">대여 도서</div>
                <div class="white--text title font-weight-bold" v-else>대여 도서</div>
                <v-row
                  class="fill-height pb-10"
                  align="center"
                  justify="center"
                  style="height: 80%;"
                >
                  <div v-if="book.length === 0">{{ bookMessage }}</div>
                  <div v-else style="width: 90%">
                    <v-container class="d-flex justify-center">
                      <div>
                        <div class="d-flex align-center">
                          <div v-if="$vuetify.breakpoint.smAndUp">
                            <v-card tile :elevation="8">
                            <v-img
                              height="170px"
                              width="120px"
                              :src="book.bookImg"
                            alt="nowRentedBookImg"
                            ></v-img>
                            </v-card>
                          </div>
                          <div v-else>
                            <v-card tile :elevation="8">
                            <v-img
                              height="100px"
                              width="80px"
                              :src="book.bookImg"
                            alt="nowRentedBookImg"
                            ></v-img>
                            </v-card>  
                          </div>
                          <v-row
                            class="flex-column ma-0 ml-3"
                            justify="center"
                          >
                            <v-col class="px-0">
                              <strong class="white--text text--darken-1"> {{ book.bookName }}</strong>
                            </v-col>
                            <v-col class="pa-0 pt-2">
                              <strong class="yellow--text text--darken-1" v-if="$vuetify.breakpoint.smAndUp">
                                <p class="mb-0">{{ moment(rentFrom).format('YYYY-MM-DD') }} ~ {{ moment(rentTo).format('YYYY-MM-DD') }}</p>
                              </strong>
                              <strong class="yellow--text text--darken-1" v-else>
                                {{ moment(rentTo).format('YYYY-MM-DD') }}
                              </strong>
                            </v-col>
                          </v-row>
                        </div>
                      </div>
                    </v-container>
                    <v-container class="pa-0 d-inline-flex align-center">
                      <div v-if="percent < 25"><v-icon color="amber">fa-hourglass-start</v-icon></div>
                      <div v-else-if="percent < 75"><v-icon color="amber">fa-hourglass-half</v-icon></div>
                      <div v-else><v-icon :color="linearBarColor">fa-hourglass-end</v-icon></div>
                      <v-progress-linear
                        active
                        background-opacity="0.4"
                        buffer-value="100"
                        height="11"
                        :value="this.percent"
                        rounded
                        :color="linearBarColor"
                        class="ml-1"
                      ></v-progress-linear>
                    </v-container>
                  </div>
                </v-row>
              </v-container>
            </v-sheet>
          </v-carousel-item>
          <v-carousel-item>
            <v-sheet
              color="blue"
              height="100%"
              min-height="250px"
              tile
            >
              <v-container fill-height>
                <div class="headline" v-if="$vuetify.breakpoint.smAndUp">내 알림</div>
                <div class="title font-weight-bold" v-else>내 알림</div>
                <v-row
                  class="fill-height pb-10"
                  align="center"
                  justify="center"
                  style="height: 80%;"
                >
                  <div v-if="notWindow.length > 0" style="width:100%">
                    <v-container v-for="notice in notWindow" :key="notice.notwindowId">
                      <v-btn block text class="d-flex justify-space-between" @click="notWindowButton(notice)">
                        <div class="d-inline-flex">
                          <p class="mb-0 body-2">{{ notice.title }}</p>
                        </div>
                        <p class="mb-0 body-2"><v-icon>{{ icons.mdiEmailOutline }}</v-icon> {{ moment(notice.writeDate).format("MM.DD") }}</p>
                      </v-btn>
                      <v-divider></v-divider>
                    </v-container>
                  </div>
                  <div v-else><p class="mb-0">받은 알림이 없습니다.</p></div>
                </v-row>
              </v-container>
            </v-sheet>
          </v-carousel-item>
        </v-carousel>
        <v-sheet
          color="blue-grey darken-4"
          height="100%"
          min-height="200px"
          class="mb-5"
          tile
        >
          <v-container fill-height>
            <div class="white--text headline" v-if="$vuetify.breakpoint.smAndUp">오늘의 책</div>
            <div class="white--text title font-weight-bold" v-else>오늘의 책</div>
            <v-row
              class="fill-height"
              align="center"
              justify="center"
              style="height: 80%; margin: 0% 0% 0% 3%;"
            >
              <v-container class="d-flex justify-center" v-if="todayBook.bookISBN">
                <div>
                  <router-link :to="{
                    name: 'bookdetail',
                    query: {
                      bookISBN: todayBook.bookISBN,
                      categoryId : todayBook.categoryId
                    }
                  }" style="text-decoration:none">
                    <div class="d-flex align-center" >
                      <div v-if="$vuetify.breakpoint.smAndUp">
                        
                        <v-card tile :elevation="8">
                          <v-img
                              height="170px"
                              width="120px"
                              :src="todayBook.bookImg"
                               alt="todayBookImg"
                          ></v-img>
                            </v-card>
                      </div>
                      <div v-else>
                        <v-card tile :elevation="8">
                            <v-img
                              height="100px"
                              width="80px"
                              :src="todayBook.bookImg"
                               alt="todayBookImg"
                            ></v-img>
                        </v-card>  
                      </div>
                     
                      <v-row
                        class="flex-column ma-0 ml-3"
                        justify="center"
                      >
                        <v-col class="px-0">
                          <strong class="white--text text--darken-1 "> {{ todayBook.bookName }}</strong>
                        </v-col>
                        <v-col class="pa-0 pt-2">
                          <strong class="yellow--text text--darken-1" v-if="$vuetify.breakpoint.smAndUp">
                            {{ todayBook.author }} | {{ todayBook.publisher }}
                          </strong>
                          <strong class="yellow--text text--darken-1" v-else>
                            {{ todayBook.author }} | {{ todayBook.publisher }}
                          </strong>
                        </v-col>
                      </v-row>
                    </div>
                  </router-link>
                </div>
              </v-container>
            </v-row>
          </v-container>
        </v-sheet>

        <v-card flat class="mb-5" :min-height="swiperMinHeight" tile>
          <v-container class="fieldBookDesign">
            <p class="black--text headline mb-1" v-if="$vuetify.breakpoint.smAndUp">분야별 도서<v-icon size="24px" style="position: absolute; right:0px; top:5px;">fa-chevron-right</v-icon></p>
            <p class="title font-weight-bold mb-1" v-else>분야별 도서<v-icon size="24px" style="position: absolute; right:0px; top:5px;">fa-chevron-right</v-icon></p>
            
            <div class="swiper-container field">
              <div class="swiper-wrapper">
                <div class="swiper-slide" v-for="(category, idx) in categories" :key="idx" :style="sliderHeight">
                  <router-link :to="{
                    name:'booklist',
                    query: {
                      selectedTab: '2', 
                      selectedFieldId: category.categoryId
                    }
                  }" class="d-flex justify-center" style="text-decoration: none">
                    <div v-if="$vuetify.breakpoint.smAndUp">
                      
                      <v-card tile :elevation="8">
                      <v-img
                        height="170px"
                        width="120px"
                        :src="category.cardImg"
                        alt="categoryBookImg"
                      ></v-img>
                      </v-card>
                      <p class="mb-0 caption black--text text-center">{{category.name}}</p>
                    </div>
                    <div v-else>
                      <v-card tile :elevation="6">
                      <v-img
                        height="100px"
                        width="80px"
                        :src="category.cardImg"
                        alt="categoryBookImg"
                      ></v-img>
                      </v-card>
                      <p class="mb-0 caption black--text text-center">{{category.name}}</p>
                    </div>
                  </router-link>
                </div>
              </div>
            </div>
          </v-container>
        </v-card>
        <hr>
        <v-card flat class="mb-5" :min-height="swiperMinHeight" tile>
          <v-container class="recommendDesign">
            <p class="black--text headline mb-1" v-if="$vuetify.breakpoint.smAndUp">내 맞춤 도서<v-icon size="24px" style="position: absolute; right:0px; top:5px;">fa-chevron-right</v-icon></p>
            <p class="title font-weight-bold mb-1" v-else>내 맞춤 도서<v-icon size="24px" style="position: absolute; right:0px; top:5px;">fa-chevron-right</v-icon></p>
            <div class="swiper-container recommend">
              <div class="swiper-wrapper">
                <div class="swiper-slide" v-for="(recommendBook, idx) in recommendBooks" :key="idx" :style="sliderHeight">
                  
                  <router-link :to="{
                    name: 'bookdetail',
                    query: {
                      bookISBN: recommendBook.bookISBN,
                      categoryId: recommendBook.categoryId
                    }
                  }">
                    <div v-if="$vuetify.breakpoint.smAndUp">
                      <v-card tile :elevation="6">
                      <v-img
                        height="170px"
                        width="120px"
                        :src="recommendBook.bookImg"
                        alt="recommendBookImg"
                        :elevation="elevationSelected"
                      ></v-img>
                      </v-card>
                      
                      
                    </div>
                    <div v-else>
                      <v-card tile :elevation="6">
                      <v-img
                        height="100px"
                        width="80px"
                        :src="recommendBook.bookImg"
                        alt="recommendBookImg"
                      ></v-img>
                      </v-card>
                      
                      
                     
                    </div>
                  </router-link>
                  
                </div>
              </div>
            </div>
          </v-container>
        </v-card>
        <hr>
        <v-card flat class="mb-5" :min-height="swiperMinHeight" tile>
          <v-container class="bestSellerDesign">
            <p class="black--text headline mb-1" v-if="$vuetify.breakpoint.smAndUp">인기 도서<v-icon size="24px" style="position: absolute; right:0px; top:5px;">fa-chevron-right</v-icon></p>
            <p class="title font-weight-bold mb-1" v-else>인기 도서<v-icon size="24px" style="position: absolute; right:0px; top:5px;">fa-chevron-right</v-icon></p>
            <div class="swiper-container best">
              <div class="swiper-wrapper">
                <div class="swiper-slide" v-for="(bestseller, idx) in bestsellers" :key="idx" :style="sliderHeight">
                  <router-link :to="{
                    name: 'bookdetail',
                    query: {
                      bookISBN: bestseller.bookISBN,
                      categoryId : bestseller.categoryId
                    }
                  }">
                    <div v-if="$vuetify.breakpoint.smAndUp">
                      <v-card tile :elevation="6">
                      <v-img
                        height="170px"
                        width="120px"
                        :src="bestseller.bookImg"
                        alt="bestsellerBookImg"
                      ></v-img>
                      </v-card>
                    </div>
                    <div v-else>
                      <v-card tile :elevation="6">
                      <v-img
                        height="100px"
                        width="80px"
                        :src="bestseller.bookImg"
                        alt="bestsellerBookImg"
                      ></v-img>
                      </v-card>
                    </div>
                  </router-link>
                </div>
              </div>
            </div>
          </v-container>
        </v-card>
        <hr>
        <v-card flat class="mb-5" :min-height="swiperMinHeight" tile>
          <v-container class="rentableDesign">
            <p class="black--text headline mb-1" v-if="$vuetify.breakpoint.smAndUp">대여 가능 도서<v-icon size="24px" style="position: absolute; right:0px; top:5px;">fa-chevron-right</v-icon></p>
            <p class="title font-weight-bold mb-1" v-else>대여 가능 도서<v-icon size="24px" style="position: absolute; right:0px; top:5px;">fa-chevron-right</v-icon></p>
            <div class="swiper-container rentable">
              <div class="swiper-wrapper">
                <div class="swiper-slide" v-for="(rentableBook, idx) in rentableBooks.slice(0,10)" :key="idx" :style="sliderHeight">
                  <router-link :to="{
                    name: 'bookdetail',
                    query: {
                      bookISBN: rentableBook.bookISBN,
                      categoryId : rentableBook.categoryId
                    }
                  }">
                    <div v-if="$vuetify.breakpoint.smAndUp">
                      <v-card tile :elevation="6">
                      <v-img
                        height="170px"
                        width="120px"
                        :src="rentableBook.bookImg"
                        alt="rentableBookImg"
                      ></v-img>
                      </v-card>
                    </div>
                    <div v-else>
                      <v-card tile :elevation="6">
                      <v-img
                        height="100px"
                        width="80px"
                        :src="rentableBook.bookImg"
                        alt="rentableBookImg"
                      ></v-img>
                      </v-card>
                    </div>
                  </router-link>
                </div>
              </div>
            </div>
          </v-container>
        </v-card>
        <Footer></Footer>
      </v-flex>
    </v-container>
    <v-dialog persistent v-model="notWindowDialog" width="400px">
      <v-card tile flat>
              <v-flex
                d-flex
                dark
                style="background-color: #3396F4; color: white;font-weight:bolder"
                class="pl-4 pt-3 pb-3"
                
              >알림</v-flex>
              <v-flex
                d-flex
                class="mt-10 ml-10 mr-10 mb-5"
                style="border-style: solid; border-width: 1px; border-color: #E0E0E0"
              >
                <v-card-title>
                  <v-flex d-flex>
                    <v-flex d-flex class="flex-column" style="line-height:18px">
                      <span class="pl-1 mt-auto mb-auto">
                        <span style="color: #777F88; font-size: 12px;font-family: Malgun Gothic,Georgia,serif;font-weight:bolder;">작성자</span>
                        <span class="pl-1" style="color: black; font-size: 12px">{{ myNotice.writer }}</span>
                      </span>
                      <span class="pl-1 mt-auto mb-auto">
                        <span style="color: #777F88; font-size: 12px;font-family: Malgun Gothic,Georgia,serif;font-weight:bolder;">작성일</span>
                        <span class="pl-1" style="color: black; font-size: 12px">{{ myNotice.writeDate}}</span>
                      </span>
                    </v-flex>
                  </v-flex>
                </v-card-title>
              </v-flex>
              <v-flex
                d-flex
                class="ml-10 mr-10 mb-5"
                style="border-style: solid; border-width: 1px; border-color: #E0E0E0"
              >
                <v-card-text style="font-size: 12px;font-family: Malgun Gothic,Georgia,serif;font-weight:bolder;">{{ myNotice.title }}</v-card-text>
              </v-flex>
              <v-flex
                d-flex
                class="ml-10 mr-10"
                style="border-style: solid; border-width: 1px; border-color: #E0E0E0;"
              >
                <v-card flat style="height: 20vh; overflow: auto; font-size: 12px">
                <v-card-text style="font-size: 12px; font-family: Malgun Gothic,Georgia,serif;font-weight:bolder;">{{ myNotice.content }}</v-card-text>
                </v-card>
              </v-flex>
              <v-flex d-flex class="ml-10 mr-10 pt-5 pb-5 justify-center">
                <v-btn
                tile
                depressed
                  dense
                  block
                  min-width="120px"
                  height="50px"
                  color="#4F5A66"
                  dark
                  class="ma-0 font-weight-bold"
                  @click="myNoticeClose()"
                >닫기</v-btn>
              </v-flex>
            </v-card>

      
    </v-dialog>
  </div>
</template>

<script>
import '../../node_modules/swiper/css/swiper.min.css'
import { Swiper } from '../../node_modules/swiper/js/swiper.esm.js'
import Footer from '../components/Footer'
import VClamp from 'vue-clamp'
import { mapGetters } from 'vuex'
import {
  mdiPencilBoxOutline,
  mdiEmailOutline
} from '@mdi/js'

export default {
  name: 'MainPage',
	components: {
    Footer, VClamp
  },
  data() {
    return {
      
      book: [
      ],
      elevationSelected: 3,
      bookMessage: '대여 중인 도서가 없습니다.',
      recommendBooks: [],
      bestsellers: [],
      rentableBooks: [],
      rentFrom: '',
      rentTo: '',
      percent: 0,
      linearBarColor: 'yellow',
      notWindow: [],
      moment: require('moment'),
      notWindowDialog: false,
      myNotice: {},
      sliderSize: 120,
      todayBook: [],
      categories: [
        {
          name: "웹 / 모바일",
          categoryId: "000",
          cardImg: "https://cdn.inflearn.com/wp-content/uploads/web3-1.png"
        },
        {
          name: "머신러닝",
          categoryId: "001",
          cardImg: "https://opgg-com-image.akamaized.net/attach/images/20190226133812.723017.png"
        },
        {
          name: "DS / 빅데이터",
          categoryId: "002",
          cardImg: "https://img.kr.news.samsung.com/kr/wp-content/uploads/2018/11/181113actionable5.jpg"
        },
        {
          name: "블록체인",
          categoryId: "003",
          cardImg: "https://dimg.donga.com/wps/NEWS/IMAGE/2019/09/29/97636275.2.jpg"
        },
        {
          name: "IoT",
          categoryId: "004",
          cardImg: "https://crevate.com/wp-content/uploads/2018/04/cm18004585-950x600.jpg"
        },
        {
          name: "언어",
          categoryId: "005",
          cardImg: "https://f1.codingworldnews.com/2019/05/e7sd2owuuo.jpeg"
        },
        {
          name: "알고리즘",
          categoryId: "006",
          cardImg: "https://img.velog.io/post-images/dvmflstm/d99bdee0-09d0-11ea-9bf5-814109ad4203/algorithm-2.png?w=1024"
        },
        {
          name: "애자일",
          categoryId: "007",
          cardImg: "https://previews.123rf.com/images/sakaidasan/sakaidasan1609/sakaidasan160900003/64884368-%EC%95%A0%EC%9E%90%EC%9D%BC-%EA%B0%9C%EB%B0%9C-%ED%94%84%EB%A1%9C%EC%84%B8%EC%8A%A4.jpg"
        },
        {
          name: "면접",
          categoryId: "008",
          cardImg: "https://t1.daumcdn.net/cfile/tistory/99AC933F5B021AE611"
        },
        {
          name: "CI / CD",
          categoryId: "009",
          cardImg: "https://www.synopsys.com/content/dam/synopsys/library/icons/icon-sig-ci-cd-continuous-integration-continuous-deployment.svg.imgo.svg"
        },
        {
          name: "VueJS",
          categoryId: "010",
          cardImg: "https://upload.wikimedia.org/wikipedia/commons/thumb/9/95/Vue.js_Logo_2.svg/1200px-Vue.js_Logo_2.svg.png"
        },
        {
          name: "ReactJS",
          categoryId: "011",
          cardImg: "https://www.trishanatechnologies.com/images/reactjs.jpg"
        },
        {
          name: "데이터베이스",
          categoryId: "012",
          cardImg: "https://previews.123rf.com/images/ctermit/ctermit1611/ctermit161100004/68500919-%EB%8D%B0%EC%9D%B4%ED%84%B0%EB%B2%A0%EC%9D%B4%EC%8A%A4-db-%EC%95%84%EC%9D%B4%EC%BD%98-%EC%84%B8%ED%8A%B8.jpg"
        },
        {
          name: "기타",
          categoryId: "013",
          cardImg: "https://file.mk.co.kr/mkde_7/N0/2019/09/20190911_4227439_1568163144.jpeg"
        }
      ],
      icons: {
        mdiPencilBoxOutline,
        mdiEmailOutline
      }
    }
  },
  computed: {
    ...mapGetters([
      'requestHeader',
      'user',
      'getAxios',
    ]),
    myBookSize() {
      if (this.$vuetify.breakpoint.xs) {
        return 100
      }
      else {
        return 150
      }
    },
    swiperMinHeight() {
      if (this.$vuetify.breakpoint.xs) {
        return 180
      }
      else {
        return 250
      }
    },
    changCols() {
      if (this.$vuetify.breakpoint.xs) {
        return "6"
      }
      else {
        return "3"
      }
    },
    bookWidth() {
      if (this.$vuetify.breakpoint.smAndUp) {
        this.sliderSize = 120
        return this.sliderSize
      }
      else {
        this.sliderSize = 85
        return this.sliderSize
      }
    },
    sliderHeight() {
      if (this.$vuetify.breakpoint.smAndUp) {
        return `height: ${this.sliderSize * 1.5}px`
      }
      else {
        return `height: ${this.sliderSize * 1.2}px`
      }
    },
  },
	methods: {
    notWindowButton(myNotice) {
      this.myNotice = myNotice
      this.notWindowDialog = true
    },
    myNoticeClose() {
      this.myNotice = {}
      this.notWindowDialog = false
    },
    mainInit() {
      const fieldSwiper = new Swiper ('.field', {
        // Optional parameters
        direction: 'horizontal',
        slidesPerView: 3.6,
        spaceBetween: 20,
      })
      this.getAxios.get("/recommend", this.requestHeader)
      .then(res => {
        this.recommendBooks = res.data.recommendBooks
        requestAnimationFrame(function() {
          const recommendSwiper = new Swiper ('.recommend', {
            // Optional parameters
            direction: 'horizontal',
            slidesPerView: 3.6,
            spaceBetween: 20,
          })
        })
      })
      .catch(err => {
        console.log(err)
      })
      this.getAxios.get("/book/today", this.requestHeader)
      .then(res => {
        this.todayBook = res.data.todayBook
      })
      .catch(err => {
        console.log(err)
      })
      this.getAxios.get("/book/best", this.requestHeader)
      .then(res => {
        this.bestsellers = res.data.bestBook
        requestAnimationFrame(function() {
          const bestSwiper = new Swiper ('.best', {
            // Optional parameters
            direction: 'horizontal',
            slidesPerView: 3.6,
            spaceBetween: 20,
          })
        })
      })
      .catch(err => {
        console.log(err)
      })
      this.getAxios.get(`/book/rentable/${this.user.cityId}`, this.requestHeader)
      .then(res => {
        this.rentableBooks = res.data.rentableBook
        requestAnimationFrame(function() {
          const rentableSwiper = new Swiper ('.rentable', {
            // Optional parameters
            direction: 'horizontal',
            slidesPerView: 3.6,
            spaceBetween: 20,
          })
        })
      })
      .catch(err => {
        console.log(err)
      })
      this.getAxios.get("/notwindow/list", this.requestHeader)
      .then(res => {
        this.notWindow = res.data.notWindowList
      })
      .catch(err => {
        console.log(err)
      })
      this.getAxios.post("/main", {}, this.requestHeader)
      .then(res => {
        if (res.data.book) {
          this.$store.dispatch('insertUserBook', res.data.book)
          const from = this.moment(res.data.rentFrom, 'YYYY-MM-DD HH:mm:ss')
          const now = this.moment()
          const to = this.moment(res.data.rentTo, 'YYYY-MM-DD HH:mm:ss')
          const numerator = this.moment.duration(now.diff(from)).asSeconds()
          const expire = this.moment.duration(now.diff(to)).asSeconds()
          const denominator = 259200
          const temp = numerator / denominator * 100
          if (temp > 100 || expire > 0) {
            this.linearBarColor = 'red'
            this.percent = 100
          }
          else {
            this.linearBarColor = 'amber'
            this.percent = parseInt(temp)
          }
          this.book = res.data.book
          this.rentFrom = res.data.rentFrom
          this.rentTo = res.data.rentTo
        }
      })
      .catch(err => {
        console.log(err)
      })
    }
  },
  mounted() {
    this.mainInit()
  },
}
</script>

<style scoped>
.cardBorder {
  border: 2px;
  border-style: solid;
  border-color: rgb(255, 230, 0);
}
.swiper-slide {
  text-align: center;
  font-size: 18px;
  /* Center slide text vertically */
  display: -webkit-box;
  display: -ms-flexbox;
  display: -webkit-flex;
  display: flex;
  -webkit-box-pack: center;
  -ms-flex-pack: center;
  -webkit-justify-content: center;
  justify-content: center;
  -webkit-box-align: center;
  -ms-flex-align: center;
  -webkit-align-items: center;
  align-items: center;
}
.recommendDesign:before {
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100px;
  background-color: #ffffff;
  content: "";
}
.recommendDesign > p {
  position: relative;
}
.bestSellerDesign:before {
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100px;
  background-color: #ffffff;
  content: "";
}
.bestSellerDesign > p {
  position: relative;
}
.fieldBookDesign:before {
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 130px;
  background-color: #ebebebc9;
  content: "";
}
.fieldBookDesign > p {
  position: relative;
}
.rentableDesign:before {
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 50px;
  background-color: #ffffff;
  content: "";
}
.rentableDesign > p {
  position: relative;
}

hr {
  margin: 2% 7%;
  width: 85%;
  color: grey;
  opacity: 80%;
}
</style>