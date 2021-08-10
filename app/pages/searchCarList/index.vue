<template>
	<scroll-view scroll-y="true" class="index" lower-threshold='200'
		:style="{height:pageHeight==='auto'?'auto':pageHeight+'px'}" @scrolltolower="lower">
		<car-card v-for="(item,index) in craneList" :topLine="index!==0" :data="item" :hasOperate="true" :key="index">
		</car-card>
		<view v-if="noMore" class="noMore">没有更多了</view>
	</scroll-view>
</template>

<script>
	import {
		getCraneList
	} from '@/api/crane.js'
	import carCard from '../common/carCard.vue'

	export default {
		components: {
			carCard,
		},
		data() {
			return {
				craneList: [],
				pageindex: 1,
				pagesize: 20,
				noMore: false,
				pageHeight: 'auto',
				loading: false
			};
		},
		onLoad(options) {
			console.log(options);
			const _this = this;
			uni.getSystemInfo({
				success: function(e) {
					_this.pageHeight = e.windowHeight;
				}
			})
			this.getCraneList();
		},
		watch: {

		},
		onShow() {

		},
		onReady() {
			// 加载dom
		},
		methods: {
			getCraneList: function() {
				const _this = this;
				if (_this.loading) {
					return
				}
				this.pageindex === 1 && uni.showLoading({
					title: '加载中···'
				});
				_this.loading = true;
				getCraneList({
					pageindex: _this.pageindex,
					pagesize: _this.pagesize
				}).then(res => {
					_this.craneList = [..._this.craneList, ...res.data.craneList];
					if (res.data.craneList.length < this.pagesize) {
						_this.noMore = true;
					}
				}).finally(() => {
					uni.hideLoading();
					setTimeout(() => {
						_this.loading = false;
					}, 500)
				})
			},
			lower: function() {
				if (this.loading) {
					return
				}
				this.pageindex += 1;
				this.getCraneList();
			}
		}
	};
</script>

<style lang="scss">
	.index {
		box-sizing: border-box;
		padding: 6upx 0;
		background: #FFFFFF;
		height: 100%;

		.noMore {
			margin: 30upx 0;
			text-align: center;
			font-size: 24upx;
			color: #999999;
		}
	}
</style>
