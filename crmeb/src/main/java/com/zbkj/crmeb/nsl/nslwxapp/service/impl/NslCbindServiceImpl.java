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
}
