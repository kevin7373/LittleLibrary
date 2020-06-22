<template>
  <div>
    <v-container>
      <v-layout>
        <v-flex sm8 offset-sm-2>
          <v-col>
            <v-card exact flat>
              <v-card color="grey" class="mb-0 pt-0 pb-4 white--text d-flex align-center">
                  <v-btn text fab @click="profileDialog = true" class="mt-1 ml-4" style="">
                    <v-avatar class="" size="64" v-on="on">
                      <!-- <img :src="userImg" alt="userImg"> -->
                      <v-img max-width="70px" :src="userImg || userDefaultImg" alt="userImg">
                      </v-img>
                    </v-avatar>
                  </v-btn>
                <v-dialog v-model="profileDialog" max-width="400">
                    <v-card>
                      <v-card-text>
                        <v-img contain :src="userImg" alt="userImg"></v-img>
                      </v-card-text>
                    </v-card>
                </v-dialog>
                <v-card-text class="pl-2 white--text d-flex flex-column align-end">
                  <p class="mb-0">{{ user.userName }}</p>
                  <p class="mb-0 grey--text text--lighten-3">{{ user.cityName }} | {{ user.className }}</p>
                </v-card-text>
              </v-card>
              <v-card tile height="45px" color="blue lighten-1" flat dark>
                <div class="pt-3 pl-3">
                  <p>기본 설정 </p>
                </div>
              </v-card>
              <v-hover v-slot:default="{ hover }" open-delay="200">
                <v-btn class="px-0 mb-2" text block @click="imgUploadDialog = true">
                  <v-card tile outlined tag="div" width="100%" height="45px" class="pt-3 pl-7" flat :elevation="hover ? 13 : 0">
                    <v-layout>
                      <v-flex class="d-flex justify-space-between">
                        <span class="subtitle-1"> 내 이미지 등록 및 해제 </span>
                        <v-icon class="mr-3" style="color: rgba(0,0,0,.54)">
                          {{ icons.mdiChevronRight }}
                        </v-icon>
                      </v-flex>
                    </v-layout>
                  </v-card>
                </v-btn>
              </v-hover>
              <v-hover v-slot:default="{ hover }" open-delay="200">
                <router-link tag="div" to="/userInfo">
                  <v-card tile outlined height="45px" class="pt-3 pl-7" flat :elevation="hover ? 13 : 0">
                    <v-layout>
                      <v-flex class="d-flex justify-space-between">
                        <span class="subtitle-1"> 내 정보 수정</span>
                        <v-icon class="mr-3">
                          {{ icons.mdiChevronRight }}
                        </v-icon>
                      </v-flex>
                    </v-layout>
                  </v-card>
                </router-link>
              </v-hover>
              <v-card tile height="45px" color="blue lighten-1" flat dark>
                <div class="pt-3 pl-3" flat>
                  대여 관리
                </div>
              </v-card>
              <v-hover v-slot:default="{ hover }" open-delay="200">
                <router-link tag="div" to="/mybook">
                  <v-card tile outlined height="45px" class="pt-3 pl-7" flat :elevation="hover ? 13 : 0">
                    <v-layout>
                      <v-flex class="d-flex justify-space-between">
                        <span class="subtitle-1"> 대여 내역 </span>
                        <v-icon class="mr-3">
                          {{ icons.mdiChevronRight }}
                        </v-icon>
                      </v-flex>
                    </v-layout>
                  </v-card>
                </router-link>
              </v-hover>
              <v-hover v-slot:default="{ hover }" open-delay="200">
                <v-btn class="px-0 mb-3" text block @click="numberRentDialog = true">
                  <v-card tile outlined tag="div" width="100%" height="45px" class="pt-3 pl-7" flat :elevation="hover ? 13 : 0">
                    <v-layout>
                      <v-flex class="d-flex justify-space-between">
                        <span class="subtitle-1">번호입력으로 대여 및 반납 신청 </span>
                        <v-icon class="mr-3" style="color: rgba(0,0,0,.54)">
                          {{ icons.mdiChevronRight }}
                        </v-icon>
                      </v-flex>
                    </v-layout>
                  </v-card>
                </v-btn>
              </v-hover>
              <v-card tile outlined height="45px" color="blue" flat dark>
                <div class="pt-3 pl-3">
                  희망 도서 신청
                </div>
              </v-card>
              <v-hover v-slot:default="{ hover }" open-delay="200">
                <router-link tag="div" to="/needbook">
                  <v-card tile outlined height="45px" class="pt-3 pl-7" flat :elevation="hover ? 13 : 0">
                    <v-layout>
                      <v-flex class="d-flex justify-space-between">
                        <span class="subtitle-1"> 희망도서 신청하기 </span>
                        <v-icon class="mr-3">
                          {{ icons.mdiChevronRight }}
                        </v-icon>
                      </v-flex>
                    </v-layout>
                  </v-card>
                </router-link>
              </v-hover>
              <v-hover v-slot:default="{ hover }" open-delay="200">
                <router-link tag="div" to="/needbooklist">
                  <v-card tile outlined height="45px" class="pt-3 pl-7" flat :elevation="hover ? 13 : 0">
                    <v-layout>
                      <v-flex class="d-flex justify-space-between">
                        <span class="subtitle-1"> 희망도서 신청 내역 </span>
                        <v-icon class="mr-3">
                          {{ icons.mdiChevronRight }}
                        </v-icon>
                      </v-flex>
                    </v-layout>
                  </v-card>
                </router-link>
              </v-hover>
            </v-card>
            <v-card tile outlined height="45px" color="blue" flat dark>
              <v-btn @click="logout()" block text style="height: 100%">
                <p class="mb-0 subtitle-1 text-left" style="width: 100%">로그아웃</p>
                <v-icon>{{ icons.mdiPower }}</v-icon>
              </v-btn>
            </v-card>
          </v-col>
        </v-flex>
      </v-layout>
    </v-container>
    <v-dialog v-model="numberRentDialog" persistent width="500">
      <v-card>
        <v-container>
          <v-alert dark :color="statusColor" elevation="2" colored-border border="bottom" >
            <v-card-title class="headline d-inline-flex justify-center">
              <v-icon class="mr-2">{{ statusIcon }}</v-icon>
              <p class="mb-0 text-center">{{ message }}</p>
            </v-card-title>
          </v-alert>
          <div v-if="getUserBook">
            <form @submit.prevent="requestReturn">
              <v-text-field
                v-model="bookId"
                label="일련번호를 입력해주세요"
              ></v-text-field>
              <div class="d-flex justify-end">
                <v-btn tile depressed type="submit" color="blue lighten-1" class="white--text">반납</v-btn>
                <v-btn tile depressed color="black" class="white--text ml-2" @click="cancelBookRent()">취소</v-btn>
              </div>              
            </form>
          </div>
          <div v-else>
            <form @submit.prevent="requestRent">
              <v-text-field
                v-model="bookId"
                label="일련번호를 입력해주세요"
              ></v-text-field>
              <div class="d-flex justify-end">
                <v-btn tile depressed type="submit" color="blue lighten-1" class="white--text">대여</v-btn>
                <v-btn tile depressed color="black" class="white--text ml-2" @click="cancelBookRent()">취소</v-btn>
              </div>
            </form>
          </div>          
        </v-container>
      </v-card>
    </v-dialog>
    <v-dialog v-model="imgUploadDialog" persistent width="500">
      <v-card>
        <v-container>
          <v-flex>
            <form @submit.prevent="requestImgUpload">
              <v-row>
                <v-col class="d-inline-flex">
                  <v-img
                    :src="userImg || userDefaultImg"
                    width="112px"
                    height="112px"
                    max-width="112px"
                    max-height="112px"
                    alt="userImg"
                  />
                  <div class="text-center d-flex align-end">
                    <v-btn
                    tile
                      type="file"
                      class="ml-3"
                      outlined
                      @click.prevent="$refs.userImg.click()"
                    >찾아보기</v-btn>
                    <input
                      v-show="false"
                      ref="userImg"
                      type="file"
                      @change="onFileChange"
                    />
                    <v-btn tile class="ml-2" outlined @click="deleteImg()">삭제</v-btn>
                  </div>
                </v-col>
                <p class="mb-3 mb-0 ml-3">사진은 20MB 이내의 jpg, png, gif 파일만 등록 가능합니다.</p>
              </v-row>
              <div class="d-flex justify-end">
                <v-btn tile depressed type="submit" color="blue lighten-1" class="white--text mr-3" @click="imgUploadDialog = false">등록</v-btn>
                <v-btn tile depressed color="black" class="white--text" @click="imgUploadDialog = false">취소</v-btn>
              </div>
            </form>
          </v-flex>
        </v-container>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
  import { mapGetters } from 'vuex'
  import UserInfo from '../components/UserInfo'
  import {
    mdiCallMade,
    mdiChevronRight,
    mdiPower
  } from '@mdi/js'


  export default {
    name: "UserPage",
    data() {
      return {
        icons: {
          mdiCallMade,
          mdiChevronRight,
        },
        states: [
          {name: '서울', cityId: '0'},
          {name: '대전', cityId: '1'},
          {name: '구미', cityId: '2'},
          {name: '광주', cityId: '3'},
        ],
        profileDialog: false,
        numberRentDialog: false,
        imgUploadDialog: false,
        bookId: '',
        message: '도서의 일련번호를 입력해주세요',
        statusColor: 'blue',
        statusIcon: 'fa-info-circle',
        userImg: "",
        uploadImg: "",
        icons: {
          mdiPower
        },
        userDefaultImg:
        "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__340.png",
      }
    },
    components: {
      UserInfo
    },
    computed: {
      ...mapGetters([
        'requestHeader',
        'user',
        'getAxios',
        'getUserBook'
      ])
    },
    methods: {
      deleteImg() {
        this.userImg = this.userDefaultImg;
        this.uploadImg = null;
      },
      onFileChange(e) {
        var files = e.target.files || e.dataTransfer.files;

        if (!files.length) {
          return;
        }
        var fileName = "";
        fileName = files[0].name;

        var reg = /(.*?)\.(jpg|jpeg|png|gif)$/;

        if (!fileName.match(reg)) {
          alert("jpg, jpeg, png, gif 중 하나만 올려주세요");
          return;
        }
        this.uploadImg = files[0];
        this.createImage(files[0]);
      },
      // 이미지 바꿔서 보여주는 부분
      createImage(file) {
        var image = new Image();
        var reader = new FileReader();
        var vm = this;

        reader.onload = e => {
          vm.userImg = e.target.result;
        };
        reader.readAsDataURL(file);
      },
      requestImgUpload() {
        if (this.uploadImg !== null) {
          const user = new FormData();
          user.append("userId", this.user.userId)
          user.append("userPw", this.user.userPw)
          user.append("userName", this.user.userName)
          user.append("studentId", this.user.studentId)
          user.append("email", this.user.email)
          user.append("categoryId", this.user.categoryId)
          user.append("cityId", this.user.cityId)
          user.append("overduePeriod", this.user.overduePeriod)
          user.append("userRoleId", this.user.userRoleId)
          user.append("file", this.uploadImg)
          this.getAxios.post("user/update/image", user, this.requestHeader)
          .then(res => {
            this.$store.dispatch("login", res.headers.token)
          })
          .catch(err => {
            console.log(err)
          })
        }
        else {
          this.getAxios.post("user/delete/image", {}, this.requestHeader)
          .then(res => {
            this.$store.dispatch("login", res.headers.token)
          })
          .catch(err => {
            console.log(err)
          })
        }
        
      },
      requestRent: function() {        
        this.getAxios.post(`/book/directRent/${this.bookId}`, {}, this.requestHeader)
        .then(res => {
          if (res.data.message === '이미 대여된 책입니다' || res.data.message === '한 권만 대여할 수 있습니다.' || res.data.message === '등록되지 않은 도서번호 입니다.') {
            this.statusColor = 'red'
            this.statusIcon = 'fa-exclamation-triangle'
          }
          else {
            this.statusColor = 'green'
            this.statusIcon = 'fa-check-circle'
            this.$store.dispatch('insertUserBook', res.data.book)
          }
          this.message = res.data.message
        })
        .catch(err => {
          this.statusColor = 'red'
          this.statusIcon = 'fa-exclamation-triangle'
          this.message = e.response.data.message
        })
      },
      requestReturn: function() {
        this.getAxios.post(`/book/directReturn/${this.bookId}`, {}, this.requestHeader)
        .then(res => {
          if (res.data.message === '이미 반납된 책입니다' || res.data.message === '대여한 책이 없습니다' || res.data.message === '대여한 책이 아닙니다' || res.data.message === '등록되지 않은 도서번호 입니다.') {
            this.statusColor = 'red'
            this.statusIcon = 'fa-exclamation-triangle'
          }
          else {
            this.statusColor = 'green'
            this.statusIcon = 'fa-check-circle'
            this.$store.dispatch('insertUserBook')
          }
          this.message = res.data.message
        })
        .catch(err => {
          this.statusColor = 'red'
          this.statusIcon = 'fa-exclamation-triangle'
          this.message = e.response.data.message
        })
      },
      cancelBookRent() {
        this.message = '도서의 일련번호를 입력해주세요'
        this.bookId = ''
        this.statusColor = 'blue'
        this.statusIcon = 'fa-info-circle'
        this.numberRentDialog = false
      },
      getImg() {
        if (this.user.userImg != null && this.user.userImg != "") {
            this.userImg = "https://i02b206.p.ssafy.io/image" + this.user.userImg
          //this.userImg = "http://localhost:8080/image/" + this.user.userImg
        }
        else {
          this.userImg = this.userDefaultImg
        }
      },
      logout() {
        this.$store.dispatch('logout')
        this.$router.push('/')
      },
    },
    mounted() {
      this.getImg()
    }
  }
</script>