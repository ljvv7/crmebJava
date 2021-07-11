<template>
	<view class="carContainer" :class="{new:isNew}">
		<view class="m-card-container" :class="{active:topLine}" @click="navToPage(data.id,isNew)">
			<view class="card-image">
				<image class="image" :src="data.images" :lazy-load="true" />
			</view>
			<view class="card-info">
				<view>
					<text class="title">车型：{{data.name||'无'}}</text>
					<text class="title">品牌：{{data.brandName||'无'}}</text>
				</view>
				<text class="text">{{data.introduce}}</text>
				<view v-if="isNew" class="price">
					指导价：<text class="price-text">{{(data.guidePrice/10000).toFixed(2)}}万</text>
				</view>
			</view>
		</view>
		<view v-if="hasOperate" class="buttonBox">
			<text v-for="(item, index) in buttons" :key="index" @click="onOperate(index)"
				:class="{active:item.active}">{{item.text}}</text>
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
			hasOperate: {
				type: Boolean,
				default: false
			},
			isNew: {
				type: Boolean,
				default: false
			},
			isDetails: {
				type: Boolean,
				default: false
			},
			data: {
				type: Object,
				default: {}
			}
		},
		components: {

		},
		name: 'carCard',
		data() {
			return {
				buttons: [{
					text: '添加',
					active: false
				}, {
					text: '收藏',
					active: false
				}, {
					text: '模拟参数',
					active: false
				}, {
					text: '参数表',
					active: false
				}]
			};
		},
		watch: {},
		onReady() {},
		methods: {
			onOperate: function(type) {
				const TYPE = {
					add: 0,
					fav: 1,
					sim: 2,
					arg: 3
				}
				switch (type) {
					case TYPE.add:
						this.operateAdd();
						break;
					case TYPE.fav:
						this.operateFav();
						break;
					case TYPE.sim:
						this.operateSim();
						break;
					case TYPE.arg:
						this.operateArg();
						break;
				}
			},
			operateAdd: function() {
				if (!this.buttons[0].active) {
					this.buttons[0] = {
						text: '已添加',
						active: true
					};
				} else {
					this.buttons[0] = {
						text: '添加',
						active: false
					};
				}
				this.buttons = [...this.buttons];
			},
			operateFav: function() {
				if (!this.buttons[1].active) {
					this.buttons[1] = {
						text: '已收藏',
						active: true
					};
				} else {
					this.buttons[1] = {
						text: '收藏',
						active: false
					};
				}
				this.buttons = [...this.buttons];
			},
			operateSim: function() {
				console.log('参数模拟');
			},
			operateArg: function() {
				console.log('参数表');
			},
			navToPage: function(cardId, isNew) {
				if (this.isDetails) return;
				const _this = this;
				const url = '/pages/carDetails/index';
				goPage({
					url,
					query: {
						craneid: _this.data.id,
						isNew
					}
				})
			}
		}
	};
</script>

<style lang="scss">
	.carContainer {
		background-color: #FFFFFF;

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
					-webkit-line-clamp: 4;
					-webkit-box-orient: vertical;
				}

				&.new .text {
					-webkit-line-clamp: 2;
				}

				.price {
					display: flex;
					line-height: 50upx;
					font-size: 24upx;

					.price-text {
						font-size: 36upx;
						color: #E95831;
					}
				}
			}
		}

		.buttonBox {
			display: flex;
			justify-content: space-between;

			text {
				display: block;
				flex: 0 0 140upx;
				width: 140upx;
				height: 62upx;
				line-height: 62upx;
				text-align: center;
				font-size: 24upx;
				color: #FFFFFF;
				background-color: #33ADFF;
				border-radius: 31upx;
				margin: 0 20upx 24upx;

				&.active {
					background-color: #CCCCCC;
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
