<template>
	<div class="bgimg">
		<v-container fluid>
			<v-layout class="flex-column">
				<div class="text-center ma-5 mb-0">
					<h1 class="white--text">우리만의 쉼터, 작은 도서관 </h1>
					<br>
				</div>
				<v-flex>
					<form @submit.prevent="signup">
					<v-card tile flat
						class="mx-auto"
						max-width="500"
					>
						<v-col cols="12">
						<v-card-text>
							<div class="d-inline-flex justify-space-between" style="width: 100%;">
								<v-text-field 
									class="input-field col s12 validate"
									color="brown"
									v-model="tempId"
									:rules="[rules.required, rules.email]"
									type="text" 
									label="이메일을 입력해 주세요."
									prepend-icon="fa-id-card"
									dense
									required
									outlined
								>
								</v-text-field>
								<v-btn tile depressed class="ml-2 white--text" color="blue lighten-1" @click="duplicatedIdCheck()">중복 체크</v-btn>
							</div>
							<v-text-field 
								class="input-field col s12 validate"
								v-model="user.userPw"
								label="비밀번호를 입력해 주세요.(최소 8자리)"
								required
								dense
								color="brown"
								:append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
								:rules="[rules.required, rules.min]"
								:type="show ? 'text' : 'password'"
								hint="At least 8 characters"
								prepend-icon="lock"
								counter
								@click:append="show = !show"
								outlined
							>
							</v-text-field>
							<v-text-field 
								class="input-field col s12 validate"
								v-model="user.email"
								color="brown"
								dense
								label="sub-Email(이메일을 입력하세요.)"
								:rules="[rules.email]"
								prepend-icon="mail"
								outlined
							> 
							</v-text-field>
						
							<v-text-field 
								class="input-field col s12 validate"
								v-model="user.userName"
								color="brown"
								dense
								label="이름을 입력하세요."
								:rules="[rules.required]"
								prepend-icon="fa-user"
								required
								outlined
							>
							</v-text-field>
						
							<v-text-field 
								class="input-field col s12 validate"
								v-model="tempNum"
								color="brown"
								dense
								label="번호를 입력하세요 ( '-' 제외)"
								:rules="[rules.required, rules.phone]"
								prepend-icon="phone"
								required
								outlined
							>
							</v-text-field>
							
							<div class="d-inline-flex justify-space-between" style="width: 100%;">
								<v-text-field 
									class="input-field col s12 validate"
									v-model="tempStudentId"
									color="brown"
									label="학번을 입력하세요."
									:rules="[rules.required]"
									dense
									prepend-icon="people"
									required
									outlined
								>
								</v-text-field>
								<v-btn tile depressed class="ml-2 white--text" color="blue lighten-1" @click="duplicatedStudentCheck()">중복 체크</v-btn>
							</div>
							<v-select
								v-model="user.classId"
								:items="classes"
								label="반을 선택하세요"
								color="brown"
								dense
								chips
								hint="What is your class"
								:rules="[rules.required]"
								persistent-hint
								prepend-icon="school"
								item-text="name"
								item-value="classId"
								append-icon="fa-sort-down"
								required
								outlined
							></v-select>
							<v-select
								v-model="user.cityId"
								:items="states"
								label="지역을 선택하세요."
								chips
								hint="What is your regions"
								color="brown"
								:rules="[rules.required]"
								dense
								persistent-hint
								prepend-icon="fa-globe"
								item-text="name"
								item-value="cityId"
								append-icon="fa-sort-down"
								required
								outlined
							></v-select>
							<v-select
								v-model="user.categoryId"
								:items="categories"
								label="분야를 선택하세요."
								color="brown"
								chips
								multiple
								prepend-icon="fa-code"
								dense
								hint="What is your interesting"
								persistent-hint
								:rules="[rules.required]"
								append-icon="fa-sort-down"
								item-text="name"
								item-value="categoryId"
								outlined
							></v-select>
						</v-card-text>
						<div class="text-center">
							<p class="red--text">{{ errMessage }}</p>
							<v-btn tile depressed type="submit" color="blue lighten-1" class="white--text mb-3 mr-3" success> 완료</v-btn>
							<router-link to="/" style="text-decoration: none">
								<v-btn tile depressed class="mb-3 white--text" color="black">취소</v-btn>
							</router-link>
						</div>						
					</v-col>
					</v-card>
					</form>
				</v-flex>
			</v-layout>
		</v-container>
		<v-dialog persistent v-model="checkDialog" width="600px">
      <v-card>
        <v-container>
					<v-card-title>
						{{ dupMessage }}
					</v-card-title>
					<v-card-text>
						
					</v-card-text>
          <div class="d-flex justify-end">
            <v-btn tile depressed class="mt-3 ml-2 white--text" color="black" @click="checkDialogClose()">취소</v-btn>
          </div>
        </v-container>   
      </v-card>
    </v-dialog>
	</div>
</template>


<script>

