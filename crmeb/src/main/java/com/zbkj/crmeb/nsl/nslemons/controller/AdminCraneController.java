package com.zbkj.crmeb.nsl.nslemons.controller;

import com.alibaba.fastjson.JSONObject;
import com.common.CommonResult;
import com.zbkj.crmeb.nsl.commountils.ReadExcelUtils;
import com.zbkj.crmeb.nsl.nslemons.request.*;
import com.zbkj.crmeb.nsl.nslemons.service.AdminCraneService;
import com.zbkj.crmeb.nsl.nslwxapp.model.NslCbrands;
import com.zbkj.crmeb.nsl.nslwxapp.model.NslCrane;
import com.zbkj.crmeb.nsl.nslwxapp.model.NslCway;
import com.zbkj.crmeb.nsl.nslwxapp.model.NslCweight;
import com.zbkj.crmeb.nsl.nslwxapp.service.NslCbrandsService;
import com.zbkj.crmeb.nsl.nslwxapp.service.NslCwayService;
import com.zbkj.crmeb.nsl.nslwxapp.service.NslCweightService;
import com.zbkj.crmeb.store.service.StoreProductService;
import com.zbkj.crmeb.system.service.SystemAttachmentService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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

    @Autowired
    private SystemAttachmentService systemAttachmentService;

    /**
     * 后台车辆列表
     * @param tableFrom
     * @return
     */
    @PostMapping("/admCraneList")
    public CommonResult getAdmCraneList(@Validated @RequestBody(required = false) AdmCraneListReqParam tableFrom){

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
    public CommonResult getAdmWeightList(@Validated @RequestBody(required = false) AdmWeightListReqParam tableFrom){
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
    public CommonResult getAdmWayList(@Validated @RequestBody(required = false) AdmWayListReqParam tableFrom){
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
    public CommonResult addCrane(@Validated @RequestBody(required = false) AdmAddCraneReqParam craneInfo){
        Integer cbrands = craneInfo.getCbrands();
        String name = craneInfo.getName();
        BigDecimal maxweight = craneInfo.getMaxweight();
        BigDecimal guidePrice = craneInfo.getGuidePrice();
        String images = systemAttachmentService.clearPrefix( craneInfo.getImages()) ;
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

    /**
     * 车辆信息修改
     * @param detailInfo
     * @return
     */
    @PostMapping("/editCrane")
    public CommonResult editCrane(@Validated @RequestBody(required = false) AdmEditCraneReqParam detailInfo){
        Integer craneid = detailInfo.getId();
        String name = detailInfo.getName();
        BigDecimal maxweight = detailInfo.getMaxweight();
        BigDecimal guidePrice = detailInfo.getGuidePrice();
        String introduce = detailInfo.getIntroduce();
        String images = systemAttachmentService.clearPrefix(detailInfo.getImages()) ;
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

    /**
     * 配重新增
     * @param weightInfo
     * @return
     */
    @PostMapping("/addWeight")
    public CommonResult addWeight(@Validated @RequestBody(required = false) AdmAddWeightReqParam weightInfo){
        Integer craneid = weightInfo.getCraneid();
        String legtype = weightInfo.getLegtype();
        String legway = weightInfo.getLegway();
        BigDecimal equipweight = weightInfo.getEquipweight();
        Integer workextent = weightInfo.getWorkextent();
        String issuperweight = weightInfo.getIssuperweight();
        BigDecimal superweight = weightInfo.getSuperweight();
        BigDecimal backmove = weightInfo.getBackmove();
        String remarks = weightInfo.getRemarks();

        Integer weightid = adminCraneService.queryMaxWeight();

        Integer flag = adminCraneService.addWeight(weightid,craneid, legtype, legway, equipweight, workextent,
                                                    issuperweight, superweight, backmove, remarks);
        String msg = null;
        if (flag>0){
            msg = "新增配重成功!";
        }else{
            msg = "新增配重失败!";
        }
        return CommonResult.success(msg);
    }

    /**
     * 配重修改
     * @param weightDetail
     * @return
     */
    @PostMapping("/editWeight")
    public CommonResult editWeight(@Validated @RequestBody(required = false) AdmEditWeightReqParam weightDetail){
        Integer weightid = weightDetail.getId();
        Integer craneid = weightDetail.getCraneid();
        String legtype = weightDetail.getLegtype();
        String legway = weightDetail.getLegway();
        BigDecimal equipweight = weightDetail.getEquipweight();
        Integer workextent = weightDetail.getWorkextent();
        String issuperweight = weightDetail.getIssuperweight();
        BigDecimal superweight = weightDetail.getSuperweight();
        BigDecimal backmove = weightDetail.getBackmove();
        String remarks = weightDetail.getRemarks();

        Integer flag = adminCraneService.editWeight(weightid,craneid,legtype,legway,equipweight,workextent,
                                                    issuperweight,superweight,backmove,remarks);
        String msg = null;
        if (flag>0){
            msg = "配重修改成功!";
        }else{
            msg = "新增修改失败!";
        }
        return CommonResult.success(msg);
    }

    /**
     * 上传excel
     * @param file
     * @return
     */
    @PostMapping("/importExcel")
    public CommonResult importExcel(@RequestParam MultipartFile file){

        String filename = file.getOriginalFilename();
        String fileXlsx = filename.substring(filename.length()-5);       //获取文件的后缀名为xlsx
        String fileXls = filename.substring(filename.length()-4);
        Map map = new HashMap();
        if(!(fileXlsx.equals(".xlsx") || fileXls.equals(".xls"))){   //如果不是excel文件
            return CommonResult.failed("文件格式错误!");
        }

        //用ApachePOI做的
        List<Map> list = ReadExcelUtils.readExcel(file);

        map.put("excelList",list);
        return CommonResult.success(map);
    }

    @PostMapping("/addWay")
    public CommonResult addWay(@RequestBody(required = false) AdmAddWayReqParam addWayParams){

        Integer craneid = addWayParams.getCraneid();
        Integer weightid = addWayParams.getWeightid();
        List excelList = addWayParams.getList();

        //判断是不是新增配重时的新增组合方式，是的话从库里把最大配重id查到再添加组合方式
        if (weightid == null){
            weightid = adminCraneService.queryMaxWeight();
        }
        //先将之前配重ID下面的组合方式删除
        adminCraneService.deleteWay(weightid);
        //
        Integer flag = adminCraneService.addWay(weightid, craneid, excelList);
        String msg = null;
        if (flag>0){
            msg = "组合方式新增成功!";
        }else{
            msg = "组合方式新增失败!";
        }
        return CommonResult.success(msg);
    }

}
