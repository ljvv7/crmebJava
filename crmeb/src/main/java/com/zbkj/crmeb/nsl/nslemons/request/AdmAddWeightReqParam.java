package com.zbkj.crmeb.nsl.nslemons.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AdmAddWeightReqParam {
    private Integer craneid;
    private String legtype;
    private String legway;
    private BigDecimal equipweight;
    private Integer workextent;
    private String issuperweight;
    private BigDecimal superweight;
    private BigDecimal backmove;
    private String remarks;
}
