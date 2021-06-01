package com.zbkj.crmeb.nsl.nslwxapp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zbkj.crmeb.nsl.nslwxapp.response.EbSystemGroup;
import com.zbkj.crmeb.nsl.nslwxapp.dao.EbSystemGroupDataMapper;
import com.zbkj.crmeb.nsl.nslwxapp.model.EbSystemGroupData;
import com.zbkj.crmeb.nsl.nslwxapp.service.EbSystemGroupDataService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 组合数据详情表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-06-01
 */
@Service
public class EbSystemGroupDataServiceImpl extends ServiceImpl<EbSystemGroupDataMapper, EbSystemGroupData> implements EbSystemGroupDataService {

    /**
     * 获取首页banner滚动图
     * @return
     */

    @Override
    public List<EbSystemGroup> PublishCourseInfo() {
        return baseMapper.getPublishCourseInfo();
    }

    /**
     * 获取首页icon
     */
    @Override
    public List getIcon() {
        return baseMapper.getPublishIconInfo();
    }

}
