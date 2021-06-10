package com.zbkj.crmeb.nsl.nslemons.request;

import lombok.Data;

@Data
public class AdmWayListReqParam {
    private Integer craneid;
    private Integer cweightid;
    private Integer cwayid;
    private Long page;
    private Long limit;
}
