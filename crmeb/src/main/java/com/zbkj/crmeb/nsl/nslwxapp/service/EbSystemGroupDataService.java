package com.zbkj.crmeb.nsl.nslwxapp.service;

import com.zbkj.crmeb.nsl.nslwxapp.response.EbSystemGroup;
import com.zbkj.crmeb.nsl.nslwxapp.model.EbSystemGroupData;
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

    /**
     * 获取首页banner滚动图
     * @return
     */
    List<EbSystemGroup> PublishCourseInfo();

    /**
     * 获取首页icon
     */
    List<EbSystemGroup> getIcon();
}
