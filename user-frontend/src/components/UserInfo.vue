<template>
  <v-layout>
    <v-flex>
      <div class="text-center">
      </div>
      <v-card outlined tile class="mx-auto my-10" max-width="600">
        <div v-if="!checkpwInput" class="pa-5">
          <form @submit.prevent="requestPassword">
            <v-text-field 
              class="input-field col s12 validate"
              v-model="userPw"
              label="비밀번호를 입력해 주세요."
              required
              dense
              solo
              color="grey"
              :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
              :type="show ? 'text' : 'password'"
              hint="At least 8 characters"
              prepend-icon="lock"
              counter
              @click:append="show = !show"
              outlined
            >
            </v-text-field>
            <v-card-text class="red--text text-center pt-0" v-if="errMessage">{{ errMessage }}</v-card-text>
            <div class="d-flex justify-end">
              <v-btn tile depressed type="submit" class="white--text" color="blue lighten-1">확인</v-btn>
            </div>
          </form>
        </div>
        <div v-else>
          <form ref="form" @submit.prevent="updateInfo" class="ma-5">
            <v-row>
              <v-col cols="12" sm="6" class="pb-0">
                <v-text-field prepend-icon="fa-id-card" v-model="userInfo.userId" readonly label="ID" outlined>
                </v-text-field>
              </v-col>
              <v-col cols="12" sm="6" class="pb-0">
                <v-text-field prepend-icon="fa-user" v-model="userInfo.userName" label="Name" outlined disabled>
                </v-text-field>
              </v-col>
              <v-col cols="12" sm="6" class="pb-0">
                <v-text-field prepend-icon="mail" v-model="userInfo.email" label="sub-Email" outlined>
                </v-text-field>
              </v-col>
              <v-col cols="12" sm="6" class="pb-0">
                <v-text-field prepend-icon="phone" v-model="phone" label="Phone Number" outlined>
                </v-text-field>
              </v-col>
              <v-col cols="12" sm="6" class="pb-0">
                <v-text-field prepend-icon="people" v-model="userInfo.studentId" readonly label="Student Id" outlined>
                </v-text-field>
              </v-col>
              <v-col cols="12" sm="6">
                <v-select prepend-icon="school" class="pt-0" v-model="userInfo.classId" :items="classes" label="반을 선택하세요." chips hint="What is your class"
                  persistent-hint item-text="name" item-value="classId"></v-select>
              </v-col>
              <v-col cols="12" sm="6">
                <v-select prepend-icon="fa-globe" class="pt-0" v-model="userInfo.cityId" :items="states" label="지역을 선택하세요." chips hint="What is your regions"
                  persistent-hint item-text="name" item-value="cityId"></v-select>
              </v-col>
              <v-col cols="12" sm="6">
                <v-select prepend-icon="fa-code" required class="pt-0" v-model="userInfo.categoryId" :items="categories" label="분야를 선택하세요." chips multiple
                  hint="What is your interesting" persistent-hint item-text="name" item-value="categoryId">
                </v-select>
              </v-col>
              <p class="mb-1 red--text text-center" style="width: 100%">{{ message }}</p>
              <v-layout class="text-center mb-5">
                <v-btn tile depressed color="blue lighten-1" class="white--text ml-2" @click="passwordDialog = true">비밀번호 변경</v-btn>
                <v-spacer></v-spacer>
                <v-btn tile depressed type="submit" color="blue lighten-1" class="white--text mr-2" @click="updateInfo()">확인</v-btn>
                <v-btn tile depressed color="black" class="white--text mr-2" @click="$router.go(-1)">
                  취소
                </v-btn>
              </v-layout>
            </v-row>
          </form>
        </div>
      </v-card>
    </v-flex>
    <v-dialog persistent v-model="passwordDialog" width="400px">
      <v-card color="grey lighten-3">
        <v-container>
          <v-card-title class="pt-0 pb-2 black--text">
          비밀번호 변경
          </v-card-title>
          <form @submit.prevent="changePassword">          
            <v-card outlined>
              <v-card-text>
                <v-text-field
                  prepend-icon="lock"
                  :type="show ? 'text' : 'password'"
                  @click:append="show = !show"
                  v-model="newPassword"
                  label="새 비밀번호"
                  hide-details
                  required
                  outlined
                >
                </v-text-field>
              </v-card-text>
              <v-card-text class="pb-0">
                <v-text-field
                  prepend-icon="lock"
                  :type="show ? 'text' : 'password'"
                  v-model="confirmPassword"
                  @click:append="show = !show"
                  label="새 비밀번호 확인"
                  required
                  outlined
                  :rules="[rules.samePassword]"
                >
                </v-text-field>
              </v-card-text>
            </v-card>
            <div class="mt-3 d-flex justify-end">
              <v-btn type="submit" color="blue lighten-1" class="mr-3 white--text">확인</v-btn>
              <v-btn class="white--text black" @click="closeDialog()">취소</v-btn>
            </div>
          </form>
        </v-container>   
      </v-card>
    </v-dialog>
  </v-layout>
