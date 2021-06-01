package com.zbkj.crmeb.nsl.crmeb.response;

import lombok.Data;

import java.util.Date;

@Data
public class EbSystemGroup {

    private Integer id;
    private Integer gid;
    private String value;
    private Integer sort;
    private Integer status;
    private Date create_time;
    private Date update_time;

}
