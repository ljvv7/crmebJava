<template>
	<view class="number-box">
		<view class="text">
			{{label}}
		</view>
		<view class="btn-box">
			<button class="btn plus" @tap="plus" @longpress="longPress('plus')" @touchend="clearTimer"></button>
			<input class="text" type="number" v-model="val" @blur="blur"></input>
			<button class="btn minus" @tap="minus" @longpress="longPress('minus')" @touchend="clearTimer"></button>
		</view>
	</view>
</template>
<script>
	export default {
		props: {
			label: {
				type: String
			},
			step: {
				type: Number,
				default: 1
			},
			initialVal: {
				type: Number,
				default: 0
			},
			value:{
				type: Number,
				default: 0
			},
			onChange: {
				type: Function
			},
			index: {
				type: String
			},
			max: {
				type: Number,
				default: 100
			},
			maxToast: {
				type: Function
			},
			min: {
				type: Number,
				default: 0
			},
			minToast: {
				type: Function
			},
		},
		name: 'numberInput',
		data() {
			return {
				val: this.min,
				timer: null
			}
		},
		async onReady() {},
		computed: {},
		watch: {
			val(val) {
				if (this.val >= this.max) {
					this.val = this.max;
					return this.maxToast();
				}
				if (this.val <= this.min) {
					this.val = this.min;
					return this.minToast();
				}
				this.onChange && this.onChange(val, this.index)
			},
			value(val){
				this.val = val;
			}
		},
		unmounted: function() {
			this.clearTimer();
		},
		methods: {
			longPress: function(type) {
				this.timer = setInterval(() => {
					type === 'plus' ? this.plus() : this.minus();
				}, 100);
			},
			plus: function() {
				this.val += this.step;
			},
			minus: function() {
				this.val -= this.step;
			},
			blur: function() {
				const val = parseInt(this.val);
				if (isNaN(val)) {
					this.val = this.min
				}
				if (val >= this.max) {
					this.val = this.max;
				}
				if (val <= this.min) {
					this.val = this.min;
				}
			},
			clearTimer: function() {
				this.timer && clearInterval(this.timer);
			},
		}
	}
</script>


<style lang="scss" scoped>
	.button {
		height: 62upx;
		padding: 0;
		margin-bottom: 20upx;
		background-color: #33ADFF;
		border-radius: 8upx;
		line-height: 62upx;
		font-weight: 400;
		font-size: 24upx;
		color: #FFF;


		&.select {
			position: relative;
			padding-left: 20upx;

			&::before {
				position: absolute;
				left: 24upx;
				top: 50%;
				transform: translateY(-30%);
				content: '﹀';
			}
		}
	}


	.number-box {
		flex: 0 0 340upx;
		width: 340upx;
		height: 156upx;
		padding: 18upx 55upx;
		margin-bottom: 24upx;
		background: #FAFAFA;
		border-radius: 18upx;
		display: flex;
		flex-direction: column;
		overflow: hidden;

		.text {
			display: block;
			line-height: 40upx;
			margin-bottom: 20upx;
			flex: 1 1 auto;
			font-size: 24upx;
			color: #212121;
			letter-spacing: 0;
			text-align: left;
			font-weight: 400;
		}

		.btn-box {
			display: flex;
			flex: 0 0 230upx;
			width: 230upx;
			height: 56upx;
			align-items: stretch;

			.text {
				height: 56upx;
				line-height: 56upx;
				text-align: center;
				font-size: 28upx;
				color: #212121;
			}

			.btn {
				flex: 0 0 56upx;
				width: 56upx;
				height: 56upx;

				&.plus {
					background: url(images/plus.png) 0 0 /100% 100% no-repeat, rgba(255, 255, 255, 0);
				}

				&.minus {
					background: url(images/minus.png) 0 0 /100% 100% no-repeat, rgba(255, 255, 255, 0);
				}
			}
		}
	}
</style>
