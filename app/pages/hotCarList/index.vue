<template>
	<scroll-view scroll-y="true" class="index">
		<car-card v-for="(item,index) in craneList" :topLine="index!==0" :data="item"  :key="index"></car-card>
		
	</scroll-view>
</template>

<script>
	import {
		getCraneList
	} from '@/api/crane.js'
	import carCard from '../common/carCard.vue'

	export default {
		components: {
			carCard
		},
		data() {
			return {
				craneList: []
			};
		},
		onLoad(options) {
			this.getCraneList();
		},
		watch: {

		},
		onShow() {

		},
		onReady() {
			// 加载dom
		},
		methods: {
			getCraneList: function() {
				const _this = this;
				uni.showLoading();
				getCraneList({}).then(res => {
					_this.craneList = res.data.craneList;
				}).finally(() => {
					uni.hideLoading();
				})
			}
		}
	};
</script>

<style lang="scss">
	.index {
		box-sizing: border-box;
		padding: 6upx 0;
		background: #FFFFFF;
	}
</style>
