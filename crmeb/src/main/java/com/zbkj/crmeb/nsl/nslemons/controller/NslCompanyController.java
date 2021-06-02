package com.zbkj.crmeb.nsl.nslemons.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.CommonPage;
import com.common.CommonResult;
import com.zbkj.crmeb.nsl.nslemons.model.NslCompany;
import com.zbkj.crmeb.nsl.nslemons.request.CompanyLimitEntry;
import com.zbkj.crmeb.nsl.nslemons.service.NslCompanyService;
import com.zbkj.crmeb.nsl.nslwxapp.request.AddCompanyEntry;
import com.zbkj.crmeb.nsl.nslwxapp.service.NslCbindService;
import com.zbkj.crmeb.nsl.nslwxapp.service.NslCraneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-05-29
 */
@RestController
@RequestMapping("api/admin/nsl/company")
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
    public CommonResult getAllCompany(@RequestBody(required = false) CompanyLimitEntry tableFrom){

        Long limit = tableFrom.getPagesize();
        Long page = tableFrom.getPageindex();
        String companyName = tableFrom.getCode();

        Page pageCourse = new Page(limit, page);
        QueryWrapper queryWrapper = new QueryWrapper();
        if(!StringUtils.isEmpty(companyName)){
            Pattern pattern = Pattern.compile("[0-9]*");
            Matcher matcher = pattern.matcher(companyName);
            if(matcher.matches()){
                queryWrapper.like("phone",companyName);
            }else {
                queryWrapper.like("name",companyName);
            }
        }
        queryWrapper.orderByAsc("id");
        nslCompanyService.page(pageCourse, queryWrapper);
        //总数
        long total = pageCourse.getTotal();
        List records = pageCourse.getRecords();
        CommonPage commonPage = new CommonPage();
        commonPage.setPage(page.intValue());
        commonPage.setLimit(limit.intValue());

        commonPage.setTotal(Long.valueOf(records.size()));
        commonPage.setList(records);;

        return CommonResult.success(commonPage);
    }

    /**
     * 根据公司Id获取公司详情
     */
    @PostMapping("/getdetail")
    public CommonResult getCompanyById(@RequestBody(required = false) CompanyLimitEntry tableFrom){
        Integer companyid = tableFrom.getCompanyid();
        Long pagesize = tableFrom.getPagesize();
        Long pageindex = tableFrom.getPageindex();
        //获取公司详情
        NslCompany byId = nslCompanyService.getById(companyid);
        //根据公司查找车辆
        List list = nslCbindService.getCraneIdByCompanyId(companyid);
        //获取公司下所有车辆
        List allCraneByCompanyId = craneService.getAllCraneByCompanyId(list, pageindex, pagesize);
        Map map = new HashMap();
        map.put("cranedetail",byId);
        map.put("companylist",allCraneByCompanyId);
        return CommonResult.success(map);
    }

    /**
     * 公司入驻
     */
    @PostMapping("add")
    public CommonResult addCompany(@RequestBody NslCompany tableFrom){
        boolean save = nslCompanyService.save(tableFrom);
        return CommonResult.success(save);

    }

    /**
     * 公司认证提示
     */
    @PostMapping("authmsgv1")
    public CommonResult authmsgv1(){

        String authmsgv1 = nslCompanyService.getAuthmsgv1();

        return CommonResult.success(authmsgv1);

    }

    /**
     * 公司车辆关系解除
     */



}

