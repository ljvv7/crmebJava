<template>
	<view class="index">
		<uni-forms :rules="rules" :value="formData" ref="form" validate-trigger="bind" err-show-type="undertext">
			<uni-forms-item class="formItem" label-width="110" name="company" label="填写公司名称：">
				<input type="text" v-model="formData.company" class="uni-input"
					@blur="binddata('company', $event.detail.value)" placeholder="请填写" />
			</uni-forms-item>
			<uni-forms-item class="formItem" label-width="110" @click="picker" name="addr" label="省-市-区：">
				<picker @change="selCity" mode="region">
					<input type="text" v-model="formData.addr" class="uni-input" :disabled="true"
						@blur="binddata('addr', $event.detail.value)" placeholder="请选择" />
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
				<input type="text" v-model="formData.tel" class="uni-input" @blur="binddata('tel', $event.detail.value)"
					placeholder="请填写" />
				<!-- <button type="default"	open-type="getPhoneNumber" @getphonenumber="getphonenumber">获取手机号</button> -->
			</uni-forms-item>
			<uni-forms-item class="formItem border-none" label-width="110" name="description" label="公司简介：">
			</uni-forms-item>
			<textarea type="text" v-model="formData.description" class="uni-textarea"
				@blur="binddata('description', $event.detail.value)" placeholder="请填写" />
			<uni-forms-item class="formItem border-none" label-width="110" name="description" label="公司Logo：">
			</uni-forms-item>
			<uni-file-picker v-model="imageValue" fileMediatype="image" mode="grid" :auto-upload="false"
				@select="select" @progress="progress" @success="success" @fail="fail" />
			<uni-forms-item class="formItem border-none" label-width="110" name="description" label="公司资质：">
			</uni-forms-item>
			<uni-file-picker v-model="imageValue" fileMediatype="image" :auto-upload="false" mode="grid"
				@select="select" @progress="progress" @success="success" @fail="fail" />
			<text class="tips">公司认证申请需要缴纳199元认证费，请熟知哦！</text>

			<button class="button" @click="submitForm('form')">申请创建</button>
			<!-- <button class="button" @click="validateField('form')">只校验用户名和邮箱项</button>
				<button class="button" @click="clearValidate('form', 'name')">移除用户名的校验结果</button>
				<button class="button" @click="clearValidate('form')">移除全部表单校验结果</button>
				<button class="button" @click="resetForm">重置表单</button> -->
		</uni-forms>
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
			this.$refs.form.setRules(this.rules)
		},
		methods: {
			// 获取上传状态
			select(e) {
				console.log('选择文件：', e)
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
				// console.log(this.formData);
				this.$refs[form]
					.submit()
					.then(res => {
						console.log('表单的值：', res)
						uni.showToast({
							title: '验证成功'
						})
					})
					.catch(errors => {
						console.error('验证失败：', errors)
					})
			},

			/**
			 * 手动重置表单
			 */
			resetForm() {
				this.$refs.form.resetFields()
			},
			/**
			 * 部分表单校验
			 * @param {Object} form
			 */
			validateField(form) {
				this.$refs[form]
					.validateField(['name', 'email'])
					.then(res => {
						uni.showToast({
							title: '验证成功'
						})
						console.log('表单的值：', res)
					})
					.catch(errors => {
						console.error('验证失败：', errors)
					})
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
			selCity(e) {
				const [province, city, district] = e.target.value;

				console.log('addressNode：', province, city, district);
			},
			picker: function() {
				document.blur();
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

	}
</style>
