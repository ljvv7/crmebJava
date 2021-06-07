package com.zbkj.crmeb.nsl.nslemons.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zbkj.crmeb.nsl.nslwxapp.model.NslCrane;

import java.util.List;

public interface AdminCraneMapper extends BaseMapper<NslCrane> {

    //获取后台页面的车辆列表
    List<NslCrane> getAdmCraneList(Integer cbrandid,Integer craneid,
                                   String craneName,Long pageindex,Long pagesize);

    //获取后台页面的车辆列表总数
    Integer getAdmCraneListCount(Integer cbrandid,Integer craneid,String craneName);

}
