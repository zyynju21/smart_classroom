import Vue from 'vue'
import axios from 'axios'
import store from '@/store'
import notification from 'ant-design-vue/es/notification'
import message from 'ant-design-vue/es/message'
import { VueAxios } from './axios'
import { ACCESS_TOKEN, TOKEN_INFO } from '@/store/mutation-types'
import moment from 'moment'

axios.defaults.timeout = 60000
axios.defaults.baseURL = process.env.VUE_APP_API_BASE_URL

const err = (error) => {
  if (error.response) {
    const data = error.response.data
    const token = Vue.ls.get(ACCESS_TOKEN)
    if (error.response.status === 403) {
      notification.error({
        message: 'Forbidden',
        description: data.message
      })
    }
    if (error.response.status === 401 && !(data.result && data.result.isLogin)) {
      notification.error({
        message: 'Unauthorized',
        description: 'Authorization verification failed'
      })
      if (token) {
        store.dispatch('Logout').then(() => {
          setTimeout(() => {
            window.location.reload()
          }, 1500)
        })
      }
    }
  }
  return Promise.reject(error)
}

/* 是否有请求正在刷新 */
window.isRefreshing = false

/* 被挂起的请求数组 */
let requests = []

/* 判断时间 */
function isTokenExpired (date, minute) {
  if (minute) {
    date = moment(date).subtract(minute, 'minute').format('YYYY-MM-DD HH:mm:ss')
  }
  return date > moment().format('YYYY-MM-DD HH:mm:ss')
}

// request interceptor
axios.interceptors.request.use(config => {
  // token信息
  const token = Vue.ls.get(ACCESS_TOKEN)
  config.headers['_lang'] = globalLanguageList[localStorage.getItem('lang') || 'zh-CN']
  if (token) {
    config.headers['Access_Token'] = token // 让每个请求携带自定义 token 请根据实际情况自行修改
  }
  // 绕过指定接口 登录接口和刷新token等地址
  if (config.url.indexOf('/login') >= 0 ||
    config.url.indexOf('/auth/logout') >= 0 ||
    config.url.indexOf('/user_info/refresh_token') >= 0 ||
    config.url.indexOf('/user_store_access/list_user_store_access_for_box') >= 0 ||
    config.url.indexOf('/user_store_access/user_store_access') >= 0) {
    return config
  }
  // 获取token信息
  const tokenInfo = Vue.ls.get(TOKEN_INFO)
  const refreshTokenExpiresAt = tokenInfo.refreshTokenExpiresAt || '2021-08-27 13:57:54'
  const accessTokenExpiresAt = tokenInfo.accessTokenExpiresAt || '2021-08-27 13:57:54'
  // 如果刷新Token时间失效直接返回登录页面在失效前一分钟退出避免后台失效
  if (!isTokenExpired(refreshTokenExpiresAt, 1)) {
    store.dispatch('Logout').then(() => {
      setTimeout(() => {
        window.location.reload()
      }, 1500)
    })
    return config
  }
  /* 判断Token是否已失效 如果失效调用后端接口刷新Token并更新到Store中在失效前一分钟去刷新 */
  if (!isTokenExpired(accessTokenExpiresAt, 1)) {
    /* 上锁只让一个请求去刷新Token */
    if (!window.isRefreshing) {
      console.log('刷新token中...')
      window.isRefreshing = true
      store.dispatch('RefreshToken').then((res) => {
        window.isRefreshing = false
        console.log('刷新token成功，执行队列')
        // 执行刷新Token
        requests.forEach(cb => cb(res.data.accessToken))
        // 执行完成后，清空队列
        requests = []
      }).catch(() => {
        // 刷新失败 跳转登陆页面
        store.dispatch('Logout').then(() => {
          setTimeout(() => {
            window.location.reload()
          }, 1500)
        })
      })
    }
    // 异步处理 将请求挂起
    const retryOriginalRequest = new Promise((resolve) => {
      requests.push((token) => {
        // 因为config中的token是旧的，所以刷新token后要将新token传进来
        config.headers['Access_Token'] = token
        resolve(config)
      })
    })
    return retryOriginalRequest
  }
  return config
}, err)

// response interceptor
axios.interceptors.response.use((response) => {
  // let errorMsg = ''
  if (response.data.code && response.data.code !== '200' && response.data.code !== '2001') {
    // switch (response.data.code) {
    //   case '50010':
    //     errorMsg = response.data.msg
    //     break
    //   case '500101':
    //     errorMsg = response.data.msg
    //     break
    // }
    // 错误提示
    message.error(response.data.msg, 2)
    return Promise.reject(response.data)
  }
  return response.data
}, err)

const installer = {
  vm: {},
  install (Vue) {
    Vue.use(VueAxios, axios)
  }
}

export {
  installer as VueAxios,
  axios
}

const globalLanguageList = {
  'en-US': 'EN_US',
  'zh-CN': 'ZH_CN'
}
