package com.zbkj.crmeb.nsl.nslemons.request;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class AdmCraneListReqParam {

    private Integer cbrandid;

    private Integer craneid;

    private String craneName;

    @NotNull(message = "当前页不能为空")
    @Min(value = 1,message = "当前页最小值为1")
    private Long page;

    @NotNull(message = "每页数量不能为空")
    @Max(value = 500,message = "每页数量最大值为500")
    private Long limit;

}
