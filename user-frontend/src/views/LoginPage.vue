<template>
  <div v-if="$vuetify.breakpoint.xs">
    <v-card class="d-flex align-center justify-center mobileLoginForm">      
      <b>
        <img src="https://edu.ssafy.com/asset/images/logo.png" width="70px" alt="ssafyLogoImg">
        <h2 class="blue-grey--text text--darken-4">우리만을 위한 쉼터<br>SSAFY 작은도서관</h2>
        <form class="login-page d-flex flex-column" @submit.prevent="login">
          <v-text-field
            v-model="userId"
            label="Email"
            color="red"
            prepend-icon="person"
            required
          ></v-text-field>
          <v-text-field
            v-model="userPw"
            type="password"
            hint="At least 8 characters"
            label="password"
            color="red"
            prepend-icon="lock"
            required
          ></v-text-field>
          <v-btn tile depressed="" type="submit" class="font-weight-bold blue lighten-1">로그인</v-btn>
        </form>
        <v-card-text class="red--text text-center mt-1 pb-0" v-if="errMessage">{{ errMessage }}</v-card-text>
        <v-layout align-center justify-center class="mt-3">
          <router-link to="/signup" class="brown--text text--darken-4">회원이 아니시라면? 회원가입</router-link>
        </v-layout>
      </b>
    </v-card>
  </div>
  <v-container v-else>
    <div class="loginImg">
      <v-card class="d-flex align-center justify-center loginForm">
        <b>
          <img src="https://edu.ssafy.com/asset/images/logo.png" width="100px" alt="loginBackgroundImg">
          <h2 class="blue-grey--text text--darken-4">우리만을 위한 쉼터<br>SSAFY LIBRARY</h2>
          <form class="login-page d-flex flex-column" @submit.prevent="login">
            <v-text-field
              v-model="userId"
              label="Email"
              color="red"
              prepend-icon="person"
              required
            ></v-text-field>
            <v-text-field
              v-model="userPw"
              type="password"
              hint="At least 8 characters"
              label="password"
              color="red"
              prepend-icon="lock"
              required
            ></v-text-field>
            <v-btn tile depressed="" type="submit" class="font-weight-bold blue lighten-1">로그인</v-btn>
          </form>
          <v-card-text class="red--text text-center mt-1 pb-0" v-if="errMessage">{{ errMessage }}</v-card-text>
          <v-layout align-center justify-center class="mt-3">
            <router-link to="/signup" class="brown--text text--darken-4">회원이 아니시라면? 회원가입</router-link>
          </v-layout>
        </b>
      </v-card>
    </div>
  </v-container>
</template>

<script>
import "url-search-params-polyfill";
export default {
	name: "LoginPage",
	components: {
	},
  data() {
    return {
      userId: "",
      userPw: "",
      status: "",
      token: "",
      errMessage: "",
    };
  },
  computed: {
    getAxios: function() {
      return this.$store.getters.getAxios
    },
  },
  methods: {
    login() {
      this.getAxios.post("user/signin", {
        userId: this.userId,
        userPw: this.userPw
      })
      .then(res => {
        this.$store.dispatch("login", res.headers["token"])
        this.$router.push("/main")
      })
      .catch(e => {
        this.errMessage = e.response.data.message
      });
    },
  },
  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (vm.$store.getters.isLoggedIn) {
        next({ path:"/main" })
      }
      else {
        next()
      }
    })
  }
};
</script>
<style scoped>
table {
    border-collapse: collapse;
    width: 100%;
    word-break: break-all;
}
th {
    width: 50px;
}
td,
th {
    border: 1px solid black;
}
.loginImg {
  background-image: url(https://images.unsplash.com/photo-1524995997946-a1c2e315a42f?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1050&q=80);
  height: 100vh;
  background-position: right;
  z-index: 1;
}
.loginImg>.loginForm {
  width: 50%;
  height: 95vh;
}
.mobileLoginForm {
  height: 100vh;
}
</style>