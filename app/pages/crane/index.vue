<template>
	<scroll-view scroll-y="true" class="wrapper">
		<index-banner :banner="banner"></index-banner>
		<view class="index">
			<nav-bar :icon="iconList"></nav-bar>
			<hot-car-list :list="hotcrane" />
			<new-car-list :list="newcrane" />
		</view>
	</scroll-view>
</template>

<script>
	import navBar from './components/navBar.vue'
	import hotCarList from './components/hotCarList.vue'
	import newCarList from './components/newCarList.vue'
	import indexBanner from './components/indexBanner.vue'
	import {
		getIndex
	} from '@/api/crane.js'
	import {
		goPage
	} from '@/utils/crane'

	export default {
		components: {
			navBar,
			hotCarList,
			newCarList,
			indexBanner
		},
		data() {
			return {
				banner: [],
				hotcrane: [],
				newcrane: [],
				iconList: []
			};
		},
		async onLoad(options) {
			uni.showLoading();
			const data = await uni.getStorageSync('pageIndex');
			if (data) {
				this.handleData(data);
			}
			await this.getIndexData();
			await this.getUserLocation(options);
			uni.hideLoading();
		},
		watch: {
			isLogin: {
				deep: true, //深度监听设置为 true
				handler: function(newV, oldV) {
					// 优惠券弹窗
					var newDates = new Date().toLocaleDateString();
					if (newV) {
						try {
							var oldDate = uni.getStorageSync('oldDate') || '';
						} catch {}
						if (oldDate != newDates) {
							// 需要登陆之后请求的数据
							console.log('需要登陆之后请求的数据');
						}
					}
				}
			}
		},
		onShow() {},
		onReady() {
			// 加载dom
		},
		methods: {
			getIndexData: function() {
				const _this = this;
				getIndex().then(res => {
					if (res.data) {
						_this.handleData(res.data);
						uni.setStorage({
							key: 'pageIndex',
							data: res.data,
						});
					}
				}).catch(e => {
					console.log('error----', e);
				})
			},
			handleData: function(data) {
				const _this = this;
				if (Array.isArray(data.icon)) {
					_this.iconList = data.icon;
				}
				if (Array.isArray(data.Banner)) {
					_this.banner = data.Banner;
				}
				if (Array.isArray(data.hotcrane)) {
					_this.hotcrane = data.hotcrane;
				}
				if (Array.isArray(data.newcrane)) {
					_this.newcrane = data.newcrane;
				}
			},
			getUserLocation: function(options) {
				const {
					state,
					scope
				} = options;
				uni.getLocation({
					type: 'wgs84',
					success: function(res) {
						try {
							uni.setStorageSync('user_latitude', res.latitude);
							uni.setStorageSync('user_longitude', res.longitude);
						} catch {}
					}
				});
			},
		},

	};
</script>

<style lang="scss">
	.wrapper {
		box-sizing: border-box;
	}

	.index {
		box-sizing: border-box;
		padding: 0 20upx;
		background: #F4F5F6;
	}
</style>
