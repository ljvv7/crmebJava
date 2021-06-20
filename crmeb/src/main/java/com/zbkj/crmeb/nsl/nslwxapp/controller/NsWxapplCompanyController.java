package com.zbkj.crmeb.nsl.nslwxapp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.common.CommonResult;
import com.zbkj.crmeb.nsl.nslemons.model.NslCompany;
import com.zbkj.crmeb.nsl.nslemons.service.NslCompanyService;
import com.zbkj.crmeb.nsl.nslwxapp.service.NslCbindService;
import com.zbkj.crmeb.nsl.nslwxapp.service.NslCraneService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-05-29
 */
@RestController
@RequestMapping("/api/wxapp/nsl/company")
@Api(tags = "nsl-wechat小程序公司接口")
@CrossOrigin
public class NsWxapplCompanyController {

    @Autowired
    private NslCompanyService nslCompanyService;

    @Autowired
    private NslCbindService nslCbindService;
    @Autowired
    private NslCraneService craneService;

    /**
     * 获取公司列表
     * @param
     * @return
     */
    @PostMapping("/getAllCompany")
    @ApiOperation(value = "wechat-获取公司列表")
    public CommonResult getAllCompany(@RequestParam (required = false) String code,
                                      @RequestParam (required = true)  int page,
                                      @RequestParam (required = true) int limit,
                                      @RequestParam (required = true) long longitude,
                                      @RequestParam (required = true) long latitude){

//        int limit = tableFrom.getPagesize();
//        int page = tableFrom.getPageindex();
//        String companyName = tableFrom.getCode();
//        double longitude = tableFrom.getLongitude();
//        double latitude = tableFrom.getLatitude();

        Map map = new HashMap();
        if(!StringUtils.isEmpty(code)){
            map = nslCompanyService.getAllCompanyLimit(page,limit,code,longitude,latitude);
        }else {
            map = nslCompanyService.getAllCompany(page,limit,longitude,latitude);
        }
        return CommonResult.success(map);
    }

    /**
     * 根据公司Id获取车辆详情
     */
    @PostMapping("/getdetail")
    @ApiOperation(value = "wechat-根据公司获取车辆")
    public CommonResult getCompanyById(@RequestParam (required = false) int code,
                                       @RequestParam (required = true)  int pageindex,
                                       @RequestParam (required = true) int pagesize){
//        Integer companyid = tableFrom.getCode();
//        int pagesize = tableFrom.getPagesize();
//        int pageindex = tableFrom.getPageindex();
        Map map = new HashMap();
        //获取公司详情
        NslCompany byId = nslCompanyService.getById(code);
        //根据公司查找车辆
        List list = nslCbindService.getCraneIdByCompanyId(code);
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
    @ApiOperation(value = "wechat-公司入驻")
    public List addCompany(@RequestParam (required = true) String name,
                           @RequestParam (required = true) String logo,
                           @RequestParam (required = true) String introduce,
                           @RequestParam (required = true) String qualification,
                           @RequestParam (required = true) String province,
                           @RequestParam (required = true) String city,
                           @RequestParam (required = true) String area,
                           @RequestParam (required = true) String street,
                           @RequestParam (required = false) String adds,
                           @RequestParam (required = true) String longitude,
                           @RequestParam (required = true) String latitude,
                           @RequestParam (required = true) String atten,
                           @RequestParam (required = true) String phone,
                           @RequestParam (required = true) String userid
                           ){
        NslCompany tableFrom = new NslCompany();
//        String userid = tableFrom.getUserid();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userid",userid);
        List list = new ArrayList();
        if(nslCompanyService.list(queryWrapper).size()>0){
            String msg = "用户只能入驻一个公司!";
            list.add(msg);
        }else {
            tableFrom.setName(name);
            tableFrom.setName(logo);
            tableFrom.setIntroduce(introduce);
            tableFrom.setQualification(qualification);
            tableFrom.setProvince(province);
            tableFrom.setCity(city);
            tableFrom.setArea(area);
            tableFrom.setAdds(adds);
            tableFrom.setAdds(adds);
            tableFrom.setLongitude(longitude);
            tableFrom.setLatitude(latitude);
            tableFrom.setAtten(atten);
            tableFrom.setPhone(phone);
            tableFrom.setUserid(userid);
            boolean save = nslCompanyService.save(tableFrom);
            if(save == true){
                list.add("入驻成功");
            }
        }
        return list;
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

