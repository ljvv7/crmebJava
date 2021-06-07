package com.zbkj.crmeb.nsl.nslemons.request;

import lombok.Data;

@Data
public class AdmCraneListReqParam {

    private Integer cbrandid;
    private Integer craneid;
    private String craneName;
    private Long page;
    private Long limit;

}
