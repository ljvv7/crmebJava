<template>
	<scroll-view scroll-y="true" class="index">
		<company-card v-for="(item,key) in companylist" :data="item" :key="key" :topLine="key!==0"></company-card>
	</scroll-view>
</template>

<script>
	import companyCard from '../common/companyCard.vue'
	import {
		getCompanyList
	} from '@/api/crane.js'

	export default {
		components: {
			companyCard,
		},
		data() {
			return {
				code: '',
				pageindex: 1,
				pagesize: 10,
				companylist: [],
				count: 0,
			};
		},
		onLoad(options) {
			const _this = this;
			this.code = options.code;
			_this.getCompanyList();
		},
		watch: {

		},
		onShow() {

		},
		onReady() {
			// 加载dom
		},
		methods: {
			getCompanyList: function() {
				const _this = this;
				getCompanyList({
					latitude: uni.getStorageSync('user_latitude'),
					longitude: uni.getStorageSync('user_longitude'),
					pageindex: _this.pageindex,
					pagesize: _this.pagesize
				}).then(res => {
					if (res.data) {
						this.companylist = res.data.list;
					}
				})
			}
		}
	};
</script>

<style lang="scss">
	.index {
		box-sizing: border-box;
		padding: 6upx 20upx 0;
		background: #F4F5F6;
	}
</style>
