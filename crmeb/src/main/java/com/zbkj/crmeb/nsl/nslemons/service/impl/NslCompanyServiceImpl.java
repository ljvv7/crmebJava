package com.zbkj.crmeb.nsl.nslemons.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.zbkj.crmeb.nsl.nslemons.dao.NslCompanyMapper;
import com.zbkj.crmeb.nsl.nslemons.model.NslCompany;
import com.zbkj.crmeb.nsl.nslemons.service.NslCompanyService;
import com.zbkj.crmeb.nsl.nslwxapp.request.AddCompanyEntry;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-05-29
 */
@Service
public class NslCompanyServiceImpl extends ServiceImpl<NslCompanyMapper, NslCompany> implements NslCompanyService {

    @Override
    public List<NslCompany> getCompanyListByIds(@Param("ids") List ids, long pageindex, long pagesize) {
        return baseMapper.getCompanyListByIds(ids,pageindex,pagesize);
    }

    @Override
    public String getAuthmsgv1() {
       return baseMapper.getAuthmsgv1();
    }

    @Override
    public Map getAllCompanyLimit(int page, int limit, String companyName) {

        QueryWrapper queryWrapper = new QueryWrapper();
        if(!StringUtils.isEmpty(companyName)){
            Pattern pattern = Pattern.compile("[0-9]*");
            Matcher matcher = pattern.matcher(companyName);
            if(matcher.matches()){
                queryWrapper.like("phone",companyName);
            }else {
                queryWrapper.like("name",companyName);
            }
        }
        queryWrapper.orderByAsc("id");
        Integer total = baseMapper.selectCount(queryWrapper);
        PageHelper.startPage(page,limit);

        List list = baseMapper.selectList(queryWrapper);

        Map map = new HashMap();
        map.put("total",total);
        map.put("list",list);
        return map;
    }

    @Override
    public Map getAllCompany(int page, int limit) {

        QueryWrapper queryCompany = new QueryWrapper();
        queryCompany.ne("kbn","9");
        Integer total = baseMapper.selectCount(queryCompany);

        PageHelper.startPage(page,limit);
        queryCompany.orderByAsc("id");
        List<NslCompany> list = baseMapper.selectList(queryCompany);

        Map map = new HashMap();
        map.put("total",total);
        map.put("list",list);

        return map;
    }


}
