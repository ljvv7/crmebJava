<template>
	<scroll-view scroll-y="true" class="index" lower-threshold='200'
		:style="{height:pageHeight==='auto'?'auto':pageHeight+'px'}" @scrolltolower="lower">
		<car-card :data="cranedetail" :isDetails="true" :topLine="false" :hasOperate="true" :isNew="isNew"></car-card>
		<view class="companyList">
			<company-card v-for="(item,index) in companylist" :topLine="index!==0" :data="item" :key="index">
			</company-card>
		</view>
	</scroll-view>
</template>

<script>
	import companyCard from '../common/companyCard.vue'
	import carCard from '../common/carCard.vue'
	import {
		getCraneDetail
	} from '@/api/crane.js'


	export default {
		props: {

		},
		components: {
			companyCard,
			carCard
		},
		data() {
			return {
				isNew: false,
				craneid: '',
				companylist: [],
				pageindex: 1,
				pagesize: 20,
				noMore: false,
				pageHeight: 'auto',
				loading: false,
				lat: uni.getStorageSync('user_latitude'),
				lot: uni.getStorageSync('user_longitude'),
				cranedetail: {
					id: "",
					name: "",
					cbrands: "",
					maxweight: "",
					introduce: "",
					images: "",
					expdbcodes: "",
					visits: "",
					remarks: ""
				}
			};
		},
		async onLoad(options) {
			const _this = this;
			uni.getSystemInfo({
				success: function(e) {
					_this.pageHeight = e.windowHeight;
				}
			});
			this.isNew = options.isNew !== 'false'
			this.craneid = options.craneid;
			uni.showLoading();
			await this.getCraneDetail();
			uni.hideLoading();
		},
		watch: {

		},
		onShow() {

		},
		onReady() {

		},
		methods: {
			getCraneDetail: function() {
				const _this = this;
				if (_this.loading) {
					return
				}
				this.pageindex === 1 && uni.showLoading({
					title: '加载中···'
				});
				_this.loading = true;
				return getCraneDetail({
					craneid: _this.craneid,
					pageindex: _this.pageindex,
					pagesize: _this.pagesize,
					lat: _this.lat,
					lot: _this.lot
				}).then(res => {
					if (res.data) {
						_this.companylist = [..._this.companylist, ...res.data.companylist];
						_this.cranedetail = res.data.cranedetail;
						if (res.data.companylist.length < _this.pagesize) {
							_this.noMore = true;
						}
					}
				}).finally(() => {
					uni.hideLoading();
					setTimeout(() => {
						_this.loading = false;
					}, 500)
				})
			},
			lower: function() {
				if (this.loading) {
					return
				}
				this.pageindex += 1;
				this.getCraneDetail();
			}
		}
	};
</script>

<style lang="scss">
	.index {
		box-sizing: border-box;
		padding: 6upx 0;
		background: #F4F5F6;
		height: 100%;

		.companyList {
			margin-top: 20upx;
		}

		.noMore {
			margin: 30upx 0;
			text-align: center;
			font-size: 24upx;
			color: #999999;
		}
	}
</style>
