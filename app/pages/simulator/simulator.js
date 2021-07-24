// pages/simulator/simulator.js
let _scaleratio = 0,
	dcsrc = '../../asset/img/dc.png'


async function loadImg(src, canvas) {
	return new Promise((res, rej) => {
		this._scaleratio = _scaleratio
		const img = canvas.createImage()
		img.src = dcsrc
		img.onload = () => {
			res(img)
		}
		img.onerror = e => {
			rej(e)
		}
	})
}

function drawGraduation(canvas, ctx, gx, gy, degree) {
	const step = (canvas.width - gx - 50) / degree
	_scaleratio = step / 10
	ctx.beginPath()
	ctx.moveTo(0, gy)
	ctx.lineTo(gx, gy)
	ctx.stroke()
	ctx.font = '18px sans-serif'
	ctx.textAlign = "left";
	for (let i = 0; i <= degree; i++) {
		ctx.beginPath()
		ctx.moveTo(gx + step * i, gy)
		ctx.lineTo(gx + step * i, gy - 15)
		ctx.fillText(10 * i, gx + step * i, gy + 40)
		ctx.moveTo(gx + step * i, gy)
		ctx.lineTo(gx + step * (i + 1), gy)
		ctx.stroke()
	}
	ctx.restore()
	ctx.save()
	ctx.textBaseline = "middle";
	for (var i = 0; i <= degree; i++) {
		ctx.beginPath()
		if (i != 0) {
			ctx.moveTo(gx, gy - step * i)
			ctx.lineTo(gx + 15, gy - step * i)
			ctx.fillText(10 * i, gx - 60, gy - step * i)
		}
		ctx.moveTo(gx, gy - step * i)
		ctx.lineTo(gx, gy - step * (i + 1))
		ctx.stroke()
	}
	ctx.moveTo(gx, gy - step * i)
	ctx.fillText('高度', gx - 80, gy - step * i)
	ctx.restore()
}
class Car {
	constructor(opt) {
		this.x = opt.x
		this.y = opt.y
		this.vx = opt.vx || 20
		this.vy = opt.vy || 1.2
		this.img = opt.img
		this._scaleratio = opt._scaleratio
	}
	render(ctx) {
		this._scaleratio = _scaleratio
		ctx.save()
		ctx.translate(this.x, this.y + this.vy * this._scaleratio)
		ctx.drawImage(this.img, -8 * this._scaleratio, 0, this.vx * this._scaleratio, 180)
		ctx.restore()
		// ctx.translate(this.x, this.y - this.r / 2)
		// ctx.beginPath()
		// ctx.fillStyle = this.color
		// ctx.fillRect(0, 0, -this.vx * this._scaleratio, this.vy * this._scaleratio)
		// ctx.beginPath()
		// ctx.fillRect(-this.vx * this._scaleratio, (this.vy - 5) * this._scaleratio, 2 * this._scaleratio, 5 * this._scaleratio)
		// ctx.beginPath()
		// ctx.fillStyle = 'black'
		// ctx.arc(-(this.vx - 2 * this.r) * this._scaleratio, (this.vy + this.r) * this._scaleratio, this.r * this._scaleratio, 0, 2 * Math.PI)
		// ctx.fill()
		// ctx.beginPath()
		// ctx.arc(-2 * this._scaleratio, (this.vy + this.r) * this._scaleratio, this.r * this._scaleratio, 0, 2 * Math.PI)
		// ctx.fill()
	}
}
class Shaft {
	constructor(opt) {
		this.x = opt.x
		this.y = opt.y
		this.vx = opt.vx || 2
		this.vy = opt.vy || 4
		this.r = opt.r
		// this.rotate = opt.rotate || 0
		this.color = opt.color
		this._scaleratio = opt._scaleratio
	}
	render(ctx) {
		this._scaleratio = _scaleratio
		ctx.save()
		ctx.strokeStyle = this.color
		ctx.lineWidth = this.r
		ctx.translate(this.x, this.y - this.r / 2 - this.vy * this._scaleratio)
		ctx.rotate(this.rotate * Math.PI / 180)
		ctx.beginPath()
		ctx.moveTo(0, 0)
		ctx.lineTo(-this.vx * this._scaleratio, 0)
		ctx.stroke()
		ctx.restore()
	}
}
class Arm {
	constructor(opt) {
		this.x = opt.x
		this.y = opt.y
		this.vx = opt.vx || -2
		this.vy = opt.vy || 4
		this.jib = opt.jib
		this.r = opt.r
		this.arc = opt.arc
		this.rotate = opt.rotate || 0
		this.visable = opt.visable
		this.color = opt.color
		this._scaleratio = opt._scaleratio
		this.dx = this.jib * Math.cos(this.arc * Math.PI / 180) * this._scaleratio
		this.dy = -this.jib * Math.sin(this.arc * Math.PI / 180) * this._scaleratio
	}
	render(ctx) {
		this._scaleratio = _scaleratio
		if (this.visable) {
			this.dx = this.jib * Math.cos(this.arc * Math.PI / 180) * this._scaleratio
			this.dy = -this.jib * Math.sin(this.arc * Math.PI / 180) * this._scaleratio
			ctx.save()
			ctx.strokeStyle = this.color
			ctx.lineWidth = this.r
			ctx.translate(this.x + this.vx * this._scaleratio, this.y - this.vy * this._scaleratio)
			ctx.rotate(this.rotate * Math.PI / 180)
			ctx.beginPath()
			ctx.moveTo(0, 0)
			ctx.lineTo(this.dx, this.dy)
			ctx.stroke()
			ctx.restore()
		}
	}
	//更新连接点坐标
	update(previous) {
		this.x = previous.x + previous.dx
		this.y = previous.y + previous.dy
	}
}
class Obstacle {
	constructor(opt) {
		this.x = opt.x
		this.y = opt.y
		this.vx = opt.vx
		this.vy = opt.vy
		this.r = opt.r
		this.color = opt.color
		this.visable = opt.visable
		this._scaleratio = opt._scaleratio
	}
	render(ctx) {
		this._scaleratio = _scaleratio
		if (this.visable) {
			ctx.save()
			ctx.lineWidth = this.r
			ctx.strokeStyle = this.color
			ctx.translate(this.x + this.vx * this._scaleratio, this.y)
			ctx.beginPath()
			ctx.moveTo(0, 0)
			ctx.lineTo(0, -this.vy * this._scaleratio)
			ctx.stroke()
			ctx.restore()
		}
	}
}
// 动画参数
let animation = wx.createAnimation({
	timingFunction: 'ease-in-out'
})
const app = getApp()

