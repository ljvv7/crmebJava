package com.zbkj.crmeb.nsl.nslemons.request;

import io.swagger.models.auth.In;
import lombok.Data;

@Data
public class CompanyLimitEntry {

    private long pageindex;

    private long pagesize;
    private String code;

    private String keywords;

    private Integer craneid;

    private Integer companyid;


}
