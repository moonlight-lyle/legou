package com.changgou.oauth.service;

import java.util.Map;

/***
 * 描述
 * @author ljh
 * @packagename com.changgou.oauth.service
 * @version 1.0
 * @date 2020/3/28
 */
public interface LoginService {
    /**
     * 密码模式登录
     * @param username 用户名
     * @param password 密码
     * @param grant_type 授权类型
     * @param clientId 客户端id
     * @param secret  客户端id对应的密码
     * @return
     */
    Map<String,String> login(String username, String password, String grant_type, String clientId, String secret);
}
