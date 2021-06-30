package com.zbkj.crmeb.nsl.nslwxapp.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CraneByWayEntry {
    private Integer id;
    private String name;
    private Integer brandId;
    private String brandName;
    private String image;
    private String introduce;
    private Integer weightid;
    private BigDecimal equipweight;
    private BigDecimal radius;
    private BigDecimal primaryLength;
    private BigDecimal minorLength;
    private BigDecimal weight;
    private String way;
    private Integer legType;
    private Double percent;
}
