package com.zbkj.crmeb.nsl.nslwxapp.dao;

import com.zbkj.crmeb.nsl.nslwxapp.model.NslCweight;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 车辆配重表 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-06-02
 */
public interface NslCweightMapper extends BaseMapper<NslCweight> {

    //根据车辆id查找配重信息列表
    List<NslCweight> getWeightList(Integer craneid,Long pageindex,Long pagesize);

    //查询配重信息总数
    Integer getWeightListCount(Integer craneid);

}
