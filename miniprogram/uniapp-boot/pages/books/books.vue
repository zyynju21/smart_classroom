<template>
	<view class="">
		<cu-custom bgColor="bg-gradual-pink">
		  <view slot="backText">返回</view>
		  <view slot="content">图书列表</view>
		</cu-custom>
		<view class="">
			<uni-search-bar @confirm="search" placeholder="请输入书名"></uni-search-bar>
		</view>
		<view v-for="(item,index) in libraryList"  class="d-flex p-3 mb-5">
			<view class="">
				<image :src="'http://127.0.0.1:8080/'+item.bookImg" style="border-radius: 16rpx;width: 300rpx;height: 300rpx;">
				</image>
			</view>
			<view class="p-3 line-h-md">
				<view class="font-md font-weight">
					{{item.bookName}}
				</view>
				<view class="mt-1">
					状态：{{item.useState==="0"?'可借':'已借'}}
				</view>
				<view class="mt-1">
					备注：{{item.bookMark}}
				</view>
				<view class="mt-1">
					上架时间：{{item.createDate.substring(0,9)}}
				</view>
			</view>
		</view>
			
	</view>
</template>

<script>
	export default {
		data() {
			return {
				libraryList:[],
				bookName:'',
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
			search(e){
				this.bookName=e.value
			
				this.getLibrary()
				console.log(e)
			},
			//展示页面
			getLibrary(){
				
				this.$http.post('/wx/booksManger/list',{current:1,size:20,bookName:this.bookName}).then(res=>{
					if(res.data.code==='200'){
						this.libraryList=res.data.data.records
					}
				})
			},
			
			//详细页
			/* detailBtn(id) {
				this.$Router.push({
					path: '/pages/basics/detail',
					query: {
						id: id
					}
				})
			}, */

		}
	}
</script>

<style>
</style>
