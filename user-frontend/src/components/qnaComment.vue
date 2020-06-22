<template>
  <div style="width:100%">
    <v-divider></v-divider>
    <v-list subheader>
     
      <v-list-item
        v-for="comment in comments"
        :key="comment.qnaCommentId"
      >
        <v-list-item-content>
          <v-list-item-subtitle>{{ comment.qnaCommentWriter }} | {{ moment(comment.qnaCommentDate).format("YY.MM.DD") }}</v-list-item-subtitle>
          <v-list-item-title class="caption" v-text="comment.qnaCommentContent"></v-list-item-title>
        </v-list-item-content>
        <div v-if="checkuser(comment)">
          <v-icon
            small
            class="mr-2"
            @click="editCommentButton(comment)"
          >
            edit
          </v-icon>
          <v-icon
            small
            @click="deleteCommentButton(comment)"
          >
            delete
          </v-icon>
        </div>
      </v-list-item>
    </v-list>
    <form @submit.prevent="commentSubmit">
      <v-text-field
        v-model="qnaCommentContent"
        label="댓글을 작성해주세요"
        maxlength="200"
        color="black"
        required
      ></v-text-field>
      <div v-if="editCheck" class="d-flex justify-end">
        <v-btn type="submit" color="light-blue darken-1" class="mt-3 ml-2 white--text">수정</v-btn>
        <v-btn @click="cancel()" color="black" class="mt-3 ml-2 white--text">취소</v-btn>
      </div>
      <div v-else class="d-flex justify-end">
        <v-btn type="submit"
                tile
                depressed
                  dense
                  block
                  min-width="120px"
                  height="50px"
                  color="blue"
                  dark
                  class="ma-0 font-weight-bold"
                  
                >등록</v-btn>
        
      </div>      
    </form>
    <v-dialog v-model="deleteDialog" max-width="300px">
      <v-card>
        <v-container>
          <v-card-title>정말 삭제하시겠습니까?</v-card-title>
          <v-card-text>
            
          </v-card-text>
          <div class="d-flex justify-end">
            <form @submit.prevent="deleteComment">
              <v-btn type="submit" color="light-blue darken-1" class="mt-3 ml-2 white--text">확인</v-btn>
              <v-btn class="mt-3 ml-2 white--text" color="black" @click="cancel()">취소</v-btn>
            </form>
          </div>
        </v-container>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { mdiCommentMultipleOutline } from '@mdi/js'
  export default {
    name: 'QnaComment',
    props:['qnaId'],
    data () {
      return {
        qnaCommentContent: '',
        editItem: {},
        deleteDialog: false,
        moment: require('moment'),
        commentheaders: [
          { text: '댓글 내용', sortable: false, value: 'qnaCommentContent' },
          { text: '작성일', sortable: false, value: 'qnaCommentDate' },
          { text: '작성자', sortable: false, value: 'qnaCommentWriter' },          
          { text: 'Actions', sortable: false, value: 'action' },
        ],
        comments: [],
        commentCheck: false,
        editCheck: false,
        icons: {
          mdiCommentMultipleOutline
        }
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
      close() {
        this.$emit('close')
      },
      checkuser(item) {
        if (this.user.userId === item.qnaCommentWriter) {
          return true
        }
      },
      commentSubmit() {        
        if (this.editCheck) {
          if (this.checkuser(this.editItem)) {
            this.getAxios.post(`/qna/${this.qnaId}/update/${this.editItem.qnaCommentId}`, {
              qnaCommentContent: this.qnaCommentContent
            }, this.requestHeader)
            .then(res => {
              this.editItem.qnaCommentContent = this.qnaCommentContent
              const index = this.comments.indexOf(this.editItem)
              Object.assign(this.comments[index], this.editItem)
              this.editItem = {}
              this.qnaCommentContent = ''
            })
            .catch(err => {
              console.log(err)
            })
          }          
          this.editCheck = false          
        }
        else {
          this.getAxios.post(`/qna/${this.qnaId}/register`, {
            qnaCommentContent: this.qnaCommentContent
          }, this.requestHeader)
          .then(res => {
            this.commentCheck = true
            this.$emit('commentCheck', this.commentCheck)
            this.commentCheck = false
          })
          .catch(err => {
            console.log(err)
          })
        }
      },
      editCommentButton(item) {
        this.qnaCommentContent = item.qnaCommentContent
        this.editItem = item
        this.editCheck = true
      },
      deleteCommentButton(item) {
        this.deleteDialog = true
        this.editItem = item
      },
      deleteComment() {
        if (this.checkuser(this.editItem)) {
          this.getAxios.post(`/qna/${this.qnaId}/delete/${this.editItem.qnaCommentId}`,
          this.requestHeader)
          .then(res => {
            const index = this.comments.indexOf(this.editItem)
            this.comments.splice(index, 1)
            this.editItem = {}
          })
          .catch(err => {
            console.log(err)
          })
          this.deleteDialog = false
        }
      },
      cancel() {
        this.editItem = {}
        this.editCheck = false
        this.qnaCommentContent = ''
        this.deleteDialog = false
      },
      init() {
        this.getAxios.get(`/qna/${this.qnaId}/list`, this.requestHeader)
        .then(res => {
          this.comments = res.data.qnaCommentList
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

<style>

</style>