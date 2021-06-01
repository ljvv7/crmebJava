package com.zbkj.crmeb.nsl.nslwxapp.request;

import lombok.Data;

@Data
public class LimitEntry {
    private long page;

    private long limit;

    private String keywords;

}
