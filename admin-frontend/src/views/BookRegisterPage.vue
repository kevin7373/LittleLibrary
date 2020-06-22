<template>
  <v-container fluid grid-list-xl>
    <v-layout row wrap style="background-color: #f5f5f5">
      <v-tabs background-color="white" color="#3396f4" left>
        <v-tab class="font-weight-bold" v-for="tab of tabs" :key="tab.index">{{ tab.name }}</v-tab>
        <v-tab-item>
          <BookRegister />
        </v-tab-item>
        <v-tab-item>
          <BookRegisterFile />
        </v-tab-item>
      </v-tabs>
    </v-layout>
  </v-container>
</template>

<script>
import { mapGetters } from "vuex";
import XLSX from "xlsx";
import BookRegister from "../components/BookRegister";
import BookRegisterFile from "../components/BookRegisterFile";

export default {
  name: "BookRegisterPage",
  components: {
    BookRegister,
    BookRegisterFile
  },
  computed: {
    ...mapGetters(["requestHeader", "user", "getAxios"])
  },
  data() {
    return {
      tabs: [
        {
          index: 0,
          name: "한 권 등록"
        },
        {
          index: 1,
          name: "여러 권 등록"
        }
      ],
    };
  },
  mounted() {
  }
};
</script>