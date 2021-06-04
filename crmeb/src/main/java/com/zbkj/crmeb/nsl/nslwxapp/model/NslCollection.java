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
 * 
 * </p>
 *
 * @author testjava
 * @since 2021-06-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="NslCollection对象", description="")
public class NslCollection implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "公司id")
    private Integer companyid;

    @ApiModelProperty(value = "用户id")
    private Integer userid;

    @ApiModelProperty(value = "车辆id")
    private Integer craneid;

    @ApiModelProperty(value = "0：绑定车辆；1绑定公司")
    private String type;

    @ApiModelProperty(value = "绑定路径")
    private String path;

    @ApiModelProperty(value = "默认传入0；   0：未绑定；1绑定")
    private String isconnection;

    @ApiModelProperty(value = "默认传入0；   0：新增；1修改；9删除")
    private String kbn;

    @ApiModelProperty(value = "不传；默认当前日期")
    private Date createDate;

    @ApiModelProperty(value = "传入数据姓名；")
    private String createUser;

    @ApiModelProperty(value = "不传；默认当前日期")
    private Date updateDate;

    @ApiModelProperty(value = "传入数据姓名；")
    private String updateUser;


}
