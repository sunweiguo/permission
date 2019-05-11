package com.swg.controller;

import com.swg.common.JsonData;
import com.swg.service.SysUserService;
import com.swg.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author swg.
 * @Date 2019/5/11 20:16
 * @CONTACT 317758022@qq.com
 * @DESC
 */
@Controller
@RequestMapping("/sys/user")
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
}
