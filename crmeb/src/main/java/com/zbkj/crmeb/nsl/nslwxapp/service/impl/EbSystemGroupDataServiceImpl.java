package com.zbkj.crmeb.nsl.nslwxapp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.utils.CrmebUtil;
import com.zbkj.crmeb.nsl.nslwxapp.dao.EbSystemGroupDataMapper;
import com.zbkj.crmeb.nsl.nslwxapp.model.EbSystemGroupData;
import com.zbkj.crmeb.nsl.nslwxapp.service.EbSystemGroupDataService;
import com.zbkj.crmeb.system.request.SystemFormItemCheckRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 组合数据详情表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-06-01
 */
@Service
public class EbSystemGroupDataServiceImpl extends ServiceImpl<EbSystemGroupDataMapper, EbSystemGroupData> implements EbSystemGroupDataService {

    /**
     * 获取首页banner滚动图
     * @return
     */

    @Override
    public List PublishCourseInfo() {

        List<EbSystemGroupData> list = baseMapper.getPublishCourseInfo();
//
        List<HashMap<String, Object>> arrayList = new ArrayList<>();
        if(list.size() < 1){
            return null;
        }
        for (EbSystemGroupData systemGroupData:list) {
            JSONObject jsonObject = JSONObject.parseObject(systemGroupData.getValue());
            List<SystemFormItemCheckRequest> systemFormItemCheckRequestList = CrmebUtil.jsonToListClass(jsonObject.getString("fields"), SystemFormItemCheckRequest.class);
            if(systemFormItemCheckRequestList.size() < 1){
                continue;
            }
            HashMap<String, Object> map = new HashMap<>();
            for (SystemFormItemCheckRequest systemFormItemCheckRequest : systemFormItemCheckRequestList) {
                map.put(systemFormItemCheckRequest.getName(), systemFormItemCheckRequest.getValue());
            }
            map.put("id", systemGroupData.getId());
            arrayList.add(map);
        }


        return arrayList;
    }

    /**
     * 获取首页icon
     */
    @Override
    public List getIcon() {
        List<EbSystemGroupData> list = baseMapper.getPublishIconInfo();

        List<HashMap<String, Object>> arrayList = new ArrayList<>();
        if(list.size() < 1){
            return null;
        }
        for (EbSystemGroupData systemGroupData:list) {
            JSONObject jsonObject = JSONObject.parseObject(systemGroupData.getValue());
            List<SystemFormItemCheckRequest> systemFormItemCheckRequestList = CrmebUtil.jsonToListClass(jsonObject.getString("fields"), SystemFormItemCheckRequest.class);
            if(systemFormItemCheckRequestList.size() < 1){
                continue;
            }
            HashMap<String, Object> map = new HashMap<>();
            for (SystemFormItemCheckRequest systemFormItemCheckRequest : systemFormItemCheckRequestList) {
                map.put(systemFormItemCheckRequest.getName(), systemFormItemCheckRequest.getValue());
            }
            map.put("id", systemGroupData.getId());
            arrayList.add(map);
        }


        return  arrayList;
    }

}
