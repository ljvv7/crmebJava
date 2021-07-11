import request from "@/utils/request.js";

// 首页
export function getIndex() {
	return request.get('admin/nsl/index', {}, {
		noAuth: true,
		isDB: true
	});
}

// 获取车辆详情
export function getCraneDetail({
	craneid,
	pageindex = 1,
	pagesize = 9999,
	lat,
	lot
}) {
	return request.post(
		`admin/nsl/crane/getdetail`, {
			craneid,
			pageindex,
			pagesize,
			lat,
			lot
		}, {
			isDB: true,
			isJson: false
		});
}

// 获取品牌列表
export function getBrandList() {
	return request.post('admin/nsl/crane/brandlist', {}, {
		isDB: true
	});
}

// 获取车辆列表
export function getCraneList({
	cbrandid = '',
	craneid = '',
	pageindex = 1,
	pagesize = 9999
}) {
	return request.post('admin/nsl/crane/cranelist', {
		cbrandid,
		craneid,
		pageindex,
		pagesize
	}, {
		isDB: true,
		isJson: false
	});
}

// 车辆配重列表
export function getWeightList({
	craneid,
	pageindex = 1,
	pagesize = 9999
}) {
	return request.post('admin/nsl/crane/weightlist', {
		craneid,
		pageindex,
		pagesize
	}, {
		isDB: true,
		isJson: false
	});
}

// 组合方式列表
export function getWayList({
	craneid = '',
	cwayid = '',
	cweightid = '',
	pageindex = 1,
	pagesize = 9999 
}) {
	return request.post('admin/nsl/crane/waylist', {
		craneid,
		cwayid,
		cweightid,
		pageindex,
		pagesize
	}, {
		isDB: true,
		isJson: false
	});
}

// 车辆添加绑定
export function compbindCrane({
	companyid,
	craneid,
	userid
}) {
	return request.post('admin/nsl/crane/compbindcrane', {
		companyid,
		craneid,
		userid
	}, {
		isDB: true
	});
}


// 公司列表
export function getCompanyList({
	code = '',
	latitude,
	longitude,
	pageindex = 1,
	pagesize = 999
}) {
	return request.post('admin/nsl/company/getAllCompany', {
		code,
		latitude,
		longitude,
		pageindex,
		pagesize,
	}, {
		isJson: true,
		isDB: true
	});
}

// 获取车辆详情
export function getCompanyCarList({
	code = "",
	pageindex = 1,
	pagesize = 9999
}) {
	return request.post(
		`admin/nsl/company/getdetail`, {
			code,
			pageindex,
			pagesize
		}, {
			isDB: true,
			isJson: true
		});
}

// 公司入驻
export function addCompbind(data) {
	return request.post('api/nsl/company/add', {
		data
	}, {
		isDB: true,
		isJson: true
	});
}

// 公司认证提示
export function addCompanyTips() {
	return request.post('admin/nsl/company/authmsgv1');
}

// 我的页面
export function getMeIndex(userId) {
	return request.post(`admin/nsl/user/index/${userId}`, {}, {
		isDB: true,
		isJson: true
	});
}
