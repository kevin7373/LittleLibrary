<template>
  <v-card class="widget-card" style="width: 100%;">
    <v-card-text class="pa-0 !important">
      <v-container class="pa-0 !important">
        <div class="layout row ma-0 !important" :style="{ backgroundColor: color }">
          <div class="sm4 xs4 flex">
            <div class="layout column ma-0 justify-center text-left">
              <v-icon size="76px" color="white" style="opacity: 0.8;">
                {{ icon }}
              </v-icon>
            </div>
          </div>
          <div class="layout column ma-0 justify-center text-left" style="color: white;">
            <div class="headline">{{ title }}</div>
            <span class="caption">{{ supTitle }}</span>
          </div>
        </div>
      </v-container>
    </v-card-text>
  </v-card>
</template>

<script>
export default {
  name:'widget',
  props: {
    supTitle: {
      type: String,
      required: false
    },
    subTitle: {
      type: String,
      required: false
    },
    title: {
      type: String,
      required: false
    },
    icon: {
      type: String,
      required: false
    },
    color: {
      type: String,
      required: false
    },
    iconColor: {
      type: String,
      required: false
    },
    path: {
      type: String,
      required: false
    },
    param: {
      type: String,
      required: false
    }
  },
  data() {
    return {};
  },
  methods: {
    go() {
      this.$router.push({ name: "userprofile", params: { userId: "" } });
    }
  }
};
</script>

<style>
/* .widget-card {
  box-shadow: 1px 1px 1px 1px rgba(0, 0, 0, 0.21);
  background-color: transparent;
} */
</style>
