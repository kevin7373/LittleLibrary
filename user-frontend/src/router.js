import Vue from 'vue'
import Router from 'vue-router'
import MainPage from './views/MainPage.vue'
import LoginPage from './views/LoginPage.vue'
import SignupPage from './views/SignupPage.vue'
import NoticePage from './views/NoticePage.vue'
import InformationPage from './views/InformationPage.vue'
import MybookList from './views/MybookList.vue'
import NeedbookPage from './views/NeedbookPage.vue'
import NeedBookList from './views/NeedBookList.vue'
import UserPage from './views/UserPage.vue'
import BookListPage from './views/BookListPage.vue'
import BookDetailPage from './views/BookDetailPage.vue'
import UserInfo from './components/UserInfo.vue'
import ScanQR from './views/ScanQR.vue'

Vue.use(Router)

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
		{
			path: '/main',
			name: 'main',
			component: MainPage
		},
		{
			path: '/',
			name: 'login',
			component: LoginPage
		},
		{
			path: '/signup',
			name: 'signup',
			component: SignupPage
		},
		{
			path: '/notice',
			name: 'notice',
			component: NoticePage
		},
		{
			path: '/information',
			name: 'information',
			component: InformationPage
		},
		{
			path: '/mybook',
			name: 'mybook',
			component: MybookList
		},
		{
			path: '/needbook',
			name: 'needbook',
			component: NeedbookPage
		},
		{
			path: '/needbooklist',
			name: 'needbooklist',
			component: NeedBookList
		},
		{
			path: '/user',
			name: 'user',
			component: UserPage,
		},
		{
			path: '/userInfo',
			name: 'userInfo',
			component: UserInfo
		},
		{
			path: '/scan',
			name: 'scan',
			component: ScanQR
		},
		{
			path: '/booklist',
			name: 'booklist',
			component: BookListPage,
		},
		{
			path: '/bookdetail',
			name: 'bookdetail',
			component: BookDetailPage,
			props: true,
		},
		{
			path: '/admin/',
			name: 'admin'
		},
		{
			path: '/*',
			name: '404error',
			redirect: {name: 'main'}
			
		}
  ],
})
