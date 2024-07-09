<template>
	<view>
		<scroll-view scroll-y class="page">
			<!-- 头部logo-->
			<view class="UCenter-bg">
				<image src="../../static/tushu/3.png" style="width: 100%;height: 320rpx;"></image>
			</view>
			<view class="padding flex text-center text-grey bg-white shadow-warp">
				<view class="flex j-center flex-sub flex-direction solid-right animation-slide-top"
					:style="[{animationDelay: '0.2s'}]">
					<!-- <view class="">
						<image :src="this.$config.apiUrl+userinfo.avatar" style="width: 100rpx;height: 100rpx;"></image>
					</view> -->
					<view class="text-xl text-orange">{{userinfo.username}}</view>
					<view class="margin-top-sm"><text class="cuIcon-people"></text> 学生</view>
				</view>

				<view class="flex flex-sub flex-direction animation-slide-top" :style="[{animationDelay: '0.2s'}]">
					<view class="text-xl text-green">{{userinfo.loginName}}</view>
					<view class="margin-top-sm"><text class="cuIcon-news"></text> 账号</view>
				</view>
			</view>
			<!-- 列表list-->
			<view class="cu-list menu card-menu margin-top-xl margin-bottom-xl shadow-lg radius">

				<view @click="orderBtn()" class="cu-item arrow animation-slide-bottom"
					:style="[{animationDelay: '0.1s'}]">
					<view class="d-flex j-sb">
						<text class="cuIcon-time text-cyan mr-2"></text>
						<text class="text-grey">预约状态</text>
					</view>
				</view>

				<view @tap="scan()" class="cu-item arrow animation-slide-bottom" :style="[{animationDelay: '0.3s'}]">
					<view class="d-flex j-sb">
						<text class="cuIcon-scan text-red mr-2"></text>
						<text class="text-grey">扫码签到</text>
					</view>
				</view>
				<view @tap="showDrawer" data-target="DrawerModalR" class="cu-item arrow animation-slide-bottom"
					:style="[{animationDelay: '0.3s'}]">
					<view class="d-flex j-sb">
						<text class="cuIcon-edit text-red mr-2"></text>
						<text class="text-grey">修改密码</text>
					</view>
				</view>
				<!-- 弹窗 -->
				<uni-drawer ref="showRight" mode="right" :mask-click="true">
					<view class="font-md font-weight p-2 main-text-color border-bottom">
						<text class="cuIcon-edit" space="emsp">{{' 修改密码'}}</text>
					</view>
					<view class="p-2">
						<view class="cu-form-group margin-top shadow-warp">
							<view class="title">
								<text class="cuIcon-lock margin-right-xs"></text>
								旧的密码:
							</view>
							<input type="password" placeholder="请输入旧的密码" v-model="userPassword.oldPassword" />
						</view>
						<view class="cu-form-group margin-top shadow-warp">
							<view class="title">
								<text class="cuIcon-people margin-right-xs"></text>
								新的密码:
							</view>
							<input type="password" placeholder="请输入密码" v-model="userPassword.newPassword" />
						</view>
						<view class="cu-form-group margin-top shadow-warp">
							<view class="title">
								<text class="cuIcon-roundcheck margin-right-xs"></text>
								确认密码:
							</view>
							<input type="password" placeholder="请输入密码" v-model="userPassword.confirmPassword" />
						</view>
					</view>
					<view class="p-2">
						<button @click="closeDrawer" class="main-bg-color font-sm" style="color: #FFFFFF;">修改</button>
					</view>
				</uni-drawer>
				<view @tap="setBtn()" class="cu-item arrow animation-slide-bottom" :style="[{animationDelay: '0.3s'}]">
					<view class="d-flex j-sb">
						<text class="cuIcon-settingsfill text-red mr-2"></text>
						<text class="text-grey">设置</text>
					</view>
				</view>

				<view @click="loginOut()" class="cu-item arrow animation-slide-bottom"
					:style="[{animationDelay: '0.3s'}]">
					<view class="d-flex j-sb">
						<text class="cuIcon-exit text-red mr-2"></text>
						<text class="text-grey">退出</text>
					</view>
				</view>


			</view>
			<view class="cu-tabbar-height"></view>
		</scroll-view>
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
	} from "vuex"
	export default {
		name: 'about',
		data() {
			return {
				userPassword:{},
				userinfo: {
					realname: '',
					post: '',
				},
				userId: '',

			}
		},
		created() {
			this.userId = this.$store.getters.userid
			this.load()

		},

		methods: {
			...mapActions(["Logout"]),
			showDrawer() {
				this.$refs.showRight.open();
			},
			//修改密码
			closeDrawer() {
				this.$http.post('/system/user/change_password',this.userPassword).then(res=>{
					console.log(res)
					if(res.data.code==='200'){
						this.$tip.success('修改成功!')
						this.$refs.showRight.close();
					}else{
						this.$tip.success(res.data.msg)
					}
				})
				
			},
			//查询个人信息
			load() {
				let userInfo = uni.getStorageSync(USER_INFO)
				this.userinfo.username=userInfo.userName
				this.userinfo.loginName = userInfo.loginName
			},
			//退出
			loginOut() {
				this.Logout()
				this.$Router.replaceAll({
					name: "login"
				})
			},
			//收藏
			collectBtn() {
				this.$Router.replaceAll({
					name: "collect"
				})
			},
			//预约状态
			orderBtn() {
				this.$Router.push({
					name: "order"
				})
			},
			//扫码签到
			scan() {
				// #ifdef H5
				this.$tip.success("暂不支持")
				// #endif
				// #ifndef H5
				var _this=this
				uni.scanCode({
					success: function(res) {
						_this.$tip.toast("签到成功")
						console.log('条码类型：' + res.scanType);
						console.log('条码内容：' + res.result);
						/* debugger */
						var id=res.result.split(",")[0].split(":")[1]
						var userId=res.result.split(",")[1].split(":")[1]
						var libraryId=res.result.split(",")[2].split(":")[1]
						var seatId=res.result.split(",")[3].split(":")[1]
						_this.$http.put('/wx/orderMange/update',{id:id,userId:userId,orderStatus:1,libraryId:libraryId,seatId:seatId}).then(resdata=>{
							console.log('条码内容：' + res);
							if(resdata.data.code===200){
								_this.$tip.success("签到成功!")
							}
						})
					}
				});
				// #endif

			},
			//设置
			setBtn() {
				this.$Router.push({
					name: "set",
					params: {
						userId: this.userId
					}
				})
			}
		}
	}
</script>

<style>
</style>
