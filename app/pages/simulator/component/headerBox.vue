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
				{{wayInfo.name&&wayInfo.name!=='暂无数据'?wayInfo.name:'组合方式'}}
			</button>
		</view>
		<button @click="open">打开弹窗</button>
		<uni-popup ref="popup" type="bottom">
			<view class="popup-content">
				<scroll-view scroll-y="true" class="scroll-content">
					<view class="content">
						<view class="label" v-for="(item,key) in selectList" :class="{active:selectedData.id===item.id}"
							:key="key" @click="selectData(item)">
							{{item.name}}
						</view>
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
			add: {
				type: Function
			},
		},
		name: 'headerBox',
		data() {
			return {
				brandList: [],
				craneList:  [],
				weightList:  [],
				wayList:  [],
				brandInfo: {},
				brandInfoIndex: 0,
				craneInfo: {},
				craneInfoIndex: 0,
				weightInfo: {},
				weightInfoIndex: 0,
				wayInfo: {},
				wayInfoIndex: 0,
				type: '',
				selectedData: null
			}
		},
		computed: {
			selectList: function() {
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
			this.add('1231');
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
			}
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
						name
					}) => ({
						id,
						name
					})) : defaultList
				})
			},
			getCraneList: function(params = {}) {
				const _this = this;
				getCraneList(params).then(res => {
					_this.craneList = res.data.craneList.length ? res.data.craneList.map(({
						id,
						name
					}) => ({
						id,
						name
					})) : defaultList
				})
			},
			getWeightList: function(params = {}) {
				const _this = this;
				getWeightList(params).then(res => {
					_this.weightList = res.data.weightList.length ? res.data.weightList.map(({
						id,
						equipweight
					}) => ({
						id,
						name: equipweight + 't'
					})) : defaultList;
				})
			},
			getWayList: function(params = {}) {
				const _this = this;
				getWayList(params).then(res => {
					_this.wayList = res.data.wayList.length ? res.data.wayList.map(({
						id,
						primaryLength,
						way
					}) => ({
						id,
						name: `${way}-${primaryLength}`
					})) : defaultList
				})
			},
			resetAll: function() {
				this.brandInfo = {};
				this.craneInfo = {};
				this.weightInfo = {};
				this.wayInfo = {};
				this.craneList = [];
				this.weightList = [];
				this.wayList = [];
				this.brandInfoIndex = 0;
				this.craneInfoIndex = 0;
				this.weightInfoIndex = 0;
				this.wayIndex = 0;
			},
			brandChange: function(e) {
				const _this = this;
				const index = e.target.value;
				_this.brandInfoIndex = index;
				this.craneInfo = {};
				this.weightInfo = {};
				this.wayInfo = {};
				this.craneInfoIndex = 0;
				this.weightInfoIndex = 0;
				this.wayIndex = 0;
				this.craneList = [];
				this.weightList = [];
				this.wayList = [];
				_this.brandInfo = _this.brandList[index];
			},
			craneChange: function(e) {
				const _this = this;
				const index = e.target.value;
				_this.craneInfoIndex = index;
				this.weightInfo = {};
				this.wayInfo = {};
				this.weightInfoIndex = 0;
				this.wayIndex = 0;
				this.weightList = [];
				this.wayList = [];
				_this.craneInfo = _this.craneList[index];
			},
			weightChange: function(e) {
				const _this = this;
				const index = e.target.value;
				_this.weightInfoIndex = index;
				this.wayInfo = {};
				this.wayIndex = 0;
				this.wayList = [];
				_this.weightInfo = _this.weightList[index];
			},
			wayChange: function(e) {
				const _this = this;
				const index = e.target.value;
				_this.wayInfoIndex = index;
				_this.wayInfo = _this.wayList[index];
			},
			open(type) {
				this.type = type;
				this.selectedData = null;
				console.log('this.type---',this.type);
				switch (type) {
					case brand:
						!this.brandList.length && uni.showToast({
							title: '暂无数据'
						})
						return this.brandList
					case crane:
					console.log('this.type---',this.craneList.length);
						!this.craneList.length && uni.showToast({
							title: '请选择品牌'
						})
						return this.craneList
					case weight:
						!this.weightList.length && uni.showToast({
							title: '请选择配重'
						})
						return this.weightList
					case way:
						!this.wayList.length && uni.showToast({
							title: '暂无数据'
						})
						return this.wayList
				}

				this.$refs.popup.open('bottom')
			},
			confirm: function() {
				console.log('confirm---');
				if (this.selectedData) {
					return uni.showToast({
						title: '请选择数据'
					})
				}
				this.$refs.popup.close()
			},
			selectData: function(data) {
				console.log('selectData---', data);
				this.selectedData = data;
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

		.popup-content {
			overflow: hidden;
			background-color: #FFFFFF;
			padding: 20upx 20upx 0;

			.scroll-content {
				height: 300upx;
				margin-bottom: 20upx;

				.content {
					display: flex;
					flex-wrap: wrap;
					justify-content: space-between;

					.label {
						height: 62upx;
						margin-bottom: 12upx;
						line-height: 62upx;
						border-radius: 8upx;
						font-size: 24upx;

						flex: 0 0 220upx;
						text-align: center;
						color: #333;
						background-color: #FFFFFF;
						border: 1px solid #01A1ED;

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
