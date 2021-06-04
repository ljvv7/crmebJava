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
     * 根据车辆id和配重id查询组合方式信息
     * @param craneid
     * @param cweightid
     * @return
     */
    List<NslCway> getCwayListBycwId(int craneid,int cweightid,long pageindex,long pagesize);

}
