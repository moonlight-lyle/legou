package com.changgou.order.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/***
 * 描述
 * @author ljh
 * @packagename com.changgou.order.config
 * @version 1.0
 * @date 2020/3/30
 */
@Component
public class MyFeignInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        // 方法就是当调用feign的时候会自动的执行
        // 1.获取原来请求对象，需注意要使用该方法hystrix配置不能是线程隔离模式
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            // 2.获取源请求对象中的头信息
            // 3.获取到所有的头信息，包括令牌的头信息的值 一起全都传递过去
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                String headerValue = request.getHeader(headerName);
                // 4.将头信息传递到下游的微服务中（调用feign的时候传递头过去）
                template.header(headerName,headerValue);
            }
        }
    }
}
