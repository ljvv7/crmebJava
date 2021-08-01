<template>
	<view class="index">
		<uni-forms :rules="rules" :value="formData" ref="form" validate-trigger="bind" err-show-type="undertext">
			<uni-forms-item class="formItem" label-width="110" name="company" label="填写公司名称：">
				<input type="text" v-model="formData.company" class="uni-input"
					@blur="binddata('company', $event.detail.value)" placeholder="请填写" />
			</uni-forms-item>
			<uni-forms-item class="formItem" label-width="110" @click="picker" name="addr" label="省-市-区：">
				<picker @change="selCity" mode="region">
					<view class="addText" :class="{active:formData.province}">
						{{formData.province?`${formData.province}-${formData.city}-${formData.district}`:'请选择'}}
					</view>
				</picker>
			</uni-forms-item>
			<uni-forms-item class="formItem" label-width="110" name="addDetailes" label="详细地址：">
				<input type="text" v-model="formData.addDetailes" class="uni-input"
					@blur="binddata('addDetailes', $event.detail.value)" placeholder="请填写" />
			</uni-forms-item>
			<uni-forms-item class="formItem" label-width="110" name="contact" label="联系人：">
				<input type="text" v-model="formData.contact" class="uni-input"
					@blur="binddata('contact', $event.detail.value)" placeholder="请填写" />
			</uni-forms-item>
			<uni-forms-item class="formItem" label-width="110" name="tel" label="联系电话：">
				<input type="number" v-model="formData.tel" class="uni-input"
					@blur="binddata('tel', $event.detail.value)" placeholder="请填写" />
				<!-- <button type="default"	open-type="getPhoneNumber" @getphonenumber="getphonenumber">获取手机号</button> -->
			</uni-forms-item>
			<uni-forms-item class="formItem border-none" label-width="110" name="description" label="公司简介：">
			</uni-forms-item>
			<textarea type="text" v-model="formData.description" class="uni-textarea"
				@blur="binddata('description', $event.detail.value)" placeholder="请填写" />
			<uni-forms-item class="formItem border-none" label-width="110" name="description" label="公司Logo：">
			</uni-forms-item>
			<uni-file-picker v-model="logo" fileMediatype="image" mode="grid" :auto-upload="false"
				@select="select('logo',$event)" @progress="progress" @success="success" @fail="fail" />
			<uni-forms-item class="formItem border-none" label-width="110" name="description" label="公司资质：">
			</uni-forms-item>
			<uni-file-picker v-model="qualification" fileMediatype="image" :auto-upload="false" mode="grid"
				@select="select('qualification',$event)" @progress="progress" @success="success" @fail="fail" />
			<text class="tips">公司认证申请需要缴纳199元认证费，请熟知哦！</text>

			<button class="button" @click="submitForm('form')">申请创建</button>
		</uni-forms>
		<view class="modal" v-if="confirm">
			<view class="modal-box">
				<view class="content">
					<text>您还需缴纳199元入住费，即可创建成功！</text>
				</view>
				<view class="button-box">
					<button type="default" @click="cancelConfirm" class="cancel">取消</button>
					<button type="default" @click="confirmModal" class="confirm">确认</button>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import {
		addCompbind,
		addCompanyTips
	} from '@/api/crane.js'
	export default {
		data() {
			return {
				imageValue: [],
				confirm: false,
				formData: {
					adds: "",
					area: "",
					atten: "",
					authend: "",
					authstart: "",
					city: "",
					distince: 0,
					id: 0,
					introduce: "",
					isauthentication: "",
					latitude: "",
					logo: "",
					longitude: "",
					name: "",
					phone: "",
					province: "",
					qualification: "",
					status: "",
					street: "",
					userid: "",
					visits: 0
				},
				address: '',
				rules: {
					age: {
						rules: [{
								required: true,
								errorMessage: '请输入年龄'
							},
							{
								format: 'int',
								errorMessage: '年龄必须是数字'
							},
							{
								minimum: 18,
								maximum: 30,
								errorMessage: '年龄应该大于 {minimum} 岁，小于 {maximum} 岁'
							}
						]
					}
				},
				imageStyles: {
					width: 120,
					height: 120,
					border: {
						color: "#D3D3D3",
						width: 1,
						style: 'dashed',
						radius: '8upx'
					},
					backgroundcolor: '#f7f7f7'
				},

			}
		},
		onLoad() {
			const that = this
		},
		onReady() {
			this.$refs.form.setRules(this.rules);
			this.formData.userid = this.$store.state.app.uid;
		},
		methods: {
			// 获取上传状态
			select(type, e) {
				console.log('选择文件：', type, e);
				this.formData[type] = e.tempFilePaths[0];
			},
			// 获取上传进度
			progress(e) {
				console.log('上传进度：', e)
			},

			// 上传成功
			success(e) {
				console.log('上传成功')
			},

			// 上传失败
			fail(e) {
				console.log('上传失败：', e)
			},
			birthChange(e) {
				console.log(e)
			},
			change(name, value) {
				this.formData.checked = value
				this.$refs.form.setValue(name, value)
			},
			getphonenumber(e) {
				console.log('getphonenumber---', e);
			},
			/**
			 * 手动提交
			 * @param {Object} form
			 */
			submitForm(form) {
				const formData = this.formData;
				if (!formData.company) {
					return uni.showToast({
						title: '请输入公司名称',
						icon: 'none'
					})
				} else if (!formData.province) {
					return uni.showToast({
						title: '请选择省-市-区',
						icon: 'none'
					})
				} else if (!formData.addDetailes) {
					return uni.showToast({
						title: '请输入公司详细地址',
						icon: 'none'
					})
				} else if (!formData.contact) {
					return uni.showToast({
						title: '请输入联系人',
						icon: 'none'
					})
				} else if (!formData.tel) {
					return uni.showToast({
						title: '请输入联系电话',
						icon: 'none'
					})
				} else if (!formData.description) {
					return uni.showToast({
						title: '请输入公司简介',
						icon: 'none'
					})
				} else if (!formData.logo) {
					return uni.showToast({
						title: '请上传公司LOGO',
						icon: 'none'
					})
				} else if (!formData.qualification) {
					return uni.showToast({
						title: '请上传公司资质',
						icon: 'none'
					})
				}

				this.formData.adds = formData.addDetailes;
				this.formData.name = formData.contact;

				// {
				// 	adds: "",
				// 	area: "",
				// 	atten: "",
				// 	authend: "",
				// 	authstart: "",
				// 	city: "",
				// 	distince: 0,
				// 	id: 0,
				// 	introduce: "",
				// 	isauthentication: "",
				// 	latitude: "",
				// 	logo: "",
				// 	longitude: "",
				// 	name: "",
				// 	phone: "",
				// 	province: "",
				// 	qualification: "",
				// 	status: "",
				// 	street: "",
				// 	userid: "",
				// 	visits: 0
				// }

				addCompbind({
					...this.formData
				}).then(res => {
					console.log('addCompbind---- 这里接口存在问题', res);
				}).finally(()=>{
					this.confirm = true;
				});

				
				// "adds": "",
				// 	"area": "",
				// 	"atten": "",
				// 	"authend": "",
				// 	"authstart": "",
				// 	"city": "",
				// 	"id": 0,
				// 	"introduce": "",
				// 	"latitude": "",
				// 	"logo": "",
				// 	"longitude": "",
				// 	"name": "",
				// 	"phone": "",
				// 	"province": "",
				// 	"qualification": "",
				// 	"status": "",
				// 	"street": "",
				// 	"userid": "2",
				// 	"visits": 0


				// this.$refs[form]
				// 	.submit()
				// 	.then(res => {
				// 		console.log('表单的值：', res)
				// 		uni.showToast({
				// 			title: '验证成功'
				// 		})
				// 	})
				// 	.catch(errors => {
				// 		console.error('验证失败：', errors)
				// 	})
			},

			/**
			 * 清除表单校验
			 * @param {Object} form
			 * @param {Object} name
			 */
			clearValidate(form, name) {
				if (!name) name = []
				this.$refs[form].clearValidate(name)
			},
			// 获取地址信息
			selCity: function(e) {
				const [province, city, district] = e.target.value;
				this.formData.province = province;
				this.formData.city = city;
				this.formData.district = district;
			},
			picker: function() {
				document.blur();
			},
			cancelConfirm: function() {
				this.confirm = false;
			},
			confirmModal: function() {
				uni.showToast({
					title: '调试阶段暂未开启支付功能',
					icon: 'none'
				})
			}
		}
	}
