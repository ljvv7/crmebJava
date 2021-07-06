package com.zbkj.crmeb.nsl.nslemons.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class AdmAddCraneReqParam {

    @NotNull(message = "车辆id不能为空")
    private Integer id;

    @NotNull(message = "品牌id不能为空")
    private Integer cbrands;

    @NotNull(message = "车辆型号不能为空")
    private String name;

    @NotNull(message = "最大起重量不能为空")
    private BigDecimal maxweight;

    @NotNull(message = "车型图片路径不能为空")
    private String images;

    @NotNull(message = "车辆简介不能为空")
    private String introduce;

    @NotNull(message = "指导价格不能为空")
    private BigDecimal guidePrice;
}
