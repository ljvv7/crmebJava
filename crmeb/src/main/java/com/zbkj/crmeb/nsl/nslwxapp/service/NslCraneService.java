package com.zbkj.crmeb.nsl.nslwxapp.service;

import com.zbkj.crmeb.nsl.nslwxapp.model.NslCrane;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zbkj.crmeb.nsl.nslwxapp.response.CannerCbrandsEntry;
import com.zbkj.crmeb.nsl.nslwxapp.response.CollectAndCraneList;

import java.util.List;

/**
 * <p>
 * 车辆信息表 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-06-01
 */
public interface NslCraneService extends IService<NslCrane> {

    /**
     * 获取最火车型
     */
    List<CannerCbrandsEntry> getHotCraneList();

    /**
     *  最新车型
     */
    List<CannerCbrandsEntry> getNewCraneList();

    /**
     * 根据车辆id获取车辆信息
     * @param craneid
     * @return
     */
    NslCrane getCraneDetailById(Integer craneid);

    /**
     * 查询车辆列表
     * @param cbrandid
     * @param craneid
     * @param pageindex
     * @param pagesize
     * @return
     */
    List<NslCrane> getCraneList(Integer cbrandid,Integer craneid,Long pageindex,Long pagesize);

    /**
     * 查询车辆信息总条数
     * @param cbrandid
     * @param craneid
     * @return
     */
    Integer getCraneListCount(Integer cbrandid,Integer craneid);

    /**
     * 根据公司id查找所有车辆
     * @return
     */
    List getAllCraneByCompanyId(List ids,long pageindex,  long pagesize);

    List<CollectAndCraneList> getcollectAndCraneList(Integer userId);
}
