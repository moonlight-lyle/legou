package com.changgou.common;

import entity.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TestJwt {
    @Test
    public void test(){
//        byte[] bytes = Base64.getDecoder().decode("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9");
        byte[] bytes = Base64.getDecoder().decode("eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9");
        System.out.println(new String(bytes));
    }
    /****
     * 创建Jwt令牌
     */
    @Test
    public void testCreateJwt(){
        JwtBuilder builder= Jwts.builder() // 头部信息有默认的
                // 设置载荷信息
                .setId("888")             // 设置唯一编号
                .setSubject("小白")       // 设置主题  可以是JSON数据
//                .setExpiration(new Date()) // 设置过期时间
                .setIssuedAt(new Date())  // 设置签发日期
                // 设置签名
                .signWith(SignatureAlgorithm.HS256,"itcast"); // 设置签名 使用HS256算法，并设置SecretKey(字符串)

        //自定义载荷信息
        Map<String, Object> map = new HashMap<>();
        map.put("username","zhangsan");
        map.put("role","admin");
        builder.addClaims(map);
        //构建 并返回一个字符串
        System.out.println( builder.compact() ); // 令牌信息
    }
    /***
     * 解析Jwt令牌数据
     */
    @Test
    public void testParseJwt(){
        String compactJwt="eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiLlsI_nmb0iLCJpYXQiOjE2MzEwMDA4NDIsInJvbGUiOiJhZG1pbiIsInVzZXJuYW1lIjoiemhhbmdzYW4ifQ.B0zX8F2Z0EtX2eLP5jlWNwhZSufkI7mjjI6lRblyWUw";
        Claims claims = Jwts.parser().
                setSigningKey("itcast").
                parseClaimsJws(compactJwt).
                getBody();
        System.out.println(claims);
    }
    /**
     * 测试JwtUtil
     */
    @Test
    public void createJwt(){
        String token = JwtUtil.createJWT("123456", "KOOK", null);
        System.out.println(token);
    }
    @Test
    public void parseJwt(){
        String token="eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjM0NTYiLCJzdWIiOiJLT09LIiwiaXNzIjoiYWRtaW4iLCJpYXQiOjE2MzEwMDEzNjAsImV4cCI6MTYzMTAwNDk2MH0._ZuROLWCPXaTUqVBjHWzUQszB3n3Y27F-pbRq6MNjos";
        try {
            Claims claims = JwtUtil.parseJWT(token);
            System.out.println(claims.getSubject());
        } catch (Exception e) {


        }

    }
    @Test
    public void test007() {
        String encode = new BCryptPasswordEncoder().encode("123456");
        System.out.println(encode);
    }

}
