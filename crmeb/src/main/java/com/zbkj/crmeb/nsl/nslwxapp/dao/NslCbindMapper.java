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

    //根据功能公司id 查找车辆
    List getCraneIdByCompanyId(int companyid);

    //查询是否绑定
    Integer queryIsBinded(Integer companyid,Integer userid,Integer craneid);

    //添加公司车辆绑定
    Integer addCompBindCrane(Integer companyid,Integer userid,Integer craneid);

    List selectUserAndCrane(Integer userid, Integer craneid);
}
