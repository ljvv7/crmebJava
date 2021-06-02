package com.zbkj.crmeb.nsl.nslwxapp.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.common.CommonResult;
import com.zbkj.crmeb.nsl.nslemons.model.NslCompany;
import com.zbkj.crmeb.nsl.nslemons.request.CompanyLimitEntry;
import com.zbkj.crmeb.nsl.nslemons.service.NslCompanyService;
import com.zbkj.crmeb.nsl.nslwxapp.model.NslCrane;
import com.zbkj.crmeb.nsl.nslwxapp.request.LimitEntry;
import com.zbkj.crmeb.nsl.nslwxapp.service.NslCbindService;
import com.zbkj.crmeb.nsl.nslwxapp.service.NslCraneService;
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
        returnMap.put("companyIds",companyIds);
        return CommonResult.success(returnMap);
    }



}

