<template>
  <v-container>
    <v-flex sm8 offset-sm-2>
      <v-tabs
        background-color="white"
        color="blue lighten-2"
        left
      > 
        <v-tab class="font-weight-bold">공지사항</v-tab>
        <v-tab class="font-weight-bold">Q&A</v-tab>
        <v-tab class="font-weight-bold">FAQ</v-tab>

        <v-tab-item>
          <Notice @noticeNew="noticeNew" :key="noticeComponentKey"></Notice>
        </v-tab-item>
        <v-tab-item>
          <QandA @qnaNew="qnaNew" :key="qnaComponentKey"></QandA>
        </v-tab-item>
        <v-tab-item>
          <FAQ @faqNew="faqNew" :key="faqComponentKey"></FAQ>
        </v-tab-item>
      </v-tabs>
    </v-flex>
  </v-container>
</template>

<script>
  import Notice from '../components/Notice'
  import QandA from '../components/QandA'
  import FAQ from '../components/FAQ'
  export default {
    name: 'NoticePage',
    components: {
      Notice, QandA, FAQ,
    },
    data() {
      return {
        noticeComponentKey: 0,
        qnaComponentKey: 1,
        faqComponentKey: 2,
        componentKey: 3,
      }
    },
    methods: {
      noticeNew(noticeNew) {
        if (noticeNew) {
          this.noticeComponentKey = this.componentKey
          this.componentKey++
        }
      },
      qnaNew(qnaNew) {
        if (qnaNew) {
          this.qnaComponentKey = this.componentKey
          this.componentKey++
        }
      },
      faqNew(faqNew) {
        if (faqNew) {
          this.faqComponentKey = this.componentKey
          this.componentKey++
        }
      },
    }
    
  }
</script>