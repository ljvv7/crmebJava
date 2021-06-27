<template>
	<view class="nav-container">
		<view v-for="(item, index) in icon" class="nav-box" @click="navToPage(icon[index].url)" :key="key">
			<image class="icon" :src="item.pic"></image>
			<text class="text">{{item.name}}</text>
		</view>
	</view>
</template>

<script>
	import {
		goPage
	} from '../../../utils/crane.js'
	export default {
		props: {
			icon: {
				type: Array,
				default: [],
			},
		},
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
				}, {
					text: '插销模拟',
					icon: '/static/images/crane/navSimulation.png',
					url: '/pages/searchCar/index',
					type: 'tab'
				}, {
					text: '模拟教程',
					icon: '/static/images/crane/navTutorials.png',
					url: 'https://baidu.com',
					type: 'web'
				}, {
					text: '公司入驻',
					icon: '/static/images/crane/navSettleIn.png',
					url: '/pages/companySettled/index'
				}]
			};
		},
		onLoad(options) {

		},
		watch: {},
		onShow() {},
		onReady() {},
		methods: {
			navToPage: function(url) {
				let type = 'page';
				if (url.startsWith('http')) {
					type = 'web'
				}
				console.log(url);
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
		padding: 50upx 6upx 54upx;

		.nav-box {
			display: flex;
			flex-direction: column;
			flex-wrap: wrap;
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
