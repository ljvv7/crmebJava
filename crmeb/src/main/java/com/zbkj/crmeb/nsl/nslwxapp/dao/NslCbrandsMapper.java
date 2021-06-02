package com.zbkj.crmeb.nsl.nslwxapp.dao;

import com.zbkj.crmeb.nsl.nslwxapp.model.NslCbrands;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 车辆品牌 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-06-01
 */
public interface NslCbrandsMapper extends BaseMapper<NslCbrands> {
    //获取品牌列表
    List getBrandList();
}
