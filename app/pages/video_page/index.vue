<template>
	<view class="index">
		<video :src="url" controls :enable-play-gesture="true"></video>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				url: '',
			};
		},
		async onLoad(options) {
			this.url = options.url;
		},

		onShow() {},
		onReady() {
			// 加载dom
		},
		methods: {

		},

	};
</script>

<style lang="scss">
	.index {
		height: 100vh;
		width:100%;
		box-sizing: border-box;
		padding: 0;
		background: #F4F5F6;

		video {
			width: 100%;
			height: 100%;
		}
	}
</style>
