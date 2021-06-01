package com.zbkj.crmeb.nsl.nslwxapp.dao;

import com.zbkj.crmeb.nsl.nslwxapp.model.NslCbind;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 公司车辆绑定表 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-06-01
 */
public interface NslCbindMapper extends BaseMapper<NslCbind> {
    //根据车辆id查询公司id
    List getCompanyIdsByCraneId(int craneid);
}
