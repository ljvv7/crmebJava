<template>
	<view>
		<view v-if="list.length" class="list">
			<view v-for="(item, index) in list" :key="index" class="item" @click="goChat(item.to_uid)">
				<view class="image-wrap">
					<image class="image" :src="item.avatar"></image>
				</view>
				<view class="text-wrap">
					<view class="name-wrap">
						<view class="name">{{ item.nickname }}</view>
						<view>{{ item._update_time }}</view>
					</view>
					<view class="info-wrap">
						<view v-if="item.message_type === 1" class="info" v-html="item.message"></view>
						<view v-if="item.message_type === 2" class="info" v-html="item.message"></view>
						<view v-if="item.message_type === 3" class="info">[图片]</view>
						<view v-if="item.message_type === 4" class="info">[语音]</view>
						<view v-if="item.message_type === 5" class="info">[商品]</view>
						<view v-if="item.message_type === 6" class="info">[订单]</view>
						<view class="num" v-if="item.mssage_num">{{ item.mssage_num }}</view>
					</view>
				</view>
			</view>
		</view>
		<view v-else-if="finished && page === 2" class="empty-wrap">
			<view class="image-wrap">
				<image class="image" src="/static/images/noMessage.png"></image>
			</view>
			<view>亲、暂无消息记录哟！</view>
		</view>
	</view>
</template>

<script>
	import {
		serviceRecord
	} from '@/api/user.js';
	export default {
		data() {
			return {
				list: [],
				page: 1,
				limit: 20,
				loading: false,
				finished: false
			};
		},
		onLoad() {
			this.getList();
		},
		onShow() {
			this.getList();
		},
		onReachBottom() {
			this.getList();
		},
		methods: {
			getList() {
				if (this.loading || this.finished) {
					return;
				}
				this.loading = true;
				uni.showLoading({
					title: '加载中'
				});
				serviceRecord({
						page: this.page,
						limit: this.limit
					})
					.then(res => {
						let data = res.data;
						uni.hideLoading();
						this.loading = false;
						data.forEach(item => {
							if (item.message_type === 1) {
								item.message = this.replace_em(item.message);
							}
							if (item.message_type === 2) {
								item.message = this.replace_em(item.message);
							}
						});
						this.list = this.list.concat(data);
						this.finished = data.length < this.limit;
						this.page += 1;
					})
					.catch(err => {
						uni.showToast({
							title: err.msg,
							icon: 'none'
						})
					})
			},
			replace_em(str) {
				str = str.replace(/\[em-([a-z_]*)\]/g, "<span class='em em-$1'/></span>");
				return str;
			},
			goChat(id) {
				// this.$router.push({ path: '/pages/customer_list/chat'})
				uni.navigateTo({
					url: '/pages/customer_list/chat?to_uid=' + id+'&type=1'
				})
			}
		},
		replace_em(str) {
			str = str.replace(/\[em-([a-z_]*)\]/g, "<span class='em em-$1'/></span>");
			return str;
		},
		goChat(item) {
			uni.navigateTo({
				url: '/pages/customer_list/chat?uid=' + item.to_uid
			});
		}
	}
</script>

<style lang="scss" scoped>
	.list {
		background-color: #fff;

		.item {
			display: flex;
			align-items: center;
			height: 130rpx;
			padding: 0 30rpx;

			~.item {
				border-top: 1rpx solid #f5f5f5;
			}
		}

		.image-wrap {
			width: 88rpx;
			height: 88rpx;
			border-radius: 50%;
			overflow: hidden;
		}

		.image {
			display: block;
			width: 100%;
			height: 100%;
		}

		.text-wrap {
			flex: 1;
			min-width: 0;
			margin-left: 20rpx;
		}

		.name-wrap {
			display: flex;
			align-items: center;
			font-size: 20rpx;
			color: #ccc;
		}

		.name {
			flex: 1;
			min-width: 0;
			margin-right: 20rpx;
			overflow: hidden;
			white-space: nowrap;
			text-overflow: ellipsis;
			font-size: 28rpx;
			color: #333;
		}

		.info-wrap {
			display: flex;
			align-items: center;
			margin-top: 18rpx;
		}

		.info {
			flex: 1;
			min-width: 0;
			overflow: hidden;
			white-space: nowrap;
			text-overflow: ellipsis;
			font-size: 24rpx;
			color: #999;
		}

		.num {
			min-width: 32rpx;
			height: 32rpx;
			border-radius: 16rpx;
			margin-left: 20rpx;
			background-color: #e93323;
			font-size: 20rpx;
			line-height: 32rpx;
			text-align: center;
			color: #fff;
		}
	}

	.empty-wrap {
		font-size: 26rpx;
		text-align: center;
		color: #999;

		.image-wrap {
			width: 414rpx;
			height: 336rpx;
			margin: 80rpx auto 0;
		}

		.image {
			display: block;
			width: 100%;
			height: 100%;
		}
	}
</style>
