package com.zbkj.crmeb.nsl.nslwxapp.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.common.CommonResult;
import com.zbkj.crmeb.nsl.nslemons.model.NslCompany;
import com.zbkj.crmeb.nsl.nslemons.request.CompanyLimitEntry;
import com.zbkj.crmeb.nsl.nslemons.service.NslCompanyService;
import com.zbkj.crmeb.nsl.nslwxapp.model.NslCbrands;
import com.zbkj.crmeb.nsl.nslwxapp.model.NslCrane;
import com.zbkj.crmeb.nsl.nslwxapp.model.NslCway;
import com.zbkj.crmeb.nsl.nslwxapp.model.NslCweight;
import com.zbkj.crmeb.nsl.nslwxapp.request.LimitEntry;
import com.zbkj.crmeb.nsl.nslwxapp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 车辆信息表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-06-01
 */
@RestController
@RequestMapping("api/admin/nsl/crane")
public class NslCraneController {

    @Autowired
    private NslCraneService nslCraneService;
    @Autowired
    private NslCbindService nslCbindService;
    @Autowired
    private NslCompanyService nslCompanyService;
    @Autowired
    private NslCbrandsService nslCbrandsService;
    @Autowired
    private NslCweightService nslCweightService;
    @Autowired
    private NslCwayService nslCwayService;

    /**
     * 车辆详情
     * @param tableFrom
     * @return
     */
    @PostMapping("/getdetail")
    public CommonResult getDetail(@RequestBody LimitEntry tableFrom){

        QueryWrapper craneQW = new QueryWrapper();
        craneQW.ne("kbn","9");
        craneQW.eq("id",tableFrom.getCraneid());

        NslCrane cranedetail = nslCraneService.getOne(craneQW);

        List companyIds = nslCbindService.getCompanyIdsByCraneId(tableFrom.getCraneid());


        long pageindex = Long.valueOf(tableFrom.getPageindex());
        long pagesize = Long.valueOf(tableFrom.getPagesize());

        List<NslCompany> companyList = nslCompanyService.getCompanyListByIds(companyIds, pageindex, pagesize);

        Map returnMap = new HashMap();
        returnMap.put("cranedetail",cranedetail);
        returnMap.put("companylist",companyList);
        return CommonResult.success(returnMap);
    }

    /**
     * 品牌列表
     * @return
     */
    @PostMapping("/brandlist")
    public CommonResult getBrandsList(){

        //获取品牌列表
        List<NslCbrands> brandList = nslCbrandsService.getBrandList();

        return CommonResult.success(brandList);
    }

    /**
     * 车型查找-品牌/型号
     * @param tableFrom
     * @return
     */
    @PostMapping("/cranelist")
    public CommonResult getCraneList(@RequestBody LimitEntry tableFrom){

        Integer cbrandid = tableFrom.getCbrandid();
        Integer craneid = tableFrom.getCraneid();
        Integer cweightid = tableFrom.getCweightid();
        long pageindex = Long.valueOf(tableFrom.getPageindex());
        long pagesize = Long.valueOf(tableFrom.getPagesize());

        //按品牌和型号查找车辆
        QueryWrapper craneQW = new QueryWrapper();
        craneQW.ne("kbn","9");
        craneQW.eq("id",craneid);
        craneQW.eq("cbrands",cbrandid);
        NslCrane craneInfo = nslCraneService.getOne(craneQW);

        //根据车辆id查出相关配重信息
        List<NslCweight> cweightList = nslCweightService.getCweightListByCraneId(craneid,pageindex,pagesize);

        //根据车辆id和配重id查出相关组合方式信息
        List<NslCway> cwayList = nslCwayService.getCwayListBycwId(craneid, cweightid,pageindex,pagesize);

        Map map = new HashMap();
        map.put("craneInfo",craneInfo);
        map.put("cweightList",cweightList);
        map.put("cwayList",cwayList);

        return CommonResult.success(map);
    }

    /**
     * 根据车辆id查出配重列表
     * @param tableFrom
     * @return
     */
    @PostMapping("/weightlist")
    public CommonResult getWeightList(@RequestBody LimitEntry tableFrom){

        Integer craneid = tableFrom.getCraneid();
        long pageindex = Long.valueOf(tableFrom.getPageindex());
        long pagesize = Long.valueOf(tableFrom.getPagesize());

        List<NslCweight> weightList = nslCweightService.getCweightListByCraneId(craneid,pageindex,pagesize);

        return CommonResult.success(weightList);
    }

    /**
     * 根据车辆id和配重id查出车辆的组合方式列表
     * @param tableFrom
     * @return
     */
    @PostMapping("/waylist")
    public CommonResult getWayList(@RequestBody LimitEntry tableFrom){

        Integer craneid = tableFrom.getCraneid();
        Integer cweightid = tableFrom.getCweightid();
        long pageindex = Long.valueOf(tableFrom.getPageindex());
        long pagesize = Long.valueOf(tableFrom.getPagesize());

        List<NslCway> wayList = nslCwayService.getCwayListBycwId(craneid, cweightid,pageindex,pagesize);

        return CommonResult.success(wayList);
    }

//    public CommonResult



}

