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
 * 车辆配重表
 * </p>
 *
 * @author testjava
 * @since 2021-06-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="NslCweight对象", description="车辆配重表")
public class NslCweight implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "ID")
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "craneid")
    private Integer craneid;

    @ApiModelProperty(value = "组合方式: 1仅主臂、2主臂+副臂、3主臂+横臂、4超起")
    private String legtype;

    @ApiModelProperty(value = "支腿方式")
    private String legway;

    @ApiModelProperty(value = "配重")
    private BigDecimal equipweight;

    @ApiModelProperty(value = "工作范围")
    private Integer workextent;

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
