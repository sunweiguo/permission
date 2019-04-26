package com.swg.controller;

import com.swg.entity.TestUser;
import com.swg.service.TestUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Autowired
    private TestUserService testUserService;

    /*测试项目启动是否出错*/
    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {
        log.info("hello");
        return "hello permission!";
    }

    /*测试能够从数据库中查询出一条数据*/
    @RequestMapping("/hello1")
    @ResponseBody
    public TestUser hello1(){
        TestUser user = testUserService.getUserById(1);
        return user;
    }

    /*插入两条主键相同的数据，看事务是否生效*/
    @RequestMapping("/hello2")
    @ResponseBody
    public String hello2(){
        testUserService.insertNewUser();
        return "success";
    }
}
