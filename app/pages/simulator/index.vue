<!-- pages/simulator/simulator.wxml -->
<template>
	<view class="simulator">
		<headerBox />
		<canvas type="2d" id="diaoche" canvas-id="diaoche" class="canvas">
			<cover-view class="dcboard">
				<cover-view class="dcboard-content">
					<cover-view class="dcboard-content-item">
						<span>高度:</span><span>{{sheight}}m</span>
					</cover-view>
					<cover-view class="dcboard-content-item">
						<span>工作半径:</span><span>{{sradius}}m</span>
					</cover-view>
					<cover-view v-if="obstacleSetting" class="dcboard-content-item">
						<span>跨障碍距离:</span><span>{{sobstacleX}}m</span>
					</cover-view>
					<cover-view v-if="obstacleSetting" class="dcboard-content-item">
						<span>吊臂与障碍物距离:</span><span>{{sobstacleY}}m</span>
					</cover-view>
					<cover-view v-if="!searched" class="dcboard-content-item">
						<span>额定起重量:</span><span>{{sweight}}m</span>
					</cover-view>
					<cover-view v-if="!searched" class="dcboard-content-item">
						<span>力矩百分比:</span><span>{{sratio}}%</span>
						<cover-view class="sratiobox outweight-tips"
							:class="{ 'ratio-green': sratiogreen,'ratio-yellow': sratioyellow,'ratio-red': sratiored }">
						</cover-view>
						<cover-view style="color: red;" v-if="sratio > 100">超载危险</cover-view>
					</cover-view>
				</cover-view>
			</cover-view>
		</canvas>
		<footerBox :legtypes="legtypes" :types="types" />
	</view>
