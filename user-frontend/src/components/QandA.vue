<template>
  <div>
    <v-card-title>
      <b>QnA</b>
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="search"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="qna"
      :items-per-page="7"
      :mobile-breakpoint="100"
      :search="search"
      item-key="qnaId"
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
      <template v-slot:item.qnaTitle="props">
        <button @click="open(props.item)" class="font-weight-bold"> {{ props.item.qnaTitle }}</button>
      </template>
      <template v-slot:item.action="{ item }">
        <div v-if="checkuser(item)" class="d-flex">
          <v-icon
            small
            class="mr-2"
            @click="editqnaButton(item)"
          >
            edit
          </v-icon>
          <v-icon
            small
            @click="deleteqnaButton(item)"
          >
            delete
          </v-icon>
        </div>
      </template>
    </v-data-table>
    <div class="d-flex justify-end mt-2">
      <v-btn color="light-blue darken-1" dark class="mb-2" @click="newqnaButton()">등록</v-btn>
    </div>
    <v-dialog persistent v-model="dialog" width="700px" >
      <v-card tile flat>
            
              <v-flex
                d-flex
                dark
                style="background-color: #3396F4; color: white;font-weight:bolder"
                class="pl-4 pt-3 pb-3"
                
              >Q&A</v-flex>
              <v-card tile style="max-height:75vh;  overflow:auto">
              <v-flex
                d-flex
                class="mt-10 ml-10 mr-10 mb-5"
                style="border-style: solid; border-width: 1px; border-color: #E0E0E0 ;"
              >
                <v-card-title>
                  <v-flex d-flex>
                    <v-flex d-flex class="flex-column" style="line-height:18px">
                      <span class="pl-1 mt-auto mb-auto">
                        <span style="color: #777F88; font-size: 12px;font-family: Malgun Gothic,Georgia,serif;font-weight:bolder;">작성자</span>
                        <span class="pl-1" style="color: black; font-size: 12px">{{ editItem.userId }}</span>
                      </span>
                      <span class="pl-1 mt-auto mb-auto">
                        <span style="color: #777F88; font-size: 12px;font-family: Malgun Gothic,Georgia,serif;font-weight:bolder;">작성일</span>
                        <span class="pl-1" style="color: black; font-size: 12px">{{ editItem.qnaWriteDate }}</span>
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
                <v-card-text style="font-size: 12px;font-family: Malgun Gothic,Georgia,serif;font-weight:bolder;line-height:12px">{{ editItem.qnaTitle }}</v-card-text>
              </v-flex>
              <v-flex
                d-flex
                class="ml-10 mr-10"
                style="border-style: solid; border-width: 1px; border-color: #E0E0E0;"
              >
                <v-card flat style="height: 20vh; overflow: auto; font-size: 12px">
                <v-card-text style="font-size: 12px;font-family: Malgun Gothic,Georgia,serif;font-weight:bolder;">{{ editItem.qnaContent }}</v-card-text>
                </v-card>
              </v-flex>
              <v-flex
                d-flex
                class="ml-10 mr-10 mb-5 mt-5"
                style="border-style: solid; border-width: 0px; "
              >
              
              <qnaComment :qnaId="editItem.qnaId" @close="close()" @commentCheck="commentCheck" :key="componentKey"></qnaComment>
              
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
                  @click="close()"
                >닫기</v-btn>
              </v-flex>
            </v-card>
          </v-card>

      
    </v-dialog>
    <v-dialog v-model="newDialog" max-width="500px">
      <v-card outlined color="white">
        <v-container>          
          <form @submit.prevent="newqna">
            <v-text-field
              v-model="title"
              label="Title"
              required
              maxlength="20"
            ></v-text-field>
            <v-textarea
              outlined
              label="Content"
              v-model="content"
              counter
              required
            ></v-textarea>
            <div class="d-flex justify-end">
              <v-btn type="submit"  color="light-blue darken-1" class="mt-3 ml-2 white--text">확인</v-btn>
              <v-btn class="mt-3 ml-2 white--text" color="black" @click="newqnaClose()">취소</v-btn>
            </div>
          </form>
        </v-container>
      </v-card>
    </v-dialog>
    <v-dialog v-model="editDialog" max-width="500px">
      <v-card>
        <v-container>          
          <form @submit.prevent="editqna">
            <v-text-field
              v-model="title"
              label="Title"
              required
            ></v-text-field>
            <v-textarea
              outlined
              label="Content"
              v-model="content"
              required
            ></v-textarea>
            <div class="d-flex justify-end">
              <v-btn type="submit" color="light-blue darken-1" class="mt-3 ml-2 white--text">확인</v-btn>
              <v-btn class="mt-3 ml-2 white--text" color="black" @click="editqnaClose()">취소</v-btn>
            </div>
          </form>          
        </v-container>
      </v-card>
    </v-dialog>
      <v-dialog v-model="deleteDialog" max-width="300px">
      <v-card>
        <v-container>
          <v-card-title>정말 삭제하시겠습니까?</v-card-title>
          <v-card-text>

          </v-card-text>
          <div class="d-flex justify-end">
            <form @submit.prevent="deleteqna">
              <v-btn type="submit" color="light-blue darken-1" class="mt-3 ml-2 white--text">확인</v-btn>
              <v-btn class="mt-3 ml-2 white--text" color="black" @click="deleteqnaClose()">취소</v-btn>
            </form>
          </div>
        </v-container>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import qnaComment from './qnaComment'
  export default {
    name: 'Qna',
    components: {
      qnaComment
	  },
    data () {
      return {
        search: '',
        title: '',
        content: '',
        qnaNew: false,
        componentKey: 0,
        editItem: {},
        moment: require('moment'),
        dialog: false,
        newDialog: false,
        editDialog: false,
        deleteDialog: false,
        headers: [
          { text: '제목', sortable: false, value: 'qnaTitle'},
          { text: '', sortable: false, value: 'action' },
          { text: '작성자', sortable: false, value: 'userId' },
        ],
        qna: [],
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
      checkuser(item) {
        if (this.user.userId === item.userId) {
          return true
        }
      },
      open (item) {
        this.componentKey++
        this.getAxios.post(`/qna/count/${item.qnaId}`, {}, this.requestHeader)
        .then(res => {
          item.qnaViewCnt++
        })
        .catch(err => {
          console.log(err)
        })
        
        this.editItem = item
        this.dialog = true
      },
      close () {
        this.editItem = {}
        this.dialog = false
        this.content = ''
        this.title = ''
      },
      newqnaButton() {
        this.content = ''
        this.title = ''
        this.newDialog = true
      },
      newqnaClose() {
        this.title = ''
        this.content = ''
        this.newDialog = false
      },
      newqna() {
        this.getAxios.post("/qna/register", {
          qnaTitle: this.title,
          qnaContent: this.content
        }, this.requestHeader)
        .then(res => {
          this.qnaNew = true
          this.$emit('qnaNew', this.qnaNew)
          this.qnaNew = false
          this.title = ''
          this.content = ''
        })
        .catch(err => {
          console.log(err)
        })        
        this.newDialog = false
      },
      editqnaButton (item) {
        if (this.user.userId === item.userId) {
          this.title = item.qnaTitle
          this.content = item.qnaContent
          this.editItem = item
          this.editDialog = true
        }
        else {
          this.editDialog = false
        }
      },
      editqna() {
        this.getAxios.post(`/qna/update/${this.editItem.qnaId}`, {
          qnaTitle: this.title,
          qnaContent: this.content
        }, this.requestHeader)
        .then(res => {
          this.editItem.qnaTitle = this.title
          this.editItem.qnaContent = this.content
          const index = this.qna.indexOf(this.editItem)
          Object.assign(this.qna[index], this.editItem)
          this.editItem = {}
        })
        .catch(err => {
          console.log(err)
        })
        this.editDialog = false
      },
      editqnaClose() {
        this.editItem = {}
        this.title = ''
        this.content = ''
        this.editDialog = false
      },
      deleteqnaButton (item) {
        if (this.user.userId === item.userId) {
          this.editItem = item
          this.deleteDialog = true
        }
        else {
          this.deleteDialog = false
        }
      },
      deleteqna() {
        this.getAxios.post(`/qna/delete/${this.editItem.qnaId}`, {
          
        }, this.requestHeader)
        .then(res => {
          const index = this.qna.indexOf(this.editItem)
          this.qna.splice(index, 1)
          this.editItem = {}
        })
        .catch(err => {
          console.log(err)
        })             
        this.deleteDialog = false   
      },
      deleteqnaClose() {
        this.editItem = {}
        this.deleteDialog = false
      },
      commentCheck(commentCheck) {
        if (commentCheck) {
          this.componentKey++
        }        
      },
      init() {
        this.getAxios.get("/qna/list", this.requestHeader)
        .then(res => {
          this.qna = res.data.qnaList
        })
        .catch(err => {
          console.log(err)
        })
      }
    },
    mounted() {
      this.init()
    }
  }
</script>