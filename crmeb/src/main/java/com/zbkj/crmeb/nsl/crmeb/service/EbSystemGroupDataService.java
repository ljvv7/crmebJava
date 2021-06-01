package com.zbkj.crmeb.nsl.crmeb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zbkj.crmeb.nsl.crmeb.model.EbSystemGroupData;
import com.zbkj.crmeb.nsl.crmeb.response.EbSystemGroup;

import java.util.List;

/**
 * <p>
 * 组合数据详情表 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-05-31
 */
public interface EbSystemGroupDataService extends IService<EbSystemGroupData> {

    List<EbSystemGroup> getList();

}
