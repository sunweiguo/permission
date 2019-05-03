package com.swg.entity;

import lombok.Data;

import java.util.Date;

@Data
public class SysRole {
    private Integer id;

    private String name;

    private Integer type;

    private Integer status;

    private String remark;

    private String operator;

    private Date operateTime;

    private String operateIp;
}