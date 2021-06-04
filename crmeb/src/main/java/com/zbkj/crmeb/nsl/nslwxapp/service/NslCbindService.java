package com.zbkj.crmeb.nsl.nslwxapp.service;

import com.zbkj.crmeb.nsl.nslwxapp.model.NslCbind;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 公司车辆绑定表 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-06-01
 */
public interface NslCbindService extends IService<NslCbind> {
    //根据车辆id查询公司id
    List getCompanyIdsByCraneId(int craneid);

    //根据功能公司id 查找车辆
    List getCraneIdByCompanyId(int companyid);

    //添加信息到绑定表
    int addCompBindCrane(int companyid,int userid,int craneid);

    List selectUserAndCrane(Integer userid, Integer craneid);
}
