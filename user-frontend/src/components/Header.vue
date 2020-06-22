<template>
  <div>
    <v-app-bar
      absolute
      color="white"
      style="position: fixed; z-index: 10000"
    >
      <v-container class="px-0">
      <v-flex sm8 offset-sm-2>
        <div class="d-flex align-center justify-space-between">
          <router-link to="/main" class="px-0 d-flex align-center"><img src="https://edu.ssafy.com/asset/images/logo.png" :style="logoChange" alt="goToHome"></router-link>
          <div class="d-flex align-center">
            <v-btn icon color="black" @click="chatDialog = true">
              <div v-if="$vuetify.breakpoint.smAndUp">
                <v-icon style="width:36px; height:36px;">{{ icons.mdiAccountQuestionOutline }}</v-icon>   
              </div>
              <div v-else>
                <v-icon>{{ icons.mdiAccountQuestionOutline }}</v-icon>
              </div>
            </v-btn>
            <v-btn icon color="black" @click="alarmDialogGet()">
              <div v-if="$vuetify.breakpoint.smAndUp">
                <v-icon style="width:36px; height:36px;">{{ icons.mdiBellOutline }}</v-icon>
              </div>
              <div v-else>
                <v-icon>{{ icons.mdiBellOutline }}</v-icon>
              </div>
            </v-btn>
            <button class="" onclick="location.href='http://edu.ssafy.com/'">
              <div class="link_edu edu_link"><a data-v-61dd7a3d="" href="#none">EDU<br data-v-61dd7a3d="">SSAFY<span data-v-61dd7a3d=""></span></a></div>
            </button>
          </div>
        </div>
      </v-flex>
      </v-container>
    </v-app-bar>
    <v-dialog persistent v-model="chatDialog" max-width="600px">
      <v-card>
        <v-card
          color="grey lighten-2"
          style="height: 60vh"
         
          class="overflow-y-auto"
          ref="botDialog"
        >
          <v-container v-for="(message, idx) in chatMessages" :key="idx">
            <div v-if="message.speaker === 'user'" class="d-flex flex-column align-end">
              <v-icon class="pb-1">{{ icons.mdiAccount }}</v-icon>
              <v-card outlined min-width="50px" max-width="150px" color="blue lighten-3" class="black--text">
                <p class="mb-0 py-2 body-2 text-center">{{ message.msg }}</p>
              </v-card>
            </div>
            <div v-else class="d-flex flex-column">
              <v-icon class="pb-1">{{ icons.mdiRobot }}</v-icon>
              <v-card outlined min-width="50px" max-width="300px" color="white" class="black--text">
                <p class="mb-0 pa-2 body-2" >{{ message.msg }}</p>
                <div v-for="(chatButton, idx) in message.buttons" :key="idx">
                  <div v-if="message.getUrls[idx]" class="pb-1 pl-2">
                    <v-btn @click="chatClickButton(message, chatButton, idx)" color="amber">
                      {{ chatButton }}
                      <v-icon>{{ icons.mdiOpenInNew }}</v-icon>
                    </v-btn>
                  </div>
                  <div v-else class="pb-1 pl-2">
                    <v-btn @click="chatClickButton(message, chatButton, idx)" class="white--text" color="light-blue lighten-1">
                      {{ chatButton }}
                    </v-btn>
                  </div>                  
                </div>    
              </v-card>
            </div>
          </v-container>
        </v-card>
        <v-card>
          <v-container>
              <form @submit.prevent="submitMessage">
                <v-text-field
                  v-model="textInput"
                  label="질문사항을 작성해주세요"
                ></v-text-field>
                <div class="d-flex justify-end">
                  <v-btn tile depressed type="submit" color="blue lighten-1" class="mt-3 ml-2 white--text">확인</v-btn>
                  <v-btn tile depressed class="mt-3 ml-2 white--text" color="black" @click="chatDialogClose()">취소</v-btn>
                </div>            
              </form>
          </v-container>
        </v-card>
      </v-card>
    </v-dialog>
    <v-dialog persistent v-model="alarmDialog" width="600px">
      <v-card flat tile color="blue lighten-1">
        <v-container>
          <v-data-table
            :headers="headers"
            :items="alarmMessages"
            :items-per-page="7"
            :mobile-breakpoint="100"
            item-key="notwindowId"
            class="elevation-1"
            :footer-props="{
              showFirstLastPage: true,
              pageText: '',
              itemsPerPageText: '',
              itemsPerPageOptions: [],
              firstIcon: 'fa-angle-double-left',
              lastIcon: 'fa-angle-double-right',
              prevIcon: 'fa-chevron-left',
              nextIcon: 'fa-chevron-right'
            }"
          >
            <template v-slot:item.title="props">
              <button @click="alarmContentOpen(props.item)" class="font-weight-bold"> {{ props.item.title }}</button>
            </template>
            <template v-slot:item.writeDate="props">
              {{ moment(props.item.writeDate).format("YY.MM.DD") }}
            </template>
          </v-data-table>
          <div class="d-flex justify-end">
            <v-btn tile depressed class="white--text mt-3 ml-2" color="black" @click="alarmDialogClose()">닫기</v-btn>
          </div>
        </v-container>
      </v-card>
    </v-dialog>
    <v-dialog persistent v-model="alarmContentDialog" width="500px">
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
                        <span class="pl-1" style="color: black; font-size: 12px">{{ getItem.writer }}</span>
                      </span>
                      <span class="pl-1 mt-auto mb-auto">
                        <span style="color: #777F88; font-size: 12px;font-family: Malgun Gothic,Georgia,serif;font-weight:bolder;">작성일</span>
                        <span class="pl-1" style="color: black; font-size: 12px">{{ getItem.writeDate }}</span>
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
                <v-card-text style="font-size: 12px;font-family: Malgun Gothic,Georgia,serif;font-weight:bolder;">{{ getItem.title }}</v-card-text>
              </v-flex>
              <v-flex
                d-flex
                class="ml-10 mr-10"
                style="border-style: solid; border-width: 1px; border-color: #E0E0E0;"
              >
                <v-card flat style="height: 20vh; overflow: auto; font-size: 12px">
                <v-card-text style="font-size: 12px; font-family: Malgun Gothic,Georgia,serif;font-weight:bolder;">{{ getItem.content }}</v-card-text>
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
                  @click="alarmContentClose()"
                >닫기</v-btn>
              </v-flex>
            </v-card>
    </v-dialog>
  </div>
