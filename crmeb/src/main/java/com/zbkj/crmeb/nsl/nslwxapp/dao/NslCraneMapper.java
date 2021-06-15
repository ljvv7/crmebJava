package com.zbkj.crmeb.nsl.nslwxapp.dao;

import com.zbkj.crmeb.nsl.nslwxapp.model.NslCrane;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zbkj.crmeb.nsl.nslwxapp.response.CollectAndCraneList;
import com.zbkj.crmeb.nsl.nslwxapp.response.HotOrNewCraneEntry;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * <p>
 * 车辆信息表 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-06-01
 */
public interface NslCraneMapper extends BaseMapper<NslCrane> {


    List getAllCraneByCompanyId(@Param("ids") List ids, long pageindex, long pagesize);

    //根据车辆id获取车辆信息
    NslCrane getCraneDetailById(Integer craneid);

    //查询车辆列表
    List<NslCrane> getCraneList(Integer cbrandid,Integer craneid,Long pageindex,Long pagesize);

    //查询车辆信息总条数
    Integer getCraneListCount(Integer cbrandid,Integer craneid);

    List<CollectAndCraneList> getcollectAndCraneList(Integer userId);

    //获取最火车型
    List<HotOrNewCraneEntry> getHotCraneList();

    //获取最新车型
    List<HotOrNewCraneEntry> getNewCraneList();
}
