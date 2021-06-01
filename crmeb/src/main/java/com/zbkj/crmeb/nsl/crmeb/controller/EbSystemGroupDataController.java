package com.zbkj.crmeb.nsl.crmeb.controller;


import com.common.CommonResult;
import com.zbkj.crmeb.nsl.crmeb.response.EbSystemGroup;
import com.zbkj.crmeb.nsl.crmeb.service.EbSystemGroupDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 组合数据详情表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-05-31
 */
@RestController
@RequestMapping("/nsl/eb-system-group-data")
public class EbSystemGroupDataController {

    @Autowired
    private EbSystemGroupDataService ebSystemGroupDataService;

    @PostMapping("getList")
    public CommonResult getAll(){
        List<EbSystemGroup> list = ebSystemGroupDataService.getList();
        return CommonResult.success(list);
    }

}

