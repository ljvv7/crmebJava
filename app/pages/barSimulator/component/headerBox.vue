<template>
	<view class="header-box">
		<view class="left">
			<button type="default" @click="open('brand')" class="button select">
				{{brandInfo.name&&brandInfo.name!=='暂无数据'?brandInfo.name:'吊车品牌'}}
			</button>
			<button type="default" @click="open('crane')" class="button select">
				{{craneInfo.name&&craneInfo.name!=='暂无数据'?craneInfo.name:'车型'}}
			</button>
		</view>
		<view class="center">
			<button type="default" class="button reset" @click="resetAll">
				重置
			</button>
			<button type="default" class="button text">
				仅供参考
			</button>
		</view>
		<view class="right">
			<button type="default" @click="open('weight')" class="button select">
				{{weightInfo.name&&weightInfo.name!=='暂无数据'?weightInfo.name:'配重'}}
			</button>
			<button type="default" @click="open('way')" class="button select">
				{{wayInfo.name&&wayInfo.name!=='暂无数据'?wayInfo.way:'组合方式'}}
			</button>
		</view>
		<uni-popup ref="popup" type="bottom" class="popup">
			<view class="popup-content">
				<scroll-view scroll-y="true" class="scroll-content">
					<view v-if="selectList.length" class="content">
						<view class="label" v-for="(item,key) in selectList" :class="{active:selectedData.id===item.id}"
							:key="key" @click="selectData(item)">
							{{item.name}}
						</view>
					</view>
					<view v-else class="content empty">
						<text class="text">
							未找到相关数据
						</text>
					</view>
				</scroll-view>
				<button class="button" type="default" @click="confirm">确认</button>
			</view>
		</uni-popup>
	</view>
