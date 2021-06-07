package com.zbkj.crmeb.nsl.nslemons.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zbkj.crmeb.nsl.nslemons.model.NslCompany;
import com.zbkj.crmeb.nsl.nslwxapp.request.AddCompanyEntry;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

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
    List<NslCompany> getCompanyList(@Param("ids") List ids, Long pageindex, Long pagesize);

    //查询公司信息总数
    Integer getCompanyListCount(List cpids);

    /**
     * 公司认证提示
     */
    String getAuthmsgv1();

    Map getAllCompanyLimit(int page, int limit, String companyName);

    Map getAllCompany(int page, int limit);

    void deleteCompanyKbn(int id);

    void updateStatusByid(int id, String status);
}
