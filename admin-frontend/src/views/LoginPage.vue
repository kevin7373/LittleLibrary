<template>
  <div v-if="$vuetify.breakpoint.xs">
    <v-card class="d-flex align-center justify-center mobileLoginForm">
      <b>
        <img src="https://edu.ssafy.com/asset/images/logo.png" width="70px" />
        <h2 class="blue-grey--text text--darken-4 text-center">
          우리만을 위한 쉼터
          <br />SSAFY 작은 도서관 | 관리자
        </h2>
        <form class="login-page d-flex flex-column" @submit.prevent="login">
          <v-text-field v-model="userId" label="Email" color="red" prepend-icon="person" required></v-text-field>
          <v-text-field
            v-model="userPw"
            type="password"
            hint="At least 8 characters"
            label="password"
            color="red"
            prepend-icon="lock"
            required
          ></v-text-field>
          <v-btn tile depressed type="submit" color="#3c90e2" dark>로그인</v-btn>
        </form>
        <v-card-text class="red--text text-center mt-1 pb-0" v-if="errMessage">{{ errMessage }}</v-card-text>
      </b>
    </v-card>
  </div>
  <v-container v-else>
    <div class="loginImg">
      <v-card class="d-flex align-center justify-center loginForm">
        <b>
          <img src="https://edu.ssafy.com/asset/images/logo.png" width="100px" />
          <h2 class="blue-grey--text text--darken-4 text-center">
            우리만을 위한 쉼터
            <br />SSAFY 작은 도서관 | 관리자
          </h2>
          <form class="login-page d-flex flex-column" @submit.prevent="login">
            <v-text-field v-model="userId" label="Email" color="red" prepend-icon="person" required></v-text-field>
            <v-text-field
              v-model="userPw"
              type="password"
              hint="At least 8 characters"
              label="password"
              color="red"
              prepend-icon="lock"
              required
            ></v-text-field>
            <v-btn tile depressed type="submit" color="#3c90e2" dark>로그인</v-btn>
          </form>
          <v-card-text class="red--text text-center mt-1 pb-0" v-if="errMessage">{{ errMessage }}</v-card-text>
        </b>
      </v-card>
    </div>
  </v-container>
</template>

<script>
export default {
  name: "LoginPage",
  components: {},
  data() {
    return {
      userId: "",
      userPw: "",
      status: "",
      token: "",
      errMessage: ""
    };
  },
  computed: {
    getAxios: function() {
      return this.$store.getters.getAxios;
    }
  },
  methods: {
    login() {
      this.getAxios
        .post("/admin/user/signin", {
          userId: this.userId,
          userPw: this.userPw
        })
        .then(res => {
          if (res.data.message == "관리자 권한이 아닙니다") {
            this.errMessage = res.data.message;
            this.userPw = "";
            return;
          } else if (res.data.message == "그런 사람은 없어요~") {
            this.errMessage = res.data.message;
            this.userPw = "";
            return;
          }
          this.$store.dispatch("login", res.headers["token"]);
          this.$router.push("/admin/main").catch(err => {});
        })
        .catch(err => {
          console.log(err);
          this.errMessage = err.response.data.failMsg;
          this.userPw = "";
        });
    }
  },
  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (vm.$store.getters.isLoggedIn) {
        next({ path: "/admin/login" });
      } else {
        next();
      }
    });
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
.loginImg > .loginForm {
  width: 50%;
  height: 95vh;
}
.mobileLoginForm {
  height: 100vh;
}
</style>