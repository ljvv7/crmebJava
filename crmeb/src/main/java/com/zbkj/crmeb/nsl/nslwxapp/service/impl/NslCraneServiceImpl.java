package com.zbkj.crmeb.nsl.nslwxapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.zbkj.crmeb.nsl.nslwxapp.model.NslCrane;
import com.zbkj.crmeb.nsl.nslwxapp.dao.NslCraneMapper;
import com.zbkj.crmeb.nsl.nslwxapp.response.CannerCbrandsEntry;
import com.zbkj.crmeb.nsl.nslwxapp.response.CollectAndCraneList;
import com.zbkj.crmeb.nsl.nslwxapp.response.CraneByWayEntry;
import com.zbkj.crmeb.nsl.nslwxapp.service.NslCraneService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
        List records = baseMapper.getHotCraneList();
        return records;
    }

    @Override
    public List<CannerCbrandsEntry> getNewCraneList() {
        List records = baseMapper.getNewCraneList();

        return records;
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
     * 根据性能参数查询组合方式ids
     * @param legType
     * @param radius
     * @param minWeight
     * @param maxWeight
     * @param minPrimary
     * @param maxPrimary
     * @param minMinor
     * @param maxMinor
     * @return
     */
    @Override
    public List getCraneWayIds(Integer legType, BigDecimal radius,
                               Double minWeight,Double maxWeight,
                               BigDecimal minPrimary, BigDecimal maxPrimary,
                               BigDecimal minMinor, BigDecimal maxMinor) {
        return baseMapper.getCraneWayIds(legType, radius, minWeight, maxWeight, minPrimary, maxPrimary, minMinor, maxMinor);
    }

    /**
     * 根据性能参数查询车辆列表
     * @param ids
     * @param pageindex
     * @param pagesize
     * @return
     */
    @Override
    public List<CraneByWayEntry> getCraneListByWay(List ids, long pageindex, long pagesize) {
        return baseMapper.getCraneListByWay(ids, pageindex, pagesize);
    }

    /**
     * 根据性能参数查询车辆列表总数
     * @param ids
     * @return
     */
    @Override
    public Integer getCraneListByWayCount(List ids) {
        return baseMapper.getCraneListByWayCount(ids);
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
