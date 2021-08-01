export const goPage = ({
	url,
	query,
	type = 'page',
	...rest
}) => {
	const realUrl = query ?
		`${url}?${Object.keys(query).reduce((ret,key)=>{return `${ret}${ret&&'&'}${key}=${query[key]}`},'')}` :
		url;

	if (type === 'page') {
		uni.navigateTo({
			url: realUrl,
			...rest
		});
	}
	if (type === 'web') {
		uni.navigateTo({
			url: `/pages/web_view/index?url=${url}`
		});
	}
	if (type === 'tab') {
		uni.switchTab({
			url: realUrl,
			...rest
		});
	}
}
