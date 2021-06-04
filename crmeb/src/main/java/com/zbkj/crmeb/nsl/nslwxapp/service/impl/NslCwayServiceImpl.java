package com.zbkj.crmeb.nsl.nslwxapp.service.impl;

import com.zbkj.crmeb.nsl.nslwxapp.model.NslCway;
import com.zbkj.crmeb.nsl.nslwxapp.dao.NslCwayMapper;
import com.zbkj.crmeb.nsl.nslwxapp.service.NslCwayService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-06-02
 */
@Service
public class NslCwayServiceImpl extends ServiceImpl<NslCwayMapper, NslCway> implements NslCwayService {


    /**
     * 根据车辆id和配重id查询组合方式信息
     * @param craneid
     * @param cweightid
     * @return
     */
    @Override
    public List<NslCway> getCwayListBycwId(int craneid, int cweightid,long pageindex,long pagesize) {
        return baseMapper.getCwayListBycwId(craneid,cweightid,pageindex,pagesize);
    }

    /**
     * 根据车辆id、配重id和组合方式id查询某条组合方式
     * @param craneid
     * @param cweightid
     * @param cwayid
     * @return
     */
    @Override
    public NslCway getCwayInfo(int craneid, int cweightid, int cwayid) {
        return baseMapper.getCwayInfo(craneid,cweightid,cwayid);
    }
}
