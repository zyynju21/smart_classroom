import Vue from 'vue'
import { login, getInfo, logout, refreshToken, changePassword } from '@/api/login'
import { ACCESS_TOKEN, TOKEN_INFO } from '@/store/mutation-types'
import { welcome } from '@/utils/util'

const user = {
  state: {
    token: '',
    name: '',
    welcome: '',
    avatar: '',
    roles: [],
    info: {}
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_NAME: (state, { name, welcome }) => {
      state.name = name
      state.welcome = welcome
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    },
    SET_INFO: (state, info) => {
      state.info = info
    }
  },

  actions: {
    // 登录
    Login ({ commit }, userInfo) {
      return new Promise((resolve, reject) => {
        login(userInfo).then(response => {
          const result = response.data
          // console.log(result)
          Vue.ls.set(ACCESS_TOKEN, result.accessToken)
          Vue.ls.set(TOKEN_INFO, result)
          commit('SET_TOKEN', result.accessToken)
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 获取用户信息
    GetInfo ({ commit }) {
      return new Promise((resolve, reject) => {
        getInfo().then(response => {
          const result = response.data
          if (result.loginName && result.loginName.length > 0) {
            const loginId = result.loginName
            commit('SET_ROLES', loginId)
            commit('SET_INFO', result)
          } else {
            reject(new Error('getInfo: roles must be a non-null array !'))
          }
          commit('SET_NAME', { name: result.loginName, welcome: welcome() })
          // console.log('SET_AVATAR:' + process.env.VUE_APP_API_BASE_URL + '/' + result.headImgUrl)
          commit('SET_AVATAR', process.env.VUE_APP_API_BASE_URL + '/' + result.headImgUrl)
          resolve(response)
          // if (result.role && result.role.permissions.length > 0) {
          //   const role = result.role
          //   role.permissions = result.role.permissions
          //   role.permissions.map(per => {
          //     if (per.actionEntitySet != null && per.actionEntitySet.length > 0) {
          //       const action = per.actionEntitySet.map(action => { return action.action })
          //       per.actionList = action
          //     }
          //   })
          //   role.permissionList = role.permissions.map(permission => { return permission.permissionId })
          //   commit('SET_ROLES', result.role)
          //   commit('SET_INFO', result)
          // } else {
          //   reject(new Error('getInfo: roles must be a non-null array !'))
          // }
          //
          // commit('SET_NAME', { name: result.name, welcome: welcome() })
          // commit('SET_AVATAR', result.avatar)
          //
          // resolve(response)
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 登出
    Logout ({ commit, state }) {
      return new Promise((resolve) => {
        logout(state.token).then(() => {
          resolve()
        }).catch(() => {
          resolve()
        }).finally(() => {
          commit('SET_TOKEN', '')
          commit('SET_ROLES', [])
          Vue.ls.remove(ACCESS_TOKEN)
        })
      })
    },

    // 刷新token
    RefreshToken ({ commit, state }) {
      return new Promise((resolve, reject) => {
        // 获取token信息
        const tokenInfo = Vue.ls.get(TOKEN_INFO)
        refreshToken(tokenInfo.refreshToken).then(res => {
          const result = res.data
          Vue.ls.set(ACCESS_TOKEN, result.accessToken)
          Vue.ls.set(TOKEN_INFO, result)
          commit('SET_TOKEN', result.accessToken)
          resolve(res)
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 验证密码
    ChangePassword ({ commit }, userPasswordDTO) {
      return new Promise((resolve, reject) => {
        changePassword(userPasswordDTO).then(response => {
          resolve(response)
        }).catch(error => {
          reject(error)
        })
      })
    }

  }
}

export default user
