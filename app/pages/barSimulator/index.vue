<template>
	<view class="simulator">
		<headerBox :selectedInfo="selectedInfo" />
		<view class="bar-box">
			<view v-if="!bixiaoSwitch&&!isBixiaoRed" class="top green long"/>
			<view v-if="isBixiaoRed" class="top red middle"/>
			<view v-if="bixiaoSwitch && !isBixiaoRed " class="top green short"/>
			<view class="box">
				<view v-if="!gangxiaoSwitch&& !isGangxiaoRed" class="left green long" />
				<view v-if="isGangxiaoRed" class="left red middle" />
				<view v-if="gangxiaoSwitch && !isGangxiaoRed" class="left green short" />
				<view v-if="!gangxiaoSwitch&& !isGangxiaoRed" class="right green long" />
				<view v-if="isGangxiaoRed" class="right red middle" />
				<view v-if="gangxiaoSwitch && !isGangxiaoRed" class="right green short" />
			</view>
			<view class="text">
				{{ currentLength < 0 ? 0 : currentLength }}
			</view>
		</view>
		<view class="process-box">
			<progress :percent="percent" activeColor="#33ADFF" border-radius='4' stroke-width="7" />
			<view class="label">
				<text>0</text>
				<text>46%</text>
				<text>92%</text>
			</view>
		</view>
		<view class="table" id="table">
			<view class="row">
				<view class="item"
					v-bind:style="{width:boxWidth?boxWidth + 'px':'auto',flex:boxWidth?'1 0 '+boxWidth+'px':'1 0 auto' }" />
				<view class="item" :class="{active:chooseWayDrawIndex - 1 == index}"
					v-for="(item,index) in chooseWayDraw" :key="index"
					v-bind:style="{width:boxWidth?boxWidth + 'px':'auto',flex:boxWidth?'1 0 '+boxWidth+'px':'1 0 auto' }">
					T{{ index + 2 }}
				</view>
			</view>
			<view class="row">
				<view class="item"
					v-bind:style="{width:boxWidth?boxWidth + 'px':'auto',flex:boxWidth?'1 0 '+boxWidth+'px':'1 0 auto' }">
					Act
				</view>
				<view class="item" v-for="(item,index) in chooseWayDraw" :key="index"
					v-bind:style="{width:boxWidth?boxWidth + 'px':'auto',flex:boxWidth?'1 0 '+boxWidth+'px':'1 0 auto' }">
					{{ chooseWayDraw[index] == 1 ? 46 : chooseWayDraw[index] == 2 ? 92 : chooseWayDraw[index] == 3 ? 100 : 0 }}
				</view>
			</view>
			<view class="row">
				<view class="item"
					v-bind:style="{width:boxWidth?boxWidth + 'px':'auto',flex:boxWidth?'1 0 '+boxWidth+'px':'1 0 auto' }">
					Aim
				</view>
				<view class="item" v-for="(item,index) in chooseWayDraw" :key="index"
					v-bind:style="{width:boxWidth?boxWidth + 'px':'auto',flex:boxWidth?'1 0 '+boxWidth+'px':'1 0 auto' }">
					{{ calcResult[index] ? calcResult[index] : 0 }}
				</view>
			</view>
		</view>
		<view class="operate-box">
			<view class="operate-btn">
				<button class="btn plus" @tap="plusMinus('plus')" @longpress="longPress('plus')"
					@touchend="clearTimer" />
				<text>臂销/杠杆增加</text>
				<button class="btn minus" @tap="plusMinus('minus')" @longpress="longPress('minus')"
					@touchend="clearTimer" />
			</view>
			<view class="switch-container">
				<view class="switch-box" :class="{off:!bixiaoSwitch}">
					<text>臂销开关</text>
					<switch :checked="bixiaoSwitch" :disabled="isGangxiaoRed" color="#33ADFF"
						@change="switchChange(1,$event)" />
				</view>
				<view class="switch-box" :class="{off:!gangxiaoSwitch}">
					<text>杠销开关</text>
					<switch :checked="gangxiaoSwitch" :disabled="isBixiaoRed" color="#33ADFF"
						@change="switchChange(2,$event)" />
				</view>
			</view>
		</view>
	</view>
