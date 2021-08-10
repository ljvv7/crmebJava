<template>
	<map style="width: 100%; height: 100%;" :latitude="latitude" :longitude="longitude" :covers="covers">
	</map>
</template>

<script>
	export default {

		components: {

		},
		data() {
			return {
				latitude: 39.909,
				longitude: 116.39742,
				covers: []
			}
		},
		onLoad(options) {
			const _this = this;
			uni.getLocation({
				type: 'wgs84',
				success: function(res) {
					if (res) {
						_this.longitude = res.longitude;
						_this.latitude = res.latitude;
						_this.covers = [{
							latitude: res.latitude,
							longitude: res.longitude,
						}]
					}
				}
			});
		},
		watch: {

		},
		onShow() {

		},
		onReady() {
			// 加载dom
		},
		methods: {

		}
	}
</script>

<style lang="scss">
	page {
		height: 100%;
	}
</style>
