package com.zbkj.crmeb.nsl.crmeb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zbkj.crmeb.nsl.crmeb.dao.EbSystemGroupDataMapper;
import com.zbkj.crmeb.nsl.crmeb.model.EbSystemGroupData;
import com.zbkj.crmeb.nsl.crmeb.response.EbSystemGroup;
import com.zbkj.crmeb.nsl.crmeb.service.EbSystemGroupDataService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 组合数据详情表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-05-31
 */
@Service
public class EbSystemGroupDataServiceImpl extends ServiceImpl<EbSystemGroupDataMapper, EbSystemGroupData> implements EbSystemGroupDataService {

    @Override
    public List<EbSystemGroup> getList() {
        return baseMapper.getPublishCourseInfo();
    }
}
