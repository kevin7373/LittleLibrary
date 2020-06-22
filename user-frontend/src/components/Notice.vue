<template>
  <div>
    <v-card-title>
      <b>공지사항</b>
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
      :items="notice"
      :items-per-page="5"
      :search="search"
      :mobile-breakpoint="100"
      item-key="noticeId"
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
      <template v-slot:item.noticeTitle="props">
        <button @click="open(props.item)" class="font-weight-bold"> {{ props.item.noticeTitle }}</button>
      </template>
      <template v-slot:item.noticeWriteDate="props">
        {{ moment(props.item.noticeWriteDate).format("YY.MM.DD") }}
      </template>
    </v-data-table>

    <v-dialog persistent v-model="dialog" width="500px">

      <v-card tile flat>
              <v-flex
                d-flex
                dark
                style="background-color: #3396F4; color: white;font-weight:bolder"
                class="pl-4 pt-3 pb-3"
                
              >공지</v-flex>
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
                        <span class="pl-1" style="color: black; font-size: 12px">{{ editItem.noticeWriteDate }}</span>
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
                <v-card-text style="font-size: 12px;font-family: Malgun Gothic,Georgia,serif;font-weight:bolder;line-height:18px">{{ editItem.noticeTitle }}</v-card-text>
              </v-flex>
              <v-flex
                d-flex
                class="ml-10 mr-10"
                style="border-style: solid; border-width: 1px; border-color: #E0E0E0;"
              >
                <v-card flat style="height: 20vh; overflow: auto;">
                <v-card-text style="font-size: 12px;font-family: Malgun Gothic,Georgia,serif;font-weight:bolder;">{{ editItem.noticeContent }}</v-card-text>
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
import { mdiPencilBoxOutline } from '@mdi/js'
import { mapGetters } from 'vuex'
  export default {
    name: 'Notice',
    data () {
      return {
        search: '',
        editItem: {},
        dialog: false,
        moment: require('moment'),
        headers: [
          {
            text: '제목',
            sortable: false,
            value: 'noticeTitle',
          },
          { text: '작성일', sortable: false, value: 'noticeWriteDate' },
          { text: '조회', sortable: false, value: 'noticeViewCnt' },
        ],
        notice: [],
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
        this.getAxios.post(`/notice/count/${item.noticeId}`, {}, this.requestHeader)
        .then(res => {
          item.noticeViewCnt++
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
        this.getAxios.get("/notice/list", this.requestHeader)
        .then(res => {
          this.notice = res.data.noticeList
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