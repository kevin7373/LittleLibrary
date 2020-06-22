<template>
  <v-footer
    white
    padless
  >
    <v-card
      flat
      tile
      color="black"
      class="white--text text-center"
      width="100%"
    > 
      <v-card-text class="white--text">
        운영 담당 : 이순희 프로님 <v-spacer></v-spacer><v-icon color="white">fa-mobile</v-icon> 010-5095-0324
      </v-card-text>
      <v-card-text class="grey--text text--lighten-1 pt-0">
        평일 운영시간 : 09:00 ~ 18:00
      </v-card-text>
      <v-card-text class="grey--text text--lighten-1 pt-0">
        대전광역시 유성구 동서대로 98-39 삼성화재 유성연수원
      </v-card-text>     
      
      <v-divider color="white"></v-divider>

      <v-card-text class="white--text">
        <p class="mb-0">Copyright by Devengers Co., Ltd. All rights reserved. - {{ new Date().getFullYear() }}</p>
         <v-btn text color="white" to="/information"> about Us</v-btn>
      </v-card-text>
    </v-card>
  </v-footer>
</template>

<script>
  export default {
    name: 'Footer',
    data() {
      return {
      }
    }
  }
</script>