</script>

<style lang="scss">
	.index {
		background-color: #FFFFFF;
		padding: 20upx 20upx 60upx;

		.formItem {
			&.border-none {
				.uni-forms-item {
					border: none;
				}
			}

			.uni-forms-item {
				padding-top: 12upx;
				border-bottom: 1px solid #CCCCCC;

				.uni-forms-item__inner {
					padding-bottom: 0;
					height: 72upx;
				}

				.uni-forms-item__label {
					height: 72upx;

					text {
						font-size: 28upx;
						color: #212121;
					}
				}

				.uni-forms-item__content {
					height: 72upx;
				}

				.uni-input {
					border: none;
					height: 72upx;
				}

				.addText {
					height: 72upx;
					line-height: 72upx;
					color: #808080;

					&.active {
						color: #212121;
					}
				}
			}
		}

		.tips {
			display: block;
			height: 30upx;
			margin-top: 24upx;
			font-family: PingFang-SC-Medium;
			font-size: 22upx;
			color: #F05B5B;
			letter-spacing: 0;
			text-align: left;
			font-weight: 500;
		}

		.button {
			width: 481upx;
			height: 85upx;
			line-height: 84upx;
			background: #0081FF;
			color: #FFFFFF;
			border-radius: 8upx;
			font-family: PingFang-SC-Bold;
			font-size: 30upx;
			color: #FFFFFF;
			letter-spacing: 0;
			text-align: left;
			font-weight: 700;
			text-align: center;
			margin: 40upx auto;
			padding-bottom: 50upx;
		}

		.uni-textarea {
			display: block;
			width: 100%;
			padding: 16upx 18upx;
			border: 1px solid #CCCCCC;
			border-radius: 8upx;
			box-sizing: border-box;
		}

		.modal {
			position: fixed;
			width: 100%;
			height: 100%;
			top: 0;
			left: 0;
			right: 0;
			bottom: 0;
			background-color: rgba(0, 0, 0, 0.24);
			z-index: 999;
			display: flex;
			align-items: center;
			justify-content: center;

			.modal-box {
				width: 583upx;
				background: #FFFFFF;
				box-shadow: 0px 0px 16upx 0upx rgba(0, 0, 0, 0.14);
				border-radius: 8upx;
				overflow: hidden;

				.content {
					height: 220upx;
					padding: 55upx 47upx;

					text {
						line-height: 40upx;
						font-size: 28upx;
						color: #212121;
					}
				}

				.button-box {
					display: flex;
					height: 78upx;
					align-items: stretch;

					button {
						line-height: 78upx;
						margin: 0;
						flex: 0 0 50%;
						height: 100%;
						font-size: 28upx;
						border-radius: 0;

						&.cancel {
							color: #212121;
						}

						&.confirm {
							color: #fff;
							background-color: #0081FF;
						}
					}
				}
			}
		}
	}
</style>
