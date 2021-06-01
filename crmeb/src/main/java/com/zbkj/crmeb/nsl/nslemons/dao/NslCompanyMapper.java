package com.zbkj.crmeb.nsl.nslemons.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zbkj.crmeb.nsl.nslemons.model.NslCompany;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-05-29
 */
public interface NslCompanyMapper extends BaseMapper<NslCompany> {

    //根据公司id查询对应的公司信息
    List<NslCompany> getCompanyListByIds(@Param("ids") List ids, long pageindex, long pagesize);
}
