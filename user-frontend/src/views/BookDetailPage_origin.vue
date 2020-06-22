<template>
  <v-container>
    <v-flex sm8 offset-sm-2>
      <v-card flat>
        <div class="step01">
          <p class="headline pt-10 mb-0 pl-5" v-if="$vuetify.breakpoint.smAndUp">{{ bookInfo.bookName }}</p>
          <p class="title pt-10 mb-0 pl-5" v-else>{{ bookInfo.bookName }}</p>
          <p class="subtitle-1 pl-5 mb-12" style="color: rgba(255, 255, 255, 0.8);" v-if="$vuetify.breakpoint.smAndUp">{{ bookInfo.author }} {{ bookInfo.publisher }}</p>
          <p class="caption pl-5 mb-8" style="color: rgba(255, 255, 255, 0.8);" v-else>{{ bookInfo.author }} {{ bookInfo.publisher }}</p>
          <div v-if="$vuetify.breakpoint.smAndUp" class="d-flex justify-space-around">
            <div style="position: relative">
              <div class="count mr-12">
                <p class="mb-1 pa-1">{{ bookInfo.rentCnt }}번 대여</p>
              </div>
              <div class="count mr-12">
                <p class="mb-1 pa-1">{{ bookInfo.rentCnt }}권 대여가능</p>
              </div>
              <div class="count mr-12">
                <p class="mb-0 pa-1">찜</p>
              </div>
            </div>
            <v-card elevation="8">
              <v-img max-width="200" min-width="200" :src="bookInfo.bookImg" alt="bookDetailImg"></v-img>
            </v-card>
          </div>
          <div v-else class="d-flex justify-space-around">
            <div style="position: relative">
              <div class="count mr-12">
                <p class="mb-1 pa-1">{{ bookInfo.rentCnt }}번 대여</p>
              </div>
              <div class="count mr-12">
                <p class="mb-1 pa-1">{{ bookInfo.rentCnt }}권 대여가능</p>
              </div>
              <div class="count mr-12">
                <p class="mb-0 pa-1">찜</p>
              </div>
            </div>
            <v-card elevation="8">
              <v-img max-width="150" min-width="150" :src="bookInfo.bookImg" alt="bookDetailImg"></v-img>
            </v-card>
          </div>
        </div>
        <v-row justify="center">
          <v-col cols="10" class="pa-0">
            <div class="my-10 body-2 font-weight-black" style="line-height: 180%;" v-if="$vuetify.breakpoint.smAndUp">
              <p>{{ description }}</p>
            </div>
            <div class="my-10 caption font-weight-black" style="line-height: 140%;" v-else>
              <p>{{ description }}</p>
            </div>
          </v-col>
        </v-row>
        
        <div class="ma-3">
          <v-card flat>
            <div class="mt-10">
              <p class="mb-0 title" v-if="$vuetify.breakpoint.smAndUp"><v-icon class="mr-1">{{ icons.mdiCommentMultipleOutline }}</v-icon> 책 리뷰</p>
              <p class="mb-0" v-else><v-icon class="mr-1">{{ icons.mdiCommentMultipleOutline }}</v-icon> 책 리뷰</p>
            </div>
            <v-layout>
              <v-flex>
                <v-card flat>
                  <v-container>
                    <v-row class="mr-2">
                      <v-col cols="2" class="d-flex justify-end pr-0">
                        <v-avatar>
                          <img class="" :src="profileImg || userDefaultImg" alt="userProfileImg" style="width: 50px;">
                        </v-avatar>
                      </v-col>
                      <v-col cols="10">
                        <form @submit.prevent="regComment">
                          <v-text-field :class="commentContentSize" v-model="bookCommentContent" :rules="[rules.counter]" label="리뷰를 작성하세요." counter
                            maxlength="200"></v-text-field>
                          <div class="d-flex justify-end" v-if="$vuetify.breakpoint.smAndUp">
                            <v-btn class="white--text" type="submit" color="black">등록</v-btn>
                          </div>
                          <div class="d-flex justify-end" v-else>
                            <v-btn class="white--text" type="submit" small color="black">등록</v-btn>
                          </div>
                        </form>
                      </v-col>
                      <v-col cols="12" class="py-0" v-for="(comment, index) in bookCmntList" :key="index">
                        <v-row>
                          <v-col cols="2" class="d-flex justify-end pr-0">
                            <v-avatar>
                              
                              <img class="" :src="setImgUrl(comment.userImg)" alt="userProfileImg" style="width: 50px;">
                            </v-avatar>
                          </v-col>
                          <v-col cols="10">
                            <div class="d-flex justify-space-between">

                            <h4>{{ comment.userName }}</h4>
                            
                            <h5 class="grey--text pt-1">{{ moment(comment.bookCommentDate).format('YY-MM-DD hh:mm ')}}</h5>
                            </div>
                            <v-card max-height="110" :class="commentContentSize" flat>
                              <v-clamp autoresize :max-lines="4" solo class="pa-3" autofocus :readonly="readonly">
                                {{ comment.bookCommentContent }}
                              </v-clamp>
                            </v-card>
                            <div class="d-flex justify-space-between pa-1">
                              <span class="grey--text body-2">
                              </span>
                              <div v-if="checkuser(comment)" class="d-flex justify-end">
                                <v-icon small @click="openEditDialog(comment)" class="mr-2">
                                  fa-edit
                                </v-icon>
                                <v-icon small @click="deleteCmnt(comment)">
                                  fa-trash
                                </v-icon>
                              </div>
                            </div>
                          </v-col>
                        </v-row>
                      </v-col>
                    </v-row>
                  </v-container>
                </v-card>
              </v-flex>
            </v-layout>
          </v-card>
        </div>
      </v-card>
      <v-dialog persistent v-model="editDialog" width="400px">
        <v-card>
          <v-container>
            <form @submit.prevent="editCmnt">
              <v-text-field
                solo
                v-model="content"
                label="리뷰를 수정하세요."
                counter
                maxlength="200"
              ></v-text-field>
              <div class="d-flex justify-end">
                <v-btn type="submit" color="green darken-1" class="white--text">변경</v-btn>
                <v-btn class="ml-2 white--text black" @click="closeEditDialog()">취소</v-btn>
              </div>
            </form>
          </v-container>
        </v-card>
      </v-dialog>
    </v-flex>
  </v-container>
