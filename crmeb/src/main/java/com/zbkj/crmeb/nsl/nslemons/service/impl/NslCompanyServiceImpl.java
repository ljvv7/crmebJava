package com.zbkj.crmeb.nsl.nslemons.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zbkj.crmeb.nsl.nslemons.dao.NslCompanyMapper;
import com.zbkj.crmeb.nsl.nslemons.model.NslCompany;
import com.zbkj.crmeb.nsl.nslemons.service.NslCompanyService;
import com.zbkj.crmeb.nsl.nslwxapp.request.AddCompanyEntry;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-05-29
 */
@Service
public class NslCompanyServiceImpl extends ServiceImpl<NslCompanyMapper, NslCompany> implements NslCompanyService {

    @Override
    public List<NslCompany> getCompanyListByIds(@Param("ids") List ids, int pageindex, int pagesize) {
        return baseMapper.getCompanyListByIds(ids,pageindex,pagesize);
    }

    @Override
    public String getAuthmsgv1() {
       return baseMapper.getAuthmsgv1();
    }

}
