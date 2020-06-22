<template>
  <v-container>
    <v-flex sm8 offset-sm-2>
      <v-expansion-panels
        focusable
        hover
        inset
      >
        <v-expansion-panel>
          <v-expansion-panel-header color="blue" class="white--text">
            <div class="d-flex justify-space-between">
              <p class="mb-0 font-weight-bold headline">서비스 소개</p><v-icon style="width:36px;height:36px; color: white">{{ icons.mdiChevronDown }}</v-icon>
            </div>
          </v-expansion-panel-header>
          <v-expansion-panel-content>
            <p class="mb-0 pt-4">본 서비스는 2020년 02월 SSAFY 대전 2기 DEVENGERS가 제작한 서비스이며 SSAFY학생들의 자기주도학습 장려를 목표로 합니다. SSAFY는 각 교육장에 작은 도서관을 마련하여 교육생들의 학습을 장려하고 있습니다. 기존의 오프라인 서비스를 온라인 서비스화하는 방법을 사용하고 있으며 작은 도서관 관련한 SSAFY의 정책을 반영하여 서비스를 제공합니다.</p>          
          </v-expansion-panel-content>
        </v-expansion-panel>
        <v-expansion-panel>
          <v-expansion-panel-header color="green" class="white--text">
            <div class="d-flex justify-space-between">
              <p class="mb-0 font-weight-bold headline">서비스 장소</p><v-icon style="width:36px;height:36px; color: white">{{ icons.mdiChevronDown }}</v-icon>
            </div>
          </v-expansion-panel-header>
          <v-expansion-panel-content>
            <v-container>
              <KakaoMap></KakaoMap>
              <p class="mb-0 pt-4"><v-icon class="mr-2" style="color: green">{{ icons.mdiMapMarker }}</v-icon>대전광역시 유성구 동서대로 98-39 삼성화재 유성연수원</p>
            </v-container>
          </v-expansion-panel-content>
        </v-expansion-panel>
        <v-expansion-panel>
          <v-expansion-panel-header color="purple" class="white--text">
            <div class="d-flex justify-space-between">
              <p class="mb-0 font-weight-bold headline">제작한 사람들</p><v-icon style="width:36px;height:36px; color: white">{{ icons.mdiChevronDown }}</v-icon>
            </div>
          </v-expansion-panel-header>
          <v-expansion-panel-content class="pt-4">
            <v-row class="d-flex justify-center">
              <v-col :cols="changeCols">
                <v-card>
                  <v-img
                    src="../assets/jungju.jpg"
                    max-height="200px"
                    alt="creatorJungjuProfileImg"
                  ></v-img>
                  <v-card-title>
                    박정주
                  </v-card-title>
                  <v-card-subtitle>
                    Full stack
                  </v-card-subtitle>
                </v-card>
              </v-col>
              <v-col :cols="changeCols">
                <v-card>
                  <v-img
                    src="../assets/yongjae.png"
                    max-height="200px"
                    max-width="200px"
                    alt="creatorYongjaeProfileImg"
                  ></v-img>
                  <v-card-title>
                    서용재
                  </v-card-title>
                  <v-card-subtitle>
                    Back-end
                  </v-card-subtitle>
                </v-card>
              </v-col>
              <v-col :cols="changeCols">
                <v-card>
                  <v-img
                    src="../assets/sungjin.jpg"
                    max-height="200px"
                    alt="creatorSungjinProfileImg"
                  ></v-img>
                  <v-card-title>
                    박성진
                  </v-card-title>
                  <v-card-subtitle>
                    Front-end & 배포
                  </v-card-subtitle>
                </v-card>
              </v-col>
              <v-col :cols="changeCols">
                <v-card>
                  <v-img
                    src="../assets/gyeongsu.jpg"
                    max-height="200px"
                    alt="creatorGyeongsuProfileImg"
                  ></v-img>
                  <v-card-title>
                    손경수
                  </v-card-title>
                  <v-card-subtitle>
                    Back-end & DB
                  </v-card-subtitle>
                </v-card>
              </v-col>
              <v-col :cols="changeCols">
                <v-card>
                  <v-img
                    src="../assets/byoungkwon.png"
                    max-height="200px"
                    alt="creatorByoungkwonProfileImg"
                  ></v-img>
                  <v-card-title>
                    고병권
                  </v-card-title>
                  <v-card-subtitle>
                    Front-end
                  </v-card-subtitle>
                </v-card>
              </v-col>
            </v-row>          
          </v-expansion-panel-content>
        </v-expansion-panel>
        <v-expansion-panel>
          <v-expansion-panel-header color="red" class="white--text">
            <div class="d-flex justify-space-between">
              <p class="mb-0 font-weight-bold headline">참고사이트</p><v-icon style="width:36px;height:36px; color: white">{{ icons.mdiChevronDown }}</v-icon>
            </div>
          </v-expansion-panel-header>
          <v-expansion-panel-content>
            <v-list
              three-line
              rounded
            >
              <v-list-item-group color="primary">
                <v-list-item
                  v-for="(site, i) in relativeSite"
                  :key="i"
                  @click="siteMove(site)"
                >
                  <v-list-item-content>
                    <v-list-item-title class="text-center font-weight-bold">{{ site.name }}</v-list-item-title>
                    <v-list-item-subtitle class="text-center">{{ site.link }}</v-list-item-subtitle>
                  </v-list-item-content>
                </v-list-item>
              </v-list-item-group>
            </v-list>
          </v-expansion-panel-content>
        </v-expansion-panel>
      </v-expansion-panels>
    </v-flex>
  </v-container>
</template>

<script>
  import KakaoMap from "../components/KakaoMap"
  import {
    mdiChevronDown,
    mdiMapMarker
  } from '@mdi/js'
  export default {
    name: "InformationPage",
    components: {
      KakaoMap
    },
    data() {
      return {
        relativeSite: [
          { name: "EDU SSAFY", link: "http://edu.ssafy.com" },
          { name: "밀리의 서재", link: "https://www.millie.co.kr/" },
          { name: "한밭대학교", link: "https://www.hanbat.ac.kr/" },
          { name: "한밭도서관", link: "https://www.daejeon.go.kr/hanbatlibrary/" },
        ],
        icons: {
          mdiChevronDown, mdiMapMarker
        }
      }
    },
    computed: {
      changeCols: function() {
        if (this.$vuetify.breakpoint.smAndUp) {
          return 4
        }
        else {
          return 12
        }
      },
    },
    methods: {
      siteMove(site) {
        location.href= site.link
      }
    },
  }
</script>


<style scoped>

</style>