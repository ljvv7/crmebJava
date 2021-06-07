package com.zbkj.crmeb.nsl.nslemons.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zbkj.crmeb.nsl.nslemons.dao.AdminCraneMapper;
import com.zbkj.crmeb.nsl.nslemons.service.AdminCraneService;
import com.zbkj.crmeb.nsl.nslwxapp.model.NslCrane;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminCraneServiceImpl extends ServiceImpl<AdminCraneMapper, NslCrane> implements AdminCraneService {

    /**
     * 获取后台页面的车辆列表
     * @param cbrandid
     * @param craneid
     * @param craneName
     * @param pageindex
     * @param pagesize
     * @return
     */
    @Override
    public List<NslCrane> getAdmCraneList(Integer cbrandid, Integer craneid, String craneName, Long pageindex, Long pagesize) {
        return baseMapper.getAdmCraneList(cbrandid, craneid, craneName, pageindex, pagesize);
    }

    /**
     * 获取后台页面的车辆列表总数
     * @param cbrandid
     * @param craneid
     * @param craneName
     * @return
     */
    @Override
    public Integer getAdmCraneListCount(Integer cbrandid, Integer craneid, String craneName) {
        return baseMapper.getAdmCraneListCount(cbrandid, craneid, craneName);
    }
}
