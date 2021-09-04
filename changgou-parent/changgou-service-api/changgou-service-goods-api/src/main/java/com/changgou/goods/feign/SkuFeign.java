package com.changgou.goods.feign;

import com.changgou.goods.pojo.Sku;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * OpenFeign使用说明：
 * 1. 在消费端添加启动依赖
 *   <dependency>
 *             <groupId>org.springframework.cloud</groupId>
 *             <artifactId>spring-cloud-starter-openfeign</artifactId>
 *         </dependency>
 *  2. 写声明式的Feign接口，也就是这里的UserFeign，请求路径、参数即返回值和服务端接口保持一致即可，使用@FeignClient(name = "user-provider")标记调用哪个微服务
 *      注意springmvc相关注解也要拷贝过来
 *  3. 在启动类使用@EnableFeignClients启用Feign声明式的调用
 *  4. 在需要调用的地方@Autowired注入要调用的Feign
 *
 *  Feign默认支持了负载均衡
 *
 */

@FeignClient(name = "goods")
@RequestMapping(value = "/sku")
public interface SkuFeign {

    /***
     * 根据审核状态查询Sku
     * @param status
     * @return
     */
    @GetMapping("/status/{status}")
    Result<List<Sku>> findByStatus(@PathVariable(name="status") String status); // 注解@PathVariable(name="status")必须要
}
