package com.zbkj.crmeb.nsl.nslemons.request;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class AdmAddCraneReqParam {
    private Integer id;
    private Integer cbrands;
    private String name;
    private BigDecimal maxweight;
    private String images;
    private String introduce;
    private BigDecimal guidePrice;
}
