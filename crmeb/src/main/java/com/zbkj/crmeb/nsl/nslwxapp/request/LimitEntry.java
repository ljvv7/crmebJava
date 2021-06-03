package com.zbkj.crmeb.nsl.nslwxapp.request;

import lombok.Data;

@Data
public class LimitEntry {

    private Integer pageindex;

    private Integer pagesize;

    private String code;

    private String keywords;

    private Integer craneid;

    private Integer companyid;

    private Integer cbrandid;

    private Integer cweightid;

    private Integer cwayid;

    private Integer userid;

}
