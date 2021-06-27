package com.zbkj.crmeb.nsl.nslemons.request;

import lombok.Data;

import java.util.List;

@Data
public class AdmAddWayReqParam {
    private Integer craneid;
    private Integer weightid;
    private List list;
}
