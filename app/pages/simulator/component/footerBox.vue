<template>
	<view class="footer" id="footer">
		<scroll-view class="footer-box" scroll-y="true" v-bind:style="{height:scrollHeight + 'px' }">
			<view class="operate-container">
				<picker @change="onPickerChange" range-key="name" :range="legtypes">
					<button class="button operate">{{text}}</button>
				</picker>
			</view>
			<view class="obstacle-container">
				<view class="checkbox-group" @click="handleObstacle">
					<view class="checkbox" :class="{active:obstacle}" />
					<view class="text">障碍物设置<text v-if="obstacle">:</text></view>
				</view>
				<template v-if="obstacle">
					<view class="input-box">
						高度(m)<input type="number" v-model="obstacleParam.height" @blur="handleObstacleBlur" />
					</view>
					<view class="input-box">
						距离(m)<input type="number" v-model="obstacleParam.distance" @blur="handleObstacleBlur" />
					</view>
				</template>
			</view>
			<view class="input-container">
				<template v-for="(item,key) in typesData">
					<numberInput v-if="item.visable" :initialVal="item.value" :step="1" :label="item.name"
						:onChange="inputChange" :index="key" :key="key" :min="item.limitMin" :max="item.limitMax"
						:maxToast="maxToast" :minToast="minToast" />
				</template>
			</view>
		</scroll-view>
	</view>
</template>
<script>
	import numberInput from './numberInput.vue'
	const defaultText = '吊臂类型';
	export default {
		components: {
			numberInput
		},
		props: {
			add: {
				type: Function
			},
			legtypes: {
				type: Array
			},
			types: {
				type: Array
			}
		},
		name: 'footerBox',
		data() {
			return {
				text: defaultText,
				typesData: this.types,
				obstacle: false,
				obstacleParam: {
					distance: 20,
					height: 5
				},
				scrollHeight: 0
			}
		},
		async onReady() {
			setTimeout(() => {
				this.handleTypes(0);
			}, 1000)
			this.$nextTick(() => {
				this.init();
			})
		},
		computed: {},
		watch: {

		},
		methods: {
			init: function() {
				const query = uni.createSelectorQuery().in(this);
				query.select('#footer').boundingClientRect();
				query.exec(data => {
					// scrollHeight
					this.scrollHeight = data[0].height
					console.log('this.scrollHeight-', this.scrollHeight);
				});
			},
			onPickerChange: function(e) {
				const index = e.target.value;
				this.handleTypes(index);
			},
			handleTypes: function(index) {
				console.log(this);
				const id = this.legtypes[index].id;
				this.$parent.setParam(id);
				this.text = this.legtypes[index].name;
				this.typesData = this.$parent.types;
			},
			inputChange: function(inputvalue, target) {
				this.$parent.$parent.drawDC('short', target, inputvalue)
				this.$parent.$parent.calc()
			},
			maxToast: function() {
				uni.showToast({
					title: '已经是该车型的最大值',
					icon: 'none'
				})
			},
			minToast: function() {
				uni.showToast({
					title: '已经是该车型的最小值',
					icon: 'none'
				})
			},
			handleObstacle: function() {
				this.obstacle = !this.obstacle;
				this.$parent.setData({
					obstacleSetting: this.obstacle,
					'obstacle.visable': this.obstacle
				})
				this.$parent.calc();
			},
			handleObstacleBlur: function() {
				this.$parent.setData({
					'obstacleParam.height': this.obstacleParam.height,
					'obstacle.vy': this.obstacleParam.height,
					'obstacleParam.distance': this.obstacleParam.distance,
					'obstacle.vx': this.obstacleParam.distance
				});
				this.$parent.calc();
			},
		}
	}
</script>


<style lang="scss" scoped>
	.button {
		height: 62upx;
		padding: 0;
		margin-bottom: 20upx;
		background-color: #33ADFF;
		border-radius: 8upx;
		line-height: 62upx;
		font-weight: 400;
		font-size: 24upx;
		color: #FFF;


		&.select {
			position: relative;
			padding-left: 20upx;

			&::before {
				position: absolute;
				left: 24upx;
				top: 50%;
				transform: translateY(-30%);
				content: '﹀';
			}
		}
	}

	.footer {
		height: 100%;
	}

	.footer-box {
		.input-container {
			display: flex;
			justify-content: space-between;
			flex-wrap: wrap;
			padding-bottom: 60upx;
		}

		.operate-container {
			display: flex;
			justify-content: center;
			margin-top: 20upx;

			.operate {
				flex: 0 0 340upx;
				width: 340upx;
			}

			.number-box {
				flex: 0 0 340upx;
				width: 340upx;
				height: 104upx;
				padding: 24upx 20upx;
				background: #FAFAFA;
				border-radius: 18upx;
				display: flex;
				align-items: center;

				.text {
					flex: 1 1 auto;
					font-size: 24upx;
					color: #212121;
					letter-spacing: 0;
					text-align: left;
					font-weight: 400;
				}

				.btn-box {
					display: flex;
					flex: 0 0 230upx;
					width: 230upx;

					.text {
						line-height: 56upx;
						text-align: center;
						font-size: 28upx;
						color: #212121;
					}

					.btn {
						flex: 0 0 56upx;
						width: 56upx;
						height: 56upx;

						&.plus {
							background: url(images/plus.png) 0 0 /100% 100% no-repeat, rgba(255, 255, 255, 0);
						}

						&.minus {
							background: url(images/minus.png) 0 0 /100% 100% no-repeat, rgba(255, 255, 255, 0);
						}
					}
				}
			}
		}

		.obstacle-container {
			display: flex;
			justify-items: center;
			height: 60upx;
			margin-bottom: 37upx;

			.checkbox-group {
				display: flex;
				align-items: center;
				height: 100%;
				margin-right: 16upx;

				.checkbox {
					width: 32upx;
					height: 32upx;
					margin-right: 16upx;
					border-radius: 4upx;
					border: 1upx solid #33ADFF;

					&.active {
						background: url(images/checked.png) 0 0/100% 100% no-repeat;
						border: none;
					}
				}

				.text {
					height: 32upx;
					line-height: 32upx;
					font-size: 28upx;
					color: #212121;
				}
			}

			.input-box {
				display: flex;
				align-items: center;
				height: 100%;
				line-height: 32upx;
				font-size: 28upx;
				color: #212121;

				input {
					height: 50upx;
					width: 107upx;
					font-size: 24upx;
					margin-left: 15upx;
					border-radius: 8upx;
					border: 1px solid #212121;
					text-align: center;
				}

				&+.input-box {
					margin-left: 36upx;
				}
			}
		}
	}
</style>
