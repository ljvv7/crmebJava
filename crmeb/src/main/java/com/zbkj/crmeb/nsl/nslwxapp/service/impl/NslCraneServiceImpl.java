package com.zbkj.crmeb.nsl.nslwxapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zbkj.crmeb.nsl.nslwxapp.model.NslCrane;
import com.zbkj.crmeb.nsl.nslwxapp.dao.NslCraneMapper;
import com.zbkj.crmeb.nsl.nslwxapp.response.CannerCbrandsEntry;
import com.zbkj.crmeb.nsl.nslwxapp.service.NslCraneService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 车辆信息表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-06-01
 */
@Service
public class NslCraneServiceImpl extends ServiceImpl<NslCraneMapper, NslCrane> implements NslCraneService {


    @Autowired
    private NslCraneService nslCraneService;

    @Override
    public List<CannerCbrandsEntry> getHotCraneList() {

        long page = 1;
        long limit =5;

        Page pageCourse = new Page(page,limit);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.ne("kbn","9");
        queryWrapper.orderByDesc("visits");
        nslCraneService.page(pageCourse,queryWrapper);
        List records = pageCourse.getRecords();
        return records;
    }

    @Override
    public List<CannerCbrandsEntry> getNewCraneList() {
        long page = 1;
        long limit =5;
        Page pageCourse = new Page(page,limit);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.ne("kbn","9");
        queryWrapper.orderByDesc("update_date");
        nslCraneService.page(pageCourse,queryWrapper);
        List records = pageCourse.getRecords();

        return null;
    }

    @Override
    public List getAllCraneByCompanyId(List ids, long pageindex, long pagesize) {
        return baseMapper.getAllCraneByCompanyId(ids,pageindex,pagesize);
    }

    /**
     * 根据品牌id获取车辆信息
     * @param cbrandid
     * @param pageindex
     * @param pagesize
     * @return
     */
    @Override
    public List<NslCrane> getCraneListByBrandId(int cbrandid,int pageindex,int pagesize) {
        return baseMapper.getCraneListByBrandId(cbrandid,pageindex,pagesize);
    }

    /**
     * 根据品牌id、车辆id获取车辆信息
     * @param cbrandid
     * @param craneid
     * @param pageindex
     * @param pagesize
     * @return
     */
    @Override
    public NslCrane getCraneListByCbrId(int cbrandid, int craneid,int pageindex, int pagesize) {
        return baseMapper.getCraneListByCbrId(cbrandid,craneid,pageindex,pagesize);
    }


}
