package com.zbkj.crmeb.nsl.nslemons.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 新增车辆请求参数实体
 */
@Data
public class AdmAddCraneReqParam {

    @NotNull(message = "品牌id不能为空")
    private Integer cbrands;

    @NotNull(message = "车辆型号不能为空")
    private String name;

    @NotNull(message = "最大起重量不能为空")
    @Min(value = 0,message = "最大起重量大于等于0")
    private BigDecimal maxweight;

    @NotNull(message = "车型图片不能为空")
    private String images;

    @NotNull(message = "车辆简介不能为空")
    private String introduce;

    @NotNull(message = "指导价格不能为空")
    @Min(value = 0,message = "指导价格大于等于0")
    private BigDecimal guidePrice;
}
