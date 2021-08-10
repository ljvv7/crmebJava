package com.zbkj.crmeb.nsl.nslemons.request;

import lombok.Data;

import java.util.List;

/**
 * 新增组合方式请求参数实体
 */
@Data
public class AdmAddWayReqParam {
    private Integer craneid;
    private Integer weightid;
    private List list;
}
