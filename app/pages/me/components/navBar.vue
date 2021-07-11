<template>
	<view class="nav-container">
		<view v-for="(item, index) in navList" class="nav-box"
			@click="navToPage(navList[index].url,navList[index].type)" :key="key">
			<image class="icon" :src="item.icon"></image>
			<text class="text">{{item.text}}</text>
		</view>
	</view>
</template>

<script>
	import {
		goPage
	} from '../../../utils/crane.js'
	export default {
		components: {},
		name: 'navBar',
		data() {
			return {
				navList: [{
					text: '吊装公司',
					icon: '/static/images/crane/navCompany.png',
					url: '/pages/companyList/index'
				}, {
					text: '车型查询',
					icon: '/static/images/crane/navSearch.png',
					url: '/pages/searchCar/index',
					type: 'tab'
				}, {
					text: '插销模拟',
					icon: '/static/images/crane/navSimulation.png',
					url: '/pages/searchCar/index',
					type: 'tab'
				}, {
					text: '模拟教程',
					icon: '/static/images/crane/navTutorials.png',
					url: '/pages/searchCar/index',
					type: 'tab'
				}, {
					text: '公司入驻',
					icon: '/static/images/crane/navSettleIn.png',
					url: '/pages/companySettled/index'
				}]
			};
		},
		onLoad(options) {},
		watch: {},
		onShow() {},
		onReady() {},
		methods: {
			navToPage: function(url, type) {
				goPage({
					url,
					type
				})
			}
		}
	};
</script>

<style lang="scss">
	.nav-container {
		display: flex;
		justify-content: space-around;
		padding: 50px 6upx 54upx;

		.nav-box {
			display: flex;
			flex-direction: column;
			align-items: center;
			justify-content: space-between;
			flex: 0 0 88upx;
			width: 88upx;

			.icon {
				width: 84upx;
				flex: 0 0 84upx;
				height: 84upx;
				margin-bottom: 20upx;
				border-radius: 50%;
			}

			.text {
				line-height: 30upx;
				font-size: 22upx;
			}
		}
	}
</style>