</template>
<script>
	import headerBox from './component/headerBox.vue'

	export default {
		components: {
			headerBox,
		},
		data() {
			return {
				boxWidth: 0,
				timer: null,
				currentStep: 2,
				gangxiaoSwitch: false,
				isGangxiaoRed: false,
				isBixiaoRed: false,
				bixiaoSwitch: false,
				isOpenedBixiao: false,
				currentWayChoose: null,
				way: '000000',
				chooseWayDraw: '000000'.split(''),
				chooseWayDrawIndex: '000000'.length,
				primaryLength: 0,
				currentLength: 0,
				calcResult: [],
				percent: 0,
				showDrawIndex: true
			}
		},
		async onLoad() {
			this.calcWidth();
		},
		computed: {},
		unmounted: function() {
			this.clearTimer();
		},
		methods: {
			selectedInfo: function(data) {
				console.log('selectedInfo---', data);
				// angle: null
				// craneid: 92
				// id: 192077
				// legtype: 1
				// minorLength: "0.00"
				// name: "11211-17.41"
				// origin: null
				// primaryLength: "17.41"
				// radius: "6.00"
				// way: "11211"
				// weight: "54.00"
				// weightid: 441

				this.currentWayChoose = this.wayInfo;
				this.way = this.wayInfo.way;
				this.primaryLength = this.wayInfo.primaryLength;
				this.currentLength = this.wayInfo.way.length;
			},
			calcWidth: function() {
				const query = uni.createSelectorQuery().in(this);
				query.select('#table').boundingClientRect();
				query.exec(data => {
					this.boxWidth = data[0].width / (this.chooseWayDraw.length + 1)
				});
			},
			switchChange: function(type, e) {
				const checked = e.detail.value;
				if (type === 1) {
					// bixiao
					this.bixiaoSwitch = checked;
					this.gangxiaoSwitch = false;
					this.isBixiaoRed = true;
					this.isOpenedBixiao = checked ? true : this.isOpenedBixiao;
				} else {
					this.gangxiaoSwitch = checked;
					this.isGangxiaoRed = true;
				}
			},
			longPress: function(type) {
				this.timer = setInterval(() => {
					this.plusMinus(type);
				}, 100);
			},
			plusMinus: function(type) {
				if (type === 'plus') {
					this.calc(this.currentStep)
				} else {
					this.calc(-this.currentStep)
				}
			},
			calc(step = 2) {
				this.step = step;
				let index = this.chooseWayDrawIndex;
				let oldIndex = index;
				let currentJisuandejieguo = this.calcResult;
				let a = this.chooseWayDraw[this.chooseWayDrawIndex - 1]
				let currentLength = this.currentLength;
				let percent = a == 1 ? 46 : a == 2 ? 92 : a == 3 ? 100 : 0;
				let currentPercent = this.percent;
				let changeLength = currentLength;
				let allIsZero = true;
				this.calcResult.forEach(v => {
					allIsZero = allIsZero && v == 0;
				});
				changeLength = this.currentLength + step;
				changeLength = changeLength % 2 != 0 ? changeLength + 1 : changeLength;
				let isBixiaoRed = this.isBixiaoRed;
				let isGangxiaoRed = this.isGangxiaoRed;
				let bLength = index * 20;
				if (this.calcResult.length == 0) {
					if (index <= this.chooseWayDraw.length) {
						isBixiaoRed = currentLength == index * 20 ? false : isBixiaoRed;
						isGangxiaoRed = currentLength == (index * 20 - 10) ? false : isGangxiaoRed;
					}
				} else {
					isBixiaoRed = currentLength == index * 20 ? false : parseInt(((currentLength - bLength) / this
						.primaryLength * 100)) == percent ? false : isBixiaoRed;
					isGangxiaoRed = currentLength == (index * 20 - 10) ? false : isGangxiaoRed;

					if (this.calcResult[this.chooseWayDrawIndex - 1] != undefined && this
						.calcResult[this.chooseWayDrawIndex - 1] <= percent) {
						isGangxiaoRed = false;
					}
				}
				let showDrawIndex = true;
				console.log('gangxiaoSwitch', this.gangxiaoSwitch);
				console.log('bixiaoSwitch', this.bixiaoSwitch);
				if (step > 0) {
					if (this.gangxiaoSwitch) {
						if (this.calcResult.length != 0 && this.calcResult[this.chooseWayDrawIndex -
								1] > 0) {
							if (changeLength < bLength + Math.ceil(this.primaryLength * (percent + 1) / 100)) {
								this.setData({
									percent: ((currentLength - bLength) / this.primaryLength * 100),
									chooseWayDrawIndex: index,
									currentLength: changeLength,
									isBixiaoRed: isBixiaoRed,
									isGangxiaoRed: isGangxiaoRed,
									showDrawIndex
								})
							}
							return;
						}

						index = changeLength < 0 ? 1 : Math.ceil(changeLength / 20);
						if (this.calcResult.length != 0) {
							if (this.calcResult[this.chooseWayDrawIndex + 1] == undefined || this
								.calcResult[this.chooseWayDrawIndex + 1] == 0 || this.calcResult[this
									.chooseWayDrawIndex + 1]) {
								index = index;
							} else {
								index = this.chooseWayDrawIndex;
							}
						}

						this.setData({
							percent: ((currentLength - bLength) / this.primaryLength * 100),
							chooseWayDrawIndex: index,
							currentLength: changeLength,
							isBixiaoRed: isBixiaoRed,
							isGangxiaoRed: isGangxiaoRed,
							showDrawIndex
						})
					} else if (this.bixiaoSwitch) {
						if (parseInt(((currentLength - bLength) / this.primaryLength)) > percent + 2) {
							return;
						}

						currentJisuandejieguo[this.chooseWayDrawIndex - 1] = parseInt(((currentLength - bLength) /
							this.primaryLength * 100))
						if (currentJisuandejieguo[this.chooseWayDrawIndex - 1] == percent - 2) {
							this.setData({
								percent: ((currentLength - bLength) / this.primaryLength * 100),
								currentLength: changeLength,
								calcResult: currentJisuandejieguo,
								isBixiaoRed: isBixiaoRed,
								isGangxiaoRed: isGangxiaoRed,
								showDrawIndex
							})

							if (this.isNew) {
								clearInterval(this.ctxTimer)
								wx.showModal({
									content: '请关闭臂销'
								})
							}
							return;
						}

						this.setData({
							percent: ((currentLength - bLength) / this.primaryLength * 100),
							currentLength: changeLength,
							calcResult: currentJisuandejieguo,
							isBixiaoRed: isBixiaoRed,
							isGangxiaoRed: isGangxiaoRed,
							showDrawIndex
						})
					}

					if (!this.bixiaoSwitch && !this.gangxiaoSwitch) {
						if (this.isBixiaoRed) {
							if (this.calcResult.length > 0) {
								if (this.calcResult[this.chooseWayDrawIndex - 1] < percent) {
									currentJisuandejieguo[this.chooseWayDrawIndex - 1] = parseInt((currentLength -
										bLength) / this.primaryLength * 100)
									if (currentJisuandejieguo[this.chooseWayDrawIndex - 1] == percent) {
										changeLength = Math.ceil(((this.primaryLength) * (percent + 1) / 100)) +
											bLength;
										currentJisuandejieguo[this.chooseWayDrawIndex - 1] = parseInt((changeLength -
											bLength) / this.primaryLength * 100)
										this.setData({
											percent: parseInt((changeLength - bLength) / this.primaryLength *
												100),
											currentLength: changeLength,
											calcResult: currentJisuandejieguo,
											isBixiaoRed: isBixiaoRed,
											isGangxiaoRed: isGangxiaoRed,
											showDrawIndex
										})
										if (this.isNew) {
											clearInterval(this.ctxTimer)
											wx.showModal({
												content: '请向后调整臂销'
											})
										}
									} else {
										this.setData({
											percent: parseInt(((currentLength - bLength) / this
												.primaryLength * 100)),
											currentLength: changeLength,
											calcResult: currentJisuandejieguo,
											isBixiaoRed: isBixiaoRed,
											isGangxiaoRed: isGangxiaoRed,
											showDrawIndex
										})
									}
								} else if (this.calcResult[this.chooseWayDrawIndex - 1] == percent) {
									if (this.isNew) {
										clearInterval(this.ctxTimer)
										wx.showModal({
											content: '请打开杠销'
										})
									}
								} else {
									this.setData({
										percent: parseInt(((currentLength - bLength) / this.primaryLength *
											100)),
										currentLength: changeLength,
										calcResult: currentJisuandejieguo,
										isBixiaoRed: isBixiaoRed,
										isGangxiaoRed: isGangxiaoRed,
										showDrawIndex
									})
								}
							}
						} else if (this.isGangxiaoRed) {
							if (currentLength > index * 20 - 10) {
								return;
							}

							if (this.calcResult.length == 0 || this.calcResult[this
									.chooseWayDrawIndex - 1] == 0) {
								if (this.currentLength != index * 20 - 10) {
									this.setData({
										currentLength: changeLength,
										isBixiaoRed: isBixiaoRed,
										isGangxiaoRed: isGangxiaoRed,
										showDrawIndex
									})
								} else {
									this.setData({
										isGangxiaoRed: false,
									})
								}
							} else {
								if (this.currentLength != (percent * this.primaryLength) / 100) {
									this.setData({
										percent: (((currentLength - bLength) / this.primaryLength * 100)),
										currentLength: changeLength,
										isBixiaoRed: isBixiaoRed,
										isGangxiaoRed: isGangxiaoRed,
										showDrawIndex
									})
								}
							}
						} else {
							if (this.calcResult[this.chooseWayDrawIndex - 1] == undefined || this
								.calcResult[this.chooseWayDrawIndex - 1] == 0) {
								if (currentLength > index * 20 - 10) {
									return;
								}
							}

							if (this.calcResult[this.chooseWayDrawIndex - 1] < percent) {
								currentJisuandejieguo[this.chooseWayDrawIndex - 1] = parseInt((currentLength -
									bLength) / this.primaryLength * 100)
								if (currentJisuandejieguo[this.chooseWayDrawIndex - 1] == percent) {
									changeLength = Math.ceil(((this.primaryLength) * (percent + 1) / 100)) + bLength;
									currentJisuandejieguo[this.chooseWayDrawIndex - 1] = parseInt((changeLength -
										bLength) / this.primaryLength * 100)
								}

								this.setData({
									percent: (((changeLength - bLength) / this.primaryLength * 100)),
									currentLength: changeLength,
									calcResult: currentJisuandejieguo,
									isBixiaoRed: isBixiaoRed,
									isGangxiaoRed: isGangxiaoRed,
									showDrawIndex
								})
							} else {
								if ((((changeLength - bLength) / this.primaryLength * 100)) >= percent + 1) {
									return;
								}

								this.setData({
									percent: (((changeLength - bLength) / this.primaryLength * 100)),
									currentLength: changeLength,
									calcResult: currentJisuandejieguo,
									isBixiaoRed: isBixiaoRed,
									isGangxiaoRed: isGangxiaoRed,
									showDrawIndex
								})
							}
						}
					}
				} else {
					changeLength = changeLength < 0 ? 0 : changeLength;
					if (this.gangxiaoSwitch) {
						index = changeLength <= 0 ? 1 : Math.ceil(changeLength / 20);
						if (this.calcResult[this.chooseWayDrawIndex - 1] != undefined && this
							.calcResult[this.chooseWayDrawIndex - 1] > 0) {
							showDrawIndex = false;
							index = this.chooseWayDrawIndex;
						}

						if (Math.ceil(changeLength / 20) < index) {
							showDrawIndex = true;
							index = changeLength <= 0 ? 1 : Math.ceil(changeLength / 20);
						}

						this.setData({
							percent: allIsZero ? this.percent : ((currentLength - (index * 20)) / this
								.primaryLength * 100),
							chooseWayDrawIndex: index,
							currentLength: changeLength,
							isBixiaoRed: isBixiaoRed,
							isGangxiaoRed: isGangxiaoRed,
							showDrawIndex
						})
					} else if (this.bixiaoSwitch) {
						if (changeLength <= this.chooseWayDrawIndex * 20) {
							changeLength = this.chooseWayDrawIndex * 20;
							currentLength = this.chooseWayDrawIndex * 20;
						}

						currentJisuandejieguo[this.chooseWayDrawIndex - 1] = parseInt(((currentLength - bLength) /
							this.primaryLength * 100))
						this.setData({
							percent: ((currentLength - bLength) / this.primaryLength * 100),
							currentLength: changeLength,
							calcResult: currentJisuandejieguo,
							isBixiaoRed: isBixiaoRed,
							isGangxiaoRed: isGangxiaoRed,
							showDrawIndex
						})
					}

					if (!this.bixiaoSwitch && !this.gangxiaoSwitch) {
						if (this.isBixiaoRed) {
							if (changeLength <= this.chooseWayDrawIndex * 20) {
								changeLength = this.chooseWayDrawIndex * 20;
								currentLength = this.chooseWayDrawIndex * 20;
							}

							if (this.calcResult.length > 0) {
								currentJisuandejieguo[this.chooseWayDrawIndex - 1] = parseInt(((currentLength -
									bLength) / this.primaryLength * 100))
								this.setData({
									percent: ((currentLength - bLength) / this.primaryLength * 100),
									currentLength: changeLength,
									calcResult: currentJisuandejieguo,
									isBixiaoRed: isBixiaoRed,
									isGangxiaoRed: isGangxiaoRed,
									showDrawIndex
								})
							}
						} else if (this.isGangxiaoRed) {
							if (this.calcResult.length == 0 || this.calcResult[this
									.chooseWayDrawIndex - 1] == 0) {
								if (this.currentLength != index * 20 - 10) {
									this.setData({
										currentLength: changeLength,
										isBixiaoRed: isBixiaoRed,
										isGangxiaoRed: isGangxiaoRed,
										showDrawIndex
									})
								} else {
									this.setData({
										isGangxiaoRed: false,
									})
								}
							} else {
								if (this.currentLength != (percent * this.primaryLength) / 100) {
									this.setData({
										percent: ((currentLength - bLength) / this.primaryLength * 100),
										currentLength: changeLength,
										isBixiaoRed: isBixiaoRed,
										isGangxiaoRed: isGangxiaoRed,
										showDrawIndex
									})
								}
							}
						}

						if (this.calcResult.length > 0 && changeLength - bLength > parseInt(this
								.primaryLength * percent / 100)) {
							currentJisuandejieguo[this.chooseWayDrawIndex - 1] = parseInt(((currentLength - bLength) /
								this.primaryLength * 100))
							this.setData({
								percent: ((currentLength - bLength) / this.primaryLength * 100),
								calcResult: currentJisuandejieguo,
								currentLength: changeLength,
								isBixiaoRed: isBixiaoRed,
								isGangxiaoRed: isGangxiaoRed,
								showDrawIndex
							})
						}
					}
				}
			},
			clearTimer: function() {
				clearInterval(this.timer);
			},
			setData: function(data) {
				const _this = this;
				const keyArr = Object.keys(data);
				keyArr.forEach((key, index) => {
					const keys = key.split('.');
					if (keys.length === 1) {
						_this[keys[0]] = data[key];
					} else if (keys.length === 2) {
						_this[keys[0]][keys[1]] = data[key];
					} else if (keys.length === 3) {
						_this[keys[0]][keys[1]][keys[2]] = data[key];
					} else if (keys.length === 4) {
						_this[keys[0]][keys[1]][keys[2]][keys[3]] = data[key];
					}
				})
			},
		}
	}
