package com.swg.filter;


import com.swg.common.RequestHolder;
import com.swg.entity.SysUser;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author swg.
 * @Date 2019/5/11 20:52
 * @CONTACT 317758022@qq.com
 * @DESC 拦截器，对/sys/*和/admin/*的路径进行拦截，将用户的登陆信息放到ThreadLocal中
 */
@Slf4j
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("【过滤器开始对请求进行拦截...】");
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        SysUser sysUser = (SysUser)req.getSession().getAttribute("user");
        if (sysUser == null) {
            log.error("【用户未登陆，返回到登陆页面】");
            String path = "/signin.jsp";
            resp.sendRedirect(path);
            return;
        }
        log.info("【用户登陆了，请求继续往下走，用户信息放进ThreadLocal中】");
        RequestHolder.add(sysUser);
        RequestHolder.add(req);
        filterChain.doFilter(servletRequest, servletResponse);
        return;
    }

    @Override
    public void destroy() {

    }
}
