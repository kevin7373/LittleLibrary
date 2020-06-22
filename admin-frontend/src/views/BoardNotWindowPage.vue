<template>
  <v-container fluid grid-list-xl>
    <v-layout row wrap style="background-color: #f5f5f5">
      <v-flex d-flex xs12>
        <v-card width="100%">
          <v-card-title>
            알림함
            <v-spacer></v-spacer>
            <v-text-field
              v-model="keyword"
              append-icon="search"
              label="Search"
              single-line
              hide-details
            ></v-text-field>
          </v-card-title>
          <v-data-table
            v-model="selected"
            item-key="notwindowId"
            :headers="headers"
            :items="notWindowList"
            :search="keyword"
            show-select
            :options="options"
            :footer-props="{
              showFirstLastPage: true,
              firstIcon: 'fa-angle-double-left',
              lastIcon: 'fa-angle-double-right',
              prevIcon: 'fa-chevron-left',
              nextIcon: 'fa-chevron-right',
              'items-per-page-options': [10, 20, 30, 40, lastIndex]
            }"
            :sort-desc="['notWindowId']"
            class="elevation-1"
          >
            <template v-slot:item.data-table-select="{ isSelected, select }">
              <v-simple-checkbox
                :value="isSelected"
                @input="select($event)"
                color="#3396f4"
                :ripple="ripple"
              ></v-simple-checkbox>
            </template>
            <template v-slot:item.title="{ item }">
              <v-flex d-flex class="pl-0 pb-0 text-left" @click="open(item)">{{ item.title }}</v-flex>
              <v-flex d-flex class="pl-0" @click="open(item)">
                <v-avatar>
                  <v-img :src="item.writerImg || userDefaultImg" width="10px" />
                </v-avatar>
                <span class="pl-3 mt-auto mb-auto">
                  {{ item.writerName }}
                  <span
                    v-if="item.writerCity && item.writerClass"
                  >({{ item.writerCity }}{{ item.writerClass }})</span>
                </span>
              </v-flex>
            </template>
            <template v-slot:item.notWriteDate="{ item }">
              <v-flex d-flex class="pl-0 text-left" @click="open(item)">{{ item.notWriteDate }}</v-flex>
            </template>
          </v-data-table>
          <v-dialog v-model="dialog" width="600px">
            <v-card tile>
              <v-flex
                d-flex
                dark
                style="background-color: #3396f4; color: white"
                class="pl-10 pt-5 pb-5"
              >받은알림</v-flex>
              <v-flex
                d-flex
                class="mt-10 ml-10 mr-10 mb-5"
                style="border-style: solid; border-width: 1px; border-color: #eceef1"
              >
                <v-card-title>
                  <v-flex d-flex>
                    <v-avatar class="mt-0">
                      <v-img :src="writerImg || userDefaultImg" width="10px" />
                    </v-avatar>
                    <v-flex d-flex class="flex-column" style="line-height: 22px;">
                      <span class="pl-3 mt-auto mb-auto">
                        <span style="color: #777f88; font-size: 15px">보낸사람</span>
                        <span class="pl-3" style="font-size: 15px">{{ writerName }}</span>
                        <span
                          style="font-size: 15px"
                          v-if="writerCity && writerClass"
                        >({{ writerCity }}{{ writerClass }})</span>
                      </span>
                      <span class="pl-3 mt-auto mb-auto">
                        <span style="color: #777f88; font-size: 15px">보낸날짜</span>
                        <span class="pl-3" style="font-size: 15px">{{ writeDate }}</span>
                      </span>
                    </v-flex>
                  </v-flex>
                </v-card-title>
              </v-flex>
              <v-flex
                d-flex
                class="ml-10 mr-10 mb-5"
                style="border-style: solid; border-width: 1px; border-color: #eceef1; line-height: 22px;"
              >
                <v-card-text>{{ title }}</v-card-text>
              </v-flex>
              <v-flex
                d-flex
                class="ml-10 mr-10"
                style="border-style: solid; border-width: 1px; border-color: #eceef1; line-height: 22px;"
              >
                <v-card-text>{{ content }}</v-card-text>
              </v-flex>
              <v-flex d-flex class="pa-5 justify-center">
                <v-btn
                  min-width="120px"
                  height="50px"
                  color="#4f5a66"
                  dark
                  tile
                  depressed
                  class="ma-0"
                  @click="close()"
                >취소</v-btn>
              </v-flex>
            </v-card>
          </v-dialog>
        </v-card>
      </v-flex>
      <v-flex class="pt-0 text-right">
        <v-btn color="#3396f4" dark tile depressed class="ma-0" @click="selectedDelete()">선택삭제</v-btn>
        <v-btn
          color="#3396f4"
          dark
          tile
          depressed
          class="ml-3 mt-0 mb-0 mr-0"
          @click="selectedAllDelete()"
        >알림함 비우기</v-btn>
      </v-flex>
      <v-dialog v-model="selectedNullDialog" max-width="400">
        <v-card tile style="border-radius: 0px;">
          <v-card-title class="pt-0 pb-0">
            <v-flex
              d-flex
              class="mt-10 pt-0 justify-center"
              style="border-radius: 0px !important; font-size: 15px; font-weight: bold;"
            >선택한 알림이 없습니다.</v-flex>
          </v-card-title>
          <v-card-actions class="pa-0">
            <v-flex d-flex class="mt-11 mb-10 justify-center">
              <v-btn
                min-width="100px"
                height="45px"
                color="#3c90e2"
                dark
                tile
                depressed
                @click="close()"
              >확인</v-btn>
            </v-flex>
          </v-card-actions>
        </v-card>
      </v-dialog>
      <v-dialog v-model="selectedDialog" max-width="420">
        <v-card>
          <v-card-title class="pt-0 pb-0">
            <v-flex
              d-flex
              class="mt-10 pt-0 justify-center text-center"
              style="font-size: 15px; line-height: 22px; font-weight: bold;"
            >선택한 알림을 삭제하시겠습니까?</v-flex>
          </v-card-title>
          <v-card-actions class="pa-0">
            <v-flex d-flex class="mt-11 mb-10 justify-center">
              <v-btn
                min-width="100px"
                height="46px"
                color="#4f5a66"
                dark
                tile
                depressed
                @click="close()"
              >취소</v-btn>
              <v-btn
                min-width="100px"
                height="45px"
                class="ml-3"
                color="#3c90e2"
                dark
                tile
                depressed
                @click="deleteNotWindow()"
              >삭제</v-btn>
            </v-flex>
          </v-card-actions>
        </v-card>
      </v-dialog>
      <v-dialog v-model="selectedAllDialog" max-width="420">
        <v-card>
          <v-card-title class="pt-0 pb-0">
            <v-flex
              d-flex
              class="mt-10 pt-0 justify-center text-center"
              style="font-size: 15px; line-height: 22px; font-weight: bold;"
            >알림을 모두 삭제하시겠습니까?</v-flex>
          </v-card-title>
          <v-card-actions class="pa-0">
            <v-flex d-flex class="mt-11 mb-10 justify-center">
              <v-btn
                min-width="100px"
                height="46px"
                color="#4f5a66"
                dark
                tile
                depressed
                @click="close()"
              >취소</v-btn>
              <v-btn
                min-width="100px"
                height="45px"
                class="ml-3"
                color="#3c90e2"
                dark
                tile
                depressed
                @click="deleteAllNotWindow()"
              >삭제</v-btn>
            </v-flex>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-layout>
  </v-container>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  name: "BoardNotWindowPage",
  components: {},
  computed: {
    ...mapGetters(["requestHeader", "user", "getAxios"])
  },
  data() {
    return {
      notWindowList: [],
      keyword: "",
      param: "",
      selected: [],
      singleSelect: false,
      dialog: false,
      title: "",
      content: "",
      writerName: "",
      writerImg: "",
      writerCity: "",
      writerClass: "",
      writeDate: "",
      lastIndex: Number,
      selectedNullDialog: false,
      selectedDialog: false,
      selectedAllDialog: false,
      userDefaultImg:
        "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__340.png",
      options: {
        itemsPerPage: 20
      },
      ripple: false,
      headers: [
        {
          text: "제목",
          value: "title"
        },
        {
          text: "작성 일자",
          value: "writeDate"
        }
      ]
    };
  },
  watch: {
    selected(e) {
    }
  },
  methods: {
    select() {
      this.selected = [];
    },
    open(item) {
      this.dialog = true;
      this.title = item.title;
      this.content = item.content;
      this.writerName = item.writerName;
      this.writerCity = item.writerCity;
      this.writerClass = item.writerClass;
      this.writerImg = item.writerImg;
      this.writeDate = item.writeDate;
    },
    close() {
      this.dialog = false;
      this.selectedNullDialog = false;
      this.selectedDialog = false;
      this.selectedAllDialog = false;
    },
    selectedDelete() {
      if (this.selected.length <= 0) {
        this.selectedNullDialog = true;
        return;
      }
      this.selectedDialog = true;
    },
    selectedAllDelete() {
      this.selectedAllDialog = true;
    },
    deleteNotWindow() {
      this.getAxios
        .post(`/admin/notwindow/delete`, this.selected, this.requestHeader)
        .then(res => {
          this.selectedDialog = false;
          location.reload();
        })
        .catch(err => {
          console.log(err);
        });
    },
    deleteAllNotWindow() {
      this.getAxios
        .post(`/admin/notwindow/delete`, this.notWindowList, this.requestHeader)
        .then(res => {
          this.selectedAllDialog = false;
          location.reload();
        })
        .catch(err => {
          console.log(err);
        });
    }
  },
  created() {
    this.getAxios
      .get(`/admin/notwindow/list`, this.requestHeader)
      .then(res => {
        this.notWindowList = res.data.notWindowList;
        this.lastIndex = this.notWindowList.length;

        for (let i = 0; i < this.notWindowList.length; i++) {
          if (
            this.notWindowList[i].writerImg == null ||
            this.notWindowList[i].writerImg == ""
          ) {
            continue;
          }
          this.notWindowList[i].writerImg =
            "https://i02b206.p.ssafy.io/image" +
            this.notWindowList[i].writerImg;
        }
      })
      .catch(err => {
        console.log(err);
      });
  }
};
</script>