</template>
<script>
	import headerBox from './component/headerBox.vue'
	import footerBox from './component/footerBox.vue'
	import {
		loadImg,
		Car,
		Shaft,
		Arm,
		Obstacle,
		drawGraduation,
		animation,
	} from './utils/index.js'

	const scaleRatio = {
		ratio: 0
	};
	const dcsrc = '/static/images/crane/dc_b.png'
	export default {
		components: {
			headerBox,
			footerBox
		},
		data() {
			return {
				searched: false,
				degree: 8,
				degreeArr: [8, 15, 20],
				ctx: uni.createCanvasContext('diaoche'),
				types: {
					shaftlength: {
						name: '铰轴距离',
						args: [],
						index: 0,
						placeholder: '',
						value: 2,
						limitMax: 6,
						limitMin: 1,
						visable: true
					},
					armlength: {
						name: '主臂长度',
						args: [],
						index: 0,
						placeholder: '',
						value: 10,
						limitMax: 150,
						limitMin: 10,
						visable: true
					},
					jiblength: {
						name: '副臂长度',
						args: [],
						index: 0,
						placeholder: '',
						value: 8,
						limitMax: 200,
						limitMin: 1,
						visable: false
					},
					tklength: {
						name: '塔况长度',
						args: [],
						index: 0,
						placeholder: '2.0米',
						value: 6,
						limitMax: 100,
						limitMin: 1,
						visable: false
					},
					shaftheight: {
						name: '铰轴高度',
						args: [],
						index: 0,
						placeholder: '2.0米',
						value: 4,
						limitMax: 10,
						limitMin: 1,
						visable: true
					},
					armangle: {
						name: '主臂角度',
						args: [],
						index: 0,
						placeholder: '0',
						value: 60,
						limitMax: 90,
						limitMin: 0,
						visable: true
					},
					jibangle: {
						name: '副臂角度',
						args: [],
						index: 0,
						placeholder: '0',
						value: 0,
						limitMax: 90,
						limitMin: 0,
						visable: false
					},
					tkangle: {
						name: '塔况角度',
						args: [],
						index: 0,
						placeholder: '0',
						value: 0,
						limitMax: 180,
						limitMin: 0,
						visable: false
					}
				},
				legtypes: [{
						id: 1,
						name: '仅主臂',
						selected: true,
						visable: true
					},
					{
						id: 2,
						name: '主臂+副臂',
						selected: false,
						visable: true
					},
					{
						id: 3,
						name: '主臂+塔况',
						selected: false,
						visable: true
					}
				],
				searchs: [{
						id: 0,
						name: '吊车品牌',
						type: 'brand',
						selected: false,
						datas: [],
					},
					{
						id: 1,
						name: '车型',
						type: 'device',
						selected: false,
						datas: [],
					}
				],
				obstacleParam: {
					distance: 20,
					height: 5
				},
				gridIndex: 0,
				grids: [{
						id: 0,
						name: '80 x 80',
						degree: 8,
						selected: true
					},
					{
						id: 1,
						name: '150 x 150',
						degree: 15,
						selected: false
					},
					{
						id: 2,
						name: '200 x 200',
						degree: 20,
						selected: false
					}
				],
				sheight: 12.6, //起升高度
				sradius: 3, //工作半径
				sobstacleX: 2, //跨障碍物距离
				sobstacleY: 0, //吊臂与障碍物距离
				sweight: '', //额定起重量
				inputweight: 2, //用户输入的起重量
				sratio: 0, //力矩百分比
				sratiogreen: false,
				sratioyellow: false,
				sratiored: false,
				obstacleSetting: false,
				obstacleSetting: false,
				legtypeid: 1,
			}
		},
		async onLoad() {
			this.initCanvas();
		},
		computed: {},
		methods: {
			//  变化网格数量
			gridShow(index) {
				const grid = this.grids[index];
				const searchgrid = grid.name;
				const id = grid.id;
				const degree = grid.degree
				let grids = this.grids.map(grid => {
					grid.selected = grid.id === id ? true : false
					return grid
				});
				this.setData({
					grids,
					degree
				})
				this.calc()
			},
			changeObstacle: function() {

			},
			childrenClick: function(str) {

			},
			initCanvas: function() {
				const _this = this;
				const query = uni.createSelectorQuery().in(_this);
				query.select('#diaoche')
					.fields({
						node: true,
						size: true
					}).exec(async res => {
						const canvas = res[0].node
						const ctx = canvas.getContext('2d');
						const dpr = uni.getSystemInfoSync().pixelRatio;
						canvas.width = res[0].width * dpr;
						canvas.height = res[0].height * dpr;
						const gy = canvas.height - 25 * dpr;
						const gx = 40 * dpr;
						const degree = 8;
						const _scaleratio = (canvas.width - gx - 50) / _this.degree / 10;
						const img = await loadImg(dcsrc, canvas);
						let car = new Car({
							x: gx,
							y: gy - 50 * dpr,
							img,
							_scaleratio
						});
						let shaft = new Shaft({
							x: gx,
							y: gy,
							vx: 6,
							r: 15,
							color: '#F09E5D',
							_scaleratio
						});
						let arm = new Arm({
							x: gx,
							y: shaft.y - shaft.r / 2,
							jib: _this.types.armlength.value,
							arc: 60,
							r: 15,
							visable: true,
							color: '#DE6853',
							_scaleratio
						});
						let jib = new Arm({
							x: arm.x + arm.dx,
							y: arm.y + arm.dy,
							jib: _this.types.jiblength.value,
							arc: 60,
							r: 10,
							visable: false,
							color: '#33ADFF',
							_scaleratio
						});
						let tk = new Arm({
							x: arm.x + arm.dx,
							y: arm.y + arm.dy,
							jib: _this.types.tklength.value,
							arc: 0,
							r: 10,
							visable: false,
							color: '#1DC76E',
							_scaleratio
						});
						let obstacle = new Obstacle({
							x: gx + 5,
							y: gy,
							r: 15,
							vx: _this.obstacleParam.distance,
							vy: _this.obstacleParam.height,
							visable: false,
							color: '#645446',
							_scaleratio
						});

						_this.gx = gx;
						_this.gy = gy;
						_this.car = car;
						_this.shaft = shaft;
						_this.arm = arm;
						_this.jib = jib;
						_this.tk = tk;
						_this.obstacle = obstacle;
						_this.canvas = canvas;

						_this.calc()
					})
			},
			//计算并绘画
			calc: function() {
				const _this = this;
				const ctx = _this.canvas.getContext('2d'),
					canvas = _this.canvas,
					legtypeid = _this.legtypeid
				ctx.clearRect(0, 0, canvas.width, canvas.height)
				drawGraduation(canvas, ctx, _this.gx, _this.gy, _this.degree, scaleRatio)
				console.log('drawGraduation----', scaleRatio);
				_this.car._scaleratio = scaleRatio.ratio;
				_this.shaft._scaleratio = scaleRatio.ratio;
				_this.arm._scaleratio = scaleRatio.ratio;
				_this.jib._scaleratio = scaleRatio.ratio;
				_this.tk._scaleratio = scaleRatio.ratio;
				_this.obstacle._scaleratio = scaleRatio.ratio;
				_this.canvas._scaleratio = scaleRatio.ratio;

				_this.car.render(ctx, {
					_scaleratio: scaleRatio.ratio
				})
				_this.shaft.render(ctx)
				_this.arm.render(ctx)
				_this.jib.update(_this.arm)
				_this.jib.render(ctx)
				_this.tk.update(_this.arm)
				_this.tk.render(ctx)
				_this.obstacle.render(ctx)
				//计算公式
				const jzjl = _this.types.shaftlength.value
				const zbcd = _this.types.armlength.value
				const fbcd = _this.types.jiblength.value
				const tkcd = _this.types.tklength.value
				const jzgd = _this.types.shaftheight.value
				const zbjd = _this.types.armangle.value
				const fbjd = _this.types.jibangle.value
				const tkjd = _this.types.tkangle.value
				let sradius = zbcd * Math.cos(zbjd * Math.PI / 180) - jzjl,
					sheight = jzgd + zbcd * Math.sin(zbjd * Math.PI / 180),
					sweight, sratio, sobstacleX, sobstacleY, sratiogreen, sratioyellow, sratiored

				if (_this.jib.visable) {
					const angle = zbjd - fbjd
					sradius += fbcd * Math.cos(angle * Math.PI / 180)
					sheight += fbcd * Math.sin(angle * Math.PI / 180)
				}
				if (_this.tk.visable) {
					const angle = tkjd
					sradius += tkcd * Math.cos(angle * Math.PI / 180)
					sheight += tkcd * Math.sin(angle * Math.PI / 180)
				}
				//障碍物距离
				sobstacleX = _this.obstacleSetting ? Math.floor((sradius - _this.obstacleParam.distance) * 10) /
					10 : ''
				//吊臂与障碍物距离
				if (sradius > _this.obstacleParam.distance) {
					sobstacleY = (((_this.obstacleParam.distance + jzjl) * Math.tan(zbjd * Math.PI / 180) + jzgd) -
						_this.obstacleParam.height).toFixed(2)
				} else {
					sobstacleY = ''
				}
				// sobstacleY = this.data.obstacleSetting ? Math.floor((sheight - this.data.obstacleParam.height) * 10) / 10 : ''
				//根据臂长匹配额定起重量

				// 判断是否选择了力矩，臂长发生了变化，那就去默认选中某一个组合方式
				if (_this.searched && _this.currentWeight) {
					let currentWay = _this.alldetails.filter(weight => weight.id == _this.currentWeight).pop()
						.ways.find(way => {
							return (way.primary_length == zbcd) && (_this.legtypeid == 1 || (way.minor_length ==
								fbcd || way.minor_length == tkcd));
						});

					// this.setData({
					//   currentWayTips: currentWay.way
					// });
				}

				//计算力矩百分比
				let ways = []
				if (_this.searched && _this.currentWeight && _this.currentWayTips) {
					sweight = 0;
					_this.alldetails.forEach(weight => {
						if (weight.id == _this.currentWeight) {
							let ways = [];
							weight.ways.forEach(way => {
								if (way.way == _this.currentWayTips && way.primary_length == zbcd) {
									if (_this.legtypeid == 2 && _this.types.jibangle.value ==
										way.angle && way.minor_length == fbcd) {
										ways.push(way);
									} else if (_this.legtypeid == 1 || way.minor_length == tkcd) {
										ways.push(way);
									}
								}
							});
							ways = ways.sort((a, b) => {
								return a.radius - b.radius
							});

							for (let i = 0; i < ways.length; i++) {
								if (i === 0 && ways[i]['radius'] >= sradius) {
									sweight = ways[i]['weight'];
									break;
								}

								if (ways[i]['radius'] == sradius) {
									sweight = ways[i]['weight'];
									break;
								}

								if (((i + 1) < ways.length) && ways[i]['radius'] < sradius && ways[i + 1][
										'radius'
									] > sradius) {
									// 插值公式进行计算
									sweight = ((ways[i + 1]['radius'] - sradius) / (ways[i + 1]['radius'] -
										ways[i]['radius'])) * ways[i]['weight'] + (((sradius - ways[i][
										'radius'
									]) / (ways[i + 1]['radius'] - ways[i]['radius'])) * ways[i + 1][
										'weight'
									]);
									sweight = sweight.toFixed(1)
									break;
								}
							}
						}
					});

					sratio = sweight == 0 ? 100 : Math.floor(_this.inputweight / sweight * 100)
					if (sratio <= 90) {
						sratiogreen = true
						sratioyellow = false
						sratiored = false
					} else if (sratio > 90 && sratio <= 100) {
						sratiogreen = false
						sratioyellow = true
						sratiored = false
					} else if (sratio > 100) {
						sratiogreen = false
						sratioyellow = false
						sratiored = true
					}
					if (sratio == 0) {
						sratiogreen = false
						sratioyellow = false
						sratiored = false
					}
				}
				_this.sheight = Math.floor(sheight * 10) / 10;
				_this.sradius = Math.floor(sradius * 10) / 10;
				_this.sobstacleX = sobstacleX;
				_this.sobstacleY = sobstacleY;
				_this.sweight = sweight || 0;
				_this.sratio = sratio || 0;
				_this.sratiogreen = sratiogreen || false;
				_this.sratioyellow = sratioyellow || false;
				_this.sratiored = sratiored || false;
			},
			drawDC: function(flag, target, inputvalue) {
				let key
				if (target.indexOf('Plus') > -1) {
					key = target.slice(0, -4)
				} else if (target.indexOf('Minus') > -1) {
					key = target.slice(0, -5)
				} else {
					key = target
				}
				let setkey = `types.${key}.value`,
					setkeyIndex = `types.${key}.index`,
					searched = this.searched,
					calcStep = searched ? 1 : 0.5
				let currentParam = this.types,
					pvalue, pindex, pMax, pMin, pindexlen
				pvalue = currentParam[key].value, pindexlen = currentParam[key].args.length, pindex = currentParam[key]
					.index, pMax = currentParam[key].limitMax, pMin = currentParam[key].limitMin
				// 判断条件
				// console.log(pindex)
				// pvalue >= this.data.armangle.value
				if (target.indexOf('Plus') > -1) {
					if (target === 'shaftlengthPlus' || target === 'shaftheightPlus' || target === 'armanglePlus' ||
						target === 'tkanglePlus') {
						if (pvalue >= pMax) {
							pvalue = pMax
							wx.showToast({
								icon: 'none',
								title: '已经是该车型最大值',
							})
							return
						}
						//塔况角度判断
						if (target === 'tkanglePlus') {
							if (pvalue >= this.types.armangle.value) {
								wx.showToast({
									icon: 'none',
									title: '已经是该车型最大值',
								})
								return
							}
						}
						pvalue = flag == 'short' ? Math.ceil((pvalue + 0.1) * 10) / 10 : Math.ceil((pvalue + 0.5) *
							10) / 10
					} else {
						if (searched) {
							if (pindex >= pindexlen - 1) {
								wx.showToast({
									icon: 'none',
									title: '已经是该车型最大值',
								})
								return
							}
							pindex += calcStep
							pvalue = currentParam[key].args[pindex]
							// console.log(currentParam[key].args, pindex, calcStep, pvalue)
						} else {
							if (pvalue >= pMax) {
								pvalue = pMax
								wx.showToast({
									icon: 'none',
									title: '已经是该车型最大值',
								})
								return
							}
							pvalue += calcStep
						}
					}
				} else if (target.indexOf('Minus') > -1) {
					if (target === 'shaftlengthMinus' || target === 'shaftheightMinus' || target === 'armangleMinus' ||
						target === 'tkangleMinus') {
						if (pvalue <= pMin) {
							pvalue = pMin
							wx.showToast({
								icon: 'none',
								title: '已经是最小值',
							})
							return
						}
						pvalue = flag == 'short' ? Math.floor((pvalue - 0.1) * 10) / 10 : Math.floor((pvalue - 0.5) *
							10) / 10
					} else {
						if (searched) {
							if (pindex <= 0) {
								wx.showToast({
									icon: 'none',
									title: '已经是该车型最小值',
								})
								return
							}
							pindex -= calcStep
							pvalue = currentParam[key].args[pindex]
						} else {
							if (pvalue <= pMin) {
								pvalue = pMin
								wx.showToast({
									icon: 'none',
									title: '已经是该车型最小值',
								})
								return
							}
							pvalue -= calcStep
						}
					}
				} else {
					if (+inputvalue < pMax || +inputvalue > pMin) {
						if (+inputvalue > pMax) {
							pvalue = pMax
						} else if (+inputvalue < pMin) {
							pvalue = pMin
						} else {
							pvalue = +inputvalue
						}
					} else {
						pvalue = currentParam[key].value
					}
				}

				// 绞轴高度
				if (target.includes('shaftheight')) {
					this.shaft.vy = pvalue
					this.arm.vy = pvalue
					this.jib.vy = pvalue
					this.tk.vy = pvalue
				}
				// 绞轴距离
				if (target.includes('shaftlength')) {
					this.arm.vx = -pvalue
					this.jib.vx = -pvalue
					this.tk.vx = -pvalue
				}
				// 主臂长度和角度
				if (target.includes('armlength')) {
					this.arm.jib = pvalue
				}
				if (target.includes('armangle')) {
					this.arm.arc = pvalue
					this.jib.arc = pvalue - this.types.jibangle.value
					//塔况角度处理
					// this.data.tk.arc = this.data.types.tkangle.value + pvalue
				}
				// 副臂长度和角度
				if (target.includes('jiblength')) {
					this.jib.jib = pvalue
				}
				if (target.includes('jibangle')) {
					this.jib.arc = this.arm.arc - pvalue
				}
				// 塔况长度和角度
				if (target.includes('tklength')) {
					this.tk.jib = pvalue
				}
				if (target.includes('tkangle')) {
					// this.data.tk.arc = this.data.arm.arc + pvalue
					this.tk.arc = pvalue
				}
				// console.log(setkey, pvalue, 555, setkeyIndex, pindex)
				this.setData({
					[setkey]: pvalue,
					[setkeyIndex]: pindex
				})
			},
			setData: function(data) {
				const _this = this;
				const keyArr = Object.keys(data);
				keyArr.forEach((key, index) => {
					const keys = key.split('.');
					if (keys.length === 1) {
						_this[keys[0]] = data[key];
					} else if (keys.length === 2) {
						_this[keys[0]][keys[1]] = data[key];
					} else if (keys.length === 3) {
						_this[keys[0]][keys[1]][keys[2]] = data[key];
					} else if (keys.length === 4) {
						_this[keys[0]][keys[1]][keys[2]][keys[3]] = data[key];
					}
				})
			},
			setParam: function(id, opt) {
				this.setData({
					legtypeid: id,
					'types.armlength.index': 0,
					'types.jiblength.index': 0,
					'types.tklength.index': 0,
					'types.armangle.index': 0,
					'types.jibangle.index': 0,
					'types.tkangle.index': 0,
				})
				let legtypes = this.legtypes.map(legtype => {
					legtype.selected = legtype.id === id ? true : false
					return legtype
				});
				let params;
				if (this.searched) {
					params = opt || this.allparams.find(param => param.leg_type == id).params;
				}
				if (id == 1) {
					if (this.searched) {
						// params = opt || this.data.allparams.find(param => param.leg_type == id).params
						this.setData({
							legtypes,
							'types.jiblength.visable': false,
							'types.jibangle.visable': false,
							'types.tklength.visable': false,
							'types.tkangle.visable': false,
							'jib.visable': false,
							'tk.visable': false,
							'types.armlength.args': (params.primary && params.primary.length) ? params
								.primary : [],
							'types.armlength.value': (params.primary && params.primary.length) ? params
								.primary[
									0] : 10,
							'types.armlength.limitMin': (params.primary && params.primary.length) ? params
								.primary[0] : 10,
							'types.armlength.limitMax': (params.primary && params.primary.length) ? params
								.primary[params.primary.length - 1] : 100,
							'arm.jib': (params.primary && params.primary.length) ? params.primary[0] : 10,
							searched: true
						})
						// console.log(params, this.data.types.armlength)
					} else {
						this.setData({
							legtypes,
							'types.jiblength.visable': false,
							'types.jibangle.visable': false,
							'types.tklength.visable': false,
							'types.tkangle.visable': false,
							'jib.visable': false,
							'tk.visable': false
						})
					}

				}
				if (id == 2) {
					if (this.searched) {
						// params = this.data.allparams.find(param => param.leg_type == id).params
						this.setData({
							legtypes,
							'types.jiblength.visable': true,
							'types.jibangle.visable': true,
							'jib.visable': true,
							'types.tklength.visable': false,
							'types.tkangle.visable': false,
							'tk.visable': false,
							'types.armlength.args': (params.primary && params.primary.length) ? params
								.primary : [],
							'types.armlength.value': (params.primary && params.primary.length) ? params
								.primary[
									0] : 10,
							'types.armlength.limitMin': (params.primary && params.primary.length) ? params
								.primary[0] : 10,
							'types.armlength.limitMax': (params.primary && params.primary.length) ? params
								.primary[params.primary.length - 1] : 100,
							'arm.jib': (params.primary && params.primary.length) ? params.primary[0] : 10,
							'types.jiblength.args': (params.minor && params.minor.length) ? params.minor : [],
							'types.jiblength.value': (params.minor && params.minor.length) ? params.minor[0] :
								10,
							'types.jiblength.limitMin': (params.minor && params.minor.length) ? params.minor[
								0] : 10,
							'types.jiblength.limitMax': (params.minor && params.minor.length) ? params.minor[
								params.primary.length - 1] : 100,
							'jib.jib': (params.minor && params.minor.length) ? params.minor[0] : 10,
							'types.jibangle.args': (params.angle && params.angle.length) ? params.angle : [],
							'types.jibangle.value': (params.angle && params.angle.length) ? params.angle[0] :
								0,
							'types.jibangle.limitMin': (params.angle && params.angle.length) ? params.angle[
								0] : 0,
							'types.jibangle.limitMax': (params.angle && params.angle.length) ? params.angle[
								params.primary.length - 1] : 90,
							'jib.arc': (params.angle && params.angle.length) ? this.data.arm.arc - params
								.angle[
									0] : this.data.arm.arc - 0,
							searched: true
						})
						// console.log(params)
					} else {
						this.setData({
							legtypes,
							'types.jiblength.visable': true,
							'types.jibangle.visable': true,
							'jib.visable': true,
							'types.tklength.visable': false,
							'types.tkangle.visable': false,
							'tk.visable': false
						})
					}
				}
				if (id == 3) {
					if (this.searched) {
						// params = this.data.allparams.find(param => param.leg_type == id).params
						this.setData({
							legtypes,
							'types.jiblength.visable': false,
							'types.jibangle.visable': false,
							'jib.visable': false,
							'types.tklength.visable': true,
							'types.tkangle.visable': true,
							'tk.visable': true,
							'types.armlength.args': (params.primary && params.primary.length) ? params
								.primary : [],
							'types.armlength.value': (params.primary && params.primary.length) ? params
								.primary[
									0] : 10,
							'types.armlength.limitMin': (params.primary && params.primary.length) ? params
								.primary[0] : 10,
							'types.armlength.limitMax': (params.primary && params.primary.length) ? params
								.primary[params.primary.length - 1] : 100,
							'arm.jib': (params.primary && params.primary.length) ? params.primary[0] : 10,
							'types.tklength.args': (params.minor && params.minor.length) ? params.minor : [],
							'types.tklength.value': (params.minor && params.minor.length) ? params.minor[0] :
								10,
							'types.tklength.limitMin': (params.minor && params.minor.length) ? params.minor[
								0] : 10,
							'types.tklength.limitMax': (params.minor && params.minor.length) ? params.minor[
								params.primary.length - 1] : 100,
							'tk.jib': (params.minor && params.minor.length) ? params.minor[0] : 10,
							searched: true
						})
						// console.log(params,3)
					} else {
						this.setData({
							legtypes,
							'types.jiblength.visable': false,
							'types.jibangle.visable': false,
							'jib.visable': false,
							'types.tklength.visable': true,
							'types.tkangle.visable': true,
							'types.tkangle.value': 0,
							'tk.arc': 0,
							'tk.visable': true,
						})
					}
				}
				Object.keys(this.types).forEach((key) => {
					console.log(this.types[key].name, this.types[key].visable);
				})
				// console.log(id, opt, params)
				this.calc()
			},
		}
	}
</script>


<style lang="scss" scoped>
	.simulator {
		display: flex;
		flex-direction: column;
		height: 100vh;
		padding: 40upx 24upx;
		box-sizing: border-box;
		overflow: hidden;

		.canvas {
			background-color: #FAFAFA;
			width: 100%;
			height: 688upx;
			flex: 0 0 688upx;

			.dcboard {
				background: rgba(0, 0, 0, 0.50);
				width: 300upx;
				position: absolute;
				top: 0;
				right: 0;

				.dcboard-content {
					padding: 22upx 21upx;

					.dcboard-content-item {
						display: flex;
						justify-content: space-between;

						span {
							line-height: 40upx;
							font-size: 20upx;
							color: #FFFFFF;
						}
					}
				}
			}
		}

		footer-box {
			flex: 1 0 auto;
		}
	}
</style>
