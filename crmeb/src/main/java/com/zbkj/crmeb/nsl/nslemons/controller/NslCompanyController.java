package com.zbkj.crmeb.nsl.nslemons.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.common.CommonResult;
import com.zbkj.crmeb.nsl.nslemons.model.NslCompany;
import com.zbkj.crmeb.nsl.nslemons.request.GetAllComapnyEntry;
import com.zbkj.crmeb.nsl.nslemons.request.GetAllCompanyByIdEntry;
import com.zbkj.crmeb.nsl.nslemons.service.NslCompanyService;
import com.zbkj.crmeb.nsl.nslwxapp.service.NslCbindService;
import com.zbkj.crmeb.nsl.nslwxapp.service.NslCraneService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-05-29
 */
@RestController
@RequestMapping("/api/admin/nsl/company")
@Api(tags = "nsl-公司接口")
@CrossOrigin
public class NslCompanyController {

    @Autowired
    private NslCompanyService nslCompanyService;

    @Autowired
    private NslCbindService nslCbindService;
    @Autowired
    private NslCraneService craneService;

    /**
     * 获取公司列表
     * @param tableFrom
     * @return
     */
    @PostMapping("/getAllCompany")
    @ApiOperation(value = "获取公司列表")
    public CommonResult getAllCompany(@RequestBody(required = false) GetAllComapnyEntry tableFrom){

        int limit = tableFrom.getPagesize();
        int page = tableFrom.getPageindex();
        String companyName = tableFrom.getCode();
        double longitude = tableFrom.getLongitude();
        double latitude = tableFrom.getLatitude();

        Map map = new HashMap();
        if(!StringUtils.isEmpty(companyName)){
            map = nslCompanyService.getAllCompanyLimit(page,limit,companyName,longitude,latitude);
        }else {
            map = nslCompanyService.getAllCompany(page,limit,longitude,latitude);
        }
        return CommonResult.success(map);
    }

    /**
     * 根据公司Id获取车辆详情
     */
    @PostMapping("/getdetail")
    @ApiOperation(value = "根据公司获取车辆")
    public CommonResult getCompanyById(@RequestBody(required = false) GetAllCompanyByIdEntry tableFrom){
        Integer companyid = tableFrom.getCode();
        int pagesize = tableFrom.getPagesize();
        int pageindex = tableFrom.getPageindex();
        Map map = new HashMap();
        //获取公司详情
        NslCompany byId = nslCompanyService.getById(companyid);
        //根据公司查找车辆
        List list = nslCbindService.getCraneIdByCompanyId(companyid);
        List list1 = new ArrayList();
        if(null == (byId)){

        }else {
            list1.add(byId);
        }
        //获取公司下所有车辆
        if(list.size()>0){
            List allCraneByCompanyId = craneService.getAllCraneByCompanyId(list, pageindex, pagesize);
            map.put("cranedetaillist",allCraneByCompanyId);
        }
        map.put("companyById",list1);

        return CommonResult.success(map);
    }

    /**
     * 公司入驻
     */
    @PostMapping("add")
    @ApiOperation(value = "公司入驻")
    public List addCompany(@RequestBody NslCompany tableFrom){
        String userid = tableFrom.getUserid();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userid",userid);
        List list = new ArrayList();
        if(nslCompanyService.list(queryWrapper).size()>0){
            String msg = "用户只能入驻一个公司!";
            list.add(msg);
        }else {
            boolean save = nslCompanyService.save(tableFrom);
            if(save == true){
                list.add("入驻成功");
            }
        }
        return list;
    }

    /**
     * 公司认证提示
     */
    @PostMapping("authmsgv1")
    @ApiOperation(value = "公司认证提示")
    public CommonResult authmsgv1(){

        String authmsgv1 = nslCompanyService.getAuthmsgv1();

        return CommonResult.success(authmsgv1);

    }

    /**
     * 公司车辆关系解除
     */

    @PostMapping("deleteCompanyAndCrane/{companyId}/{craneId}")
    public CommonResult deleteCompanyAndCrane(@PathVariable int companyId,
                                              @PathVariable int craneId){

        QueryWrapper queryWrapper= new QueryWrapper();
        queryWrapper.eq("companyid",companyId);
        queryWrapper.eq("craneid",craneId);
        boolean remove = nslCbindService.remove(queryWrapper);

        return CommonResult.success(remove);
    }

    /**
     * 更新公司信息
     */
    @PostMapping("updateCompanyById")
    public CommonResult updateCompanyById(@RequestBody NslCompany tableFrom){
        System.out.println("tableForm == "+tableFrom);
        boolean save = nslCompanyService.updateById(tableFrom);
        return CommonResult.success(save);
    }

    /**
     * 根据公司id删除公司  (逻辑删除)
     */
    @GetMapping("deleteCompanyById/{id}")
    public void deleteCompanyById(@PathVariable int id){
        nslCompanyService.deleteCompanyKbn(id);
    }

    /**
     * 根据公司id设置公司状态
     */
    @GetMapping("updateStatusByid/{id}/{status}")
    public void updateStatusByid (@PathVariable int id,
                                               @PathVariable String status){
        nslCompanyService.updateStatusByid(id,status);

    }
}

