package com.zbkj.crmeb.nsl.nslemons.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zbkj.crmeb.nsl.nslemons.request.AdmAddCraneReqParam;
import com.zbkj.crmeb.nsl.nslwxapp.model.NslCrane;

import java.math.BigDecimal;
import java.util.List;

public interface AdminCraneMapper extends BaseMapper<NslCrane> {

    //获取后台页面的车辆列表
    List<NslCrane> getAdmCraneList(Integer cbrandid,Integer craneid,
                                   String craneName,Long pageindex,Long pagesize);

    //获取后台页面的车辆列表总数
    Integer getAdmCraneListCount(Integer cbrandid,Integer craneid,String craneName);

    //新增车辆
    Integer addCrane(String name, Integer cbrands, BigDecimal maxweight,
                     String introduce,String images,BigDecimal guidePrice);

    //移除车辆
    Integer removeCrane(Integer id);

    //移除配重
    Integer removeWeight(Integer id);

    //修改车辆
    Integer editCrane(Integer craneid,String name,BigDecimal maxweight,
                      String introduce,String images,BigDecimal guidePrice);

    //新增配重
    Integer addWeight(Integer craneid,String legtype,String legway,BigDecimal equipweight,Integer workextent,
                      String issuperweight, BigDecimal superweight, BigDecimal backmove,String remarks);

}
