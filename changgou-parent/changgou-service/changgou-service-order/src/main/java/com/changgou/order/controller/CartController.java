package com.changgou.order.controller;

import com.changgou.order.config.TokenDecode;
import com.changgou.order.pojo.OrderItem;
import com.changgou.order.service.CartService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/***
 * 描述
 * @author ljh
 * @packagename com.changgou.order.controller
 * @version 1.0
 * @date 2020/3/30
 */
@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private TokenDecode tokenDecode;

    /**
     *  添加购物车
     * @param num 要购买的数量
     * @param id 要购买的商品的ksu的ID
     * @return 是否成功
     */
    @RequestMapping(value = "/add")
    public Result add(Integer num,Long id){
        String username = tokenDecode.getUsername();
        cartService.add(num,id,username);

        return new Result(true, StatusCode.OK,"添加购物车成功");
    }

    @GetMapping("/list")
    public Result<List<OrderItem>> list(){
        String username = tokenDecode.getUsername();
        List<OrderItem> orderItemList = cartService.list(username);
        return new Result<List<OrderItem>>(true,StatusCode.OK,"查询购购物车列表成功",orderItemList);
    }
}
