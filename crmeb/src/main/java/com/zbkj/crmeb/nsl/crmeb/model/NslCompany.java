package com.zbkj.crmeb.nsl.crmeb.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author testjava
 * @since 2021-05-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="NslCompany对象", description="公司列表")
public class NslCompany implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "LOGO")
    private String logo;

    @ApiModelProperty(value = "公司简介")
    private String introduce;

    @ApiModelProperty(value = "资质")
    private String qualification;

    @ApiModelProperty(value = "省")
    private String province;

    @ApiModelProperty(value = "市")
    private String city;

    @ApiModelProperty(value = "区")
    private String area;

    @ApiModelProperty(value = "街道")
    private String street;

    @ApiModelProperty(value = "详细地址")
    private String adds;

    @ApiModelProperty(value = "经度")
    private String longitude;

    @ApiModelProperty(value = "纬度")
    private String latitude;

    @ApiModelProperty(value = "联系人")
    private String atten;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "认证开始时间")
    private Date authstart;

    @ApiModelProperty(value = "认证结束时间")
    private Date authend;

    @ApiModelProperty(value = "公司创建人")
    private String userid;

    @ApiModelProperty(value = "访问量")
    private Integer visits;


}