</template>

<script>
  import VClamp from 'vue-clamp'
  import {
    mdiCommentMultipleOutline,
    mdiBookOpenPageVariant
  } from '@mdi/js'
  import { mapGetters } from 'vuex'

  export default {
    name: "BookDetailPage",
    data() {
      return {
        bookCmntList: [],
        bookInfo: [],
        description: '',
        content: '',
        selectedComment: {},
        bookCommentContent: '',
        bookISBN: '',
        bookCommentWriter: '',
        profileImg: '',
        moment: require('moment'),
        userDefaultImg: 'https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__340.png',
        readonly: true,
        editDialog: false,
        rules: {
          counter: value => value.length <= 200 || 'Max 200 characters',
        },
        icons: {
          mdiCommentMultipleOutline, mdiBookOpenPageVariant
        }
      }
    },
    components: {
      VClamp,
    },
    computed: {
      ...mapGetters([
        'requestHeader',
        'user',
        'getAxios',
      ]),
      commentContentSize: function() {
        if (this.$vuetify.breakpoint.smAndUp) {
          return ''
        }
        else {
          return 'body-2'
        }
      }
    },
    methods: {
      regComment() {
        this.getAxios.post("/book/regcomment", {
          bookCommentContent: this.bookCommentContent,
          bookISBN: this.bookInfo.bookISBN,
          bookCommentWriter: this.user.userId
        }, this.requestHeader)
        .then(res => {
          this.bookCmntList.unshift({
            bookCommentContent: this.bookCommentContent,
            userName: this.user.userName,
            bookCommentDate: this.moment().format('YYYY-MM-DD hh:mm:ss'),
          })
          this.bookCommentContent = ''
        })
        .catch(err => {
          console.log(err)
        })
      },
      checkuser(comment) {
        if (this.user.userName === comment.userName) {
          return true
        }
      },
      openEditDialog(comment) {
        this.editDialog = true
        this.content = comment.bookCommentContent
        this.selectedComment = comment
      },
      editCmnt() {
        this.getAxios.post(`/book/update/${this.selectedComment.bookCommentId}`, {
          bookCommentCont,
          ent: this.content,
          bookISBN: this.bookInfo.bookISBN,
          bookCommentWriter: this.user.userId
        }, this.requestHeader)
          .then(res => {
            this.selectedComment.bookCommentContent = this.content
            const index = this.bookCmntList.indexOf(this.selectedComment)
            Object.assign(this.bookCmntList[index], this.selectedComment)
            this.closeEditDialog()
          })
          .catch(err => {
            console.log(err)
          })
      },
      closeEditDialog() {
        this.selectedComment = {}
        this.content = ''
        this.editDialog = false
      },
      deleteCmnt(comment) {
        this.getAxios.post(`/book/comment/delete/${comment.bookCommentId}`, {}, this.requestHeader)
          .then(res => {
            const index = this.bookCmntList.indexOf(comment)
            this.bookCmntList.splice(index, 1)
          })
          .catch(err => {
            console.log(err)
          })
      },
      getImg() {
        if (this.user.userImg != null && this.user.userImg != "") {
            this.profileImg = "https://i02b206.p.ssafy.io/image" + this.user.userImg
          //this.profileImg = "http://localhost:8080/image/" + this.user.userImg
        }
        else {
          this.profileImg = this.userDefaultImg
        }
      },
      setImgUrl(userImg){
        if(userImg == null || userImg == ""){
          return this.userDefaultImg
        } 
        else {
          return 'https://i02b206.p.ssafy.io/image' + userImg
        }

      },
      leftbook(){
        this.getAxios.get(`/book/rentable/count/${this.$route.query.bookISBN}`, this.requestHeader)
      }
    },
    created() {
      this.getAxios.get(`/book/detail/${this.$route.query.bookISBN}`, this.requestHeader)
        .then(res => {
          console.log(res)
          this.bookInfo = res.data.book
          let str = res.data.description
          str = str.replace("&nbsp;", " ")
          str = str.replace("&lt;", "<")
          str = str.replace("&gt;", ">")
          str = str.replace("&quot;", '"')
          str = str.replace("&#39;", "'")
          str = str.replace("&#039;", "'")
          str = str.replace("&amp;", '&')
          this.description = str
          this.bookCmntList = res.data.bookCmntList.reverse()
        }).catch(err => {
          console.log(err)
        })
    },
    mounted() {
      this.getImg()
    }
  }
</script>

<style scoped>

  .step01 {
    height: 100%;
    position: relative;
  }
 
  .step01:before {
    position: absolute;
    left: 0;
    top: 0;
    width: 100%;
    height: 80%;
    /* background-color: rgb(20, 40, 160); */
    background-color: #3c90e2;
    content: "";
  }

  @media (min-width: 200px) and (max-width: 600px) {
    .step01:before {
      height: 80%;
    }
  }

  .step01 > p {
    position: relative;
    font-size: 200%;
    color: white;
  }

  .count {
    position: relative;
    height: 100%;
    /* color: rgb(20, 40, 160); */
    background-color: #3c90e2;
    background-color: white;
  }

  .count > p {
    color: #3c90e2;
    font-weight: 500;
  }
</style>