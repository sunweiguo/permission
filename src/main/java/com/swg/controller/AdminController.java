package com.swg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author swg.
 * @Date 2019/5/11 21:01
 * @CONTACT 317758022@qq.com
 * @DESC 管理平台登陆后的首页
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping("index.page")
    public ModelAndView index() {
        return new ModelAndView("admin");
    }
}
