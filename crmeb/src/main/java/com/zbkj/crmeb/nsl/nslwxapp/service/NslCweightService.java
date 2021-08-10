package com.zbkj.crmeb.nsl.nslwxapp.service;

import com.zbkj.crmeb.nsl.nslwxapp.model.NslCweight;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 车辆配重表 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-06-02
 */
public interface NslCweightService extends IService<NslCweight> {

    /**
     * 根据车辆id查找配重信息列表
     * @param craneid
     * @param pageindex
     * @param pagesize
     * @return
     */
    List<NslCweight> getWeightList(Integer craneid,Long pageindex,Long pagesize);

    /**
     * 查询配重信息总数
     * @param craneid
     * @return
     */
    Integer getWeightListCount(Integer craneid);

}
