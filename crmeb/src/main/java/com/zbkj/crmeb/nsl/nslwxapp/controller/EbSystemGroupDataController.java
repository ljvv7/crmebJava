package com.zbkj.crmeb.nsl.nslwxapp.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.CommonResult;
import com.zbkj.crmeb.nsl.nslwxapp.response.CannerCbrandsEntry;
import com.zbkj.crmeb.nsl.nslwxapp.response.EbSystemGroup;
import com.zbkj.crmeb.nsl.nslwxapp.service.EbSystemGroupDataService;
import com.zbkj.crmeb.nsl.nslwxapp.service.NslCollectionService;
import com.zbkj.crmeb.nsl.nslwxapp.service.NslCraneService;
import com.zbkj.crmeb.system.model.SystemGroupData;
import com.zbkj.crmeb.system.service.SystemGroupDataService;
import com.zbkj.crmeb.system.service.SystemGroupService;
import com.zbkj.crmeb.user.model.User;
import com.zbkj.crmeb.user.model.UserBill;
import com.zbkj.crmeb.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private NslCollectionService collectionService;
    @Autowired
    private UserService userService;
    @Autowired
    private SystemGroupDataService systemGroupDataService;


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

    /**
     * 我的
     */
    @PostMapping("/user/index/{userId}")
    public CommonResult info(@PathVariable  int userId){

        User byId = userService.getById(userId);
        List listByGid = systemGroupDataService.getSystemByGidId(73);
        List listByGids = systemGroupDataService.getSystemByGidId(74);

        Map map = new HashMap();
        map.put("userinfo",byId);
        map.put("iconserver",listByGid);
        map.put("iconsystem",listByGids);


        return CommonResult.success(map);
    }

    /**
     * 获取收藏列表
     */
    @PostMapping("/collectlist/{userId}")
    public CommonResult getAllConnection(@PathVariable Integer userId){

        Page page = new Page(0,100);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userid",userId);
        queryWrapper.ne("kbn",9);
        collectionService.page(page, queryWrapper);
        //获取所有车辆id
        List list =  collectionService.getCraneCollectByUserId(userId);
        //根据车辆id查找所有车辆
        List allCraneByCompanyId = nslCraneService.getAllCraneByCompanyId(list, 0, 100);
        Map map = new HashMap();
        map.put("connection",page);
        map.put("allCraneByCompany",allCraneByCompanyId);
        return CommonResult.success(map);
    }

}

