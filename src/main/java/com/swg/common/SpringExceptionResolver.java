package com.swg.common;

import com.swg.exception.ParamException;
import com.swg.exception.PermissionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author swg.
 * @Date 2019/5/3 13:48
 * @CONTACT 317758022@qq.com
 * @DESC 全局异常处理类，分别对自定义异常和系统异常进行处理
 * 请求的路径也分为.json和,page两种，这里做个区分
 */
@Component
@Slf4j
public class SpringExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse httpServletResponse, Object o, Exception e) {
        //拿到请求的路径，需要用到它的接口后缀
        String url = request.getRequestURL().toString();
        ModelAndView mv;
        //系统错误默认提示信息
        String defaultMsg = "System error";

        //表示接口是以.json结尾的路径
        if(url.endsWith(".json")){
            if(e instanceof PermissionException || e instanceof ParamException){
                log.error("PermissionException ：{}",e);
                JsonData result = JsonData.fail(e.getMessage());
                //当时jsonView的时候，配置文件中就会将其转为json
                mv = new ModelAndView("jsonView",result.toMap());
            }else{
                log.error("unknown json exception, url:" + url, e);
                JsonData result = JsonData.fail(defaultMsg);
                //会来到exception.jsp页面
                mv = new ModelAndView("exception",result.toMap());
            }
        //表示接口请求的是页面
        }else if(url.endsWith(".page")){
            log.error("unknown page exception, url:" + url, e);
            JsonData result = JsonData.fail(defaultMsg);
            mv = new ModelAndView("exception", result.toMap());
        }else{
            //不可预知的情况
            log.error("unknow exception, url:" + url, e);
            JsonData result = JsonData.fail(defaultMsg);
            mv = new ModelAndView("jsonView", result.toMap());
        }
        return mv;
    }
}
