package com.changgou.oauth.controller;

import com.changgou.oauth.service.LoginService;
import com.changgou.oauth.util.CookieUtil;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/***
 * 描述
 * @author ljh
 * @packagename com.changgou.oauth.controller
 * @version 1.0
 * @date 2020/3/28
 */
@RestController
@RequestMapping("/user")
public class UserLoginController {
    private static final String grant_type="password";
    private static final String clientId="changgou";
    private static final String secret="changgou";



    //Cookie存储的域名
    @Value("${auth.cookieDomain}")
    private String cookieDomain;

    //Cookie生命周期
    @Value("${auth.cookieMaxAge}")
    private int cookieMaxAge;//-1 浏览器一关闭就没了

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public Result<Map> login(String username, String password){
       Map<String,String> map  =  loginService.login(username,password,grant_type,clientId,secret);
       // 生成令牌给用户 1.cookie  2.头 3.返回
        // 存储到cookie中
        saveCookie(map.get("access_token"));

       return new Result<Map>(true, StatusCode.OK,"登录成功",map.get("access_token"));
    }

    private void saveCookie(String token){
        // 获取当前的线程threalocal 下的响应对象
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        CookieUtil.addCookie(response,cookieDomain,"/","Authorization",token,cookieMaxAge,false);
    }
}
