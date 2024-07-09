<template>
	<view class="">
		<cu-custom bgColor="bg-gradual-pink" :isBack="true">
			<view @click="goBack()" slot="backText">返回</view>
			<view slot="content">收藏</view>
		</cu-custom>
		<!-- //收藏列表 -->
		<view class="">
			<view class="d-flex border-bottom p-2 j-sb ml-3 mr-3">
				<view class="">
					图书馆1
				</view>
				<view class="">
					<uni-fav :checked="checked" @click="onClick" />
				</view>
			</view>
			<view class="d-flex border-bottom p-2 j-sb ml-3 mr-3">
				<view class="">
					图书馆1
				</view>
				<view class="">
					<uni-fav :checked="checked" @click="onClick" />
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				checked: false
			}
		},
		methods: {
			//返回
			goBack() {
				this.$Router.push({ name: 'index', params: { PageCur: 'about' }})
				/* this.$Router.replaceAll({
					name: "index",
					 query: { PageCur: 'about' }
				}) */
			},
			//收藏
			onClick(){
				this.checked=!this.checked
			}
		}
	}
</script>

<style>
</style>
