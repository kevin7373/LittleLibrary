<template>
  <div>
    <v-card-title>
      <b>FAQ</b>
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
      :items="faq"
      :items-per-page="7"
      :search="search"
      :mobile-breakpoint="100"
      item-key="faqId"
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
      <template v-slot:item.faqTitle="props">
        <button @click="open(props.item)" class="font-weight-bold"> {{ props.item.faqTitle }}</button>
      </template>
      <template v-slot:item.faqWriteDate="props">
        {{ moment(props.item.faqWriteDate).format("YY.MM.DD") }}
      </template>
    </v-data-table>
    <v-dialog persistent v-model="dialog" width="500px">
      <v-card tile flat>
              <v-flex
                d-flex
                dark
                style="background-color: #3396F4; color: white;font-weight:bolder"
                class="pl-4 pt-3 pb-3"
                
              >자주 묻는 질문</v-flex>
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
                        <span class="pl-1" style="color: black; font-size: 12px">{{ editItem.userId }}</span>
                      </span>
                      <span class="pl-1 mt-auto mb-auto">
                        <span style="color: #777F88; font-size: 12px;font-family: Malgun Gothic,Georgia,serif;font-weight:bolder;">작성일</span>
                        <span class="pl-1" style="color: black; font-size: 12px">{{ editItem.faqWriteDate }}</span>
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
                <v-card-text style="font-size: 12px;font-family: Malgun Gothic,Georgia,serif;font-weight:bolder;">{{ editItem.faqTitle }}</v-card-text>
              </v-flex>
              <v-flex
                d-flex
                class="ml-10 mr-10"
                style="border-style: solid; border-width: 1px; border-color: #E0E0E0;"
              >
                <v-card flat style="height: 20vh; overflow: auto; font-size: 12px">
                <v-card-text style="font-size: 12px; font-family: Malgun Gothic,Georgia,serif;font-weight:bolder;">{{ editItem.faqContent }}</v-card-text>
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
                  @click="close()"
                >닫기</v-btn>
              </v-flex>
            </v-card>
    </v-dialog>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { mdiPencilBoxOutline } from '@mdi/js'
  export default {
    name: 'Faq',
    data () {
      return {
        search: '',
        editItem: {},
        moment: require('moment'),
        dialog: false,
        headers: [
          {
            text: '제목',
            sortable: false,
            value: 'faqTitle',
          },
          { text: '작성일', sortable: false, value: 'faqWriteDate' },
          { text: '조회', sortable: false, value: 'faqViewCnt' },
        ],
        faq: [],
        icons: {
          mdiPencilBoxOutline
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
      checkuser(item) {
        if (this.user.userId === item.userId) {
          return true
        }
      },
      open (item) {
        this.getAxios.post(`/faq/count/${item.faqId}`, {}, this.requestHeader)
        .then(res => {
          item.faqViewCnt++
        })
        .catch(err => {
          console.log(err)
        })        
        this.editItem = item
        this.dialog = true
      },
      close () {
        this.dialog = false
        this.editItem = {}
      },
      init() {
        this.getAxios.get("/faq/list", this.requestHeader)
        .then(res => {
          this.faq = res.data.faqList
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