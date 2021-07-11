<template>
	<scroll-view scroll-y="true" class="index">
		<view class="companyBox">
			<company-card :data="data" :topLine="false" :isDetails="true" />
		</view>
		<view class="tabContainer">
			<view class="tabList">
				<view class="tabItem" :class="{ active: current===0 }" v-on:click="onClickItem(0)">简介</view>
				<view class="tabItem" :class="{ active: current===1 }" v-on:click="onClickItem(1)">车型</view>
				<view class="tabItem" :class="{ active: current===2 }" v-on:click="onClickItem(2)">资质</view>
			</view>
			<view class="content">
				<view v-show="current === 0">
					<view class="description">
						{{data.introduce||'暂无'}}
					</view>
				</view>
				<view v-show="current === 1">
					<view class="carShowList">
						<car-card v-for="(item,index) in craneList" :data="item" :topLine="index!==0" :hasOperate="true"
							:isDetails="true" :key="index"></car-card>
					</view>
				</view>
				<view v-show="current === 2">
					<view class="credentials">
						<image :src="data.qualification"></image>
					</view>
				</view>
			</view>
		</view>
	</scroll-view>
</template>

<script>
	import companyCard from '../common/companyCard.vue'
	import carCard from '../common/carCard.vue'
	import {
		getCompanyCarList
	} from '@/api/crane.js'
	export default {
		components: {
			companyCard,
			carCard
		},
		data() {
			return {
				data: {},
				current: 0,
				craneList: []
			};
		},
		async onLoad(options) {
			uni.showLoading();
			this.data = JSON.parse(options.data);
			await this.getCompanyCarList(this.data.id);
			uni.hideLoading();
		},
		watch: {

		},
		onShow() {

		},
		onReady() {
			// 加载dom
		},
		methods: {
			onClickItem: function(index) {
				if (index !== this.current) {
					this.current = index
				}
			},
			getCompanyCarList: function(code) {
				const _this = this;
				return getCompanyCarList({
					code
				}).then(res => {
					_this.craneList = res.data.cranedetaillist;
				})
			}
		}
	}
</script>

<style lang="scss">
	.index {
		box-sizing: border-box;
		background: #F4F5F6;

		.companyBox {
			padding: 10upx 0 6upx;
			background-color: #FFFFFF;
		}

		.tabContainer {
			background-color: #FFFFFF;
			margin-top: 20upx;

			.tabList {
				display: flex;
				justify-content: space-between;

				.tabItem {
					flex: 0 0 33%;
					height: 88upx;
					line-height: 88upx;
					font-size: 30upx;
					text-align: center;
					position: relative;

					&.active {
						color: #33ADFF;

						&::after {
							position: absolute;
							width: 160upx;
							height: 4upx;
							background-color: #33ADFF;
							bottom: 0;
							left: 50%;
							margin-left: -80upx;
							content: '';
						}
					}

				}
			}

			.content {
				.description {
					padding: 32upx 45upx 60upx;
					color: #212121;
				}

				.carShowList {
					padding: 6upx 0;
				}

				.credentials {
					padding: 32upx 24upx 60upx;

					image {
						width: 100%;
					}
				}
			}
		}
	}
</style>