//各种初始化参数
let types = {
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
	grids = [{
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
	legtypes = [{
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
	searchs = [{
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
	]

Page({

	/**
	 * 页面的初始数据
	 */
	data: {
		searched: false, //是否为查询车辆
		ctxTimer: '', //定时器
		showCanvas: true, //是否显示canvas
		degree: 8, //刻度尺
		alldevices: [],
		deviceID: '',
		legtypeid: 1,
		searchs: JSON.parse(JSON.stringify(searchs)),
		legtypes: JSON.parse(JSON.stringify(legtypes)),
		grids: JSON.parse(JSON.stringify(grids)),
		animationData: '', //动画参数
		showInfo: true, //查询参数显示
		searchparam: '', //当前查询参数
		canvas: {},
		car: {},
		shaft: {},
		arm: {},
		jib: {},
		tk: {},
		obstacle: {},
		gx: 0, //起始坐标点x
		gy: 0, //起始坐标点y
		step: 0, //网格步长
		types: JSON.parse(JSON.stringify(types)),
		alldetails: [], //参数
		allparams: [], //参数
		sheight: 2, //起升高度
		sradius: 12, //工作半径
		sobstacleX: 2, //跨障碍物距离
		sobstacleY: 0, //吊臂与障碍物的距离
		sweight: '', //额定起重量
		inputweight: 2, //用户输入的起重量
		sratio: 0, //力矩百分比
		sratiogreen: false,
		sratioyellow: false,
		sratiored: false,
		obstacleSetting: false,
		obstacleParam: {
			distance: 20,
			height: 5
		},
		weights: [],
		currentWeight: null,
		currentWeightKey: '配重',
		ways: [],
		chooseWays: [],
		chooseWaysTips: [],
		currentWay: null,
		currentWayTips: '组合方式',
		currentSearchParam: {
			brandId: 0,
			craneId: 0,
			primaryLength: 0,
			radius: 0,
			minorLength: 0,
			weightId: 0,
			way: '',
			legType: 0,
		}
	},
	async resetAll() {
		console.log('resetAll')
		clearInterval(this.data.ctxTimer)
		this.setData({
			types: JSON.parse(JSON.stringify(types)),
			searchs: JSON.parse(JSON.stringify(searchs)),
			legtypes: JSON.parse(JSON.stringify(legtypes)),
			grids: JSON.parse(JSON.stringify(grids)),
			obstacleParam: {
				distance: 20,
				height: 5
			},
			alldetails: [], //参数
			allparams: [], //参数
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
			searched: false, //是否为查询车辆
			deviceID: '',
			degree: 8, //刻度尺
			ctxTimer: null, //定时器
			weights: [],
			currentWeight: null,
			currentWeightKey: '配重',
			ways: [],
			chooseWays: [],
			chooseWaysTips: [],
			currentWay: null,
			currentWayTips: '组合方式',
			currentSearchParam: {
				brandId: 0,
				craneId: 0,
				primaryLength: 0,
				radius: 0,
				minorLength: 0,
				weightId: 0,
				way: '',
				legType: 0,
			}
		})
		try {
			let {
				brands,
				devices
			} = await app.$http({
				url: '/setting'
			})

			// brands = await app.$request({url:'/api/admin/nsl/crane/brandlist'});
			// console.log('res====',brands[0]);

			// await app.$request({url:'/api/admin/nsl/crane/cranelist',data:{pageindex:1,pagesize:999,cbrandid:brands[0]&&brands[0].id}});
			brands = brandRes.data.map(brand => {
				brand.selected = false
				return brand
			})
			devices = devices.map(device => {
				device.selected = false
				return device
			})
			this.setData({
				'searchs[0].datas': brands,
				alldevices: devices
			})
			// console.log(this.data.searchs,666)
		} catch (e) {
			console.log(e)
			wx.showToast({
				title: e,
			})
		}
		// this.calc()
		this.onReady()
	},
	closeInfo() {
		const searchs = this.data.searchs.map(search => {
			search.selected = false
			return search
		})
		animation.height(0).opacity(0).step()
		this.setData({
			showInfo: true,
			searchparam: '',
			animationData: animation.export(),
			searchs,
			showCanvas: true
		})
	},
	gridShow(e) {
		const searchgrid = e.currentTarget.dataset.grid.name,
			id = e.currentTarget.dataset.grid.id,
			degree = e.currentTarget.dataset.grid.degree
		let grids = this.data.grids.map(grid => {
			grid.selected = grid.id === id ? true : false
			return grid
		})
		this.setData({
			grids,
			degree
		})
		this.calc()
	},
	setParam(id, opt) {
		// console.log(id, opt, this.data.allparams)
		this.setData({
			legtypeid: id,
			'types.armlength.index': 0,
			'types.jiblength.index': 0,
			'types.tklength.index': 0,
			'types.armangle.index': 0,
			'types.jibangle.index': 0,
			'types.tkangle.index': 0,
		})
		let legtypes = this.data.legtypes.map(legtype => {
				legtype.selected = legtype.id === id ? true : false
				return legtype
			}),
			params
		if (this.data.searched) {
			params = opt || this.data.allparams.find(param => param.leg_type == id).params
		}
		if (id == 1) {
			if (this.data.searched) {
				// params = opt || this.data.allparams.find(param => param.leg_type == id).params
				this.setData({
					legtypes,
					'types.jiblength.visable': false,
					'types.jibangle.visable': false,
					'types.tklength.visable': false,
					'types.tkangle.visable': false,
					'jib.visable': false,
					'tk.visable': false,
					'types.armlength.args': (params.primary && params.primary.length) ? params.primary :
						[],
					'types.armlength.value': (params.primary && params.primary.length) ? params.primary[
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
			if (this.data.searched) {
				// params = this.data.allparams.find(param => param.leg_type == id).params
				this.setData({
					legtypes,
					'types.jiblength.visable': true,
					'types.jibangle.visable': true,
					'jib.visable': true,
					'types.tklength.visable': false,
					'types.tkangle.visable': false,
					'tk.visable': false,
					'types.armlength.args': (params.primary && params.primary.length) ? params.primary :
						[],
					'types.armlength.value': (params.primary && params.primary.length) ? params.primary[
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
					'types.jibangle.value': (params.angle && params.angle.length) ? params.angle[0] : 0,
					'types.jibangle.limitMin': (params.angle && params.angle.length) ? params.angle[0] :
						0,
					'types.jibangle.limitMax': (params.angle && params.angle.length) ? params.angle[
						params.primary.length - 1] : 90,
					'jib.arc': (params.angle && params.angle.length) ? this.data.arm.arc - params.angle[
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
			if (this.data.searched) {
				// params = this.data.allparams.find(param => param.leg_type == id).params
				this.setData({
					legtypes,
					'types.jiblength.visable': false,
					'types.jibangle.visable': false,
					'jib.visable': false,
					'types.tklength.visable': true,
					'types.tkangle.visable': true,
					'tk.visable': true,
					'types.armlength.args': (params.primary && params.primary.length) ? params.primary :
						[],
					'types.armlength.value': (params.primary && params.primary.length) ? params.primary[
						0] : 10,
					'types.armlength.limitMin': (params.primary && params.primary.length) ? params
						.primary[0] : 10,
					'types.armlength.limitMax': (params.primary && params.primary.length) ? params
						.primary[params.primary.length - 1] : 100,
					'arm.jib': (params.primary && params.primary.length) ? params.primary[0] : 10,
					'types.tklength.args': (params.minor && params.minor.length) ? params.minor : [],
					'types.tklength.value': (params.minor && params.minor.length) ? params.minor[0] :
						10,
					'types.tklength.limitMin': (params.minor && params.minor.length) ? params.minor[0] :
						10,
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
		// console.log(id, opt, params)
		this.calc()
	},
	typeShow(e) {
		const id = e.currentTarget.dataset.legtype.id
		this.setParam(id)
	},
	infoShow(e) {
		const searchparam = e.currentTarget.dataset.param.id,
			id = e.currentTarget.dataset.param.id
		let searchs = this.data.searchs.slice()

		if (searchparam === this.data.searchparam) {
			searchs = searchs.map(search => {
				search.selected = false
				return search
			})
			animation.height(0).opacity(0).step()
			this.setData({
				showInfo: true,
				searchparam: '',
				animationData: animation.export(),
				showCanvas: true,
				searchs
			})
		} else {
			searchs = searchs.map(search => {
				search.selected = search.id === id ? true : false
				return search
			})
			animation.height('25vh').opacity(1).step()
			this.setData({
				searchparam,
				animationData: animation.export(),
				showInfo: false,
				showCanvas: false,
				searchs
			})
		}
	},
	async selectInfo(e) {
		let index = e.currentTarget.dataset.index,
			info = e.currentTarget.dataset.info,
			id = e.currentTarget.dataset.info.id,
			name = e.currentTarget.dataset.info.name
		// console.log(index, id,e)
		//过滤车型
		if (index == 0) {
			let brand
			let datas = this.data.searchs[0].datas.map(data => {
				if (data.id == id) {
					data.selected = !data.selected
					brand = data
				} else {
					data.selected = false
				}
				return data
			})
			const devices = this.data.alldevices.filter(device => device.brand_id === id)
			const devicesdata = devices.map(device => device.selected = false)
			this.setData({
				'searchs[0].name': brand.selected ? name : '吊车品牌',
				'searchs[0].datas': datas,
				'searchs[1].name': '车型',
				'searchs[1].datas': devices,
				deviceID: ''
			})
			// console.log(datas, id, name, devices)
		}
		//选择车型，请求参数
		if (index == 1) {
			let device
			let datas = this.data.searchs[1].datas.map(data => {
				if (data.id == id) {
					data.selected = !data.selected
					device = data
				} else {
					data.selected = false
				}
				return data
			})
			const showdeviceID = datas.find(data => data.id == id)
			const deviceID = showdeviceID.selected ? id : 0
			this.setData({
				'searchs[1].name': device.selected ? name : '车型',
				'searchs[1].datas': datas,
				deviceID
			})
			// console.log(deviceID)
		}

	},
	cancelbtn() {
		let datas = this.data.searchs[0].datas.map(data => {
			data.selected = false
			return data
		})
		this.setData({
			deviceID: '',
			'searchs[0].name': '吊车品牌',
			'searchs[0].datas': datas,
			'searchs[1].name': '车型',
			'searchs[1].datas': [],
		})
		this.closeInfo()
	},
	confirmbtn() {
		if (this.data.deviceID) {
			this.closeInfo()
			this.resetparam(this.data.deviceID)
		} else {
			wx.showToast({
				icon: 'none',
				title: '还未选择车型'
			})
		}
	},
	//输入起重量
	changeCraneweight(e) {
		const inputweight = e.detail.value
		this.setData({
			inputweight
		})
		this.calc()
	},
	//障碍物
	obstacleChange(e) {
		const checked = e.detail.value
		this.setData({
			obstacleSetting: checked.length ? true : false,
			'obstacle.visable': checked.length
		})
		this.calc()
	},
	changeObstacle(e) {
		const target = e.target.dataset.obstacletype,
			inputvalue = e.detail.value
		let inputKey, renderKey
		// console.log(target, inputvalue)
		if (target == 'height') {
			inputKey = 'obstacleParam.height'
			renderKey = 'obstacle.vy'
		} else {
			inputKey = 'obstacleParam.distance'
			renderKey = 'obstacle.vx'
		}
		this.setData({
			[inputKey]: +inputvalue,
			[renderKey]: +inputvalue
		})
		this.calc()
	},
	longpress(e) {
		// if (this.data.searched) return
		const target = e.target.dataset.dctype
		if (target) {
			clearInterval(this.data.ctxTimer)
			let delay = this.data.searched ? 500 : 150
			this.data.ctxTimer = setInterval(() => {
				this.drawDC('long', target)
				this.calc()
			}, delay)
		}
	},
	enddraw() {
		clearInterval(this.data.ctxTimer)
		this.setData({
			ctxTimer: null
		})
	},
	inputChange(e) {
		const target = e.target.dataset.inputtype,
			inputvalue = e.detail.value;
		// console.log(target, inputvalue)
		this.drawDC('short', target, inputvalue)
		this.calc()
	},
	btnChange(e) {
		if (this.data.ctxTimer) return
		const target = e.target.dataset.dctype
		if (target) {
			this.drawDC('short', target)
			this.calc()
		}
	},
	drawDC(flag, target, inputvalue) {
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
			searched = this.data.searched,
			calcStep = searched ? 1 : 0.5
		let currentParam = this.data.types,
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
					if (pvalue >= this.data.types.armangle.value) {
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
			this.data.shaft.vy = pvalue
			this.data.arm.vy = pvalue
			this.data.jib.vy = pvalue
			this.data.tk.vy = pvalue
		}
		// 绞轴距离
		if (target.includes('shaftlength')) {
			this.data.arm.vx = -pvalue
			this.data.jib.vx = -pvalue
			this.data.tk.vx = -pvalue
		}
		// 主臂长度和角度
		if (target.includes('armlength')) {
			this.data.arm.jib = pvalue
		}
		if (target.includes('armangle')) {
			this.data.arm.arc = pvalue
			this.data.jib.arc = pvalue - this.data.types.jibangle.value
			//塔况角度处理
			// this.data.tk.arc = this.data.types.tkangle.value + pvalue
		}
		// 副臂长度和角度
		if (target.includes('jiblength')) {
			this.data.jib.jib = pvalue
		}
		if (target.includes('jibangle')) {
			this.data.jib.arc = this.data.arm.arc - pvalue
		}
		// 塔况长度和角度
		if (target.includes('tklength')) {
			this.data.tk.jib = pvalue
		}
		if (target.includes('tkangle')) {
			// this.data.tk.arc = this.data.arm.arc + pvalue
			this.data.tk.arc = pvalue
		}
		// console.log(setkey, pvalue, 555, setkeyIndex, pindex)
		this.setData({
			[setkey]: pvalue,
			[setkeyIndex]: pindex
		})
	},
	
	//计算并绘画
	calc() {
		const ctx = this.data.canvas.getContext('2d'),
			canvas = this.data.canvas,
			legtypeid = this.data.legtypeid
		ctx.clearRect(0, 0, canvas.width, canvas.height)
		drawGraduation(canvas, ctx, this.data.gx, this.data.gy, this.data.degree)
		this.data.car.render(ctx)
		this.data.shaft.render(ctx)
		this.data.arm.render(ctx)
		this.data.jib.update(this.data.arm)
		this.data.jib.render(ctx)
		this.data.tk.update(this.data.arm)
		this.data.tk.render(ctx)
		this.data.obstacle.render(ctx)
		//计算公式
		const jzjl = this.data.types.shaftlength.value
		const zbcd = this.data.types.armlength.value
		const fbcd = this.data.types.jiblength.value
		const tkcd = this.data.types.tklength.value
		const jzgd = this.data.types.shaftheight.value
		const zbjd = this.data.types.armangle.value
		const fbjd = this.data.types.jibangle.value
		const tkjd = this.data.types.tkangle.value
		let sradius = zbcd * Math.cos(zbjd * Math.PI / 180) - jzjl,
			sheight = jzgd + zbcd * Math.sin(zbjd * Math.PI / 180),
			sweight, sratio, sobstacleX, sobstacleY, sratiogreen, sratioyellow, sratiored

		if (this.data.jib.visable) {
			const angle = zbjd - fbjd
			sradius += fbcd * Math.cos(angle * Math.PI / 180)
			sheight += fbcd * Math.sin(angle * Math.PI / 180)
		}
		if (this.data.tk.visable) {
			const angle = tkjd
			sradius += tkcd * Math.cos(angle * Math.PI / 180)
			sheight += tkcd * Math.sin(angle * Math.PI / 180)
		}
		//障碍物距离
		sobstacleX = this.data.obstacleSetting ? Math.floor((sradius - this.data.obstacleParam.distance) * 10) /
			10 : ''
		//吊臂与障碍物距离
		if (sradius > this.data.obstacleParam.distance) {
			sobstacleY = (((this.data.obstacleParam.distance + jzjl) * Math.tan(zbjd * Math.PI / 180) + jzgd) -
				this.data.obstacleParam.height).toFixed(2)
		} else {
			sobstacleY = ''
		}
		// sobstacleY = this.data.obstacleSetting ? Math.floor((sheight - this.data.obstacleParam.height) * 10) / 10 : ''
		//根据臂长匹配额定起重量

		// 判断是否选择了力矩，臂长发生了变化，那就去默认选中某一个组合方式
		if (this.data.searched && this.data.currentWeight) {
			let currentWay = this.data.alldetails.filter(weight => weight.id == this.data.currentWeight).pop()
				.ways.find(way => {
					return (way.primary_length == zbcd) && (this.data.legtypeid == 1 || (way.minor_length ==
						fbcd || way.minor_length == tkcd));
				});

			// this.setData({
			//   currentWayTips: currentWay.way
			// });
		}

		//计算力矩百分比
		let ways = []
		if (this.data.searched && this.data.currentWeight && this.data.currentWayTips) {
			sweight = 0;
			this.data.alldetails.forEach(weight => {
				if (weight.id == this.data.currentWeight) {
					let ways = [];
					weight.ways.forEach(way => {
						if (way.way == this.data.currentWayTips && way.primary_length == zbcd) {
							if (this.data.legtypeid == 2 && this.data.types.jibangle.value ==
								way.angle && way.minor_length == fbcd) {
								ways.push(way);
							} else if (this.data.legtypeid == 1 || way.minor_length == tkcd) {
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

			sratio = sweight == 0 ? 100 : Math.floor(this.data.inputweight / sweight * 100)
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
		// console.log(sratio, sratiogreen,
		//   sratioyellow,
		//   sratiored)
		this.setData({
			sheight: Math.floor(sheight * 10) / 10,
			sradius: Math.floor(sradius * 10) / 10,
			sobstacleX,
			sobstacleY,
			sweight: sweight || 0,
			sratio: sratio || 0,
			sratiogreen: sratiogreen || false,
			sratioyellow: sratioyellow || false,
			sratiored: sratiored || false
		})
	},
	async getAllInfo() {
		try {
			let {
				brands,
				devices
			} = await app.$http({
				url: '/setting'
			})
			// let brands = [],devices=[];
			// brands = await app.$request({url:'/api/admin/nsl/crane/brandlist'});

			// devices =await app.$request({url:'/api/admin/nsl/crane/cranelist',data:{pageindex:1,pagesize:999,cbrandid:brands[0].id}});

			if (this.data.currentSearchParam.brandId && this.data.currentSearchParam.craneId) {
				let name, craneName;
				brands = brandRes.data.map(brand => {
					if (craneName.id == this.data.currentSearchParam.brandId) {
						name = brand.name;
					}
					brand.selected = brand.id == this.data.currentSearchParam.brandId
					return brand
				})

				devices = devices.map(device => {
					if (device.id == this.data.currentSearchParam.craneId) {
						craneName = device.name;
					}

					device.selected = device.id == this.data.currentSearchParam.craneId
					return device
				})

				this.setData({
					searched: true,
					'searchs[0].datas': brands,
					alldevices: devices,
					'searchs[0].name': name,
					'searchs[1].name': craneName,
					deviceID: this.data.currentSearchParam.craneId,
				})
				// console.log('fuck')
				this.resetparam(this.data.currentSearchParam.craneId)
			} else {
				brands = brands.map(brand => {
					brand.selected = false
					return brand
				})
				devices = devices.map(device => {
					device.selected = false
					return device
				})
				this.setData({
					'searchs[0].datas': brands,
					alldevices: devices
				})
			}
			// console.log(this.data.searchs,666)
		} catch (e) {
			console.log(e)
			wx.showToast({
				title: e,
			})
		}
	},
	/**
	 * 生命周期函数--监听页面加载
	 */
	onLoad(options) {
		let searchData = app.globalData.searchResult;

		if (searchData) {
			this.setData({
				'currentSearchParam.brandId': searchData.brandId,
				'currentSearchParam.craneId': searchData.id,
				'currentSearchParam.primaryLength': searchData.plength,
				'currentSearchParam.radius': searchData.radius,
				'currentSearchParam.minorLength': 0,
				'currentSearchParam.weightId': searchData.weight,
				'currentSearchParam.way': searchData.way,
				'currentSearchParam.legType': searchData.legtype,
			});
		}

		this.getAllInfo()
		app.globalData.searchResult = null;
	},
	/**
	 * 生命周期函数--监听页面初次渲染完成
	 */
	onReady: function() {
		const query = wx.createSelectorQuery()
		query.select('#diaoche')
			.fields({
				node: true,
				size: true
			})
			.exec(async res => {
				const canvas = res[0].node
				// const ctx = wx.createCanvasContext('diaoche')
				const ctx = canvas.getContext('2d')
				const dpr = wx.getSystemInfoSync().pixelRatio
				canvas.width = res[0].width * dpr
				canvas.height = res[0].height * dpr
				const gy = canvas.height - 25 * dpr,
					gx = 40 * dpr,
					_scaleratio = (canvas.width - gx - 50) / this.data.degree / 10
				const img = await loadImg(dcsrc, canvas)
				let car = new Car({
					x: gx,
					y: gy - 35 * dpr,
					img,
					_scaleratio
				})
				let shaft = new Shaft({
					x: gx,
					y: gy,
					vx: 6,
					r: 15,
					color: 'orange',
					_scaleratio
				})
				let arm = new Arm({
					x: gx,
					y: shaft.y - shaft.r / 2,
					jib: this.data.types.armlength.value,
					arc: 60,
					r: 15,
					visable: true,
					color: 'red',
					_scaleratio
				})
				let jib = new Arm({
					x: arm.x + arm.dx,
					y: arm.y + arm.dy,
					jib: this.data.types.jiblength.value,
					arc: 60,
					r: 10,
					visable: false,
					color: 'blue',
					_scaleratio
				})
				let tk = new Arm({
					x: arm.x + arm.dx,
					y: arm.y + arm.dy,
					jib: this.data.types.tklength.value,
					arc: 0,
					r: 10,
					visable: false,
					color: 'green',
					_scaleratio
				})
				let obstacle = new Obstacle({
					x: gx + 5,
					y: gy,
					r: 15,
					vx: this.data.obstacleParam.distance,
					vy: this.data.obstacleParam.height,
					visable: false,
					color: 'grey',
					_scaleratio
				})
				this.setData({
					gx,
					gy,
					car,
					shaft,
					arm,
					jib,
					tk,
					obstacle,
					canvas
				})
				this.calc()
			})
	},
	async resetparam(id) {
		try {
			//只获取配重信息2020-03-20下午改
			let onlyWeights = await app.$http({
				url: `/crane/weight/${id}`
			})
			//2020-03-20下午改
			// let {
			//   details: alldetails,
			//   params: allparams
			// } = await app.$http({

			//   url: `/crane/ways/${id}`
			// })
			// // console.log(alldetails, allparams) 
			// const legtypeids = allparams.map(param => param.leg_type)
			// const legtypes = this.data.legtypes.map(legtype => {
			//   legtype.selected = legtype.id == 1
			//   legtype.visable = legtypeids.includes(legtype.id)
			//   return legtype
			// })
			// let args = allparams.find(p => p.leg_type == 1)
			// 配重重量
			let weights = onlyWeights.map(v => {
				return {
					id: v.id,
					value: (v.out_weight ? '超起' : '') + (v.leg_type == 1 ? v.crane_weight + 't' :
						'') + ((v.other_weight != 0) ? ((v.leg_type == 2 ? '副臂' : '塔况') + v
						.other_weight + 't') : '') + (v.back_length > 0 ? `后移${v.back_length}` :
						''),
					key: v.crane_weight + 't',
					back_length: v.back_length,
					weight: v.crane_weight,
					leg_type: v.leg_type,
					out_weight: v.out_weight,
					other_weight: v.other_weight,
				}
			});

			// 排序 (主臂，超起，副臂，超起副臂，塔况。)
			weights = weights.sort((a, b) => {
				if (a.leg_type == b.leg_type && a.out_weight == b.out_weight) {
					if (a.weight == 0 && b.weight == 0) {
						return b.other_weight - a.other_weight
					}
					return b.weight - a.weight
				}
				return a.leg_type - b.leg_type
			})

			// 组合方式
			// console.log(args, 555)
			this.setData({
				weights,
			})
			// console.log(this.data.types.armlength, 666)
		} catch (e) {
			console.log(e)
		}
	},
	/**
	 * 生命周期函数--监听页面显示
	 */
	async onShow() {
		var pages = getCurrentPages() //获取加载的页面
		var currentPage = pages[pages.length - 1] //获取当前页面的对象
		var url = currentPage.route //当前页面url
		app.globalData.currentURL = url
		if (app.globalData.craneId) {
			const id = app.globalData.craneId
			this.resetparam(id)
			app.globalData.craneId = ''
			await this.getAllInfo()
			let devicename, brandID
			let devicedatas = this.data.alldevices.map(data => {
				if (data.id == id) {
					data.selected = true
					devicename = data.name
					brandID = data.brand_id
				} else {
					data.selected = false
				}
				return data
			})
			let brandname
			let branddatas = this.data.searchs[0].datas.map(data => {
				if (data.id == brandID) {
					data.selected = true
					brandname = data.name
				} else {
					data.selected = false
				}
				return data
			})
			this.setData({
				'searchs[0].name': brandname,
				'searchs[0].datas': branddatas,
				'searchs[1].name': devicename,
				'searchs[1].datas': devicedatas,
				deviceID: id
			})
		} else {
			//进入当前页面是否需要刷新，需要则执行resetAll
			// this.resetAll()
		}
	},

	async changeWeight(ev) {
		wx.showLoading()
		let weightInfo = this.data.weights[parseInt(ev.detail.value)];
		let legtypeid = weightInfo['leg_type']
		let ways = await app.$http({
			url: `/crane/weights/${weightInfo['id']}/way`
		})
		weightInfo.ways = ways;
		wx.hideLoading();

		let legtypes = this.data.legtypes.map(legtype => {
			legtype.selected = legtype.id == legtypeid ? true : false
			legtype.visable = legtype.id == legtypeid
			return legtype
		});

		let chooseWays = {};
		ways.forEach(v => {
			if (legtypeid == 1) {
				chooseWays[v.way] = v.way + '-' + v.primary_length + 'm';
			} else {
				chooseWays[v.way] = v.way + '-' + v.primary_length + 'm-' + v.minor_length + 'm';
			}
		});
		let chooseWayValue = Object.values(chooseWays);
		chooseWayValue = chooseWayValue.sort((a, b) => {
			return a.split('-')[1].replace('m', '') - b.split('-')[1].replace('m', '')
		});

		let primary = [],
			minor = [],
			angle = [];
			// angle: null
			// craneid: 29
			// id: 46869
			// legtype: 1
			// minorLength: "0.00"
			// origin: null
			// primaryLength: "10.80"
			// radius: "3.00"
			// way: "0000"
			// weight: "25.00"
			// weightid: 100
		ways.forEach(sv => {
			if (primary.indexOf(sv.primary_length) === -1) {
				primary.push(sv.primary_length);
			}

			if (sv.minor_length != 0 && minor.indexOf(sv.minor_length) === -1) {
				minor.push(sv.minor_length);
			}

			if (sv.hasOwnProperty('angle') && angle.indexOf(sv.angle) === -1) {
				angle.push(sv.angle)
			}
		})

		this.setData({
			currentWeight: this.data.weights[parseInt(ev.detail.value)]['id'],
			currentWeightKey: this.data.weights[parseInt(ev.detail.value)]['key'],
			chooseWays: chooseWayValue.map(v => {
				return v.split('-').shift()
			}),
			chooseWaysTips: chooseWayValue,
			legtypes,
			legtypeid,
			alldetails: [
				weightInfo
			]
		});

		this.setParam(this.data.legtypeid, {
			primary,
			minor,
			angle
		});

		if (this.data.currentSearchParam.way) {
			let index = 0;
			chooseWayValue.forEach((v, i) => {
				if (v.split('-').shift() == this.data.currentSearchParam.way) {
					index = i;
				}
			})

			this.changeCraneWay({
				detail: {
					value: index
				}
			})
		}
	},

	// 更改组合方式
	changeCraneWay(ev) {
		let primary = [],
			minor = [],
			angle = [];
		this.data.alldetails.forEach(v => {
			if (v.id == this.data.currentWeight) {
				v.ways.forEach(sv => {
					if (sv.way == this.data.chooseWays[parseInt(ev.detail.value)]) {
						if (primary.indexOf(sv.primary_length) === -1) {
							primary.push(sv.primary_length);
						}

						if (sv.minor_length != 0 && minor.indexOf(sv.minor_length) === -1) {
							minor.push(sv.minor_length);
						}

						if (sv.hasOwnProperty('angle') && angle.indexOf(sv.angle) === -1) {
							angle.push(sv.angle)
						}
					}
				})
			}
		});
		// console.log(primary, minor, angle, 666);

		if (this.data.currentSearchParam.primaryLength) {
			let angle = Number((Math.acos(this.data.currentSearchParam.radius / this.data.currentSearchParam
				.primaryLength) / Math.PI * 180).toFixed(1));
			// 判断臂长，设置臂长
			this.setData({
				'arm.jib': this.data.currentSearchParam.primaryLength,
				'arm.arc': angle,
				'jib.arc': angle,
				'types.armlength.index': this.data.types.armlength.args.indexOf(this.data
					.currentSearchParam.primaryLength),
				'types.armlength.value': this.data.currentSearchParam.primaryLength,
				'types.armangle.value': angle,
			})
		}

		minor = minor.sort((a, b) => {
			return a - b
		});
		angle = angle.sort((a, b) => {
			return a - b
		});

		this.setData({
			currentWay: parseInt(ev.detail.value),
			currentWayTips: this.data.chooseWays[parseInt(ev.detail.value)],
			searched: true,
		})
		this.setParam(this.data.legtypeid, {
			primary,
			minor,
			angle
		})
	}
})

