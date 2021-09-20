package com.changgou;

import entity.IdWorker;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * ToDo
 *
 * @author Lyle
 * @date 2021/9/19
 */
@SpringBootApplication
@EnableEurekaClient
@EnableScheduling // SpringBoot集成spring task，启用该注解生效
@MapperScan(basePackages = "com.changgou.seckill.dao")
@EnableAsync//启用线程池异步支持
public class SeckillApplication {
    public static void main(String[] args) {
        SpringApplication.run(SeckillApplication.class,args);
    }

    // 修改Redis序列化方式
    @Bean
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<Object, Object> redisTemplate(
            RedisConnectionFactory redisConnectionFactory) throws Exception {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        //设置序列化机制 设置成字符串
        template.setKeySerializer(new StringRedisSerializer());
        return template;
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker(0,1);
    }

    @Autowired
    private Environment environment;

    // 配置秒杀交换机  配置 秒杀队列  配置秒杀相关绑定

    //创建队列  秒杀订单
    @Bean
    public Queue queueSeckillOrder(){
        return new Queue(environment.getProperty("mq.pay.queue.seckillorder"));
    }

    //创建交互机 路由模式的交换机  秒杀订单的交互机
    @Bean
    public DirectExchange createSeckillExchange(){
        return new DirectExchange(environment.getProperty("mq.pay.exchange.seckillorder"));
    }

    //创建绑定  秒杀的订单的绑定
    //创建绑定  秒杀的订单的绑定
    @Bean
    public Binding createSeckillBinding(){
        return BindingBuilder.bind(queueSeckillOrder()).to(createSeckillExchange()).with(environment.getProperty("mq.pay.routing.seckillkey"));
    }
}
