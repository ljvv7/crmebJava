package com.zbkj.crmeb.nsl.nslwxapp.dao;

import com.zbkj.crmeb.nsl.nslwxapp.model.NslCollection;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-06-03
 */
public interface NslCollectionMapper extends BaseMapper<NslCollection> {

    List getCraneCollectByUserId(Integer userId);
}
