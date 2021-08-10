package com.zbkj.crmeb.nsl.nslwxapp.service.impl;

import com.zbkj.crmeb.nsl.nslwxapp.model.NslCweight;
import com.zbkj.crmeb.nsl.nslwxapp.dao.NslCweightMapper;
import com.zbkj.crmeb.nsl.nslwxapp.service.NslCweightService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 车辆配重表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-06-02
 */
@Service
public class NslCweightServiceImpl extends ServiceImpl<NslCweightMapper, NslCweight> implements NslCweightService {

    /**
     * 根据车辆id查找配重信息列表
     * @param craneid
     * @param pageindex
     * @param pagesize
     * @return
     */
    @Override
    public List<NslCweight> getWeightList(Integer craneid,Long pageindex,Long pagesize) {
        return baseMapper.getWeightList(craneid,pageindex,pagesize);
    }

    /**
     * 查询配重信息总数
     * @param craneid
     * @return
     */
    @Override
    public Integer getWeightListCount(Integer craneid) {
        return baseMapper.getWeightListCount(craneid);
    }
}
