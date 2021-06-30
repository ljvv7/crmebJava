package com.zbkj.crmeb.nsl.nslwxapp.controller;

import com.common.CommonResult;
import com.zbkj.crmeb.nsl.commountils.DistanceUtil;
import com.zbkj.crmeb.nsl.nslemons.model.NslCompany;
import com.zbkj.crmeb.nsl.nslemons.service.NslCompanyService;
import com.zbkj.crmeb.nsl.nslwxapp.model.NslCbrands;
import com.zbkj.crmeb.nsl.nslwxapp.model.NslCrane;
import com.zbkj.crmeb.nsl.nslwxapp.model.NslCway;
import com.zbkj.crmeb.nsl.nslwxapp.model.NslCweight;
import com.zbkj.crmeb.nsl.nslwxapp.response.CraneByWayEntry;
import com.zbkj.crmeb.nsl.nslwxapp.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
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
@Api(tags = "nsl-车辆接口")
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
     * @param craneid
     * @param pageindex
     * @param pagesize
     * @return
     */
    @PostMapping("/getdetail")
    @ApiOperation(value = "车辆详情")
    public CommonResult getDetail(@RequestParam Integer craneid,
                                  @RequestParam Long pageindex,
                                  @RequestParam Long pagesize,
                                  @RequestParam Double lot,
                                  @RequestParam Double lat){

        Map map = new HashMap();
        //根据车辆id获取车辆信息
        NslCrane cranedetail = nslCraneService.getCraneDetailById(craneid);

        Double longitude = cranedetail.getLongitude();
        Double latitude = cranedetail.getLatitude();
        double distance = DistanceUtil.getDistance(lot, lat, longitude, latitude);
        map.put("distance",distance);

        //根据车辆id查询公司ids
        List companyIds = nslCbindService.getCompanyIdsByCraneId(craneid);

        //根据公司ids查询公司的信息
        List<NslCompany> companyList = nslCompanyService.getCompanyList(companyIds, ((pageindex-1)*pagesize), pagesize);
        //查询公司信息总条数
        Integer companyListCount = nslCompanyService.getCompanyListCount(companyIds);


        map.put("cranedetail",cranedetail);
        map.put("companylist",companyList);
        map.put("count",companyListCount);

        return CommonResult.success(map);
    }

    /**
     * 品牌列表
     * @return
     */
    @PostMapping("/brandlist")
    @ApiOperation(value = "品牌列表")
    public CommonResult getBrandsList(){

        //获取品牌列表
        List<NslCbrands> brandList = nslCbrandsService.getBrandList();

        return CommonResult.success(brandList);
    }

    /**
     * 车辆查找-品牌、型号
     * @param cbrandid
     * @param craneid
     * @param pageindex
     * @param pagesize
     * @return
     */
    @PostMapping("/cranelist")
    @ApiOperation(value = "按品牌和型号查车辆")
    public CommonResult getCraneList(@RequestParam(required = false) Integer cbrandid,
                                     @RequestParam(required = false) Integer craneid,
                                     @RequestParam Long pageindex,
                                     @RequestParam Long pagesize){

        Map craneMap = new HashMap();

        List<NslCrane> craneList = nslCraneService.getCraneList(cbrandid, craneid, ((pageindex-1)*pagesize), pagesize);

        Integer craneListCount = nslCraneService.getCraneListCount(cbrandid, craneid);

        craneMap.put("count",craneListCount);
        craneMap.put("craneList",craneList);
        return CommonResult.success(craneMap);
    }

    @PostMapping("/cranelistByWay")
    @ApiOperation(value = "按参数查车辆")
    public CommonResult getCraneListByWay(@RequestParam(required = false) Integer legType,
                                          @RequestParam BigDecimal radius,
                                          @RequestParam BigDecimal weight,
                                          @RequestParam Double minPercent,
                                          @RequestParam Double maxPercent,
                                          @RequestParam(required = false) BigDecimal minPrimary,
                                          @RequestParam(required = false) BigDecimal maxPrimary,
                                          @RequestParam(required = false) BigDecimal minMinor,
                                          @RequestParam(required = false) BigDecimal maxMinor,
                                          @RequestParam Long pageindex,
                                          @RequestParam Long pagesize){


        Map craneMap = new HashMap();
        //计算weight范围
        Double minWeight = weight.doubleValue()/(maxPercent/100);
        Double maxWeight = weight.doubleValue()/(minPercent/100);
        List craneWayIds = nslCraneService.getCraneWayIds(legType, radius,minWeight, maxWeight,
                                            minPrimary, maxPrimary,minMinor, maxMinor);
        //craneWayIds为空，则返回空
        if (craneWayIds.isEmpty()){
            List list = new ArrayList();
            craneMap.put("count",0);
            craneMap.put("craneList",list);
            return CommonResult.success(craneMap);
        }

        //拿到查到的craneList
        List<CraneByWayEntry> craneListByWay = nslCraneService.getCraneListByWay(craneWayIds, ((pageindex-1)*pagesize), pagesize);
        //查询总条数
        Integer craneListByWayCount = nslCraneService.getCraneListByWayCount(craneWayIds);

        //给返回列表每一项percent设置值
        for (CraneByWayEntry item : craneListByWay) {
            item.setPercent(Math.floor(weight.doubleValue()/item.getWeight().doubleValue()*100));
        }

        craneMap.put("count",craneListByWayCount);
        craneMap.put("craneList",craneListByWay);

        return CommonResult.success(craneMap);
    }

    /**
     * 根据车辆id查出配重列表
     * @param craneid
     * @param pageindex
     * @param pagesize
     * @return
     */
    @PostMapping("/weightlist")
    @ApiOperation(value = "车辆配重列表")
    public CommonResult getWeightList(@RequestParam Integer craneid,
                                      @RequestParam Long pageindex,
                                      @RequestParam Long pagesize){

        List<NslCweight> weightList = nslCweightService.getWeightList(craneid,((pageindex-1)*pagesize),pagesize);
        Integer weightListCount = nslCweightService.getWeightListCount(craneid);
        Map weightMap = new HashMap();
        weightMap.put("count",weightListCount);
        weightMap.put("weightList",weightList);
        return CommonResult.success(weightMap);
    }

    /**
     * 组合方式列表
     * @param craneid
     * @param cweightid
     * @param cwayid
     * @param pageindex
     * @param pagesize
     * @return
     */
    @PostMapping("/waylist")
    @ApiOperation(value = "车辆组合方式列表")
    public CommonResult getWayList(@RequestParam Integer craneid,
                                   @RequestParam Integer cweightid,
                                   @RequestParam(required = false) Integer cwayid,
                                   @RequestParam Long pageindex,
                                   @RequestParam Long pagesize){

        List<NslCway> wayList = nslCwayService.getWayList(craneid, cweightid, cwayid, ((pageindex-1)*pagesize), pagesize);
        Integer wayListCount = nslCwayService.getWayListCount(craneid, cweightid, cwayid);
        Map wayMap = new HashMap();
        wayMap.put("count",wayListCount);
        wayMap.put("wayList",wayList);

        return CommonResult.success(wayMap);

    }

    /**
     * 车辆添加绑定
     * @param companyid
     * @param userid
     * @param craneid
     * @return
     */
    @PostMapping("/compbindcrane")
    @ApiOperation(value = "车辆添加绑定")
    public List addCompBandCrane(@RequestParam Integer companyid,
                                 @RequestParam Integer userid,
                                 @RequestParam Integer craneid){

        List list = new ArrayList();
        Integer isBindedCount = nslCbindService.queryIsBinded(companyid, userid, craneid);
        if (isBindedCount>0){
            String msg = "车辆和公司已经存在绑定关系!";
            list.add(msg);
        }else {
            nslCbindService.addCompBindCrane(companyid, userid, craneid);
            String msg = "绑定成功!";
            list.add(msg);
        }
        return list;
    }

}

