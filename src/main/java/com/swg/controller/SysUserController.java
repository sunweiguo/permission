package com.swg.controller;

import com.swg.beans.PageQuery;
import com.swg.beans.PageResult;
import com.swg.common.JsonData;
import com.swg.entity.SysUser;
import com.swg.service.SysUserService;
import com.swg.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author swg.
 * @Date 2019/5/11 20:16
 * @CONTACT 317758022@qq.com
 * @DESC
 */
@Controller
@RequestMapping("/sys/user")
@Slf4j
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveUser(UserVO userVO) {
        sysUserService.saveUser(userVO);
        return JsonData.success();
    }

    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateUser(UserVO userVO) {
        sysUserService.update(userVO);
        return JsonData.success();
    }

    @RequestMapping("/list.json")
    @ResponseBody
    public JsonData listUser(@RequestParam("deptId") int deptId, PageQuery pageQuery){
        log.info("分页查询的参数为：{}",pageQuery);
        PageResult<SysUser> result = sysUserService.getPageByDeptId(deptId, pageQuery);
        return JsonData.success(result);
    }

}
