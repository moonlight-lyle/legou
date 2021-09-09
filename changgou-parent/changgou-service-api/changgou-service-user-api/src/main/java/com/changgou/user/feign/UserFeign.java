package com.changgou.user.feign;

import com.changgou.user.pojo.User;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/***
 * 描述
 * @author
 * @packagename com.changgou.user
 * @version 1.0
 * @date 2020/3/30
 */
@FeignClient(name="user")
@RequestMapping("/user")
public interface UserFeign {

    @GetMapping("/load/{id}")
    public Result<User> loadById(@PathVariable(name="id") String id);
}
