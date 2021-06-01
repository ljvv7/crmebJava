package com.zbkj.crmeb.nsl.nslwxapp.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zbkj.crmeb.nsl.nslwxapp.model.EbSystemGroupData;
import com.zbkj.crmeb.nsl.nslwxapp.response.EbSystemGroup;

import java.util.List;

/**
 * <p>
 * 组合数据详情表 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-06-01
 */
public interface EbSystemGroupDataMapper extends BaseMapper<EbSystemGroupData> {

    List<EbSystemGroup> getPublishCourseInfo();

    List<EbSystemGroup> getPublishIconInfo();
}
