package com.swg.controller;

import com.swg.common.ApplicationContextHelper;
import com.swg.dao.SysUserMapper;
import com.swg.entity.SysUser;
import com.swg.entity.TestUser;
import com.swg.exception.ParamException;
import com.swg.exception.PermissionException;
import com.swg.service.TestUserService;
import com.swg.util.BeanValidator;
import com.swg.vo.TestVO;
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
    @RequestMapping("/hello.json")
    @ResponseBody
    public String hello() {
        log.info("hello");
        /*这边是后加，目的是为了测试全局异常中对自定义异常的处理*/
//        if(true){
//            throw new PermissionException("出错啦，抛出自定义异常");
//        }
        return "hello permission!";
    }

    /*测试能够从数据库中查询出一条数据*/
    @RequestMapping("/hello1.json")
    @ResponseBody
    public TestUser hello1(){
        TestUser user = testUserService.getUserById(1);
        return user;
    }

    /*插入两条主键相同的数据，看事务是否生效*/
    @RequestMapping("/hello2.json")
    @ResponseBody
    public String hello2(){
        testUserService.insertNewUser();
        return "success";
    }

    /**
     * bean的校验
     * @param testVO
     * @return
     * @throws ParamException
     */
    @RequestMapping("/hello3.json")
    @ResponseBody
    public String hello3(TestVO testVO) throws ParamException {
        log.info("hello3");
        BeanValidator.check(testVO);
        return "hello permission!";
    }

    /**
     * 获取spring上下文
     * @param testVO
     * @return
     * @throws ParamException
     */
    @RequestMapping("/hello4.json")
    @ResponseBody
    public SysUser hello4(TestVO testVO) throws ParamException {
        SysUserMapper sysUserMapper = ApplicationContextHelper.popBean(SysUserMapper.class);
        SysUser user = sysUserMapper.selectByPrimaryKey(1);
        log.info("【用户为:{}】",user);
        return user;
    }
}
