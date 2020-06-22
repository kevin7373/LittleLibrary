<template>
  <v-container>
    <v-flex sm8 offset-sm-2>
      <v-tabs
        background-color="white"
        color="blue lighten-2"
        centered
        grow
        v-model="selectedTab"
      >
        <v-tab class="font-weight-bold" href="#all">전체 도서</v-tab>
        <v-tab class="font-weight-bold" href="#rentable">대여 가능 도서</v-tab>
        <v-tab class="font-weight-bold" href="#field">분야별 도서</v-tab>
        
        <v-tab-item id="all"><BookList></BookList></v-tab-item>
        <v-tab-item id="rentable"><RentableBookList></RentableBookList></v-tab-item>
        <v-tab-item id="field"><FieldBookList></FieldBookList></v-tab-item>
        
      </v-tabs>
    </v-flex>
  </v-container>
</template>

<script>
  import BookList from '../components/BookList'
  import RentableBookList from '../components/RentableBookList'
  import FieldBookList from '../components/FieldBookList'

  export default {
    name: "BookListPage",
    components: {
      BookList, RentableBookList, FieldBookList
    },
    data() {
      return {
        selectedTab: "all",
      }
    },
    methods: {
      checkTab() {
        if (this.$route.query.selectedTab === "2") {
          this.selectedTab = "field"
        }
      }
    },
    mounted() {
      this.checkTab()
    }
  }
</script>

<style scoped>
</style>