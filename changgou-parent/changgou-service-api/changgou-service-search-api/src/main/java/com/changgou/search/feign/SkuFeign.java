package com.changgou.search.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "search") // 指定要调用的微服务名称，即：changgou-service-search中配置文件application.yml文件中配置的spring.application.name的值
@RequestMapping("/search") // 要和changgou-service-search中SkuEsController的请求路径保持一致
public interface SkuFeign {

    @GetMapping
    Map search(@RequestParam(required = false) Map<String,String> searchMap);
}
