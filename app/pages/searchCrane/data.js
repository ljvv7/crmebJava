const genData = (len = 0) => {
	const arr = [];
	for (let i = 1; i <= len; i++) {
		arr.push({
			id: i,
			value: i
		})
	}
	return arr;
}


export const combinationList = [{
	id: 1,
	value: '仅主臂'
}, {
	id: 2,
	value: '主臂+塔矿'
}, {
	id: 3,
	value: '主臂+副臂'
}];

export const raduisList = genData(300);

export const weightList = genData(300);

export const momentPercentageList = [genData(120),genData(120)];

export const mainArmLengthList = [genData(300),genData(300)];

export const jibLengthList = [genData(300),genData(300)];

export const mineTowerLengthList = [genData(300),genData(300)];
