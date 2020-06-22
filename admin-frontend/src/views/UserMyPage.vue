<template>
  <v-container fluid grid-list-xl>
    <v-layout row wrap style="background-color: #f5f5f5">
      <v-tabs background-color="white" color="#3396f4" left>
        <v-tab class="font-weight-bold" v-for="tab of tabs" :key="tab.index">{{ tab.name }}</v-tab>
        <v-tab-item>
          <UserMyInfo />
        </v-tab-item>
        <v-tab-item>
          <UserUpdatePassword />
        </v-tab-item>
      </v-tabs>
    </v-layout>
  </v-container>
</template>

<script>
import { mapGetters } from "vuex";
import UserMyInfo from "../components/UserMyInfo";
import UserUpdatePassword from "../components/UserUpdatePassword";

export default {
  name: "UserMyPage",
  components: {
    UserMyInfo,
    UserUpdatePassword
  },
  computed: {
    ...mapGetters(["requestHeader", "user", "getAxios"])
  },
  props: ["userId"],
  data() {
    return {
      tabs: [
        {
          index: 0,
          name: "회원정보 수정"
        },
        {
          index: 1,
          name: "비밀번호 변경"
        }
      ]
    };
  },
  methods: {},
  created() {}
};
</script>