package com.zbkj.crmeb.nsl.nslwxapp.model;

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
 * 车辆品牌
 * </p>
 *
 * @author testjava
 * @since 2021-06-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="NslCbrands对象", description="车辆品牌")
public class NslCbrands implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "ID")
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "名称")
    private String name;

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
