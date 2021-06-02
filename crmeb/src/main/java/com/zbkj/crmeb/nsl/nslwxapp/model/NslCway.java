package com.zbkj.crmeb.nsl.nslwxapp.model;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author testjava
 * @since 2021-06-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="NslCway对象", description="")
public class NslCway implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private Long id;

    @ApiModelProperty(value = "配重id")
    private Integer weightid;

    @ApiModelProperty(value = "工作范围")
    private BigDecimal radius;

    @ApiModelProperty(value = "主臂长度")
    private BigDecimal primaryLength;

    @ApiModelProperty(value = "次臂长度")
    private BigDecimal minorLength;

    @ApiModelProperty(value = "起重量")
    private BigDecimal weight;

    @ApiModelProperty(value = "伸缩方式")
    private String way;

    @ApiModelProperty(value = "副臂角度")
    private Integer angle;

    @ApiModelProperty(value = "吊车id")
    private Integer craneid;

    @ApiModelProperty(value = "数据来源，插值还是原始值")
    private Integer origin;

    @ApiModelProperty(value = "吊臂组合方式")
    private Integer legtype;


}
