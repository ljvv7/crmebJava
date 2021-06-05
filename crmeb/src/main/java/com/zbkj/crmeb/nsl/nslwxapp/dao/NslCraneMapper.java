package com.zbkj.crmeb.nsl.nslwxapp.dao;

import com.zbkj.crmeb.nsl.nslwxapp.model.NslCrane;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zbkj.crmeb.nsl.nslwxapp.response.CollectAndCraneList;
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

    //根据品牌id获取车辆信息
    List<NslCrane> getCraneListByBrandId(int cbrandid,long pageindex, long pagesize);

    //根据品牌id、车辆id获取车辆信息
    NslCrane getCraneListByCbrId(int cbrandid,int craneid,long pageindex, long pagesize);

    List<CollectAndCraneList> getcollectAndCraneList(Integer userId);
}
