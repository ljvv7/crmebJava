<template>
	<scroll-view scroll-y="true" class="index" lower-threshold='300'
		:style="{height:pageHeight==='auto'?'auto':pageHeight+'px'}" @scrolltolower="lower">
		<company-card v-for="(item,key) in companylist" :data="item" :key="key" :topLine="key!==0"/>
		<view v-if="noMore" class="noMore">没有更多了</view>
	</scroll-view>
</template>

<script>
	import companyCard from '../common/companyCard.vue'
	import {
		getCompanyList
	} from '@/api/crane.js'

	export default {
		components: {
			companyCard,
		},
		data() {
			return {
				code: '',
				companylist: [],
				pageindex: 1,
				pagesize: 20,
				noMore: false,
				pageHeight: 'auto',
				loading: false
			};
		},
		onLoad(options) {
			const _this = this;
			uni.getSystemInfo({
				success: function(e) {
					_this.pageHeight = e.windowHeight;
				}
			});
			this.code = options.code;
			_this.getCompanyList();
		},
		watch: {

		},
		onShow() {

		},
		onReady() {
			// 加载dom
		},
		methods: {
			getCompanyList: function() {
				const _this = this;
				if (_this.loading) {
					return
				}
				this.pageindex === 1 && uni.showLoading({
					title: '加载中···'
				});
				_this.loading = true;
				getCompanyList({
					latitude: uni.getStorageSync('user_latitude'),
					longitude: uni.getStorageSync('user_longitude'),
					pageindex: _this.pageindex,
					pagesize: _this.pagesize
				}).then(res => {
					if (res.data) {
						this.companylist = [...this.companylist, ...res.data.list];
						if (res.data.list.length < this.pagesize) {
							_this.noMore = true;
						}
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
				this.getCompanyList();
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
