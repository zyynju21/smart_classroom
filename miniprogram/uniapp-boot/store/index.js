import Vue from 'vue'
import Vuex from 'vuex'
import api from "@/api/api"
import {
	ACCESS_TOKEN,
	USER_NAME,
	USER_INFO
} from "@/common/util/constants"

Vue.use(Vuex)

export default new Vuex.Store({
	state: {
		token: '',
		userid: '',
		username: '',
		phone: '',
		welcome: '',
		avatar: ''
	},
	mutations: {
		SET_TOKEN: (state, token) => {
			state.token = token
		},
		SET_NAME: (state, {
			userid,
			username,
			phone
		}) => {
			state.userid = userid
			state.username = username
			state.phone = phone
		},
		SET_AVATAR: (state, avatar) => {
			state.avatar = avatar
		}
	},
	actions: {
		// 登录
		mLogin({
			commit
		}, userInfo) {
			return new Promise((resolve, reject) => {
				api.login(userInfo).then(response => {
					if (response.data.code == 200) {
						const result = response.data
						const userInfo = result.data
						uni.setStorageSync(ACCESS_TOKEN, userInfo.accessToken);
						uni.setStorageSync(USER_INFO, userInfo);
						commit('SET_TOKEN', userInfo.accessToken)
						commit('SET_AVATAR', userInfo.headImgUrl)
						commit('SET_NAME', {
							userid: userInfo.userId,
							username: userInfo.userName,
							phone: userInfo.phone
						})
						resolve(response)
					} else {
						resolve(response)
					}
				}).catch(error => {
					console.log("catch===>response")
					reject(error)
				})
			})
		},
		//手机号登录
		PhoneLogin({
			commit
		}, userInfo) {
			return new Promise((resolve, reject) => {
				api.phoneNoLogin(userInfo).then(response => {
					if (response.data.code == 200) {
						const result = response.data.result
						const userInfo = result.userInfo
						uni.setStorageSync(ACCESS_TOKEN, result.token);
						uni.setStorageSync(USER_INFO, userInfo);
						commit('SET_TOKEN', result.token)
						commit('SET_NAME', {
							username: userInfo.username,
							realname: userInfo.realname
						})
						commit('SET_AVATAR', userInfo.avatar)
						resolve(response)
					} else {
						reject(response)
					}
				}).catch(error => {
					reject(error)
				})
			})
		},
		// 退出
		Logout({
			commit,
			state
		}) {
			return new Promise((resolve) => {
				let logoutToken = state.token;
				commit('SET_TOKEN', '')
				uni.removeStorageSync(ACCESS_TOKEN)
				/* api.logout(logoutToken).then(() => {
				  resolve()
				}).catch(() => {
				  resolve()
				}) */
			})
		},

	},
	getters: {
		token: state => state.token,
		username: state => {
			state.userid = uni.getStorageSync(USER_INFO).username;
			return state.username
		},
		nickname: state => {
			state.userid = uni.getStorageSync(USER_INFO).realname;
			return state.user.realname
		},
		avatar: state => {
			state.userid = uni.getStorageSync(USER_INFO).avatar;
			return state.user.avatar
		},
		userid: state => {
			state.userid = uni.getStorageSync(USER_INFO).userId;
			return state.userid
		},
	}
})
