<template>
	<view class="index">
		<view class="itemBox" :class="{active:brandIndex!==null}">
			<picker @change="bindPickerBrandChange" :value="brandIndex" range-key="name" :range="carBrand">
				{{brandIndex===null?'请选择吊车品牌':carBrand[brandIndex].name}}
			</picker>
		</view>
		<view class="itemBox" :class="{active:modelIndex!==null}">
			<picker @change="bindPickerModelChange" :value="modelIndex" range-key="name" :range="carModel">
				{{modelIndex===null?'请选择吊车型号':carModel[modelIndex].name}}
			</picker>
		</view>
		<view class="button" @click="search">查询</view>
	</view>
</template>

<script>
	import {
		getBrandList,
		getCraneList
	} from '@/api/crane.js'
	export default {
		data() {

			return {
				carBrand: [],
				carModel: [],
				brandIndex: null,
				modelIndex: null,
				brandList: [],
			}
		},
		async onLoad() {
			uni.showLoading();
			await this.getBrandList();
			uni.hideLoading();
			
		},
		computed: {

		},
		methods: {
			bindPickerBrandChange: function(e) {
				this.brandIndex = e.target.value;
				this.getCarList(this.carBrand[this.brandIndex].id);
			},
			bindPickerModelChange: function(e) {
				this.modelIndex = e.target.value
			},
			search: function() {
				if (this.brandIndex === null || this.modelIndex === null) {
					uni.showToast({
						title: `请选择吊车${this.brandIndex === null ?'品牌':'型号'}`,
						icon: 'none',
						duration: 1500
					});
					return;
				}
				const craneid = this.carModel[this.modelIndex].id;

				uni.navigateTo({
					url: `/pages/carDetails/index?craneid=${craneid}`,
					complete: (e) => {
						console.log('-----', e);
					}
				})
			},
			getBrandList: function() {
				const _this = this;
				return getBrandList().then(res => {
					if (res.data) {
						_this.brandList = res.data;
						_this.carBrand = res.data;
					}
				})
			},
			getCarList: function(id) {
				const _this = this;
				getCraneList({
					cbrandid: id
				}).then(res => {
					_this.modelIndex = null;
					_this.carModel = res.data.craneList;
				})
			}
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
		justify-content: center;
		align-items: center;

		.itemBox {
			height: 66upx;
			width: 480upx;
			line-height: 66upx;
			font-size: 30upx;
			color: #CCCCCC;
			border-bottom: 1upx solid #CCCCCC;
			position: relative;

			&+.itemBox {
				margin-top: 78upx;
			}

			&.active {
				color: #212121;
			}

			&::after {
				position: absolute;
				width: 40upx;
				height: 40upx;
				right: 0;
				top: 50%;
				margin-top: -20upx;
				background: url(./images/downArrow.png) 0 0 /100% 100%;
				content: '';
			}
		}

		.button {
			width: 480upx;
			height: 85upx;
			margin-top: 284upx;
			line-height: 85upx;
			font-size: 30upx;
			text-align: center;
			color: #FFFFFF;
			border-radius: 8upx;
			background-color: #0081FF;
		}
	}
</style>
