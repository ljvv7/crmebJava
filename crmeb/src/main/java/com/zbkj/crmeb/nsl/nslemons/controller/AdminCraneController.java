package com.zbkj.crmeb.nsl.nslemons.controller;

import com.common.CommonResult;
import com.zbkj.crmeb.nsl.nslemons.request.AdmCraneListReqParam;
import com.zbkj.crmeb.nsl.nslemons.service.AdminCraneService;
import com.zbkj.crmeb.nsl.nslwxapp.model.NslCbrands;
import com.zbkj.crmeb.nsl.nslwxapp.model.NslCrane;
import com.zbkj.crmeb.nsl.nslwxapp.service.NslCbrandsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/admin/nsl/adminCrane")
public class AdminCraneController {

    @Autowired
    private AdminCraneService adminCraneService;

    @Autowired
    private NslCbrandsService nslCbrandsService;

    /**
     * 后台车辆列表
     * @param tableFrom
     * @return
     */
    @PostMapping("/admCraneList")
    public CommonResult getAdmCraneList(@RequestBody(required = false) AdmCraneListReqParam tableFrom){

        Integer cbrandid = tableFrom.getCbrandid();
        Integer craneid = tableFrom.getCraneid();
        String craneName = tableFrom.getCraneName();
        Long pageindex = tableFrom.getPage();
        Long pagesize = tableFrom.getLimit();

        List<NslCrane> admCraneList = adminCraneService.getAdmCraneList(cbrandid, craneid, craneName, (pageindex-1)*pagesize, pagesize);

        Integer admCraneListCount = adminCraneService.getAdmCraneListCount(cbrandid, craneid, craneName);

        Map map = new HashMap();
        map.put("admCraneList",admCraneList);
        map.put("count",admCraneListCount);
        return CommonResult.success(map);
    }

    /**
     * 后端品牌列表
     * @return
     */
    @PostMapping("/admBrandList")
    public CommonResult getAdmBrandList(){

        List<NslCbrands> brandList = nslCbrandsService.getBrandList();

        return CommonResult.success(brandList);
    }

}
