<template>
	<view class="footer-box">
		<button class="button operate">障碍物设置</button>
		<picker @change="onPickerChange" range-key="name" :range="legtypes">
			<button class="button operate">{{text}}</button>
		</picker>
		<template v-for="(item,key) in typesData">
			<numberInput v-if="item.visable" :initialVal="item.value" :step="1" :label="item.name"
				:onChange="inputChange" :index="key" :key="key" :min="item.limitMin"  :max="item.limitMax" :maxToast="maxToast"  :minToast="minToast"/>
		</template>
	</view>
</template>
<script>
	import numberInput from './numberInput.vue'
	const defaultText = '吊臂类型';
	export default {
		components: {
			numberInput
		},
		props: {
			add: {
				type: Function
			},
			legtypes: {
				type: Array
			},
			types: {
				type: Array
			}
		},
		name: 'footerBox',
		data() {
			return {
				text: defaultText,
				typesData: this.types
			}
		},
		async onReady() {},
		computed: {},
		watch: {},
		methods: {
			onPickerChange: function(e) {
				const index = e.target.value;
				const id = this.legtypes[index].id;
				this.$parent.setParam(id);
				this.text = this.legtypes[index].name;
				this.typesData = this.$parent.types;
			},
			inputChange: function(inputvalue, target) {
				this.$parent.$parent.drawDC('short', target, inputvalue)
				this.$parent.$parent.calc()
			},
			maxToast:function(){
				uni.showToast({
					title:'已经是该车型的最大值',
					icon:'none'
				})
			},
			minToast:function(){
				uni.showToast({
					title:'已经是该车型的最小值',
					icon:'none'
				})
			}
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

	.footer-box {
		display: flex;
		flex-wrap: wrap;
		justify-content: space-between;
		width: 100%;

		.operate {
			flex: 0 0 340upx;
			width: 340upx;
		}

		.number-box {
			flex: 0 0 340upx;
			width: 340upx;
			height: 104upx;
			padding: 24upx 20upx;
			background: #FAFAFA;
			border-radius: 18upx;
			display: flex;
			align-items: center;

			.text {
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

				.text {
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
	}
</style>
