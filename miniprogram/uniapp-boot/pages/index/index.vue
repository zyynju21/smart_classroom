<template>
	<view>
		<!-- 导航页 -->
		<basics v-if="PageCur=='basics'"></basics>
		<!-- 图书馆 -->
		<library v-if="PageCur=='library'"></library>
		<!-- 借阅 -->
		<books v-if="PageCur=='book'"></books>
		<!-- 关于 -->
		<about v-if="PageCur=='about'"></about>
		<!-- 底部导航 -->
		<view class="cu-bar tabbar bg-white shadow foot">
			<view class="action" @click="NavChange" data-cur="basics">
				<view class="cuIcon-cu-image">
					<image :src="'/static/tabbar/basics'+[PageCur=='basics'?'_cur':'']+'.png'"></image>
				</view>
				<view :class="PageCur=='basics'?'text-green':'text-gray'">主页</view>
			</view>
			<view class="action" @click="NavChange" data-cur="library">
				<view class="cuIcon-cu-image">
					<image :src="'/static/tabbar/component'+[PageCur=='library'?'_cur':'']+'.png'"></image>
				</view>
				<view :class="PageCur=='library'?'text-green':'text-gray'">图书馆</view>
			</view>
			<view class="action" @click="NavChange" data-cur="book">
				<view class="cuIcon-cu-image">
					<image :src="'/static/tabbar/plugin'+[PageCur=='plugin'?'_cur':'']+'.png'"></image>
				</view>
				<view :class="PageCur=='book'?'text-green':'text-gray'">借阅</view>
			</view>
			<view class="action" @click="NavChange" data-cur="about">
				<view class="cuIcon-cu-image">
					<image :src="'/static/tabbar/about'+[PageCur=='about'?'_cur':'']+'.png'"></image>
				</view>
				<view :class="PageCur=='about'?'text-green':'text-gray'">我的</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				PageCur: 'basics'
			}
		},
		onLoad() {
			let navTitle=this.$Route.query.PageCur
			if(navTitle===undefined){
				this.PageCur='basics'
				return
			}
			this.PageCur = this.$Route.query.PageCur;
		},
		methods: {
			//切换导航
			NavChange(e) {
				this.PageCur = e.currentTarget.dataset.cur
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
