package com.zbkj.crmeb.nsl.nslwxapp.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class HotOrNewCraneEntry {

    @ApiModelProperty(value = "ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "车辆型号名称")
    private String name;

    @ApiModelProperty(value = "最大起重量")
    private BigDecimal maxweight;

    @ApiModelProperty(value = "介绍")
    private String introduce;

    @ApiModelProperty(value = "车型图片")
    private String images;

    @ApiModelProperty(value = "指导价格")
    private BigDecimal guidePrice;

    @ApiModelProperty(value = "访问量")
    private Integer visits;

}
