package com.swg.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author swg.
 * @Date 2019/4/26 18:28
 * @CONTACT 317758022@qq.com
 * @DESC
 */
@Data
public class TestUser {
    private Integer id;

    private String username;

    private String telephone;

    private String mail;

    private String password;

    private Integer deptId;

    private Integer status;

    private String remark;

    private String operator;

    private Date operateTime;

    private String operateIp;
}
