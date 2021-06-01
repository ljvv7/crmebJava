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
}
