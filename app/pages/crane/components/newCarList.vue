<template>
	<view class="hot-card-list">
		<view class="title-bar">
			热门车型<button class="more" type="default" @click="moreInfo">更多</button>
		</view>
		<car-card v-for="(item,index) in list" :topLine="index!==0" :data='item'  :isNew="true" :key="index"/>
	</view>
</template>

<script>
	import {
		goPage
	} from '@/utils/crane'
	import carCard from '../../common/carCard.vue'
	export default {
		props: {
			list: {
				type: Array,
				default: [],
			},
		},
		components: {
			carCard
		},
		name: 'hotCarList',
		data() {
			return {};
		},
		onLoad(options) {},
		watch: {},
		onShow() {},
		onReady() {},
		methods: {
			moreInfo: function() {
				const url = '/pages/newCarList/index';
				goPage({
					url
				})
			}
		}
	};
</script>

<style lang="scss">
	.hot-card-list {
		padding-top: 20upx;
		margin-bottom: 24upx;
		border-radius: 32upx;
		background-color: #FFFFFF;
		overflow: hidden;

		.title-bar {
			line-height: 45upx;
			font-size: 32upx;
			display: flex;
			justify-content: space-between;
			align-items: center;
			padding: 0 20upx;

			.more {
				height: 36upx;
				flex: 0 0 68upx;
				line-height: 36upx;
				font-size: 18upx;
				color: #FFFFFF;
				background-color: #33ADFF;
				border-radius: 18upx;
			}
		}
	}
</style>
