package com.zbkj.crmeb.nsl.nslemons.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class AdmAddWeightReqParam {

    @NotNull(message = "配重id不能为空")
    private Integer id;

    @NotNull(message = "车辆id不能为空")
    private Integer craneid;

    @NotNull(message = "组合方式不能为空")
    private String legtype;

    @NotNull(message = "支腿方式不能为空")
    private String legway;

    @NotNull(message = "配重不能为空")
    private BigDecimal equipweight;

    @NotNull(message = "工作范围不能为空")
    private Integer workextent;

    @NotNull(message = "是否超起不能为空")
    private String issuperweight;

    @NotNull(message = "超起配重不能为空")
    private BigDecimal superweight;

    @NotNull(message = "后移不能为空")
    private BigDecimal backmove;

    @NotNull(message = "备注不能为空")
    private String remarks;
}
