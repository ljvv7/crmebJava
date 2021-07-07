package com.zbkj.crmeb.nsl.nslemons.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 编辑配重请求参数实体
 */
@Data
public class AdmEditWeightReqParam {

    @NotNull(message = "配重id不能为空")
    private Integer id;

    @NotNull(message = "车辆id不能为空")
    private Integer craneid;

    @NotNull(message = "组合方式不能为空")
    private String legtype;

    @NotNull(message = "支腿方式不能为空")
    private String legway;

    @NotNull(message = "配重不能为空")
    @Min(value = 0,message = "配重大于等于0")
    private BigDecimal equipweight;

    @NotNull(message = "工作范围不能为空")
    @Min(value = 0,message = "工作范围大于等于0")
    private Integer workextent;

    @NotNull(message = "是否超起不能为空")
    private String issuperweight;

    @NotNull(message = "超起配重不能为空")
    @Min(value = 0,message = "超起配重大于等于0")
    private BigDecimal superweight;

    @NotNull(message = "后移不能为空")
    @Min(value = 0,message = "后移大于等于0")
    private BigDecimal backmove;

    @NotNull(message = "备注不能为空")
    private String remarks;

}
