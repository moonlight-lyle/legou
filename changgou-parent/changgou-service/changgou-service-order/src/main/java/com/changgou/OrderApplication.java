package com.changgou;

import entity.IdWorker;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import tk.mybatis.spring.annotation.MapperScan;

/***
 * 描述
 * @author ljh
 * @packagename com.changgou
 * @version 1.0
 * @date 2020/3/30
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = "com.changgou.order.dao")
@EnableFeignClients(basePackages = {"com.changgou.goods.feign","com.changgou.user.feign"})
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class,args);
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker(0,0);
    }

    @Autowired
    private Environment environment;


    // 创建队列
    @Bean
    public Queue queueOrder(){
        return new Queue(environment.getProperty("mq.pay.queue.order"));
    }

    // 创建交互机 路由模式的交换机
    @Bean
    public DirectExchange createExchange(){
        return new DirectExchange(environment.getProperty("mq.pay.exchange.order"));
    }

    // 创建绑定
    @Bean
    public Binding createBinding(){
        return BindingBuilder.bind(queueOrder()).to(createExchange()).with(environment.getProperty("mq.pay.routing.key"));
    }
}
