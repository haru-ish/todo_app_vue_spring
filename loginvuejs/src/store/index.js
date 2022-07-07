import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from 'vuex-persistedstate'

Vue.use(Vuex)

const store = new Vuex.Store({
  state () {
    return {
      token: '',
      username: ''
    }
  },
  mutations: {
    saveToken (state, token) {
      state.token = token
    },
    removeToken (state) {
      state.token = ''
    },
    saveUserName (state, username) {
      state.username = username
    },
    removeUserName (state) {
      state.username = ''
    }
  },
  actions: {
    saveToken ({commit}, token) {
      commit('saveToken', token)
    },
    removeToken ({commit}) {
      commit('removeToken')
    },
    saveUserName ({commit}, username) {
      commit('saveUserName', username)
    },
    removeUserName ({commit}) {
      commit('removeUserName')
    }
  },
  getters: {
    getToken (state) {
      return state.token
    },
    getUserName (state) {
      return state.username
    }
  },
  plugins: [createPersistedState(
    {
      key: 'loginTodo',
      Storage: window.sessionStorage
    }
  )]
})

export default store
