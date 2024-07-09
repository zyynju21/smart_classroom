<template>
	<view class="">
		<swiper class="screen-swiper" :class="dotStyle?'square-dot':'round-dot'" :indicator-dots="true" :circular="true"
			:autoplay="true" interval="5000" duration="500">
			<swiper-item v-for="(item,index) in swiperList" :key="index">
				<image :src="item.url" mode="aspectFill" v-if="item.type=='image'"></image>
				<video :src="item.url" autoplay loop muted :show-play-btn="false" :controls="false" objectFit="cover"
					v-if="item.type=='video'"></video>
			</swiper-item>
		</swiper>
		<!-- 通知 -->
		<view class="p-3" @click="noticeDesc(noticeMsg.id)">
			<text class="cuIcon-notice">{{noticeMsg.title}}</text>
		</view>
		<divider></divider>
		<!-- 图书馆推荐 -->
		<view class="p-2 font-weight font-md main-text-color">
			<view class="border-bottom ml-2">
				<text class="cuIcon-tag">图书馆推荐</text>
			</view>
		</view>

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
				<!-- <view class="">
					可用座位数：16
				</view> -->
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
			this.noticeBtn()
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
			aaa(){
				this.$router.push({name:'books'})
			},
			//通知
			noticeBtn() {
				this.$http.post('/wx/noticeAdvise/list', {
					current: 1,
					size: 5
				}).then(res => {
					if (res.data.code === '200') {
						this.noticeMsg.title = res.data.data.records[0].title
						this.noticeMsg.id = res.data.data.records[0].id
					} else {
						/* this.$tip.success() */
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
			//通知
			noticeDesc(id){
				this.$Router.push({
					path: '/pages/library/notice',
					query: {
						id: id
					}
				})
			}
			

		}
	}
</script>

<style>
</style>
