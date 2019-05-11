package com.swg.common;

import com.swg.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author swg.
 * @Date 2019/5/3 16:42
 * @CONTACT 317758022@qq.com
 * @DESC 拦截器
 */
@Slf4j
@Component
public class HttpInterceptor extends HandlerInterceptorAdapter {

    private static final ThreadLocal<Long> local = new ThreadLocal<>();
    /**
     * 请求开始前都要先执行一下
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI().toString();
        Map parameterMap = request.getParameterMap();
        //实际开发中，不要忘记剔除掉一些敏感信息再写入日志
        log.info("request start. url:{}, params:{}", url, JsonUtil.obj2String(parameterMap));
        local.set(System.currentTimeMillis());
        return true;
    }

    /**
     * 请求正常结束之后会执行，异常不会执行
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String url = request.getRequestURI().toString();
        Map parameterMap = request.getParameterMap();
        log.info("request post. url:{}, params:{}", url, JsonUtil.obj2String(parameterMap));
    }

    /**
     * 请求无论是正常还是异常都会执行，
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String url = request.getRequestURI().toString();
        Map parameterMap = request.getParameterMap();
        log.info("request completed. url:{}, params:{}", url, JsonUtil.obj2String(parameterMap));
        log.info("【本接口的执行时间为：{}(毫秒)】",System.currentTimeMillis()-local.get());
        local.remove();
        RequestHolder.remove();;
    }
}
