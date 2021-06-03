package com.zbkj.crmeb.nsl.nslwxapp.service.impl;

import com.zbkj.crmeb.nsl.nslwxapp.model.NslCbind;
import com.zbkj.crmeb.nsl.nslwxapp.dao.NslCbindMapper;
import com.zbkj.crmeb.nsl.nslwxapp.service.NslCbindService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 公司车辆绑定表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-06-01
 */
@Service
public class NslCbindServiceImpl extends ServiceImpl<NslCbindMapper, NslCbind> implements NslCbindService {

    @Override
    public List getCompanyIdsByCraneId(int craneid) {
        return baseMapper.getCompanyIdsByCraneId(craneid);
    }

    @Override
    public List getCraneIdByCompanyId(int companyid) {
        return baseMapper.getCraneIdByCompanyId(companyid);
    }

    /**
     * 添加信息到绑定表
     * @param companyid
     * @param userid
     * @param craneid
     * @return
     */
    @Override
    public int addCompBindCrane(int companyid, int userid, int craneid) {
        return baseMapper.addCompBindCrane(companyid,userid,craneid);
    }

    @Override
    public List selectUserAndCrane(Integer userid, Integer craneid) {
        return baseMapper.selectUserAndCrane(userid,craneid);
    }
}
