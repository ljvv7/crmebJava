package com.zbkj.crmeb.nsl.wxapp.service;

import com.zbkj.crmeb.nsl.wxapp.response.EbSystemGroup;
import com.zbkj.crmeb.nsl.wxapp.model.EbSystemGroupData;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 组合数据详情表 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-06-01
 */
public interface EbSystemGroupDataService extends IService<EbSystemGroupData> {

    List<EbSystemGroup> getList();
}
