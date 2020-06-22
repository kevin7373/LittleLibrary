import Vue from "vue";
import Router from "vue-router";
import LoginPage from "./views/LoginPage.vue";
import MainPage from "./views/MainPage.vue";
import UserMyPage from "./views/UserMyPage.vue";

import BookListPage from "./views/BookListPage.vue";
import BookDetailPage from "./views/BookDetailPage.vue";
import BookEditPage from "./views/BookEditPage.vue";
import BookRentPage from "./views/BookRentPage.vue";
import BookReturnDetailPage from "./views/BookReturnDetailPage.vue";
import BookRequirePage from "./views/BookRequirePage.vue";
import BookRequireDetailPage from "./views/BookRequireDetailPage.vue";
import BookRegisterPage from "./views/BookRegisterPage.vue";

import BoardNoticePage from "./views/BoardNoticePage.vue";
import BoardNoticeWritePage from "./views/BoardNoticeWritePage.vue";
import BoardNoticeEditPage from "./views/BoardNoticeEditPage.vue";
import BoardFAQPage from "./views/BoardFAQPage.vue";
import BoardFAQWritePage from "./views/BoardFAQWritePage.vue";
import BoardFAQEditPage from "./views/BoardFAQEditPage.vue";
import BoardQNAPage from "./views/BoardQNAPage.vue";
import BoardQNAWritePage from "./views/BoardQNAWritePage.vue";
import BoardQNAEditPage from "./views/BoardQNAEditPage.vue";
import BoardNotWindowPage from "./views/BoardNotWindowPage.vue";

import UserInfoPage from "./views/UserInfoPage.vue";
import UserProfilePage from "./views/UserProfilePage.vue";
import UserRentPage from "./views/UserRentPage.vue";
import UserRequirePage from "./views/UserRequirePage.vue";
import UserExpirePage from "./views/UserExpirePage.vue";

Vue.use(Router);

export default new Router({
    mode: "history",
    base: process.env.BASE_URL,
    routes: [
        {
            path: "/admin",
            name: "index",
            component: LoginPage
        },
        {
            path: "/admin/login",
            name: "login",
            component: LoginPage
        },
        {
            path: "/admin/main",
            name: "main",
            component: MainPage
        },
        {
            path: "/admin/book/list",
            name: "booklist",
            component: BookListPage,
            props: true
        },
        {
            path: "/admin/book/rent",
            name: "bookrent",
            component: BookRentPage
        },
        {
            path: "/admin/book/require",
            name: "bookrequire",
            component: BookRequirePage
        },
        {
            path: "/admin/book/detail",
            name: "bookdetail",
            component: BookDetailPage
        },
        // {
        //     path: "/admin/book/edit",
        //     name: "bookedit",
        //     component: BookEditPage
        // },
        {
            path: "/admin/book/return/detail",
            name: "returnbookdetail",
            component: BookReturnDetailPage
        },
        {
            path: "/admin/book/require/detail",
            name: "reqbookdetail",
            component: BookRequireDetailPage,
        },
        {
            path: "/admin/book/register",
            name: "bookregister",
            component: BookRegisterPage
        },
        {
            path: "/admin/board/notice",
            name: "notice",
            component: BoardNoticePage
        },
        {
            path: "/admin/board/notice/write",
            name: "noticewrite",
            component: BoardNoticeWritePage
        },
        {
            path: "/admin/board/notice/edit",
            name: "noticeedit",
            component: BoardNoticeEditPage,
        },
        {
            path: "/admin/board/faq",
            name: "faq",
            component: BoardFAQPage
        },
        {
            path: "/admin/board/faq/write",
            name: "faqwrite",
            component: BoardFAQWritePage
        },
        {
            path: "/admin/board/faq/edit",
            name: "faqedit",
            component: BoardFAQEditPage,
        },
        {
            path: "/admin/board/qna",
            name: "qna",
            component: BoardQNAPage
        },
        {
            path: "/admin/board/qna/write",
            name: "qnawrite",
            component: BoardQNAWritePage
        },
        {
            path: "/admin/board/qna/edit",
            name: "qnaedit",
            component: BoardQNAEditPage,
        },
        {
            path: "/admin/board/notWindow",
            name: "notWindow",
            component: BoardNotWindowPage
        },
        {
            path: "/admin/user/info",
            name: "userinfo",
            component: UserInfoPage
        },
        {
            path: "/admin/user/profile",
            name: "userprofile",
            component: UserProfilePage
        },
        {
            path: "/admin/user/mypage",
            name: "mypage",
            component: UserMyPage
        },
        {
			path: '/*',
			name: '404error',
			redirect: {name: 'main'}
			
		}
    ]
});