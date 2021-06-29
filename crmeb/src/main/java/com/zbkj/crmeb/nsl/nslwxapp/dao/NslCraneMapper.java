package com.zbkj.crmeb.nsl.nslwxapp.dao;

import com.zbkj.crmeb.nsl.nslwxapp.model.NslCrane;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zbkj.crmeb.nsl.nslwxapp.response.CollectAndCraneList;
import com.zbkj.crmeb.nsl.nslwxapp.response.CraneByWayEntry;
import com.zbkj.crmeb.nsl.nslwxapp.response.HotOrNewCraneEntry;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
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

    //根据车辆品牌、车辆id查询车辆列表
    List<NslCrane> getCraneList(Integer cbrandid,Integer craneid,Long pageindex,Long pagesize);

    //根据性能参数查询组合方式ids
    List getCraneWayIds(Integer legType, BigDecimal radius,
                        Double minWeight,Double maxWeight,
                        BigDecimal minPrimary,BigDecimal maxPrimary,
                        BigDecimal minMinor,BigDecimal maxMinor);

    //根据性能参数查询车辆列表
    List<CraneByWayEntry> getCraneListByWay(@Param("ids") List ids, long pageindex, long pagesize);

    //根据性能参数查询车辆列表总数
    Integer getCraneListByWayCount(@Param("ids") List ids);

    //查询车辆信息总条数
    Integer getCraneListCount(Integer cbrandid,Integer craneid);

    List<CollectAndCraneList> getcollectAndCraneList(Integer userId);

    //获取最火车型
    List<HotOrNewCraneEntry> getHotCraneList();

    //获取最新车型
    List<HotOrNewCraneEntry> getNewCraneList();
}
