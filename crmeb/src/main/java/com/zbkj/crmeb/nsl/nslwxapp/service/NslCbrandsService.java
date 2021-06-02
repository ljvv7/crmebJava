package com.zbkj.crmeb.nsl.nslwxapp.service;

import com.zbkj.crmeb.nsl.nslwxapp.model.NslCbrands;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 车辆品牌 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-06-01
 */
public interface NslCbrandsService extends IService<NslCbrands> {

    //获取品牌列表
    List getBrandList();

}
