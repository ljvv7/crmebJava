package com.zbkj.crmeb.nsl.nslemons.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.CommonPage;
import com.common.CommonResult;
import com.zbkj.crmeb.nsl.nslemons.model.NslCompany;
import com.zbkj.crmeb.nsl.nslemons.request.CompanyLimitEntry;
import com.zbkj.crmeb.nsl.nslemons.service.NslCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
        queryWrapper.orderByDesc("id");
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
        NslCompany byId = nslCompanyService.getById(companyid);

        return CommonResult.success(byId);
    }

}

