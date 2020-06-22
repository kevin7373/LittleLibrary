<template>
  <div>
    <v-navigation-drawer v-model="drawer" app clipped color="#ffffff">
      <v-list-item>
        <v-list-item-content>
          <v-list-item-title class="title" v-if="cityId == '0' && this.user.userRoleId != 1">서울 관리자</v-list-item-title>
          <v-list-item-title
            class="title"
            v-else-if="cityId == '1' && this.user.userRoleId != 1"
          >대전 관리자</v-list-item-title>
          <v-list-item-title
            class="title"
            v-else-if="cityId == '2' && this.user.userRoleId != 1"
          >구미 관리자</v-list-item-title>
          <v-list-item-title
            class="title"
            v-else-if="cityId == '3' && this.user.userRoleId != 1"
          >광주 관리자</v-list-item-title>
          <v-list-item-title class="title">[{{ userName }}]님</v-list-item-title>
          <v-list-item-subtitle>환영합니다</v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>
      <v-divider></v-divider>
      <v-list dense nav>
        <v-list-item link :to="'/admin/main'">
          <v-list-item-icon>
            <v-icon color="#757575 royalblue--text">mdi-view-dashboard</v-icon>
          </v-list-item-icon>
          <v-list-item-title>대시보드</v-list-item-title>
        </v-list-item>
        <v-list-group
          v-for="item in items"
          :key="item.title"
          link
          v-model="item.active"
          :prepend-icon="item.action"
          no-action
        >
          <template v-slot:activator>
            <v-list-item-content>
              <v-list-item-title v-text="item.title"></v-list-item-title>
            </v-list-item-content>
          </template>

          <v-list-item v-for="subItem in item.items" :key="subItem.title" :to="subItem.path">
            <v-list-item-content>
              <v-list-item-title v-text="subItem.title" @click="removeSessionParam"></v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list-group>
      </v-list>
    </v-navigation-drawer>

    <v-app-bar clipped-left app color="#3396f4" dark>
      <v-app-bar-nav-icon @click.stop="drawer = !drawer" />
      <v-toolbar-title style="width: 300px" class="ml-0 pl-4">
        <span>SSAFY 작은 도서관</span>
      </v-toolbar-title>
      <v-spacer />
      <v-btn icon @click="NotWindowList()">
        <v-icon style="font-size: 35px;">mdi-bell</v-icon>
      </v-btn>
      <v-menu offset-y>
        <template v-slot:activator="{ on }">
          <v-btn v-on="on" icon class="ml-1">
            <v-avatar>
              <v-img :src="userImg || defaultImg" />
            </v-avatar>
          </v-btn>
        </template>
        <v-list>
          <v-list-item
            v-for="(dropItem, index) in dropItems"
            :key="index"
            @click="dropItem.menuAction"
          >
            <v-list-item-title>{{ dropItem.title }}</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
    </v-app-bar>
  </div>
</template>
​
<script>
import { mapGetters } from "vuex";

export default {
  name: "Header",

  data() {
    return {
      userImg: "",
      userRoleId: "",
      userName: "",
      cityId: "",
      defaultImg:
        "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__340.png",
      drawer: this.$vuetify.breakpoint.smAndDown ? false : true,
      dropItems: [
        {
          title: "프로필",
          menuAction: action => {
            window.sessionStorage.removeItem("bookListKeyword");
            window.sessionStorage.removeItem("rentListKeyword");
            window.sessionStorage.removeItem("activeTab");
            window.sessionStorage.removeItem("bookId");
            window.sessionStorage.removeItem("reqId");
            window.sessionStorage.removeItem("bookRentId");
            this.$router.push({
              name: "mypage"
            });
          }
        },
        {
          title: "로그아웃",
          menuAction: action => {
            window.sessionStorage.removeItem("bookListKeyword");
            window.sessionStorage.removeItem("rentListKeyword");
            window.sessionStorage.removeItem("activeTab");
            window.sessionStorage.removeItem("bookId");
            window.sessionStorage.removeItem("reqId");
            window.sessionStorage.removeItem("bookRentId");
            this.logout();
          }
        }
      ],
      items: [
        {
          title: "도서",
          action: "mdi-book-open-variant",
          items: [
            {
              title: "도서 현황",
              path: "/admin/book/list"
            },
            {
              title: "대여 / 반납",
              path: "/admin/book/rent"
            },
            {
              title: "신청 도서",
              path: "/admin/book/require"
            },
            {
              title: "도서 등록",
              path: "/admin/book/register"
            }
          ]
        },
        {
          title: "게시판",
          action: "mdi-bulletin-board",
          items: [
            {
              title: "공지사항",
              path: "/admin/board/notice"
            },
            {
              title: "QnA",
              path: "/admin/board/qna"
            },
            {
              title: "FAQ",
              path: "/admin/board/faq"
            }
          ]
        },
        {
          title: "회원 관리",
          action: "mdi-account",
          items: [
            {
              title: "회원 정보",
              path: "/admin/user/info"
            }
          ]
        }
      ]
    };
  },
  computed: {
    ...mapGetters(["requestHeader", "user", "getAxios"]),
    mobile: function() {
      if (this.$vuetify.breakpoint.xs) {
        return true;
      } else {
        return false;
      }
    }
  },
  methods: {
    logout() {
      this.$store.dispatch("logout");
      this.$router.push("/admin");
    },
    removeSessionParam() {
      window.sessionStorage.removeItem("bookListKeyword");
      window.sessionStorage.removeItem("rentListKeyword");
      window.sessionStorage.removeItem("activeTab");
      window.sessionStorage.removeItem("bookId");
      window.sessionStorage.removeItem("reqId");
      window.sessionStorage.removeItem("bookRentId");
    },
    NotWindowList() {
      window.sessionStorage.removeItem("bookListKeyword");
      window.sessionStorage.removeItem("rentListKeyword");
      window.sessionStorage.removeItem("activeTab");
      window.sessionStorage.removeItem("bookId");
      window.sessionStorage.removeItem("reqId");
      window.sessionStorage.removeItem("bookRentId");
      this.$router.push("/admin/board/notWindow");
    }
  },
  created() {
    if (this.user.userImg != null && this.user.userImg != "") {
      this.userImg = "https://i02b206.p.ssafy.io/image" + this.user.userImg;
    }
    this.userRoleId = this.user.userRoleId;
    this.userName = this.user.userName;
    this.cityId = this.user.cityId;
  }
};
</script>
<style scoped>
.v-list-item {
  color: #1976d2 !important;
}
</style>