</template>
<script>
	import {
		getCraneList,
		getBrandList,
		getWeightList,
		getWayList
	} from '@/api/crane.js'
	const defaultList = [{
		name: '暂无数据'
	}];
	const [crane, brand, weight, way] = ['crane', 'brand', 'weight', 'way'];
	export default {
		components: {

		},
		props: {
			types: {
				type: Array
			},
			selectedInfo: {
				types: Function
			}
		},
		name: 'headerBox',
		data() {
			return {
				brandList: [],
				craneList: [],
				weightList: [],
				wayList: [],
				brandInfo: null,
				craneInfo: null,
				weightInfo: null,
				wayInfo: null,
				type: '',
				selectedData: null,
			}
		},
		computed: {
			selectList() {
				switch (this.type) {
					case brand:
						return this.brandList
					case crane:
						return this.craneList
					case weight:
						return this.weightList
					case way:
						return this.wayList
				}
			}
		},
		async onReady() {
			uni.showLoading();
			await this.getBrandList();
			uni.hideLoading();
		},
		watch: {
			brandInfo(val) { //普通的watch监听
				const _this = this;
				if (val && val.id) {
					_this.getCraneList({
						cbrandid: val.id
					})
				}
			},
			craneInfo(val) {
				const _this = this;
				if (val && val.id) {
					_this.getWeightList({
						craneid: val.id
					})
				}
			},
			weightInfo(val) {
				const _this = this;
				if (val && val.id) {
					_this.getWayList({
						craneid: _this.craneInfo.id,
						cweightid: val.id
					})
				}
			},
		},
		methods: {
			test: function() {
				console.log('test----');
				return false
			},
			onTips: function(title) {
				uni.showToast({
					title,
					icon: 'none'
				})
			},
			getBrandList: function(params = {}) {
				const _this = this;
				return getBrandList(params).then(res => {
					_this.brandList = res.data.length ? res.data.map(({
						id,
						name,
						...rest
					}) => ({
						...rest,
						id,
						name,
					})) : []
				})
			},
			getCraneList: function(params = {}) {
				const _this = this;
				getCraneList(params).then(res => {
					_this.craneList = res.data.craneList.length ? res.data.craneList.map(({
						id,
						name,
						...rest
					}) => ({
						...rest,
						id,
						name,
					})) : []
				})
			},
			getWeightList: function(params = {}) {
				const _this = this;
				getWeightList(params).then(res => {
					_this.weightList = res.data.weightList.length ? res.data.weightList.map(({
						id,
						equipweight,
						...rest
					}) => ({
						...rest,
						id,
						equipweight,
						name: equipweight + 't',
					})) : [];
				})
			},
			getWayList: function(params = {}) {
				const _this = this;
				getWayList(params).then(res => {
					_this.wayList = res.data.wayList.length ? res.data.wayList.map(({
						id,
						primaryLength,
						way,
						...rest
					}) => ({
						...rest,
						id,
						primaryLength,
						way,
						name: `${way}-${primaryLength}`,
					})) : [];
				})
			},
			resetAll: function() {
				this.brandInfo = null;
				this.craneInfo = null;
				this.weightInfo = null;
				this.wayInfo = null
				this.craneList = [];
				this.weightList = [];
				this.wayList = [];
				this.selectedInfo && this.selectedInfo({
					brandInfo: null,
					craneInfo: null,
					weightInfo: null,
					wayInfo: null,
				});
			},
			open(type) {
				this.type = type;
				this.selectedData = null;
				switch (this.type) {
					case brand:
						if (this.brandInfo) {
							this.selectedData = this.brandInfo;
						}
						break
					case crane:
						if (!this.brandInfo) {
							return uni.showToast({
								title: '请选择品牌',
								icon: 'none'
							})
						} else {
							this.selectedData = this.craneInfo;
						}
						break
					case weight:
						if (!this.craneInfo) {
							return uni.showToast({
								title: '请选择车型',
								icon: 'none'
							})
						} else {
							this.selectedData = this.weightInfo;
						}
						break
					case way:
						if (!this.weightInfo) {
							return uni.showToast({
								title: '请选择配重',
								icon: 'none'
							})
						} else {
							this.selectedData = this.wayInfo;
						}
						break
				}
				this.$refs.popup.open('bottom')
			},
			confirm: function() {
				if (this.selectList.length) {
					if (!this.selectedData) {
						return uni.showToast({
							title: '请选择数据',
							icon: 'none'
						})
					}
					switch (this.type) {
						case brand:
							if (!(this.brandInfo && this.brandInfo.id === this.selectedData.id)) {
								this.brandInfo = this.selectedData;
								this.craneInfo = null;
								this.weightInfo = null;
								this.wayInfo = null;
								this.craneList = [];
								this.weightList = [];
								this.wayList = [];
							}
							this.selectedInfo && this.selectedInfo({
								brandInfo: this.brandInfo,
								craneInfo: this.craneInfo,
								weightInfo: this.weightInfo,
								wayInfo: this.wayInfo
							});
							break;
						case crane:
							if (!this.brandInfo) {
								return uni.showToast({
									title: '请选择数据',
									icon: 'none'
								})
							}
							if (!(this.craneInfo && this.craneInfo.id === this.selectedData.id)) {
								this.craneInfo = this.selectedData;
								this.weightInfo = null;
								this.wayInfo = null;
								this.weightList = [];
								this.wayList = [];
							}
							this.selectedInfo && this.selectedInfo({
								brandInfo: this.brandInfo,
								craneInfo: this.craneInfo,
								weightInfo: this.weightInfo,
								wayInfo: this.wayInfo
							});
							break
						case weight:
							if (!this.craneInfo) {
								return uni.showToast({
									title: '请选择数据',
									icon: 'none'
								})
							}
							if (!(this.weightInfo && this.weightInfo.id === this.selectedData.id)) {
								this.weightInfo = this.selectedData;
								this.wayInfo = null;
								this.wayList = [];
							}
							this.$nextTick(() => {
								this.changeWeight();
							});
							this.selectedInfo && this.selectedInfo({
								brandInfo: this.brandInfo,
								craneInfo: this.craneInfo,
								weightInfo: this.weightInfo,
								wayInfo: this.wayInfo
							});
							break
						case way:
							if (!this.weightInfo) {
								return uni.showToast({
									title: '请选择数据',
									icon: 'none'
								})
							}
							if (!(this.wayInfo && this.wayInfo.id === this.selectedData.id)) {
								this.wayInfo = this.selectedData;
							}
							this.$nextTick(() => {
								this.changeCraneWay();
							})
							this.selectedInfo && this.selectedInfo({
								brandInfo: this.brandInfo,
								craneInfo: this.craneInfo,
								weightInfo: this.weightInfo,
								wayInfo: this.wayInfo 
							});
							break
					}
				}

				this.$refs.popup.close()

			},
			selectData: function(data) {
				this.selectedData = data;
			},
			changeWeight() {
				let weightInfo = this.weightInfo;
				let legtypeid = weightInfo['legtype'];
				console.log('weightInfo---', weightInfo);
				let weightData = this.weightList.find(function({
					id
				}) {
					return id === weightInfo.id;
				});
				console.log('weightData---', weightData);

				// this.$parent.setData({
				// 	currentWeight: weightInfo['id'],
				// 	currentWeightKey: weightInfo['key'],
				// 	legtypeid: weightInfo['legtype'],
				// 	alldetails: [
				// 		weightInfo
				// 	]
				// });
			},

			// 更改组合方式
			changeCraneWay(ev) {
				const wayInfo = this.wayInfo;
				console.log('wayInfo---', wayInfo);
				let wayData = this.weightList.find(function({
					id
				}) {
					return id === wayInfo.id;
				});

				// let angle = Number((Math.acos(wayInfo.radius / wayInfo.primaryLength) / Math.PI * 180).toFixed(1));
				// // 判断臂长，设置臂长
				// this.$parent.setData({
				// 	'arm.jib': wayInfo.primaryLength,
				// 	'arm.arc': angle,
				// 	'jib.arc': angle,
				// 	// 'types.armlength.index': this.types.armlength.args.indexOf(wayInfo.primaryLength),
				// 	'types.armlength.value': wayInfo.primaryLength,
				// 	'types.armangle.value': angle,
				// 	currentWay: wayInfo.id,
				// 	searched: true,
				// })
			}
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

	.header-box {
		display: flex;
		justify-content: space-between;
		width: 100%;

		.left,
		.right {
			flex: 0 0 240upx;
		}

		.center {
			flex: 0 0 126upx;

			.button {
				height: 62upx;
			}

			.reset {
				line-height: 60upx;
				background-color: rgba(255, 255, 255, 0);
				border: 1px solid #212121;
				color: #212121;
			}

			.text {
				background-color: rgba(255, 255, 255, 0);
				color: #212121;
			}
		}

		.popup {
			position: fixed;
			width: 0;
			height: 0;
			z-index: 999;
		}

		.popup-content {
			overflow: hidden;
			background-color: #FFFFFF;
			padding: 20upx 20upx 0;

			.scroll-content {
				height: 180upx;
				margin-bottom: 20upx;

				.content {
					display: flex;
					flex-wrap: wrap;
					min-height: 100%;

					&.empty {
						justify-content: center;

						.text {
							font-size: 24upx;
							color: #666;
						}
					}

					.label {
						height: 46upx;
						margin-bottom: 8upx;
						line-height: 44upx;
						border-radius: 8upx;
						font-size: 24upx;
						flex: 0 0 220upx;
						text-align: center;
						color: #333;
						background-color: #FFFFFF;
						border: 1px solid #01A1ED;
						margin-right: 25upx;

						&:nth-of-type(3n) {
							margin-right: 0;
						}

						&.active {
							background-color: #01A1ED;
							color: #FFFFFF;
							border-color: #01A1ED;
						}
					}
				}
			}
		}
	}
</style>
