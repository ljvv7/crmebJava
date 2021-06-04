package com.zbkj.crmeb.nsl.nslemons.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zbkj.crmeb.nsl.nslemons.model.NslCompany;
import com.zbkj.crmeb.nsl.nslwxapp.request.AddCompanyEntry;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2021-05-29
 */
public interface NslCompanyService extends IService<NslCompany> {

    //根据公司id查询对应的公司信息
    List<NslCompany> getCompanyListByIds(@Param("ids") List ids, long pageindex, long pagesize);


    /**
     * 公司认证提示
     */
    String getAuthmsgv1();
}
