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
    public List<NslCway> getCwayListBycwId(int craneid, int cweightid,int pageindex,int pagesize) {
        return baseMapper.getCwayListBycwId(craneid,cweightid,pageindex,pagesize);
    }
}
