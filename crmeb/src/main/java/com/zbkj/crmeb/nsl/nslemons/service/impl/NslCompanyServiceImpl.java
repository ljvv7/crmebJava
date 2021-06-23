package com.zbkj.crmeb.nsl.nslemons.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.zbkj.crmeb.nsl.commountils.DistanceUtil;
import com.zbkj.crmeb.nsl.nslemons.dao.NslCompanyMapper;
import com.zbkj.crmeb.nsl.nslemons.model.NslCompany;
import com.zbkj.crmeb.nsl.nslemons.service.NslCompanyService;
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
    public List<NslCompany> getCompanyList(@Param("ids") List ids, Long pageindex, Long pagesize) {
        return baseMapper.getCompanyList(ids,pageindex,pagesize);
    }

    @Override
    public Integer getCompanyListCount(List cpids) {
        return baseMapper.getCompanyListCount(cpids);
    }

    @Override
    public String getAuthmsgv1() {
        return baseMapper.getAuthmsgv1();
    }

    @Override
    public Map getAllCompanyLimit(int page, int limit, String companyName,double longitude,double latitude) {

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
        for(int i = 0;i<list.size(); i++){
            NslCompany nslCompany =(NslCompany) list.get(i);
            String lo = nslCompany.getLongitude();
            String la = nslCompany.getLatitude();

            Double lon = Double.valueOf(lo);
            Double lat = Double.valueOf(la);
            double distince = DistanceUtil.getDistance(latitude, longitude, lat, lon);
            nslCompany.setDistince(distince);
        }

        Map map = new HashMap();
        map.put("total",total);
        map.put("list",list);
        return map;
    }

    @Override
    public Map getAllCompany(int page, int limit,double longitude,double latitude) {

        QueryWrapper queryCompany = new QueryWrapper();
        queryCompany.ne("kbn","9");
        Integer total = baseMapper.selectCount(queryCompany);

        PageHelper.startPage(page,limit);
        queryCompany.orderByAsc("id");
        List list = baseMapper.selectList(queryCompany);
        Map map = new HashMap();
        for(int i = 0;i<list.size(); i++){
            NslCompany nslCompany =(NslCompany) list.get(i);
            String lo = nslCompany.getLongitude();
            String la = nslCompany.getLatitude();

            try {
                Double lon = Double.valueOf(lo);
                Double lat = Double.valueOf(la);
                double distince = DistanceUtil.getDistance(latitude, longitude, lat, lon);
                nslCompany.setDistince(distince);

                map.put("total",total);
                map.put("list",list);
            }catch (Exception e){
                map.put("error","请确认经纬度！");
            }

        }
        return map;
    }

    @Override
    public void deleteCompanyKbn(int id) {
         baseMapper.deleteCompanyKbn(id);
    }

    @Override
    public void updateStatusByid(int id, String status) {
        baseMapper.updateStatusByid(id,status);
    }


}
