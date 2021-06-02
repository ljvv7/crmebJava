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

    //根据车辆id和配重id查询组合方式信息
    List<NslCway> getCwayListBycwId(int craneid,int cweightid,long pageindex,long pagesize);

}
