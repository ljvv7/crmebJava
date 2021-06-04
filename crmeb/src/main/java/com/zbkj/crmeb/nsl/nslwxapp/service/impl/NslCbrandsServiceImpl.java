package com.zbkj.crmeb.nsl.nslwxapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zbkj.crmeb.nsl.nslwxapp.model.NslCbrands;
import com.zbkj.crmeb.nsl.nslwxapp.dao.NslCbrandsMapper;
import com.zbkj.crmeb.nsl.nslwxapp.service.NslCbrandsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 车辆品牌 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-06-01
 */
@Service
public class NslCbrandsServiceImpl extends ServiceImpl<NslCbrandsMapper, NslCbrands> implements NslCbrandsService {

    /**
     * 获取品牌列表
     * @return
     */
    @Override
    public List<NslCbrands> getBrandList() {
        return baseMapper.getBrandList();
    }
}
