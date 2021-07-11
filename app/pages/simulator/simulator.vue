<!-- pages/simulator/simulator.wxml -->
<template>
	<view class="simulator">
		<view class="simulator-reset" bindtap="resetAll">重置</view>
		<view class="simulator-tips">仅供参考</view>
		<!-- 查询参数 -->
		<view class="simulator-search">
			<view class="simulator-search-param {{item.selected?'param-selected':''}}" wx:for="{{searchs}}"
				wx:key="name" bindtap="infoShow" data-param="{{item}}">
				{{item.name}}
				<text class="iconfont icon-down {{item.selected?'icon-up param-selected':''}}"></text>
			</view>
			<view class="simulator-search-shadewrap">
				<view wx:for="{{[0,1]}}" wx:key="*this"
					class="simulator-search-shade {{searchparam!==item?'':'shade-show'}}"></view>
			</view>
			<!-- <view hidden="{{showInfo}}" class="simulator-search-mask" catchtap="closeInfo"></view> -->
			<view hidden="{{showInfo}}" class="simulator-search-mask"></view>
			<view animation="{{animationData}}" class="simulator-search-info">
				<view class="simulator-search-content">
					<view class="simulator-search-contentwrap" wx:for="{{searchs}}" hidden="{{!item.selected}}"
						wx:key="id">
						<template v:if="{{!item.datas.length}}">
							<view class="simulator-search-nocontent">暂无数据</view>
						</template>
						<template wx:else>
							<view class="simulator-search-contentitem" wx:for="{{item.datas}}" wx:for-item="data"
								wx:key="name" data-index="{{item.id}}" data-info="{{data}}" bindtap="selectInfo">
								<text>{{data.name}}</text>
								<icon hidden="{{!data.selected}}" type="success_no_circle" color="red" size="18"></icon>
							</view>
						</template>
					</view>
				</view>
				<view hidden="{{searchparam==0}}" class="simulator-search-btn">
					<view class="simulator-search-cancel" catchtap="cancelbtn">取消</view>
					<view class="simulator-search-confirm" catchtap="confirmbtn">确定</view>
				</view>
			</view>
		</view>
		<view class="weight-search-wrapper" style="z-index: {{ showInfo ? 100 : 99 }};">
			<view class="weight-search-item">
				<picker mode="selector" range="{{ weights }}" range-key="value" bindchange="changeWeight">
					<text>{{ currentWeightKey }}</text>
					<text class="iconfont icon-down"></text>
				</picker>
			</view>
			<view class="weight-search-item">
				<picker mode="selector" range="{{ chooseWaysTips }}" bindchange="changeCraneWay">
					<text>{{ currentWayTips }}</text>
					<text class="iconfont icon-down"></text>
				</picker>
			</view>
		</view>
		<view class="dccanvas">
			<view hidden="{{!searched}}" class="dccanavas-input">
				<text class="obstacle-title">起重量(T)(可输入):</text>
				<input class="input-border__obstacle" data-craneweight="wheight" type="number"
					bindblur="changeCraneweight" value="{{inputweight}}" />
			</view>
			<view hidden="{{showCanvas}}"></view>
			<canvas hidden="{{!showCanvas}}" type="2d" id="diaoche" canvas-id="diaoche"
				style="width:100%;height:100%;background-color:rgba(215,215,215,.15)">
				<cover-view class="dcboard">
					<!-- <cover-view class="dcboard-title">测量结果</cover-view> -->
					<cover-view class="dcboard-content">
						<cover-view class="dcboard-content-item">
							<cover-view>高度:{{sheight}}m</cover-view>
						</cover-view>
						<cover-view class="dcboard-content-item">
							<cover-view>工作半径:{{sradius}}m</cover-view>
						</cover-view>
						<cover-view hidden="{{!obstacleSetting}}" class="dcboard-content-item">
							<cover-view>跨障碍距离:{{sobstacleX}}m</cover-view>
						</cover-view>
						<cover-view hidden="{{!obstacleSetting}}" class="dcboard-content-item">
							<cover-view>吊臂与障碍物距离:{{sobstacleY}}m</cover-view>
						</cover-view>
						<cover-view hidden="{{!searched}}" class="dcboard-content-item">
							<cover-view>额定起重量:{{sweight}} T</cover-view>
						</cover-view>
						<cover-view hidden="{{!searched}}" class="dcboard-content-item">
							<cover-view>力矩百分比:{{sratio}}%</cover-view>
							<cover-view
								class="sratiobox outweight-tips {{sratiogreen?'ratio-green':''}} {{sratioyellow?'ratio-yellow':''}} {{sratiored?'ratio-red':''}}">
							</cover-view>
							<cover-view style="color: red;" wx:if="{{ sratio > 100 }}">超载危险</cover-view>
						</cover-view>
					</cover-view>
				</cover-view>
			</canvas>
		</view>
		<!-- 参数按钮 -->
		<view class="button-box">
			<!-- 网格大小 -->
			<view class="simulator-grid">
				<view class="simulator-grid-item {{item.selected? 'param-selected': ''}}" wx:for="{{grids}}"
					wx:key="name" bindtap="gridShow" data-grid="{{item}}">
					{{item.name}}
				</view>
			</view>
			<view class="simulator-grid">
				<view class="simulator-grid-item {{item.selected? 'param-selected': ''}}" wx:for="{{legtypes}}"
					hidden="{{!item.visable}}" wx:key="name" bindtap="typeShow" data-legtype="{{item}}">
					{{item.name}}
				</view>
			</view>
			<view class="input-wrap__obstacle">
				<text class="obstacle-title">障碍物设置:</text>
				<checkbox-group bindchange="obstacleChange" style="line-height:72rpx;">
					<checkbox color="#f58651" value="{{obstacleSetting}}" checked="{{false}}" />
				</checkbox-group>
				<block wx:if="{{obstacleSetting}}">
					<view class="input-obstacleSetting">
						<text class="obstacle-title">高度(m):</text>
						<input class="input-border__obstacle" data-obstacletype="height" type="number"
							bindblur="changeObstacle" value="{{obstacleParam.height}}" />
						<text class="obstacle-title">距离(m):</text>
						<input class="input-border__obstacle" data-obstacletype="distance" type="number"
							bindblur="changeObstacle" value="{{obstacleParam.distance}}" />
					</view>
				</block>
			</view>
			<!-- 按钮 -->
			<view class="fun-wrap" bindtap="btnChange" bindlongpress="longpress" bindtouchend="enddraw">
				<view class="input-wrap {{legtypes[0].selected? 'input-wrap-two': ''}}" wx:for="{{types}}"
					wx:for-index="key" wx:for-item="value" wx:key="name" hidden="{{!value.visable}}">
					<view class="input-title">{{value.name}}</view>
					<view class="input-border">
						<view class="input-btn" data-dctype="{{key+ 'Plus'}}">+</view>
						<input disabled="{{searched}}" value="{{value.value}}" data-inputtype="{{key}}" type="digit"
							bindblur="inputChange" placeholder="{{value.placeholder}}" />
						<view class="input-btn" data-dctype="{{key+ 'Minus'}}">-</view>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>
<script>
	export default {
		data() {
			return {

			}
		},
		onLoad() {},
		computed: {},
		methods: {

		}
	}
</script>


<style lang="scss" scoped>
	@import './simulator.css';
</style>