export default {
	name: 'SignupPage',
 	data () {
		return {
			user: {
				userId: '',
				userPw: '',
				email: '',
				userName: '',
				phone: '',
				studentId: '',
				cityId: '',
				classId: '',
				categoryId: [],
			
			},
			tempNum: '',
			show: false,
			rules: {
				required: value => !!value || 'Required.',
				min: v => v.length >= 8 || 'Min 8 characters',
				emailMatch: () => ('The email and password you entered don\'t match'),
				counter: value => value.length <= 20 || 'Max 20 characters',
				email: value => {
					const pattern = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
					return pattern.test(value) || 'Invalid e-mail.'
				},
				phone: value => {
					const pattern = /[0-9]/
					return pattern.test(value) || '숫자만 입력해주세요'
				}
			},
			states: [
				{name: '서울', cityId: '0'},
				{name: '광주', cityId: '3'},
				{name: '구미', cityId: '2'},
				{name: '대전', cityId: '1'},

			],
			categories: [
				{
					name: "웹 / 모바일",
					categoryId: "000"
				},
				{
					name: "머신러닝",
					categoryId: "001"
				},
				{
					name: "DS / 빅데이터",
					categoryId: "002"
				},
				{
					name: "블록체인",
					categoryId: "003"
				},
				{
					name: "IoT",
					categoryId: "004"
				},
				{
					name: "프로그래밍 언어",
					categoryId: "005"
				},
				{
					name: "알고리즘",
					categoryId: "006"
				},
				{
					name: "애자일",
					categoryId: "007"
				},
				{
					name: "면접",
					categoryId: "008"
				},
				{
					name: "CI / CD",
					categoryId: "009"
				},
				{
					name: "VueJS",
					categoryId: "010"
				},
				{
					name: "ReactJS",
					categoryId: "011"
				},
				{
					name: "데이터베이스",
					categoryId: "012"
				},
				{
					name: "기타",
					categoryId: "013"
				}
			],
			classes: [
				{name: '1반', classId: '0'},
				{name: '2반', classId: '1'},
				{name: '3반', classId: '2'},
				{name: '4반', classId: '3'},
			],
			emailCheck: false,
			idCheck: false,
			checkDialog: false,
			studentIdCheck: false,
			tempId: '',
			tempStudentId: '',
			dupMessage: '',
			errMessage: '',
		}
	},
	components: {
		
	},
	computed: {
		getAxios: function() {
      return this.$store.getters.getAxios
    },
	},
	watch: {
		tempNum(val) {
			this.tempNum = this.tempNum.replace(/[^0-9]/g, '')
			this.user.phone = this.tempNum
		},
		tempId(val) {
			this.tempId = this.tempId.replace(/ /, '')
		}
	},
 	methods: {
		duplicatedIdCheck() {
			this.getAxios.post('user/check/', {
				userId: this.tempId
			})
			.then(res => {
				if (res.data.message === '사용 가능한 아이디 입니다') {
					this.user.userId = this.tempId
					this.idCheck = true
				}				
				this.dupMessage = res.data.message
			})
			.catch(err => {
				console.log(err)
			})
			this.checkDialog = true
		},
		duplicatedStudentCheck() {
			this.getAxios.post('user/check/studentId', {
				studentId: this.tempStudentId
			})
			.then(res => {
				if (res.data.message === '신규 등록이 가능합니다.') {
					this.user.studentId = this.tempStudentId
					this.studentIdCheck = true
				}
				this.dupMessage = res.data.message
			})
			.catch(err => {
				console.log(err)
			})
			this.checkDialog = true
		},
		checkDialogClose() {
			this.dupmessage = ''
			this.checkDialog = false
		},
		signup () {
			if (!this.idCheck) {
				this.errMessage = 'Id 중복을 확인해주세요'
			}
			if (!this.studentIdCheck) {
				this.errMessage = 'studentId 중복을 확인해주세요'
			}
			if (this.user.userId !== this.tempId) {
				this.errMessage = 'Id 중복을 다시 확인해주세요'
				this.idCheck = false
				return false
			}
			if (this.user.studentId !== this.tempStudentId) {
				this.errMessage = 'studentId 중복을 다시 확인해주세요'
				this.studentIdCheck = false
				return false
			}
			const userId = this.user.userId
			const userPw = this.user.userPw
			const email = this.user.email
			const userName = this.user.userName
			const phone = this.user.phone
			const studentId = this.user.studentId
			const cityId = this.user.cityId
			const classId = this.user.classId
			const categoryId = this.user.categoryId

			if (!userId || !userPw || !userName || !phone || !studentId || !classId || !cityId) {
				this.errMessage = '필요한 정보를 입력하세요.(아이디, 비밀번호, 이메일)'
				return false
			}
			if (categoryId.length === 0) {
				this.errMessage = '분야를 1개 이상 선택해주세요'
				return false
			}
			this.getAxios.post('user/register' , {
				userId: userId, 
				userPw: userPw,
				email: email,
				userName: userName,
				phone: phone,
				studentId: studentId,
				classId: classId,
				cityId: cityId,
				categoryId: categoryId
			})
			.then(res => {
				this.$router.push('/')
			})
			.catch(e => {
				console.log(e)
			});
		},
	},
	beforeRouteEnter(to, from, next) {
    next(vm => {
      if (vm.$store.getters.isLoggedIn) {
        next({ path:'/main' })
      }
      else {
        next()
      }
    })
  }
}
</script>

<style scoped>
.bgimg {
	background-image: url("https://images.unsplash.com/photo-1504454449875-92cfa39e1315?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1050&q=80");
	background-position: center top;
  background-attachment: fixed;
	background-size: 1100px auto;

}
</style>