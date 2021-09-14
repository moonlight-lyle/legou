package com.changgou.filter;

import com.changgou.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 全局过滤器，所有请求都会经过这个过滤器
 */
@Component
public class AuthorizeFilter implements GlobalFilter, Ordered {
    // 令牌头名字
    private static final String AUTHORIZE_TOKEN = "Authorization";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取Request、Response对象
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        // 获取请求的URI
        String path = request.getURI().getPath();
        // 如果是登录、goods等开放的微服务[这里的goods部分开放],则直接放行,这里不做完整演示，完整演示需要设计一套权限系统
        if (path.startsWith("/api/user/login")) {
            // 放行
            Mono<Void> filter = chain.filter(exchange);
            return filter;
        }
        // 获取头文件中的令牌信息
        String token = request.getHeaders().getFirst(AUTHORIZE_TOKEN);
        // 如果头文件中没有，则从请求参数中获取
        if (StringUtils.isEmpty(token)) {
            token = request.getQueryParams().getFirst(AUTHORIZE_TOKEN);
        }
        // 如果为空，则输出错误代码
        if (StringUtils.isEmpty(token)) {
            // 设置方法不允许被访问，401错误代码
//            response.setStatusCode(HttpStatus.UNAUTHORIZED);
//            return response.setComplete();
            //没有令牌  说明无权限 不是直接返回 而是要重定向到登录的页面http://localhost:9001/oauth/login
            String loginurl = "http://localhost:9001/oauth/login?From=" + request.getURI().toString();
            response.getHeaders().set("Location", loginurl);
            response.setStatusCode(HttpStatus.SEE_OTHER);
            return response.setComplete();//请求完成
        }
        // 解析令牌数据
//        try {
//            Claims claims = JwtUtil.parseJWT(token);
//        } catch (Exception e) {
//            e.printStackTrace();
//            // 解析失败，响应401错误
//            response.setStatusCode(HttpStatus.UNAUTHORIZED);
//            return response.setComplete();
//        }

        // 如果有令牌信息，需要将令牌传递给下一个微服务
        request.mutate().header(AUTHORIZE_TOKEN, "bearer " + token);

        // 放行
        return chain.filter(exchange);
    }

    /**
     * 过滤器顺序
     *
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
