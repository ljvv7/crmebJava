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
 * 公司车辆绑定表
 * </p>
 *
 * @author testjava
 * @since 2021-06-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="NslCbind对象", description="公司车辆绑定表")
public class NslCbind implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "ID")
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "公司ID")
    private Integer companyid;

    @ApiModelProperty(value = "用户ID")
    private Integer userid;

    @ApiModelProperty(value = "吊车ID")
    private Integer craneid;

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
