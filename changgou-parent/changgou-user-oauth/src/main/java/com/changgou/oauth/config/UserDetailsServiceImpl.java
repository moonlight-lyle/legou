package com.changgou.oauth.config;


import com.changgou.user.feign.UserFeign;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/***
 * 描述
 * @author ljh
 * @packagename com.it.config
 * @version 1.0
 * @date 2020/1/10
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserFeign userFeign;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("获取到的用户名是：" + username);
        String permission = "ROLE_ADMIN,ROLE_USER"; // 设置权限
//        return new User(username, passwordEncoder.encode("szitheima"),
//                AuthorityUtils.commaSeparatedStringToAuthorityList(permission));
        // 1.根据页面传递过来的要登录的用户名 通过feign 调用用户微服务
        Result<com.changgou.user.pojo.User> userResult = userFeign.loadById(username);
        if(userResult.getData()==null ){
            // 用户名登录的时候错误
            return null;
        }
        // 2.获取到用户的信息（主要是获取到密码的信息）
        String password = userResult.getData().getPassword();
        // 3.返回密码信息给springsecurity的框架 它来自动的 进行匹配
        return new User(username, password,
                AuthorityUtils.commaSeparatedStringToAuthorityList(permission));
    }
}
