package com.zbkj.crmeb.nsl.nslwxapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zbkj.crmeb.nsl.nslwxapp.model.NslCrane;
import com.zbkj.crmeb.nsl.nslwxapp.dao.NslCraneMapper;
import com.zbkj.crmeb.nsl.nslwxapp.response.CannerCbrandsEntry;
import com.zbkj.crmeb.nsl.nslwxapp.response.CollectAndCraneList;
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

    /**
     * 根据车辆id获取车辆信息
     * @param craneid
     * @return
     */
    @Override
    public NslCrane getCraneDetailById(Integer craneid) {
        return baseMapper.getCraneDetailById(craneid);
    }

    /**
     * 查询车辆列表
     * @param cbrandid
     * @param craneid
     * @param pageindex
     * @param pagesize
     * @return
     */
    @Override
    public List<NslCrane> getCraneList(Integer cbrandid,Integer craneid,Long pageindex,Long pagesize) {
        return baseMapper.getCraneList(cbrandid,craneid,pageindex,pagesize);
    }

    /**
     * 查询车辆信息总条数
     * @param cbrandid
     * @param craneid
     * @return
     */
    @Override
    public Integer getCraneListCount(Integer cbrandid, Integer craneid) {
        return baseMapper.getCraneListCount(cbrandid, craneid);
    }


    @Override
    public List getAllCraneByCompanyId(List ids, long pageindex, long pagesize) {
        return baseMapper.getAllCraneByCompanyId(ids,pageindex,pagesize);
    }

    @Override
    public List<CollectAndCraneList> getcollectAndCraneList(Integer userId) {
        return baseMapper.getcollectAndCraneList(userId);
    }


}
