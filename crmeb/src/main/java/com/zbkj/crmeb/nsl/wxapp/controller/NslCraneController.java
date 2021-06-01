package com.zbkj.crmeb.nsl.wxapp.controller;


import com.zbkj.crmeb.nsl.wxapp.service.NslCraneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 车辆信息表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-06-01
 */
@RestController
@RequestMapping("api/admin/nsl/company")
public class NslCraneController {

    @Autowired
    private NslCraneService nslCraneService;



}

