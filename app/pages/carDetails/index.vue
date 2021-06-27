<template>
	<scroll-view scroll-y="true" class="index">
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
				offset: 1,
				limit: 10,
				companylist: [],
				count: 0,
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
				return getCraneDetail({
					craneid: _this.craneid,
					pageindex: _this.offset,
					pagesize: _this.limit,
					lat: _this.lat,
					lot: _this.lot
				}).then(res => {
					if (res.data) {
						_this.companylist = res.data.companylist;
						_this.count = res.data.count;
						_this.cranedetail = res.data.cranedetail;
					}
				})
			}
		}
	};
</script>

<style lang="scss">
	.index {
		box-sizing: border-box;
		padding: 6upx 0;
		background: #F4F5F6;

		.companyList {
			margin-top: 20upx;
		}
	}
</style>
