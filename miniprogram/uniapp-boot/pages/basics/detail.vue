<template>
	<view>
		<cu-custom bgColor="bg-gradual-pink" :isBack="true">
			<view @click="backNav()" slot="backText">返回</view>
			<view slot="content">图书馆介绍</view>
		</cu-custom>

		<image src="../../static/tushu/library.png" style="width: 100%;height: 300rpx;"></image>
		<view>
			<view class="d-flex">
				<view class="p-3 line-h-md">
					<view class="font-md font-weight">
						{{libraryType.libraryName}}
					</view>
					<view class="">
						{{libraryType.addName}}
					</view>
					<view class="">
						{{libraryType.businessDate}}
					</view>
					<!-- <view class="">
						可用座位数：16
					</view> -->
				</view>
			</view>
			<view class="border-bottom">

			</view>
			<view class="p-2 font-weight main-text-color">
				座位状态(余位:{{totalSeat}})
			</view>
			<view class="d-flex px-3 a-center flex-wrap">
				<view @click="selectSeat(item.id,item.seatName)" v-for="(item,index) in seatList" :key="index">
					<view class="">
						<view class="" style="width: 120rpx;">
							<image
								:src="item.status===0?'../../static/tushu/seat.png':'../../static/tushu/seat_sel.png'"
								style="width: 60rpx;height: 60rpx;"></image>
						</view>
						<view class="a-center">
							<text>{{item.seatName}}</text>
						</view>
					</view>
				</view>
			</view>

		</view>
		<view class="">
			<uni-popup ref="popup" background-color="#ffffff">
				<view class="cu-list menu text-left">
					<view class="p-2" style="margin-top: 130rpx;">
						<view class="font-md font-weight border-bottom p-1">
							<text class="cuIcon-cascades" space="emsp">{{" 座位预约"}}</text>
						</view>
						<view class="font-sm border-bottom p-1">
							姓名:<text>{{libraryType.username}}</text>
						</view>
						<view class="font-sm border-bottom p-1">
							名称:<text>{{libraryType.libraryName}}</text>
						</view>
						<view class="font-sm border-bottom p-1">
							座号:<text>{{seatName}}</text>
						</view>
						<divider></divider>
						<view class="font-md font-weight border-bottom p-1">
							<text class="cuIcon-cascades" space="emsp">{{" 选定时间"}}</text>
						</view>
						<view class="font-sm border-bottom p-1 d-flex j-sb">
							<view class="main-text-color">选择时间段:</view>
							<picker mode="date" :value="date" @change="DateChange">
								<view class="picker">
									{{date}}
								</view>
							</picker>
						</view>
						<view class="font-sm border-bottom p-1 d-flex a-center j-sb mb-3">
							<view class="main-text-color"></view>
							<uni-data-checkbox v-model="value" :localdata="range" @change="change"></uni-data-checkbox>
						</view>
					</view>
				</view>
				<view class="d-flex px-3 w-100 position-fixed bottom-0">
					<button @click="orderBtn()" class="cu-btn flex-1 bg-red margin-tb-sm lg">确认</button>
				</view>
			</uni-popup>
		</view>

	</view>
</template>

<script>
	export default {
		data() {
			return {
				id: '',
				seatList: [],
				seatName: '',
				orderMange: {},
				libraryType: {},
				date: '2022-01-25',
				startTime: '9:00',
				endTime: '22:30',
				modalName: null,
				cardCur: 0,
				dotStyle: false,
				towerStart: 0,
				direction: '',
				totalSeat: '',
				value: 0,
				range: [{
					"value": 0,
					"text": "9:00-11:00"
				}, {
					"value": 1,
					"text": "11:00-13:00"
				}, {
					"value": 2,
					"text": "13:00-15:00"
				}, {
					"value": 3,
					"text": "15:00-17:00"
				}]
			};
		},
		onLoad() {
			this.id = this.$Route.query.id
			this.getLibrary()
		},
		methods: {
			change(e) {
				var statDate=e.detail.data.text
				this.startTime=statDate.split('-')[0]
				this.endTime=statDate.split('-')[1]

				console.log('e:', this.startTime);
				console.log('e:', this.endTime);

			},
			//图书馆信息
			getLibrary() {
				this.$http.get('/wx/libraryType/get_by_id?id=' + this.id).then(res => {
					if (res.data.code === '200') {
						this.libraryType = res.data.data
						this.libraryType.username = this.$store.state.username
						this.orderMange.libraryId = res.data.data.id
						this.orderMange.libraryName2 = res.data.data.libraryName
					}
					this.getSeat()
				})
			},
			getSeat() {
				this.$http.post('/wx/seatStatus/getList?id=' + this.id).then(res => {
					if (res.data.code === '200') {
						this.totalSeat = res.data.count
						this.seatList = res.data.data
					}
					console.log(res)
				})
			},
			selectSeat(id, name) {
				this.$refs.popup.open('left')
				this.seatName = name
				this.orderMange.seatId = id
				this.orderMange.seatName2 = name

				this.orderMange.userId = this.$store.state.userid
				this.orderMange.orderStatus = 0
				this.orderMange.userId = this.$store.state.userid
				this.orderMange.orderStatus = 0
				console.log(this.orderMange)
				this.$tip.success("已选中:" + name)
			},
			//保存订单
			orderBtn() {

				/* this.orderMange.planTime = this.date + " " + this.startTime + ":00"
				this.orderMange.endTime = this.date + " " + this.endTime + ":00"
				var dateBegin = new Date(this.orderMange.planTime);
				var dateEnd = new Date(this.orderMange.endTime);
				var dateDiff = dateEnd.getTime() - dateBegin.getTime(); //时间差的毫秒数
				var leave1 = dateDiff % (24 * 3600 * 1000); //计算天数后剩余的毫秒数
				var hours = Math.floor(leave1 / (3600 * 1000)); //计算出小时数 */

				/* if (hours > 2) {
					uni.showToast({
						icon: "error",
						title: "每次只能预约2小时"
					})
				} else {
					this.$http.post('/wx/orderMange/create', this.orderMange).then(res => {
						if (res.data.code === '200') {
							this.getLibrary()
							this.$tip.success('预定成功')
							this.$refs.popup.close()

						} else {
							this.$tip.success(res.data.msg)
						}
					})
				} */
				this.orderMange.planTime = this.date + " " + this.startTime + ":00"
				this.orderMange.endTime = this.date + " " + this.endTime + ":00"
				this.$http.post('/wx/orderMange/create', this.orderMange).then(res => {
					if (res.data.code === '200') {
						this.getLibrary()
						this.$tip.success('预定成功')
						this.$refs.popup.close()

					} else {
						this.$tip.success(res.data.msg)
					}
				})
			},
			TimeChange(e) {

				this.startTime = e.detail.value
			},
			TimeChange2(e) {
				this.endTime = e.detail.value
			},
			DateChange(e) {
				this.date = e.detail.value
			},

			//预定按钮
			reseveBtm() {
				this.$tip.success("预定成功!")
			},
			//返回
			backNav() {
				this.$Router.replaceAll({
					name: 'index'
				})
			}


		}
	}
</script>

<style>
	.content {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
	}

	.logo {
		height: 200rpx;
		width: 200rpx;
		margin-top: 200rpx;
		margin-left: auto;
		margin-right: auto;
		margin-bottom: 50rpx;
	}

	.text-area {
		display: flex;
		justify-content: center;
	}

	.title {
		font-size: 36rpx;
		color: #8f8f94;
	}
</style>
