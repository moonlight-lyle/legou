package com.changgou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient // 开启Eureka客户端
@EnableFeignClients(basePackages = "com.changgou.search.feign") // 开启Feign调用，并配置要扫描的包
public class SearchWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(SearchWebApplication.class,args);
    }
}
