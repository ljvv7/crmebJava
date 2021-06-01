package com.zbkj.crmeb.nsl.crmeb.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zbkj.crmeb.nsl.crmeb.model.EbSystemGroupData;
import com.zbkj.crmeb.nsl.crmeb.response.EbSystemGroup;

import java.util.List;

/**
 * <p>
 * 组合数据详情表 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-05-31
 */
public interface EbSystemGroupDataMapper extends BaseMapper<EbSystemGroupData> {

    List<EbSystemGroup> getPublishCourseInfo();

}
