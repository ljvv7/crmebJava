export const loadImg = async (src, canvas) => {
	return new Promise((resolve, reject) => {
		const img = canvas.createImage();
		img.src = src;
		img.onload = () => {
			resolve(img);
		};
		img.onerror = e => {
			reject(e);
		};
	})
}


export class Car {
	constructor(opt) {
		this.x = opt.x
		this.y = opt.y
		this.vx = opt.vx || 20
		this.vy = opt.vy || 1.2
		this.img = opt.img
		this._scaleratio = opt._scaleratio
	}
	render(ctx) {
		ctx.save()
		ctx.translate(this.x, this.y + this.vy * this._scaleratio)
		// ctx.drawImage(this.img, -8 * this._scaleratio, 0, this.vx * this._scaleratio, 180)
		console.log('this._scaleratio', this._scaleratio);
		ctx.drawImage(this.img, -6 * this._scaleratio,6 * this._scaleratio, 97, 57)
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
export class Shaft {
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
export class Arm {
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
export class Obstacle {
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

export const drawGraduation = (canvas, ctx, gx, gy, degree,scaleRatio) => {
	const step = (canvas.width - gx - 50) / degree
	scaleRatio.ratio = step / 10
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

export const animation = uni.createAnimation({
	timingFunction: 'ease-in-out'
})
