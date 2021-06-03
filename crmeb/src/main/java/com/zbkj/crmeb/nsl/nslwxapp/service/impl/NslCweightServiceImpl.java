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
     * @return
     */
    @Override
    public List<NslCweight> getCweightListByCraneId(int craneid,int pageindex,int pagesize) {
        return baseMapper.getCweightListByCraneId(craneid,pageindex,pagesize);
    }
}
