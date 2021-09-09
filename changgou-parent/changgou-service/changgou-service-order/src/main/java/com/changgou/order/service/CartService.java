package com.changgou.order.service;

import com.changgou.order.pojo.OrderItem;

import java.util.List;

/***
 * 描述
 * @author ljh
 * @packagename com.changgou.order.service
 * @version 1.0
 * @date 2020/3/30
 */
public interface CartService {
    /**
     * 为 指定的用户名添加商品到购物车
     * @param num 购买数量
     * @param id 购买的商品的ID
     * @param username 用户名
     */
    void add(Integer num, Long id, String username);

    List<OrderItem> list(String username);

}
