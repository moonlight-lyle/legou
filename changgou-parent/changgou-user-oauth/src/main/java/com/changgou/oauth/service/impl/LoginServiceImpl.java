package com.changgou.oauth.service.impl;

import com.changgou.oauth.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Map;

/***
 * 描述
 * @author ljh
 * @packagename com.changgou.oauth.service.impl
 * @version 1.0
 * @date 2020/3/28
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Map<String, String> login(String username, String password, String grant_type, String clientId, String secret) {
        String url = "http://localhost:9001/oauth/token";
        // 2.模拟postman 给自己发送请求 http://localhost:9001/oauth/token 而且必须是POST请求 申请令牌
            // 2.1 传递参数1 用户名
            // 2.2 传递参数2 密码
            // 2.2 传递参数3 grant_type  写死使用密码模式
            // 2.2 传递参数4 clientId 客户端ID
            // 2.2 传递参数5 secret  密码

        /**
         * 参数1 指定要发送的请求路径
         * 参数2 指定要发送的请求携带的请求体对象（有body  header ）
         * 参数3 指定返回值的数据类型是什么Map
         */
        // 请求体封装对象
        MultiValueMap<String, String> body = new LinkedMultiValueMap<String,String>();
        body.add("grant_type",grant_type);
        body.add("username",username);
        body.add("password",password);

        // 头信息封装的对象
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String,String>();
        headers.add("Authorization","Basic "+Base64.getEncoder().encodeToString((clientId+":"+secret).getBytes()));


        HttpEntity<MultiValueMap<String,String>> requestEntity = new HttpEntity<MultiValueMap<String,String>>(body,headers);
        // 发送请求了
        ResponseEntity<Map> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Map.class);
        Map<String,String> bodydata = responseEntity.getBody(); // 响应体
        // 3.令牌返回
        return bodydata;
    }

    public static void main(String[] args) {
        // Basic YWJkZGQ6Y2ZhZmE=
        byte[] decode = Base64.getDecoder().decode("YWJkZGQ6Y2ZhZmE=");
        String s = new String(decode);
        System.out.println(s);
    }
}
