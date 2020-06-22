<template>
  <v-container>
    <v-flex sm8 offset-sm-2>
      <v-card outlined>
        <v-list>
          
          <v-subheader class="font-weight-bold">신청내역 {{ needbooks.length }}건</v-subheader>
          <v-list-item-group color="primary">
            <template v-for="(needbook, idx) in needbooks">
              <v-list-item :key="needbook.reqId" v-if="!needbook.flag">
                <v-card flat tile style="margin: 5% 5% 5% 5%">
                  <v-row justify="center" align="center">
                  <v-card tile :elevation="8">
                  <v-img
                  max-width="80px"
                  max-height="100px"
                  min-width="80px"
                  min-height="100px"
                    c
                    :src="needbook.bookImg"
                    alt="suggestedBookImg"
                  ></v-img>
                  </v-card>
                
                <v-list-item-content class="d-flex justify-center; ml-5 ; mr-5">
                  <v-list-item-title class="black--text">{{needbook.bookName}}</v-list-item-title>
                  <v-list-item-subtitle>{{ needbook.publisher }} | {{ needbook.bookPrice }}원</v-list-item-subtitle>
                  <v-list-item-subtitle>{{ needbook.reqReason }}</v-list-item-subtitle>
                  <v-list-item-subtitle v-if="needbook.have">보유</v-list-item-subtitle>
                  <v-list-item-subtitle v-else>미 보유</v-list-item-subtitle>
                </v-list-item-content>
                  </v-row>
                </v-card>
              </v-list-item>
              <v-divider :key="idx+1000"></v-divider>
            </template>
          </v-list-item-group>
        </v-list>
      </v-card>
    </v-flex>
  </v-container>
</template>

<script>
  import { mapGetters } from 'vuex'
  export default {
    name: "NeedBookList",
    data() {
      return {
        needbooks: [],
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
      needBookListInit() {
        this.getAxios.get('book/request/list', this.requestHeader)
        .then(res => {
          this.needbooks = res.data.reqBooks
        })
        .catch(err => {
          console.log(err)
        })
      }
    },
    mounted() {
      this.needBookListInit()
    }
  }
</script>
