package com.zbkj.crmeb.nsl.nslwxapp.service;

import com.zbkj.crmeb.nsl.nslwxapp.model.NslCway;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2021-06-02
 */
public interface NslCwayService extends IService<NslCway> {

    /**
     * 查询组合方式列表
     * @param craneid
     * @param cweightid
     * @param cwayid
     * @param pageindex
     * @param pagesize
     * @return
     */
    List<NslCway> getWayList(Integer craneid,Integer cweightid,Integer cwayid,Long pageindex,Long pagesize);

    /**
     * 查询组合方式总数
     * @param craneid
     * @param cweightid
     * @param cwayid
     * @return
     */
    Integer getWayListCount(Integer craneid,Integer cweightid,Integer cwayid);


}