</template>

<script>
  import {
    mapGetters
  } from 'vuex'
  export default {
    name: "UserInfo",
    data() {
      return {
        valid: true,
        passwordDialog: false,
        show: false,
        newPassword: '',
        confirmPassword: '',
        userInfo: [],
        phone: '',
        userPw: '',
        show: false,
        checkpwInput: false,
        message: '',
        errMessage: '',
        rules: {
          samePassword: value => {
            return this.newPassword === value ? true : '비밀번호가 일치하지 않습니다.'
          },
        },
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
        states: [
          {
            name: '서울',
            cityId: '0'
          },
          {
            name: '광주',
            cityId: '3'
          },
          {
            name: '구미',
            cityId: '2'
          },
          {
            name: '대전',
            cityId: '1'
          },
        ],
        classes: [
          {name: '1반', classId: '0'},
          {name: '2반', classId: '1'},
          {name: '3반', classId: '2'},
          {name: '4반', classId: '3'},
        ],
      }
    },
    computed: {
      ...mapGetters([
        'requestHeader',
        'user',
        'getAxios',
      ]),
      userInfoInit() {
        this.userInfo = this.user
        if (this.user.email == "" || this.user.email == null) {
          this.userInfo.email = this.user.userId
        }
      },
    },
    methods: {
      updateInfo() {
        const userName = this.userInfo.userName
        const userId = this.userInfo.userId
        const email = this.userInfo.email
        const phone = this.phone
        const studentId = this.userInfo.studentId
        const classId = this.userInfo.classId
        const cityId = this.userInfo.cityId
        const categoryId = this.userInfo.categoryId
        if (!userId || !email || !userName || !phone || !studentId || !cityId || categoryId.length === 0) {
          this.message = '빈칸을 채우세요.'
          return false
        }
        this.getAxios.post(`/user/updateInfo`, {
          userId: userId,
          userPw: this.userPw,
          email: email,
          userName: userName,
          phone: phone,
          studentId: studentId,
          classId: classId,
          cityId: cityId,
          categoryId: categoryId
        })
        .then(res => {
          this.$store.dispatch('login', res.headers.token)
          this.$router.push("/main").catch(err => {})
        })
        .catch(err => {
          console.log(err)
        })
      },
      requestPassword() {
        this.getAxios.post('user/check/password', {
          userPw: this.userPw
        }, this.requestHeader)
        .then(res => {
          if (res.data.message === "비밀번호가 틀렸습니다") {
            this.errMessage = res.data.message            
          }
          else {
            this.phone = res.data.message
            this.checkpwInput = true
          }          
        })
        .catch(err => {
          console.log(err)
        })
      },
      changePassword() {
        if (this.newPassword !== this.confirmPassword) {
          return false
        }
        this.getAxios.post('user/update/password', {
          userPw: this.userPw,
          userNewPw: this.newPassword
        }, this.requestHeader)
        .then(res => {
          if (res.data.message === "비밀번호가 변경되었습니다") {
            this.$store.dispatch("logout")
          }
          this.errMessage = res.data.message
        })
        .catch(err => {
          console.log(err)
        })
      },
      closeDialog() {
        this.userPw = ''
        this.newPassword = ''
        this.confirmPassword = ''
        this.errMessage = ''
        this.passwordDialog = false
      },
    },
    mounted() {
      this.userInfoInit
    },
  }
</script>