package com.zbkj.crmeb.nsl.nslwxapp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.CommonResult;
import com.zbkj.crmeb.nsl.nslwxapp.model.EbSystemGroupData;
import com.zbkj.crmeb.nsl.nslwxapp.model.NslCollection;
import com.zbkj.crmeb.nsl.nslwxapp.response.CannerCbrandsEntry;
import com.zbkj.crmeb.nsl.nslwxapp.response.CollectAndCraneList;
import com.zbkj.crmeb.nsl.nslwxapp.response.EbSystemGroup;
import com.zbkj.crmeb.nsl.nslwxapp.service.EbSystemGroupDataService;
import com.zbkj.crmeb.nsl.nslwxapp.service.NslCbindService;
import com.zbkj.crmeb.nsl.nslwxapp.service.NslCollectionService;
import com.zbkj.crmeb.nsl.nslwxapp.service.NslCraneService;
import com.zbkj.crmeb.system.service.SystemGroupDataService;
import com.zbkj.crmeb.user.model.User;
import com.zbkj.crmeb.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "nsl-首页")
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
    @Autowired
    private NslCbindService nslCbindService;

    @ApiOperation(value = "首页导航图")
    @GetMapping("/index")
    public CommonResult getAll(){

        /**
         * 获取首页banner图
         */
        List banner = ebSystemGroupDataService.PublishCourseInfo();

        /**
         * 获取首页Icon图
         */
        List icon = ebSystemGroupDataService.getIcon();

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
    @ApiOperation(value = "我的")
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
    @ApiOperation(value = "获取收藏列表")
    public CommonResult getAllConnection(@PathVariable Integer userId){

        List<CollectAndCraneList> lists = nslCraneService.getcollectAndCraneList(userId);
        return CommonResult.success(lists);
    }

    /**
     * 车辆收藏
     */
    @PostMapping("addCollection/{userid}/{craneid}")
    @ApiOperation(value = "车辆收藏")
    public CommonResult addCollection(@PathVariable Integer userid,
                                      @PathVariable Integer craneid){

        NslCollection collection = new NslCollection();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userid",userid);
        int count = collectionService.count(queryWrapper);
        if(count > 10){
            return CommonResult.success("每个用户只能收藏10条数据!");
        }
        collection.setCraneid(craneid);
        collection.setUserid(userid);
        collection.setType("0");
        collection.setKbn("0");
        boolean save = collectionService.save(collection);
        return CommonResult.success(save);
    }

}

