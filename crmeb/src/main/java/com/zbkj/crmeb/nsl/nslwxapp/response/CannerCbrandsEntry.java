package com.zbkj.crmeb.nsl.nslwxapp.response;

import lombok.Data;

import java.util.Date;

@Data
public class CannerCbrandsEntry {

    private String name;
    private Integer cbrands;
    private String maxweight;
    private String introduce;
    private String images;
    private String expdbcodes;
    private String visits;
    private String remarks;
    private Date create_date;
    private String create_user;
    private Date update_date;
    private String update_user;

}
