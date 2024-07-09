<template>
	<view class="">
		<cu-custom bgColor="bg-gradual-pink">
		  <view slot="backText">返回</view>
		  <view slot="content">图书馆列表</view>
		</cu-custom>
		<view v-for="(item,index) in libraryList" @tap="detailBtn(item.id)" class="d-flex p-3 mb-5">
			<view class="">
				<image :src="'http://127.0.0.1:8080/'+item.libraryImg" style="border-radius: 16rpx;width: 300rpx;height: 300rpx;">
				</image>
			</view>
			<view class="p-3 line-h-md">
				<view class="font-md font-weight">
					{{item.libraryName}}
				</view>
				<view class="">
					位置：{{item.addName}}
				</view>
				<view class="">
					时间：{{item.businessDate}}
				</view>
			</view>
		</view>
			
	</view>
</template>

<script>
	export default {
		name: "basics",
		data() {
			return {
				libraryList:[],
				noticeMsg: {
					id: 0,
					title: ''
				},
				cardCur: 0,
				swiperList: [{
					id: 0,
					type: 'image',
					url: '../../static/tushu/1.png'
				}, {
					id: 1,
					type: 'image',
					url: '../../static/tushu/2.png',
				}],
				dotStyle: false,
				towerStart: 0,
				direction: ''
			}
		},
		mounted() {
			console.log(this.$config.apiUrl)
			this.getLibrary()
			
		},
		methods: {
			//展示页面
			getLibrary(){
				this.$http.post('/wx/libraryType/list',{current:1,size:20}).then(res=>{
					if(res.data.code==='200'){
						this.libraryList=res.data.data.records
					}
				})
			},
			
			//详细页
			detailBtn(id) {
				this.$Router.push({
					path: '/pages/basics/detail',
					query: {
						id: id
					}
				})
			},

		}
	}
</script>

<style>
</style>
