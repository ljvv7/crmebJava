package com.zbkj.crmeb.nsl.nslwxapp.model;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 车辆信息表
 * </p>
 *
 * @author testjava
 * @since 2021-06-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="NslCrane对象", description="车辆信息表")
public class NslCrane implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "ID")
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "车辆型号名称")
    private String name;

    @ApiModelProperty(value = "品牌ID")
    private Integer cbrands;

    @ApiModelProperty(value = "品牌名称")
    private String brandName;

    @ApiModelProperty(value = "最大起重量")
    private BigDecimal maxweight;

    @ApiModelProperty(value = "介绍")
    private String introduce;

    @ApiModelProperty(value = "车型图片")
    private String images;

    @ApiModelProperty(value = "导出图片")
    private String expdbcodes;

    @ApiModelProperty(value = "指导价格")
    private BigDecimal guidePrice;

    @ApiModelProperty(value = "是否被收藏；1：被收藏，0：未被收藏")
    private String iscollected;

    @ApiModelProperty(value = "经度")
    private Double longitude;

    @ApiModelProperty(value = "纬度")
    private Double latitude;

    @ApiModelProperty(value = "访问量")
    private Integer visits;

    @ApiModelProperty(value = "分录备注")
    private String remarks;

//    @ApiModelProperty(value = "默认传入0；   0：新增；1修改；9删除")
//    private String kbn;
//
//    @ApiModelProperty(value = "不传；默认当前日期")
//    private Date createDate;
//
//    @ApiModelProperty(value = "传入数据姓名；")
//    private String createUser;
//
//    @ApiModelProperty(value = "不传；默认当前日期")
//    private Date updateDate;
//
//    @ApiModelProperty(value = "传入数据姓名；")
//    private String updateUser;


}
