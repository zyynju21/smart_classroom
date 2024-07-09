<template>
	<view class="">
		<cu-custom bgColor="bg-gradual-blue" :isBack="true">
			<view @tap="canBtn()" slot="backText">返回</view>
			<view slot="content">注册</view>
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
							<input placeholder="请输入账号" v-model="sysUser.loginName" />
						</view>
						<view class="cu-form-group mt-3 shadow-warp">
							<view class="title">
								<text class="cuIcon-vipcard margin-right-xs"></text>
								姓名:
							</view>

							<input placeholder="请输入姓名" v-model="sysUser.userName" />
						</view>
						<view class="cu-form-group margin-top shadow-warp">
							<view class="title">
								<text class="cuIcon-lock margin-right-xs"></text>
								密码:
							</view>
							<input placeholder="请输入密码" :password="!showPassword" v-model="sysUser.loginPass" />
							<view class="">
								<text :class="showPassword?'cuIcon-attention':'cuIcon-attentionforbid'"
									@click="changePassword"></text>
							</view>
						</view>
						<view class="cu-form-group mt-3 shadow-warp">
							<view class="title">
								<text class="cuIcon-friendfill margin-right-xs"></text>
								性别:
							</view>
							<switch class='switch-sex' @change="SwitchC" :class="switchC?'checked':''"
								:checked="switchC?true:false"></switch>
						</view>
						<view class="cu-form-group mt-3 shadow-warp">
							<view class="title">
								<text class="cuIcon-phone margin-right-xs"></text>
								手机号:
							</view>
							<input placeholder="请输入手机号" v-model="sysUser.phone" />
						</view>
						<view class="mt-3 p-2 d-flex j-center w-100">
							<button @click="regBtn()" class="cu-btn bg-blue lg shadow w-100">
								<text space="emsp">{{' 注册 '}}</text>
							</button>
						</view>
					</block>

				</view>
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
				switchC: false,
				showPassword: false, //true：密文
				loading: false, //false:非登录，true:登录中
				sysUser:{userSex:true},


			}
		},
		onLoad() {

		},

		methods: {
			//注册
			regBtn() {
				this.sysUser.accessTokenValidity=1440
				this.sysUser.refreshTokenValidity=1440
				console.log(this.sysUser)
				if (!this.sysUser.userName || this.sysUser.userName.length == 0) {
					this.$tip.toast("请填写用户名")
					return;
				}
				if (!this.sysUser.loginPass || this.sysUser.loginPass.length == 0) {
					this.$tip.toast("请填写密码")
					return;
				}
				if(this.switchC){
          this.sysUser.userSex="男";
        }else {
          this.sysUser.userSex="女";
        }
				console.log(this.sysUser)
                this.$http.post('/system/user/create',this.sysUser).then(res=>{
					if(res.data.code==='200'){
						this.$tip.success('注册成功!')
						this.$Router.push({name:'login'})
					}else{
						this.$tip.success(res.data.msg)
					}
					console.log(res)
				}).catch(err=>{

				}).finally(()=>{

				})
			},
			//密文显示
			changePassword() {
				this.showPassword = !this.showPassword
			},
			SwitchC(e) {
				this.switchC = e.detail.value
				this.sysUser.userSex=e.detail.value
				console.log(e.detail.value)
			},
			canBtn() {
				this.$Router.replaceAll({
					name: 'login'
				})
			},

		}
	}
</script>

<style>

</style>
