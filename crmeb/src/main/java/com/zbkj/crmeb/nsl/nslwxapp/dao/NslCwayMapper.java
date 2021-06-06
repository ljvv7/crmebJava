package com.zbkj.crmeb.nsl.nslwxapp.dao;

import com.zbkj.crmeb.nsl.nslwxapp.model.NslCway;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-06-02
 */
public interface NslCwayMapper extends BaseMapper<NslCway> {

    //查询组合方式列表
    List<NslCway> getWayList(Integer craneid,Integer cweightid,Integer cwayid,Long pageindex,Long pagesize);

    //查询组合方式总数
    Integer getWayListCount(Integer craneid,Integer cweightid,Integer cwayid);

}
