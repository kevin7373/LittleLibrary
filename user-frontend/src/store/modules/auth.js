import jwt_decode from 'jwt-decode'
import axios from "axios"
import store from '..'


const axiosURL = axios.create({
  // baseURL: "http://52.79.100.152:8080/api/"
  // baseURL: "http://192.168.31.72:8080/api/"
   baseURL: "https://i02b206.p.ssafy.io/api/"
  // baseURL: "http://172.19.66.244:8080/api/"
  // baseURL: "http://localhost:8080/api/"

})

axiosURL.interceptors.response.use(function (response) {
  // Do something with response data
  if (response.data.msg === "Token Expired") {

    store.dispatch("logout")
  }
  return response;
}, function (error) {
  // Do something with response error
  return Promise.reject(error);
})

const state = {
  token: null,
  user: null,
  userBook: null,
  URL: axiosURL ? axiosURL || null : null,
}

// data(state)를 변경하지 않음
// data를 원본 그대로 혹은 가공된 데이터를 사용
const getters = {
  isLoggedIn: function(state) {
    return state.token ? true : false
  },
  requestHeader: function(state) {
    return {
      headers: {
        token: state.token
      }
    }
  },
  user: function(state) {
    return state.user ? state.user : null
  },
  getAxios: function(state) {
    return state.URL ? state.URL : null
  },
  getUserBook: function(state) {
    return state.userBook ? state.userBook : null
  }
}

// 상태(토큰)을 받아와서 state를 update
const mutations = {
  setToken: function(state, token) {
    state.token = token
  },
  setUser: function(state, token) {
    state.user = token ? jwt_decode(token).User : token
  },
  setUserBook: function(state, userBook) {
    state.userBook = userBook
  },
}

// 비동기 로직(axios 로 django 서버에 로그인 / 로그아웃 요청)
// options
// action 에서 사용할 수 있도록 만든 객체 / vuex에서 제공하는 actions 함수에서 사용할 수 있는 option 들이 있는 객체
const actions = {
  // commit은 첫번째 인자로 mutations에 정의한 함수를 받는다.
  // 두번째 인자로 토큰을 받아서, mutations에 정의된 함수를 통해 state를 변경한다.
  login: function(options, token) {
    options.commit('setToken', token)
    options.commit('setUser', token)
  },
  // 로그아웃의 경우 추가로 받는 인자는 없고 token의 상태를 null로 변경한다.
  logout: function(options) {
    options.commit('setToken')
    options.commit('setUser')
    options.commit('setUserBook')
  },
  insertUserBook: function(options, userBook) {
    options.commit('setUserBook', userBook)
  }
}

export default {
  state,
  mutations,
  actions,
  getters,
}