package com.zbkj.crmeb.nsl.nslemons.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zbkj.crmeb.nsl.nslemons.dao.AdminCraneMapper;
import com.zbkj.crmeb.nsl.nslemons.request.AdmAddCraneReqParam;
import com.zbkj.crmeb.nsl.nslemons.service.AdminCraneService;
import com.zbkj.crmeb.nsl.nslwxapp.model.NslCrane;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AdminCraneServiceImpl extends ServiceImpl<AdminCraneMapper, NslCrane> implements AdminCraneService {

    /**
     * 获取后台页面的车辆列表
     * @param cbrandid
     * @param craneid
     * @param craneName
     * @param pageindex
     * @param pagesize
     * @return
     */
    @Override
    public List<NslCrane> getAdmCraneList(Integer cbrandid, Integer craneid, String craneName, Long pageindex, Long pagesize) {
        return baseMapper.getAdmCraneList(cbrandid, craneid, craneName, pageindex, pagesize);
    }

    /**
     * 获取后台页面的车辆列表总数
     * @param cbrandid
     * @param craneid
     * @param craneName
     * @return
     */
    @Override
    public Integer getAdmCraneListCount(Integer cbrandid, Integer craneid, String craneName) {
        return baseMapper.getAdmCraneListCount(cbrandid, craneid, craneName);
    }

    /**
     * 新增车辆
     * @param
     * @return
     */
    @Override
    public Integer addCrane(String name, Integer cbrands, BigDecimal maxweight,
                            String introduce, String images, BigDecimal guidePrice) {
        return baseMapper.addCrane(name, cbrands, maxweight, introduce, images, guidePrice);
    }

    /**
     * 移除车辆
     * @param id
     * @return
     */
    @Override
    public Integer removeCrane(Integer id) {
        return baseMapper.removeCrane(id);
    }

    /**
     * 移除配重
     * @param id
     * @return
     */
    @Override
    public Integer removeWeight(Integer id) {
        return baseMapper.removeWeight(id);
    }

    /**
     * 修改车辆
     * @param craneid
     * @param name
     * @param maxweight
     * @param introduce
     * @param guidePrice
     * @return
     */
    @Override
    public Integer editCrane(Integer craneid, String name, BigDecimal maxweight,
                             String introduce, String images, BigDecimal guidePrice) {
        return baseMapper.editCrane(craneid, name, maxweight, introduce, images, guidePrice);
    }

    /**
     * 新增配重
     * @param craneid
     * @param legtype
     * @param legway
     * @param equipweight
     * @param workextent
     * @param remarks
     * @return
     */
    @Override
    public Integer addWeight(Integer craneid, String legtype, String legway, BigDecimal equipweight, Integer workextent,
                             String issuperweight, BigDecimal superweight, BigDecimal backmove,String remarks) {
        return baseMapper.addWeight(craneid, legtype, legway, equipweight, workextent, issuperweight, superweight, backmove, remarks);
    }
}
