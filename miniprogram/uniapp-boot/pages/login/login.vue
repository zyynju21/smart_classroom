<template>
	<view style="padding: 0 20upx;padding-top: 100upx;position: relative;">
		<view class="text-center shadow mt-5" style="margin-top: 100rpx;">
			<view class="">
				<image src="../../static/tushu.jpg" mode='aspectFit'
					style="width: 350upx;height: 350upx;"></image>
				<view class="text-shadow text-xxl" >
					图书馆座位预约管理系统
				</view>
			</view>
			<view class="px-4" style="margin-top: 10rpx;">
				<block>
					<view class="cu-form-group mt-3 shadow-warp">
						<view class="title">
							<text class="cuIcon-people margin-right-xs"></text>
							账号:
						</view>
						<input style="text-align: start;" placeholder="请输入账号" v-model="userName" />
					</view>
					<view class="cu-form-group margin-top shadow-warp">
						<view class="title">
							<text class="cuIcon-lock margin-right-xs"></text>
							密码:
						</view>
						<input style="text-align: start;" placeholder="请输入密码" :password="!showPassword" v-model="password" />
						<view class="">
							<text :class="showPassword?'cuIcon-attention':'cuIcon-attentionforbid'"
								@click="changePassword"></text>
						</view>
					</view>
					<view class="mt-3 p-5 text-center">
						<button @tap="onLogin()" class="cu-btn bg-blue lg shadow margin-right">
							<text space="emsp">{{loading?'登录中...' :' 登录 '}}</text>
						</button>
						<button @click="onReg()" class="cu-btn line-blue lg shadow margin-right">
							<text space="emsp">{{' 注册 '}}</text>
						</button>
					</view>
					
				</block>
				
			</view>
		</view>
	</view>
</template>

<script>
	import {
		mapActions
	} from 'vuex'
	export default {
		data() {
			return {
				showPassword: false, //true：密文
				loading: false, //false:非登录，true:登录中
				userName: 'admin',
				password: '123',
			}
		},
		onLoad() {

		},
		
		methods: {
			...mapActions(['mLogin']),
			//登录
			onLogin() {
				/* this.$Router.replaceAll({name:'index'}) */
				if (!this.userName || this.userName.length == 0) {
					this.$tip.toast("请填写用户名")
					return;
				}
				if (!this.password || this.password.length == 0) {
					this.$tip.toast("请填写密码")
					return;
				}
				
				this.loading=true
				let loginParams={
					loginName:this.userName,
					loginPass:this.password
				}
				
				this.mLogin(loginParams).then((res)=>{
					this.loading=false
					if(res.data.code==='200'){
						this.$tip.success('登录成功!')
						this.$Router.push({name:'index'})
					}else{
						this.$tip.success(res.data.msg)
					}
				}).catch((err)=>{
					console.log(err)
					let msg=err.data.msg||"请求出现错误,请稍后再试"
					this.loading=false
					this.$tip.success(msg)
				}).finally(()=>{
					this.loading=false
				})
			},
			//注册
			onReg(){
				this.$Router.push({name:'reg'})
			},
			//密文显示
			changePassword() {
				this.showPassword = !this.showPassword
			},
		
		}
	}
</script>

<style>

</style>
