package com.changgou.goods.feign;

import com.changgou.goods.pojo.Spu;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/***
 * 描述
 * @author ljh
 * @packagename com.changgou.goods.feign
 * @version 1.0
 * @date 2020/3/30
 */
@FeignClient(name="goods")
@RequestMapping("/spu")
public interface SpuFeign {
    @GetMapping("/{id}")
    public Result<Spu> findById(@PathVariable(name="id") Long id) ;
}
