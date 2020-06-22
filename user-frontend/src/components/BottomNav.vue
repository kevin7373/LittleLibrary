<template>
  <v-app-bar
    absolute
    bottom
    color="white"
    style="position: fixed; z-index: 10000"
  >
  <v-container class="px-0">
    <v-flex sm8 offset-sm-2 class="d-flex justify-space-between">
      <div v-if="$route.fullPath === '/main'">
        <v-btn to="/main" icon tile color="amber darken-2">
          <div class="d-flex flex-column align-center">
              <v-icon size="24px">fa-home</v-icon>
              <p class="ma-0" v-if="$vuetify.breakpoint.smAndUp">홈</p>
              <p class="ma-0 caption" v-else>홈</p>
          </div>
        </v-btn>
      </div>
      <div v-else>
        <v-btn @click="$router.go(-1)" icon tile color="black">
          <div class="d-flex flex-column align-center">
            <v-icon size="24px">fa-chevron-left</v-icon>
          </div>
        </v-btn>
      </div>
      <div
        v-for="link in links"
        :key="link.title">
        <v-btn :to="link.to" icon tile :color="changeColor(link.to)">
          <div class="d-flex flex-column align-center">
              <v-icon size="24px">{{ link.icon }}</v-icon>
              <p class="ma-0" v-if="$vuetify.breakpoint.smAndUp">{{ link.title }}</p>
              <p class="ma-0 caption" v-else>{{ link.title }}</p>
          </div>
        </v-btn>
      </div>
    </v-flex>
  </v-container>
  </v-app-bar>
</template>

<script>
  export default {
    name: 'BottomNav',
    data() {
      return {
        links: [
          { title: '도서', to: "/booklist", icon: "mdi-book-open-variant"},
          { title: '대여/반납', to: "/scan", icon: "fa-qrcode"},
          { title: '알림', to: "/notice", icon: "mdi-bulletin-board"},
          { title: '관리', to: "/user", icon: "mdi-account-cog-outline"},
        ]
      }
    },
    methods: {
      changeColor(to) {
        if (this.$route.fullPath === to) {
          return 'amber darken-2'
        }
        else {
          return 'black'
        }
      }
    }
  }
</script>

<style scoped>
.v-btn:before {
  background-color: white;
}
</style>