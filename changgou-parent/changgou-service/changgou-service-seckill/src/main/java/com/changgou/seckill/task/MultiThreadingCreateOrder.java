package com.changgou.seckill.task;

import com.changgou.seckill.dao.SeckillGoodsMapper;
import com.changgou.seckill.pojo.SeckillGoods;
import com.changgou.seckill.pojo.SeckillOrder;
import com.changgou.seckill.pojo.SeckillStatus;
import entity.IdWorker;
import entity.SystemConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;

/***
 * 描述
 * @author ljh
 * @packagename com.changgou.seckill.task
 * @version 1.0
 * @date 2020/4/3
 */
@Component
public class MultiThreadingCreateOrder {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;

    @Autowired
    private IdWorker idWorker;

    // 处理下单的任务
    @Async //异步
    public void createOrder(){
        System.out.println("模拟下单开始===============start==========="+Thread.currentThread().getName());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SeckillStatus seckillStatus = (SeckillStatus) redisTemplate.boundListOps(SystemConstants.SEC_KILL_USER_QUEUE_KEY).rightPop();
        if(seckillStatus!=null) {
            String username = seckillStatus.getUsername();
            String time = seckillStatus.getTime();
            Long id = seckillStatus.getGoodsId();
            // 1.获取商品的数据
            SeckillGoods seckillGoods = (SeckillGoods) redisTemplate.boundHashOps(SystemConstants.SEC_KILL_GOODS_PREFIX + time).get(id);
            // 2.判断商品是否存在 或者库存是否已经为0  如果是商品没有，或者商品库存为0 说明已经售罄
            if (seckillGoods == null || seckillGoods.getStockCount() <= 0) {
                throw new RuntimeException("卖完了");
            }
            // 3.减库存
            seckillGoods.setStockCount(seckillGoods.getStockCount() - 1);
            redisTemplate.boundHashOps(SystemConstants.SEC_KILL_GOODS_PREFIX + time).put(id, seckillGoods);
            //4.再判断库存是为0  如果为0 更新到数据库中
            if (seckillGoods.getStockCount() <= 0) {
                seckillGoodsMapper.updateByPrimaryKeySelective(seckillGoods);//库存设置为了0
                redisTemplate.boundHashOps(SystemConstants.SEC_KILL_GOODS_PREFIX + time).delete(id);
            }
            // 5.下预订单到redis中
            SeckillOrder seckillOrder = new SeckillOrder();//设置数据
            seckillOrder.setId(idWorker.nextId());
            seckillOrder.setSeckillId(id);//买的商品的ID
            seckillOrder.setMoney(seckillGoods.getCostPrice());//金额
            seckillOrder.setUserId(username);
            seckillOrder.setCreateTime(new Date());
            seckillOrder.setStatus("0");//未支付
            redisTemplate.boundHashOps(SystemConstants.SEC_KILL_ORDER_KEY).put(username, seckillOrder);//某一个用户的预订单 key  :username value:订单对象
            // 这里就是标识订单已经创建成功了，更新用户的抢单的状态
            seckillStatus.setStatus(2); //订单创建成功待支付
            seckillStatus.setMoney(Float.valueOf(seckillOrder.getMoney()));
            seckillStatus.setOrderId(seckillOrder.getId());
            redisTemplate.boundHashOps(SystemConstants.SEC_KILL_USER_STATUS_KEY).put(username,seckillStatus);
        }
        System.out.println("模拟下单结束===============end==========="+Thread.currentThread().getName());
    }
}
