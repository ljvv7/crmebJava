<template>
	<view class="m-card-container" :class="{active:topLine}">
		<view class="card-image" @click="openCompanyDetails">
			<image class="image" :src="data.logo" />
		</view>
		<view class="card-info">
			<view>
				<text class="title" @click="openCompanyDetails">{{data.name}}</text>
				<view class="company-icon" @click="openCompanyDetails">
					<image class="icon" src="/static/images/crane/certification.png" />认证企业标示
				</view>
				<view class="contact">王女士：<text class="tel"
						@click="callPhone(data.phone||'')">{{data.phone||'暂无'}}</text></view>
			</view>
			<view class="address-box">
				<image class="location" src="/static/images/crane/location.png"></image>
				<view @click="openMap(data.latitude,data.longitude)">
					<text class="text">{{data.adds}}</text>
					<text class="text" v-if="data.distince">距离您{{Math.round( data.distince).toFixed(2)}}公里</text>
					<text class="text" v-else>暂无距离信息</text>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import {
		goPage
	} from '@/utils/crane'

	export default {
		props: {
			topLine: {
				type: Boolean,
				default: true
			},
			isDetailes: {
				type: Boolean,
				default: false
			},
			data: {
				type: Object,
				default: {
					adds: "",
					area: null,
					atten: null,
					authend: null,
					authstart: null,
					city: null,
					id: '',
					introduce: null,
					latitude: null,
					logo: null,
					longitude: null,
					name: "",
					phone: null,
					province: null,
					qualification: null,
					status: null,
					street: null,
					userid: null,
					visits: null,
				}
			}
		},
		components: {

		},
		name: 'companyCard',
		data() {
			return {};
		},
		onLoad(options) {
			
		},
		watch: {},
		onShow() {
			console.log('data----',this.data);
		},
		onReady() {},
		methods: {
			callPhone: function(phoneNumber) {
				if (phoneNumber) {
					uni.makePhoneCall({
						phoneNumber
					})
				}
			},
			openMap: function() {
				const url = '/pages/companyMap/index'
				goPage({
					url
				});
			},
			openCompanyDetails: function() {
				if (this.isDetailes) return;
				const url = '/pages/companyDetails/index?data='+JSON.stringify(this.data);
				goPage({url});
			}
		}
	};
</script>

<style lang="scss">
	.m-card-container {
		padding: 24upx 20upx;
		background-color: #FFFFFF;
		display: flex;
		box-sizing: border-box;
		position: relative;

		&.active::after {
			content: '';
			position: absolute;
			width: calc(100% - 40upx);
			height: 1upx;
			top: 0;
			background-color: #DADADA;
		}

		.card-image {
			flex: 0 0 240upx;
			width: 240upx;
			height: 240upx;
			margin-right: 20upx;

			.image {
				width: 240upx;
				height: 240upx;
			}
		}

		.card-info {
			display: flex;
			flex-direction: column;
			justify-content: space-between;

			.title {
				display: block;
				line-height: 42upx;
				font-size: 30upx;
				margin-bottom: 8upx;
			}

			.text {
				line-height: 33upx;
				font-size: 24upx;
				color: #999;
				overflow: hidden;
				text-overflow: ellipsis;
				display: -webkit-box;
				-webkit-line-clamp: 3;
				-webkit-box-orient: vertical;
			}

			.company-icon {
				display: flex;
				width: 166upx;
				margin-bottom: 8upx;
				align-items: center;
				height: 32upx;
				padding: 0 9upx;
				line-height: 28upx;
				font-size: 20upx;
				border-radius: 4upx;
				color: #E27238;
				border: 1px solid #E27238;

				.icon {
					width: 24upx;
					height: 24upx;
					margin-right: 2upx;
				}
			}

			.contact {
				line-height: 33upx;
				font-size: 24upx;

				.tel {
					color: #0081FF;
				}
			}

			.address-box {
				display: flex;

				.location {
					position: relative;
					top: 3upx;
					flex: 0 0 24upx;
					width: 24upx;
					height: 24upx;
					margin-right: 8upx;
				}

				.text {
					overflow: hidden;
					text-overflow: ellipsis;
					display: -webkit-box;
					-webkit-line-clamp: 1;
					-webkit-box-orient: vertical;

					&+.text {
						margin-top: 8upx;
					}
				}
			}
		}

		.line {
			position: absolute;
			bottom: 0;
			height: 1px;
			width: calc(100% - 40upx);
			background-color: #DADADA;
		}
	}
</style>
