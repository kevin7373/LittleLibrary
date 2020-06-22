<template>
  <v-container fluid grid-list-xl>
    <v-layout row wrap style="background-color: #f5f5f5">
      <v-tabs background-color="white" color="#3396f4" left v-model="activeTab">
        <v-tab
          class="font-weight-bold"
          v-for="tab of tabs"
          :key="tab.index"
          @click="setActiveTab(tab.index)"
        >{{ tab.name }}</v-tab>
        <v-tab-item>
          <BookRent />
        </v-tab-item>
        <v-tab-item>
          <BookReturn />
        </v-tab-item>
      </v-tabs>
    </v-layout>
  </v-container>
</template>
<script>
import { mapGetters } from "vuex";
import BookRent from "../components/BookRent";
import BookReturn from "../components/BookReturn";

export default {
  name: "BookRentPage",
  components: {
    BookRent,
    BookReturn
  },
  computed: {
    ...mapGetters(["requestHeader", "user", "getAxios"])
  },
  data() {
    return {
      tabs: [
        {
          index: 0,
          name: "대여"
        },
        {
          index: 1,
          name: "반납"
        }
      ]
    };
  },
  methods: {
    setActiveTab(index) {
      if (index == 0) {
        window.sessionStorage.setItem("activeTab", 0);
      } else {
        window.sessionStorage.setItem("activeTab", 1);
      }
    }
  },
  created() {
    this.activeTab = window.sessionStorage.getItem("activeTab");
    this.activeTab *= 1;
  }
};
</script>