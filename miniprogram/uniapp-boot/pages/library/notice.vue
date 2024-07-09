<template>
	<view class="">
		<cu-custom bgColor="bg-gradual-blue" :isBack="true">
			<view @click="backBtn()" slot="backText">返回</view>
			<view slot="content">通知</view>
		</cu-custom>
		<!-- 公告信息展示页 -->
		<view class="p-3">
			<view class="d-flex j-center">
				<text class="font-md font-weight">{{noticeAdvise.title}}</text>
			</view>
			<view class="d-flex j-center">
				<text class="font-sm text-light-muted">{{noticeAdvise.createDate}}</text>
			</view>
			<view class="mt-2" style="text-indent: 2em;">
				<text>{{noticeAdvise.noticeContent}}</text>
			</view>
		</view>

	</view>
</template>

<script>
	export default {
		data() {
			return {
				noticeAdvise: {}
			}
		},
		onLoad() {
			console.log(this.$Route.query.id)
			this.$http.get('/wx/noticeAdvise/get_by_id?id=' + this.$Route.query.id).then(res => {
				if (res.data.code === '200') {
					this.noticeAdvise = res.data.data
				}
			})
		},
		methods: {
			backBtn() {
				this.$Router.replaceAll({
					name: 'index'
				})
			}
		}
	}
</script>

<style>
</style>
