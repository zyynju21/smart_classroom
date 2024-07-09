<template>
	<view class="">
		<cu-custom bgColor="bg-gradual-blue" :isBack="true">
			<view @click="goBack()" slot="backText">返回</view>
			<view slot="content">设置</view>
		</cu-custom>
		<view style="padding: 0 20upx;padding-top: 60upx;position: relative;">
			<view class="text-center shadow">

				<view class="px-4" style="margin-top: 10rpx;">
					<block>
						<view class="cu-form-group mt-3 shadow-warp">
							<view class="title">
								<text class="cuIcon-people margin-right-xs"></text>
								账号:
							</view>
							<input disabled placeholder="请输入账号" v-model="userinfo.loginName" />
						</view>
						<view class="cu-form-group mt-3 shadow-warp">
							<view class="title">
								<text class="cuIcon-vipcard margin-right-xs"></text>
								姓名:
							</view>
							<input placeholder="请输入姓名" v-model="userinfo.userName" />
						</view>
						<!-- <view class="cu-form-group margin-top shadow-warp">
							<view class="title">
								<text class="cuIcon-lock margin-right-xs"></text>
								修改密码:
							</view>
							<input placeholder="请输入密码" :password="!showPassword" v-model="password" />
							<view class="">
								<text :class="showPassword?'cuIcon-attention':'cuIcon-attentionforbid'"
									@click="changePassword"></text>
							</view>
						</view> -->
						<!-- <view class="cu-form-group mt-3 shadow-warp">
							<view class="title">
								<text class="cuIcon-friendfill margin-right-xs"></text>
								性别:
							</view>
							<switch class='switch-sex' @change="SwitchC" :class="switchC?'checked':''"
								:checked="switchC?true:false"></switch>
						</view> -->
						<view class="cu-form-group mt-3 shadow-warp">
							<view class="title">
								<text class="cuIcon-phone margin-right-xs"></text>
								手机号:
							</view>
							<input placeholder="请输入手机号" v-model="userinfo.phone" />
						</view>
						<view class="mt-3 p-5 d-flex j-center">
							<view class="mr-5">
								<button @tap="goBack()" class="cu-btn line-blue lg shadow w-100">
									<text space="emsp">{{' 取消 '}}</text>
								</button>
							</view>
							<view class="">
								<button @tap="updateData()" class="cu-btn bg-blue lg shadow w-100">
									<text space="emsp">{{' 修改 '}}</text>
								</button>
							</view>
						</view>
					</block>

				</view>
			</view>
		</view>

	</view>

</template>

<script>
	import {
		ACCESS_TOKEN,
		USER_NAME,
		USER_INFO
	} from "@/common/util/constants"
	import {
		mapActions
	} from 'vuex'
	export default {
		data() {
			return {
				switchC: false,
				showPassword: false, //true：密文
				loading: false, //false:非登录，true:登录中
				userId: '',
				userinfo: {
					userId:'',
					loginName: '',
					userName: '',
					phone: '',
					post: '',
					userSex: 0,
				},

			}
		},
		onLoad() {
			let userInfo = uni.getStorageSync(USER_INFO)
			this.userinfo.userId=userInfo.userId
			this.userinfo.loginName = userInfo.loginName
			this.userinfo.userName = userInfo.userName
			this.userinfo.phone = userInfo.phone
		},

		methods: {
      ...mapActions(["Logout"]),
			//返回
			goBack() {
				this.$Router.push({
					name: 'index',
					params: {
						PageCur: 'about'
					}
				})
			},

			//密文显示
			changePassword() {
				/* this.showPassword = !this.showPassword */
			},
			SwitchC(e) {
				this.switchC = e.detail.value
				if (this.switchC) {
					this.userinfo.userSex = 0
				} else {
					this.userinfo.userSex = 1
				}
			},

			//修改设置
			updateData() {
				this.$http.put("/system/user/update", this.userinfo).then(res => {
					if (res.data.code==='200') {
						this.$tip.success("修改成功,请重新登录!")
            this.Logout()
            this.$Router.replaceAll({
              name: "login"
            })
						return
					}
					this.$tip.success(res.data.msg)
				})
			}

		}
	}
</script>

<style>

</style>
