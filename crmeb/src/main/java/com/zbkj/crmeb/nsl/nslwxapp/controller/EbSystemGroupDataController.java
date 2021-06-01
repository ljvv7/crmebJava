package com.zbkj.crmeb.nsl.nslwxapp.controller;


import com.common.CommonResult;
import com.zbkj.crmeb.nsl.nslwxapp.response.CannerCbrandsEntry;
import com.zbkj.crmeb.nsl.nslwxapp.response.EbSystemGroup;
import com.zbkj.crmeb.nsl.nslwxapp.service.EbSystemGroupDataService;
import com.zbkj.crmeb.nsl.nslwxapp.service.NslCraneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 组合数据详情表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-06-01
 */
@RestController
@RequestMapping("api/admin/nsl")
public class EbSystemGroupDataController {

    @Autowired
    private EbSystemGroupDataService ebSystemGroupDataService;

    @Autowired
    private NslCraneService nslCraneService;


    @PostMapping("/index")
    public CommonResult getAll(){

        /**
         * 获取首页banner图
         */
        List<EbSystemGroup> banner = ebSystemGroupDataService.PublishCourseInfo();

        /**
         * 获取首页Icon图
         */
        List<EbSystemGroup> icon = ebSystemGroupDataService.getIcon();

        /**
         * 获取最新车辆
         */
        List<CannerCbrandsEntry> hotCraneList = nslCraneService.getHotCraneList();

        /**
         * 获取最火车辆
         */
        List<CannerCbrandsEntry> newCraneList = nslCraneService.getNewCraneList();

        Map map = new HashMap();
        map.put("Banner",banner);
        map.put("icon",icon);
        map.put("hotcrane",hotCraneList);
        map.put("newcrane",newCraneList);
        return CommonResult.success(map);

    }

}