</script>


<style lang="scss" scoped>
	.simulator {
		display: flex;
		flex-direction: column;
		height: 100vh;
		padding: 40upx 24upx 0;
		box-sizing: border-box;
		overflow: hidden;
		background-color: #FFF;

		.bar-box {
			width: 100%;
			height: 244upx;
			background: #FAFAFA;
			display: flex;
			flex-direction: column;
			justify-content: center;
			align-items: center;

			.top {
				width: 42upx;
				height: 108upx;
				background-color: #48C589;
			}

			.box {
				width: 116upx;
				height: 48upx;
				background-color: #EBA573;
				position: relative;

				&.green {
					background-color: #48C589;
				}

				&.red {
					background-color: #FF0000;
				}
				&.short{
					height: 19upx;
				}
				&.middle{
					height: 29upx;
				}
				&.long{
					height: 48upx;
				}

				.left,
				.right {
					position: absolute;
					height: 28upx;
					top: 50%;
					margin-top: -14upx;;

					&.green {
						background-color: #48C589;
					}

					&.red {
						background-color: #FF0000;
					}
					&.short{
						width: 46upx;
					}
					&.middle{
						width: 69upx;
					}
					&.long{
						width: 116upx;
					}
				}
				.left{
					left:0 ;
					transform: translateX(-100%);
				}
				.right {
					right: 0;
					transform: translateX(100%);
				}
			}

			.text {
				margin-top: 10upx;
				line-height: 30upx;
				font-size: 22upx;
				color: #212121;
			}

		}

		.process-box {
			margin-top: 20upx;

			.label {
				display: flex;
				justify-content: space-between;
				margin-top: 8upx;

				text {
					line-height: 30upx;
					font-size: 22upx;
					color: #212121;
				}
			}
		}

		.table {
			margin-top: 24upx;
			border-top: 1px solid #CCCCCC;

			.row {
				display: flex;
				height: 60upx;
				border: 1px solid #CCCCCC;
				border-top: 0;

				.item {
					flex: 1 0 auto;
					border-right: 1px solid #CCCCCC;
					text-align: center;
					line-height: 59upx;
					font-size: 24upx;
					color: #212121;

					&:last-of-type {
						border-right: none;
					}

					&.active {
						background-color: #33ADFF;
						color: #FFFFFF;
						border-color: #33ADFF;
					}
				}
			}
		}

		.operate-box {
			width: 502upx;
			margin: 60upx auto 0;

			.operate-btn {
				display: flex;
				justify-content: space-between;
				align-items: center;

				text {
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

			.switch-container {
				display: flex;
				justify-content: space-between;
				margin-top: 44upx;

				.switch-box {
					flex: 0 0 230upx;
					height: 156upx;
					padding: 24upx 0;
					background-color: #FAFAFA;
					border-radius: 18upx;
					display: flex;
					flex-direction: column;
					align-items: center;
					justify-content: center;

					text {
						margin-bottom: 20upx;
						line-height: 40upx;
						font-size: 28upx;
						color: #212121;
					}

					switch {
						transform: scale(0.6)
					}

					&.off {
						text {
							color: #999999;
						}
					}
				}
			}
		}

	}
</style>
