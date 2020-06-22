<template>
     <ul>
      <li v-for="(comment, index) in bookCmntList" :key="comment.bookCommentId">
        {{ index + 1}} | {{ comment.bookCommentContent }} | {{ comment.bookCommentWriter }} | {{ comment.bookCommentDate }}
      </li>
      <form @submit.prevent="regComment">
        <v-text-field type="text" v-model="bookCommentContent" label="BookComment"></v-text-field><v-btn type="submit">입력</v-btn>
      </form>
    </ul>
</template>

<script>
import VClamp from 'vue-clamp'
import { mapGetters } from 'vuex'
const moment = require('moment')

export default {
    name: 'BookReview',
    data () {
        
    },
    props: {
        
    },
    computed: {
      ...mapGetters([
        'requestHeader',
        'user',
        'getAxios',
      ]),
    },
    methods: {
      regComment() {
        this.bookCmntList.push({
            bookCommentContent: this.bookCommentContent, 
            bookCommentWriter: this.user.userName, 
            bookCommentDate: moment().format('YYYY-MM-DD'),
          })

        this.bookCommentContent = ''
        
        this.getAxios.post("/book/regcomment", {
          bookCommentContent: this.bookCommentContent,
          bookISBN: this.BookInfo.bookISBN,
          bookCommentWriter: this.user.userName   

        }, this.requestHeader)
        .then(res => {
          console.log(res)
        })
      },
    },
    components: {
      VClamp
    }
}
</script>