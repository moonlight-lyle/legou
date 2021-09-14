package com.changgou.user.feign;

import com.changgou.user.pojo.User;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    //添加积分   给某一个用户 添加指定的积分

    /**
     * 给指定的用户添加积分
     * @param username 指定用户名
     * @param points 指定要添加的积分
     * @return
     */
    @GetMapping("/points/add")
    public Result addPoints(@RequestParam(name="username") String username,
                            @RequestParam(name="points") Integer points);
}
