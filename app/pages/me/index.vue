<template>
	<scroll-view class="meContainer">
		<view class="useInfoWrapper">
			<view class="useInfo">
				<view class="avatarInfo" @click="goToUserInfoPage">
					<image :src="userInfo.avatar" />
				</view>
				<view class="accountInfo">
					<text class="usename">{{userInfo.nickname}}</text>
					<text class="tel">13629274074</text>
				</view>
			</view>
			<view class="accountVip">
				<view class="item">
					<text class="text1">0.00</text>
					<text class="text2">我的佣金</text>
				</view>
				<view class="item">
					<text class="text1">1/天</text>
					<text class="text2">模拟器会员</text>
				</view>
				<view class="item">
					<text class="text1">1/天</text>
					<text class="text2">插销会员</text>
				</view>
			</view>
		</view>
		<view class="serviceCard myService">
			<view class="title">我的服务</view>
			<view class="options">
				<view class="option" v-for="(item,index) in iconserver" :key="index"
					@click="navToPage(iconserver[index].wxappLine)">
					<image :src="item.pic" />
					<text>{{item.name}}</text>
				</view>
			</view>
		</view>
		<view class="serviceCard systemService">
			<view class="title">系统服务</view>
			<view class="options">
				<view class="option" v-for="(item,index) in iconsystem" :key="index"
					@click="navToPage(iconsystem[index].wxappLine)">
					<image :src="item.icon" />
					<text>{{item.menuName}}</text>
				</view>
			</view>
		</view>
	</scroll-view>
</template>

<script>
	import {
		goPage
	} from '@/utils/crane'
	import {
		getMeIndex
	} from '@/api/crane'
	import {
		getUserInfo
	} from "@/api/user";
 
	export default {
		components: {

		},
		data() {
			return {
				userInfo: this.$store.state.app.userInfo,
				iconserver: [],
				iconsystem: []
			};
		},
		async onLoad(options) {
			const _this = this;
			uni.showLoading();
			if (!_this.$store.state.app.uid) {
				const userInfo = await uni.getStorageSync('userInfo');
				if (userInfo) {
					await _this.handleUserInfo(userInfo);
					await _this.getMeIndexData(userInfo.uid);
				} else {
					await _this.getUserInfo().then(() => {
						_this.getMeIndexData(_this.$store.state.app.uid);
					})
				}
			} else {
				await _this.getMeIndexData(_this.$store.state.app.uid);
			}
			uni.hideLoading();
		},
		watch: {

		},
		onShow() {},
		onReady() {
			// 加载dom
		},
		methods: {
			getMeIndexData: function(uid) {
				const _this = this;
				const data = uni.getStorageSync('pageMe');
				if (data) {
					_this.iconserver = data.iconserver;
					_this.iconsystem = data.iconsystem;
				}
				getMeIndex(uid).then(res => {
					_this.iconserver = res.data.iconserver;
					_this.iconsystem = res.data.iconsystem;
					uni.setStorage({
						key: 'pageMe',
						data: res.data,
					});
				})
			},
			getUserInfo: function() {
				const _this = this;
				return getUserInfo().then(res => {
					if (res.data) {
						_this.handleUserInfo(res.data);
					}
				}).finally(() => {
					uni.hideLoading();
				})
			},
			handleUserInfo: function(data) {
				const _this = this;
				_this.userInfo = data
				_this.$store.commit("SETUID", data.uid);
				_this.$store.commit("UPDATE_USERINFO", data);
				uni.setStorage({
					key: 'userInfo',
					data: data
				});
			},
			navToPage: function(url) {
				goPage({
					url
				})
			},
			goToUserInfoPage: function() {
				goPage({
					url: '/pages/users/user_info/index'
				})
			},

		},

	};
</script>

<style lang="scss">
	.meContainer {
		background: #F4F5F6;
		padding: 0 24upx 60upx;
		position: relative;
		overflow: hidden;
		box-sizing: border-box;

		* {
			box-sizing: border-box;
		}

		&::after {
			position: absolute;
			top: 0;
			left: 50%;
			right: 0;
			width: 200%;
			height: 450upx;
			margin-left: -100%;
			background-image: linear-gradient(134deg, #5BE5FF 0%, #2393FF 100%);
			border-bottom-left-radius: 1500upx;
			border-bottom-right-radius: 1500upx;
			z-index: 0;
			content: '';
		}

		.useInfoWrapper {
			position: relative;
			z-index: 1;

			.useInfo {
				display: flex;
				align-items: center;
				padding: 100upx 36upx 0;

				.avatarInfo {
					width: 125upx;
					height: 125upx;
					box-sizing: border-box;
					margin-right: 36upx;
					border: 3upx solid #FFFFFF;
					border-radius: 50%;
					overflow: hidden;

					image {
						width: 100%;
						height: 100%;
					}
				}

				.accountInfo {
					text {
						display: block;
						color: #FFFFFF;
					}

					.usename {
						line-height: 56upx;
						font-size: 40upx;
						margin-bottom: 4upx;
					}

					.tel {
						line-height: 42upx;
						font-size: 30upx;
					}
				}
			}
		}

		.accountVip {
			margin-top: 80upx;
			height: 220upx;
			border-radius: 20upx;
			background-color: #FFFFFF;
			display: flex;
			justify-content: space-between;

			.item {
				display: flex;
				flex-direction: column;
				justify-content: center;
				flex: 0 0 33.3%;
				position: relative;

				text {
					color: #212121;
					text-align: center;
				}

				.text1 {
					line-height: 62upx;
					font-size: 44upx;
				}

				.text2 {
					margin-top: 24upx;
					line-height: 40upx;
					font-size: 28upx;
				}

				&::after {
					position: absolute;
					top: 50%;
					right: 0;
					width: 2upx;
					height: 130upx;
					background-color: #CCCCCC;
					margin-top: -65upx;
					content: '';
				}

				&:last-of-type::after {
					display: none;
				}

			}
		}

		.serviceCard {
			margin-top: 24upx;
			padding: 16upx 24upx 0;
			background-color: #FFFFFF;
			border-radius: 20upx;

			&.myService {
				.options {
					height: 350upx;
				}
			}

			&.systemService {}

			.title {
				margin-bottom: 19upx;
				line-height: 45upx;
				font-size: 32upx;
				color: #212121;
			}

			.options {
				display: flex;
				flex-wrap: wrap;
				align-items: space-between;

				.option {
					display: flex;
					flex-direction: column;
					justify-content: center;
					align-items: center;
					flex: 0 0 163upx;
					height: 154upx;

					image {
						flex: 0 0 80upx;
						width: 80upx;
						height: 80upx;
					}

					text {
						margin-top: 8upx;
						line-height: 30upx;
						height: 22upx;
						color: #212121;
					}
				}
			}
		}
	}
</style>
