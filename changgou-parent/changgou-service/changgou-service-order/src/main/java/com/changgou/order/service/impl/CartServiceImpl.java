package com.changgou.order.service.impl;

import com.changgou.goods.feign.SkuFeign;
import com.changgou.goods.feign.SpuFeign;
import com.changgou.goods.pojo.Sku;
import com.changgou.goods.pojo.Spu;
import com.changgou.order.pojo.OrderItem;
import com.changgou.order.service.CartService;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/***
 * 描述
 * @author ljh
 * @packagename com.changgou.order.service.impl
 * @version 1.0
 * @date 2020/3/30
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired(required = false)
    private SkuFeign skuFeign; // null 也可以 就是说spring容器就算没有实力 也不报错

    @Autowired(required = false)
    private SpuFeign spuFeign;

    @Override
    public void add(Integer num, Long id, String username){ // 2
        // 如果数量为 0 或者是负数 就应该从购物车中移除原来的商品
        if(num<=0){
            redisTemplate.boundHashOps("Cart_"+username).delete(id);
            // 移除商品
            return;
        }
        // 1.需要调用feign根据商品的ID 获取商品的数据
        Result<Sku> skuResult = skuFeign.findById(id);
        Sku sku = skuResult.getData();
        // 2.将数据转成orderitmem数据

        OrderItem orderItem = new OrderItem();
        orderItem.setNum(num);
        // 设置1级二级三级分类的ID:写一个feign 根据sku的ID 获取到sku对象再获取到对象所属的spuId 再根据spuid的获取SPU对象 对象中有1 2 3 级数据
        Spu spu = spuFeign.findById(sku.getSpuId()).getData();
        orderItem.setCategoryId1(spu.getCategory1Id());
        orderItem.setCategoryId2(spu.getCategory2Id());
        orderItem.setCategoryId3(spu.getCategory3Id());
        orderItem.setSpuId(sku.getSpuId());
        orderItem.setSkuId(id);
        orderItem.setName(sku.getName());
        orderItem.setPrice(sku.getPrice());
        orderItem.setMoney(num*sku.getPrice()); // 应付金额
        orderItem.setPayMoney(num*sku.getPrice()); // 实付金额
        orderItem.setImage(sku.getImage()); // 图片地址
        // 3.存储到redis中    hset key field value    hset Cart_zhangsan skuid value
        redisTemplate.boundHashOps("Cart_"+username).put(id,orderItem);
    }

    @Override
    public List<OrderItem> list(String username) {
        // key feild1 value1  orderitems
        // key feidl2 value2
        // key field3 value3
        return redisTemplate.boundHashOps("Cart_"+username).values();
    }
}
