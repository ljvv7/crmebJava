<template>
	<swiper class="swiper" :autoplay="autoplay" :interval="interval" :duration="duration">
		<swiper-item v-for="(item,index) in banner" :key="index">
			<view class="swiper-item" @click="navToPage(item.url,item.id)"><img :src="item.pic"></view>
		</swiper-item>
	</swiper>
</template>

<script>
	import {
		goPage
	} from '../../../utils/crane.js'
	export default {
		props: {
			banner: {
				type: Array,
				default: [],
			},
		},
		components: {

		},
		name: 'indexBanner',
		data() {
			return {
				indicatorDots: true,
				autoplay: true,
				interval: 2000,
				duration: 800
			};
		},
		onLoad(options) {}, 
		watch: {},
		onShow() {},
		onReady() {},
		methods: {
			navToPage: function(url,id) {
				if(url!=='*'){
					goPage({
						url,
						query:{id}
					})
				}
			}
		}
	};
</script>

<style lang="scss">
	.swiper {
		height: 340upx;

		.swiper-item {
			height: 100%;
			width: 100%;

			image {
				display: block;
				width: 100%;
				height: 100%;
			}
		}
	}
</style>