</template>
​
<script>
import {
  mdiPencilBoxOutline,
  mdiRobot,
  mdiOpenInNew,
  mdiAccount,
  mdiBellOutline,
  mdiAccountQuestionOutline
} from '@mdi/js'
  export default {
    name: 'Header',
    data() {
      return {
        chatDialog: false,
        textInput: '',
        moment: require('moment'),
        chatMessages: [{
          speaker: 'bot',
          msg: "질문사항이 있으신가요? 해당되는 키워드를 입력해주세요. '대여', 'QR', '직접 대여', 'QR코드 스캔 안됨'",}],
        alarmDialog: false,
        alarmContentDialog: false,
        alarmMessages: [],
        alarmTitle: '',
        alarmContent: '',
        getItem: {},
        headers: [
          { text: '제목', sortable: false, value: 'title'},
          
          { text: '수신일', sortable: false, value: 'writeDate' },
        ],
        icons: {
          mdiPencilBoxOutline,
          mdiRobot,
          mdiOpenInNew,
          mdiAccount,
          mdiBellOutline,
          mdiAccountQuestionOutline
        }
      }
    },
    watch: {
      chatMessages: function() {
        this.$nextTick(function() {
          this.$refs.botDialog.$el.scrollTop = this.$refs.botDialog.$el.scrollHeight
        })
      }
    },
    computed: {
      getAxios: function() {
        return this.$store.getters.getAxios
      },
      requestHeader: function() {
        return this.$store.getters.requestHeader
      },
      logoChange: function() {
        if (this.$vuetify.breakpoint.smAndUp) {
          return "width: 75px; height: 50px;"
        }
        else {
          return "width: 45px; height: 30px;"
        }
      }
    },
    methods: {
      chatDialogClose() {
        this.textInput = ''
        this.chatDialog = false
      },
      submitMessage() {
        if (this.textInput === '') {
          return false
        }
        this.chatMessages.push({speaker: 'user', msg: this.textInput})
        this.getAxios.post("/user/chatbot", {
          desc: this.textInput
        }, this.requestHeader)
        .then(res => {
          const messages = res.data.message
          this.chatMessages.push({speaker: 'bot', msg: messages.desc, buttons: messages.buttonName, getUrls: messages.routeURL})
        })
        .catch(err => {
          console.log(err)
        })
        this.textInput = ''
      },
      chatClickButton(message, chatButton, idx) {
        if (message.getUrls[idx]) {
          this.chatDialogClose()
          this.$router.push(`${message.getUrls}`)
        }
        else {
          this.textInput = chatButton
          this.submitMessage()
        }
      },
      alarmDialogClose() {
        this.alarmTitle = ''
        this.alarmContent = ''
        this.alarmDialog = false
      },
      alarmDialogGet() {
        this.alarmDialog = true
        this.getAxios.get('/notwindow/list', this.requestHeader)
        .then(res => {
          this.alarmMessages = res.data.notWindowList
        })
        .catch(err => {
          console.log(err)
        })
      },
      alarmContentOpen(item) {
        this.getItem = item
        this.alarmContentDialog = true
      },
      alarmContentClose() {
        this.getItem = {}
        this.alarmContentDialog = false
      }
    }
  }
</script>

<style scoped>
a{
  font-family: 'Noto Sans',Dotum,돋움,verdana,sans-serif;
}
a:link, a:active, a:visited{
  text-decoration: none;
  color: black;
}
.eduButton {
  height: 55px;
  width: 80px;
  background-color: rgb(233, 238, 241);
}
hr {
    border: none;
    height: 2.4px;
    width: 55px;
    /* Set the hr color */
    color: #333; /* old IE */
    background-color: #333; /* Modern Browsers */
}
.btnOverOff:before {
  display: none;
}
.btnOverOff:hover:before {
  background-color: transparent;
}
.edu_link{
  display: block;
  width: 100%;
  background: rgb(255, 255, 255);
  text-align: right;
  font-size: 1.7vh;
  border-bottom: 1.7px solid;
  font-weight: 800;
  letter-spacing: 0.6px;
  padding-left: 3px;
}
@media (min-width: 320px) and (max-width: 480px) {
  .edu_link{
    font-size:1.3vh;
    padding-left: 1px;
  }
}
</style>