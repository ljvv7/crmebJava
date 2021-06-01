package com.zbkj.crmeb.nsl.nslemons.request;

import lombok.Data;

@Data
public class CompanyLimitEntry {
    private long page;

    private long limit;

    private String keywords;

}
