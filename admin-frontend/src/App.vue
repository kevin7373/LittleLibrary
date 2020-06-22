<template>
  <v-app id="inspire">
    <div class="app-container" v-if="$route.name !== 'login' && $route.name !== 'index'">
      <Header />
    </div>
    <v-content style="background-color: #f5f5f5">
      <div :style="checkloggedIn"></div>
      <router-view :key="$route.fullPath" />
    </v-content>
    <div v-if="$route.name !== 'login' && $route.name !== 'index'">
      <Footer />
    </div>
  </v-app>
</template>

<script>
import store from "./store";
import Header from "./components/Header";
import Footer from "./components/Footer";
import router from "./router";

export default {
  name: "App",
  store,
  components: {
    Header,
    Footer
  },
  data() {
    return {};
  },
  computed: {
    checkloggedIn: function() {
      if (this.$store.getters.isLoggedIn && this.$store.getters.isAdmin) {
        return "display: none;";
      } else {
        this.$router.push("/admin/login").catch(err => {});
      }
    }
  }
};
</script>
