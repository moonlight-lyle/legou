package com.changgou;

import entity.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableEurekaClient // 开启Eureka客户端依赖
@MapperScan(basePackages = "com.changgou.goods.dao") // 扫描Dao接口所在的包
public class GoodsApplication {
    public static void main(String[] args) {
        SpringApplication.run(GoodsApplication.class,args);
    }

    @Bean
    public IdWorker getIdWorker(){
        return new IdWorker(0,0);
    }
}
