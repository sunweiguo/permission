package com.swg.entity;

import lombok.Data;

import java.util.Date;

@Data
public class SysRoleAcl {
    private Integer id;

    private Integer roleId;

    private Integer aclId;

    private String operator;

    private Date operateTime;

    private String operateIp;
}