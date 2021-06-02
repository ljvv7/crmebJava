package com.zbkj.crmeb.nsl.nslwxapp.request;

import lombok.Data;

@Data
public class LimitEntry {

    private long pageindex;

    private long pagesize;

    private String code;

    private String keywords;

    private Integer craneid;

    private Integer companyid;

}
