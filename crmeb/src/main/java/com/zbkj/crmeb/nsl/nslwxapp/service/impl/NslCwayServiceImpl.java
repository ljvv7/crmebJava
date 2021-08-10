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
     * 查询组合方式列表
     * @param craneid
     * @param cweightid
     * @param cwayid
     * @param pageindex
     * @param pagesize
     * @return
     */
    @Override
    public List<NslCway> getWayList(Integer craneid, Integer cweightid, Integer cwayid, Long pageindex, Long pagesize) {
        return baseMapper.getWayList(craneid, cweightid, cwayid, pageindex, pagesize);
    }

    /**
     * 查询组合方式总数
     * @param craneid
     * @param cweightid
     * @param cwayid
     * @return
     */
    @Override
    public Integer getWayListCount(Integer craneid, Integer cweightid, Integer cwayid) {
        return baseMapper.getWayListCount(craneid, cweightid, cwayid);
    }
}
