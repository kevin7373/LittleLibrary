<template>
  <v-app>
    <v-content>
			<div v-show="$route.name !== 'login' && $route.name !== 'signup' && $route.name !== 'scan'">
				<div style="padding-top: 55px;">
					<Header></Header>
				</div>			
			</div>
			<div :style="checkloggedIn"></div>
			<div style="max-width: 1200px; margin: auto;">
			<router-view :key="$route.fullPath"></router-view>
			</div>
			<div v-show="$route.name !== 'login' && $route.name !== 'signup' && $route.name !== 'scan'">
				<div style="padding-bottom: 55px;">
					<BottomNav></BottomNav>
				</div>
			</div>
    </v-content>
  </v-app>
</template>

<script>
import store from './store'
import Header from './components/Header'
import BottomNav from './components/BottomNav'
import router from './router'

export default {
	name: 'App',
	store,
	components: {
		Header, BottomNav
  },
	data() {
		return {
		}
	},
	computed: {
		checkloggedIn: function() {
			if (this.$store.getters.isLoggedIn) {
				return 'display: none;'
			}

			else if (this.$route.name == 'admin') {
				this.$router.push('/admin').catch(err => {})
			}

			else if (this.$route.name === 'signup') {
				return 'display: none'
			}
			else {
				this.$router.push('/').catch(err => {})
			}
		},
	}
}
</script>
