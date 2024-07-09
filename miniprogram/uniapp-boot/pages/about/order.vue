<template>
	<view class="">
		<cu-custom bgColor="bg-gradual-pink" :isBack="true">
			<view @click="goBack()" slot="backText">返回</view>
			<view slot="content">预约时间</view>
		</cu-custom>
		<!-- //收藏列表 -->
		<view class="">
			<view class="p-2">
				<text class="cuIcon-favor font-weight">预定状态</text>
			</view>
			<view v-for="(item,index) in planList" class="d-flex border-bottom p-2 j-sb ml-3 mr-3">
				<view class="mr-2">
					<text class="">{{item.libraryName2}}</text>
				</view>
				<view class="">
					<text class="">{{item.planTime}}</text>
					<text @click="canState(item.id,item.seatId,item.libraryId)" class="main-text-color" space="emsp"> {{' [取消]'}}</text>
				</view>
			</view>

			<divider></divider>
			<view class="p-2">
				<text class="cuIcon-title font-weight">历史记录</text>
			</view>
			<view v-for="(item,index) in historyList" class="d-flex border-bottom p-2 j-sb ml-3 mr-3">
				<view class="mr-2">
					<text class="">{{item.libraryName2}}</text>
				</view>
				<view class="">
					<text class="">{{item.planTime}}</text>
				</view>
			</view>
		</view>
	</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				checked: false,
				planTime: '',
				createDate: '',
				historyList: [],
				planList:[],
			}
		},
		onLoad() {
			this.initLibrary()
			this.getHistory()
		},
		methods: {
			//预约状态
			initLibrary() {
				let userId = this.$store.getters.userid
				this.$http.post('/wx/orderMange/list', {
					current: 1,
					size: 16,
					orderStatus: 0,
					userId: userId
				}).then(res => {
					console.log(res)
					if (res.data.code === '200') {
						this.planList = res.data.data.records
					}
				})
			},
			//历史记录
			getHistory() {
				let userId = this.$store.getters.userid
				this.$http.post('/wx/orderMange/list', {
					current: 1,
					size: 16,
					orderStatus: 1,
					userId: userId
				}).then(res => {
					if (res.data.code === '200') {
						this.historyList = res.data.data.records
					}
				})
			},
			//返回
			goBack() {
				this.$Router.push({
					name: 'index',
					params: {
						PageCur: 'about'
					}
				})
			},
			//取消预约
			canState(id,seatId,libraryId){
				
				this.$http.put('/wx/orderMange/update3/',{id:id,seatId:seatId,libraryId:libraryId,orderStatus:1}).then(res=>{
					console.log(res.data)
					if(res.data.code==='200'){
						this.$tip.success('取消预约')
						this.initLibrary()
						this.getHistory()
					}
					
				}).catch(error=>{
					this.$tip.success(error)
					console.log(error)
				})
			}

		}
	}
</script>

<style>
</style>
