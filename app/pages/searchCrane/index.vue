<template>
	<view class="index">
		<view class="itemBox" v-for="(item,key) in attrs" :key="key" v-if="item.show">
			<view class="label">
				{{item.label}}
			</view>
			<view class="inputBox" :class="{active:values[item.name]}">
				<picker @change="bindPickerChange($event,item)" :value="item.value" range-key="value" :mode="item.mode"
					@columnchange="columnchange($event,item)" :range="item.list">
					{{values[item.name]?values[item.name]:item.placeholder}}
				</picker>
				<view v-if="item.showWarning" class="warning">{{item.warning}}</view>
			</view>
		</view>
		<view class="button" @click="search">查询吊车</view>
	</view>
</template>

<script>
	import {
		combinationList,
		raduisList,
		weightList,
		momentPercentageList,
		mainArmLengthList,
		jibLengthList,
		mineTowerLengthList
	} from './data.js';
	import {
		goPage
	} from '@/utils/crane';
	const typeMap = ['main', 'mainTower', 'mainJib'];
	export default {
		data() {
			return {
				values: {
					combination: null,
					raduis: null,
					weight: null,
					momentPercentage: null,
					mainArmLength: null,
					jibLength: null,
					mineTower: null
				},
				attrs: [{
					name: 'combination',
					label: '臂长组合模式：',
					warning: '请输入臂长组合模式',
					showWarning: false,
					placeholder: '请选择',
					list: combinationList,
					mode: 'selector',
					value: 0,
					show: true
				}, {
					name: 'raduis',
					label: '作业半径（m）：',
					warning: '请输入臂长组合模式',
					showWarning: false,
					placeholder: '请选择',
					list: raduisList,
					mode: 'selector',
					value: 0,
					show: true
				}, {
					name: 'weight',
					label: '起重量（T/吨）：',
					warning: '请输入起重量',
					showWarning: true,
					placeholder: '请选择',
					list: weightList,
					mode: 'selector',
					value: 0,
					show: true
				}, {
					name: 'momentPercentage',
					label: '力矩百分比范围（%）：',
					warning: '请输入力矩百分比范围',
					showWarning: false,
					placeholder: '请选择',
					list: momentPercentageList,
					mode: 'multiSelector',
					value: [0, 0],
					show: true
				}, {
					name: 'mainArmLength',
					label: '主臂长度范围（m）：',
					warning: '请输入主臂长度范围',
					showWarning: false,
					placeholder: '请选择',
					list: mainArmLengthList,
					mode: 'multiSelector',
					value: [0, 0],
					show: true
				}, {
					name: 'jibLength',
					label: '副臂长度范围（m）：',
					warning: '请输入副臂长度范围',
					showWarning: false,
					placeholder: '请选择',
					list: jibLengthList,
					mode: 'multiSelector',
					value: [0, 0],
					show: false
				}, {
					name: 'mineTower',
					label: '矿塔长度范围（m）：',
					warning: '请输入矿塔长度范围',
					showWarning: false,
					placeholder: '请选择',
					list: mineTowerLengthList,
					mode: 'multiSelector',
					value: [0, 0],
					show: false
				}]
			}
		},
		onLoad() {},
		computed: {},
		methods: {
			bindPickerChange: function(e, data) {
				const val = e.target.value;
				if (data.name === 'combination') {
					if (Number(val) === 0) {
						this.attrs[5].show = false;
						this.attrs[6].show = false;
					} else if (Number(val) === 1) {
						this.attrs[5].show = false;
						this.attrs[6].show = true;
					} else if (Number(val) === 2) {
						this.attrs[5].show = true;
						this.attrs[6].show = false;
					}
				}
				data.value = val;
				if (Array.isArray(e.target.value)) {
					this.values[data.name] = data.list[0][val[0]].value + ' ~ ' + data.list[1][val[1]].value;
				} else {
					this.values[data.name] = data.list[val].value;
				}
				const name = data.name;
				const mode = data.mode;
				this.attrs = [...this.attrs];
			},
			columnchange: function(e, data) {
				if (e.target.column === 0) {
					if (e.target.value > data.value[1]) {
						data.value = [e.target.value, e.target.value]
					}
				} else {
					if (e.target.value < data.value[0]) {
						data.value = [e.target.value, e.target.value]
					}
				}
				this.attrs = [...this.attrs];
			},
			search: function() {
				if (!(this.values.combination &&
					this.values.raduis &&
					this.values.weight &&
					this.values.momentPercentage &&
					this.values.mainArmLength)
				) {
					return uni.showToast({
						title: '请配置完全',
						icon: 'none'
					})
				}

				if (Number(this.attrs[0].value) === 1) {
					if (!this.values.mineTower) {
						return uni.showToast({
							title: '请配置完全',
							icon: 'none'
						})
					}
				} else if (Number(this.attrs[0].value) === 2) {
					if (!this.values.jibLength) {
						return uni.showToast({
							title: '请配置完全',
							icon: 'none'
						})
					}
				}
				const url = '/pages/searchCarList/index';
				goPage({
					url
				});
			},
		}
	}
</script>
<style lang="scss">
	page {
		height: 100%;
	}

	.index {
		height: 100%;
		background-color: #FFFFFF;
		display: flex;
		flex-direction: column;
		align-items: center;
		padding: 60upx;

		.itemBox {
			display: flex;
			justify-content: space-between;
			align-items: center;
			width: 100%;
			margin-bottom: 50upx;
			line-height: 66upx;
			font-size: 30upx;
			color: #CCCCCC;

			.label {
				line-height: 40upx;
				font-size: 28upx;
				color: #212121;
			}

			.inputBox {
				width: 320upx;
				height: 72upx;
				border: 1px solid #999999;
				border-radius: 8upx;
				line-height: 70upx;
				font-size: 28upx;
				padding: 0 16upx;
				position: relative;

				&.active {
					color: #212121;
				}

				&::after {
					position: absolute;
					width: 44upx;
					height: 44upx;
					right: 0;
					top: 50%;
					margin-top: -22upx;
					background: url(./images/downArrow.png) 0 0 /100% 100%;
					content: '';
				}

				.warning {
					position: absolute;
					bottom: -38upx;
					left: 0;
					line-height: 30upx;
					font-family: 'PingFangSC-Regular';
					font-size: 22upx;
					color: #999999;
					letter-spacing: 0;
					text-align: left;
					font-weight: 400;
				}
			}
		}

		.button {
			width: 480upx;
			height: 85upx;
			margin-top: 40upx;
			line-height: 85upx;
			font-size: 30upx;
			text-align: center;
			color: #FFFFFF;
			border-radius: 8upx;
			background-color: #0081FF;
		}
	}
</style>
