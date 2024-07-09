import { axios } from '@/utils/request'

const userApi = {
  Login: '/system/login',
  Logout: '/system/logout',
  ForgePassword: '/auth/forge-password',
  Register: '/auth/register',
  twoStepCode: '/auth/2step-code',
  SendSms: '/account/sms',
  SendSmsErr: '/account/sms_err',
  // get my info
  UserInfo: '/system/get_user_info',
  UserMenu: '/system/get_user_menu_nav',
  RefreshToken: '/user_info/refresh_token',
  ChangePassword: '/system/user/change_password'
}

/**
 * login func
 * parameter: {
 *     username: '',
 *     password: '',
 *     remember_me: true,
 *     captcha: '12345'
 * }
 * @param parameter
 * @returns {*}
 */
export function login (parameter) {
  return axios({
    url: axios.defaults.baseURL + `${userApi.Login}`,
    data: {
      loginName: parameter.username,
      loginPass: parameter.password
    },
    method: 'post',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function getSmsCaptcha (parameter) {
  return axios({
    url: userApi.SendSms,
    method: 'post',
    data: parameter
  })
}

export function getInfo () {
  return axios({
    url: axios.defaults.baseURL + `${userApi.UserInfo}`,
    method: 'post',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function getCurrentUserNav () {
  return axios({
    url: axios.defaults.baseURL + `${userApi.UserMenu}`,
    method: 'post',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function logout () {
  return axios({
    url: userApi.Logout,
    method: 'post',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function refreshToken (parameter) {
  return axios({
    url: `${userApi.RefreshToken}?refreshToken=${parameter}`,
    method: 'post',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * get user 2step code open?
 * @param parameter {*}
 */
export function get2step (parameter) {
  return axios({
    url: userApi.twoStepCode,
    method: 'post',
    data: parameter
  })
}

/**
 * 修改密码
 * @param {*} parameter
 */
export function changePassword (parameter) {
  return axios({
    url: axios.defaults.baseURL + `${userApi.ChangePassword}`,
    method: 'post',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}
