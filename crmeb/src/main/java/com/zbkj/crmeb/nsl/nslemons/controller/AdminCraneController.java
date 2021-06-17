package com.zbkj.crmeb.nsl.nslemons.controller;

import com.common.CommonResult;
import com.zbkj.crmeb.nsl.nslemons.request.AdmAddCraneReqParam;
import com.zbkj.crmeb.nsl.nslemons.request.AdmCraneListReqParam;
import com.zbkj.crmeb.nsl.nslemons.request.AdmWayListReqParam;
import com.zbkj.crmeb.nsl.nslemons.request.AdmWeightListReqParam;
import com.zbkj.crmeb.nsl.nslemons.service.AdminCraneService;
import com.zbkj.crmeb.nsl.nslwxapp.model.NslCbrands;
import com.zbkj.crmeb.nsl.nslwxapp.model.NslCrane;
import com.zbkj.crmeb.nsl.nslwxapp.model.NslCway;
import com.zbkj.crmeb.nsl.nslwxapp.model.NslCweight;
import com.zbkj.crmeb.nsl.nslwxapp.service.NslCbrandsService;
import com.zbkj.crmeb.nsl.nslwxapp.service.NslCwayService;
import com.zbkj.crmeb.nsl.nslwxapp.service.NslCweightService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/admin/nsl/adminCrane")
@Api(tags = "Admin后台车辆接口")
public class AdminCraneController {

    @Autowired
    private AdminCraneService adminCraneService;

    @Autowired
    private NslCbrandsService nslCbrandsService;

    @Autowired
    private NslCweightService nslCweightService;

    @Autowired
    private NslCwayService nslCwayService;

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
     * 后台品牌列表
     * @return
     */
    @PostMapping("/admBrandList")
    public CommonResult getAdmBrandList(){

        List<NslCbrands> brandList = nslCbrandsService.getBrandList();

        return CommonResult.success(brandList);
    }

    /**
     * 后台配重列表
     * @param tableFrom
     * @return
     */
    @PostMapping("/admWeightList")
    public CommonResult getAdmWeightList(@RequestBody(required = false) AdmWeightListReqParam tableFrom){
        Integer craneid = tableFrom.getCraneid();
        Long pageindex = tableFrom.getPage();
        Long pagesize = tableFrom.getLimit();
        List<NslCweight> admWeightList = nslCweightService.getWeightList(craneid, (pageindex - 1) * pagesize, pagesize);
        Integer admWeightListCount = nslCweightService.getWeightListCount(craneid);
        Map map = new HashMap();
        map.put("admWeightList",admWeightList);
        map.put("count",admWeightListCount);

        return CommonResult.success(map);
    }

    /**
     * 组合方式
     * @param tableFrom
     * @return
     */
    @PostMapping("/admWayList")
    public CommonResult getAdmWayList(@RequestBody(required = false) AdmWayListReqParam tableFrom){
        Integer craneid = tableFrom.getCraneid();
        Integer cweightid = tableFrom.getCweightid();
        Integer cwayid = tableFrom.getCwayid();
        Long pageindex = tableFrom.getPage();
        Long pagesize = tableFrom.getLimit();

        List<NslCway> wayList = nslCwayService.getWayList(craneid, cweightid, cwayid, (pageindex - 1) * pagesize, pagesize);
        Integer wayListCount = nslCwayService.getWayListCount(craneid, cweightid, cwayid);
        Map map = new HashMap();
        map.put("admWayList",wayList);
        map.put("count",wayListCount);
        return CommonResult.success(map);
    }

    /**
     * 车辆新增
     * @param craneInfo
     * @return
     */
    @PostMapping("/addCrane")
    public CommonResult addCrane(@RequestBody(required = false) AdmAddCraneReqParam craneInfo){
        Integer cbrands = craneInfo.getCbrands();
        String name = craneInfo.getName();
        BigDecimal maxweight = craneInfo.getMaxweight();
        BigDecimal guidePrice = craneInfo.getGuidePrice();
        String images = craneInfo.getImages();
        String introduce = craneInfo.getIntroduce();

        Integer flag = adminCraneService.addCrane(name, cbrands, maxweight, introduce, images, guidePrice);
        String msg = null;
        if (flag>0){
            msg = "车辆新增成功!";
        }else{
            msg = "车辆新增失败!";
        }
        return CommonResult.success(msg);
    }

    @PostMapping("/editCrane")
    public CommonResult editCrane(@RequestBody(required = false) AdmAddCraneReqParam detailInfo){
        Integer craneid = detailInfo.getId();
        String name = detailInfo.getName();
        BigDecimal maxweight = detailInfo.getMaxweight();
        BigDecimal guidePrice = detailInfo.getGuidePrice();
        String introduce = detailInfo.getIntroduce();
        String images = detailInfo.getImages();
        Integer flag = adminCraneService.editCrane(craneid, name, maxweight, introduce, images,guidePrice);

        String msg = null;
        if (flag>0){
            msg = "车辆信息修改成功!";
        }else{
            msg = "车辆信息修改失败!";
        }
        return CommonResult.success(msg);
    }

    /**
     * 车辆移除
     * @param id
     * @return
     */
    @GetMapping("/removeCrane/{id}")
    public CommonResult removeCrane(@PathVariable Integer id){
        Integer flag = adminCraneService.removeCrane(id);
        String msg = null;
        if (flag>0){
            msg = "车辆移除成功!";
        }else{
            msg = "车辆移除失败!";
        }
        return CommonResult.success(msg);
    }

    /**
     * 配重移除
     * @param id
     * @return
     */
    @GetMapping("/removeWeight/{id}")
    public CommonResult removeWeight(@PathVariable Integer id){
        Integer flag = adminCraneService.removeWeight(id);
        String msg = null;
        if (flag>0){
            msg = "配重移除成功!";
        }else{
            msg = "配重移除失败!";
        }
        return CommonResult.success(msg);
    }

}
