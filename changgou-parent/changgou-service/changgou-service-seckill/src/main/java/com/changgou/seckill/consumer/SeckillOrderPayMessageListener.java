package com.changgou.seckill.consumer;

import com.alibaba.fastjson.JSON;
import com.changgou.seckill.dao.SeckillGoodsMapper;
import com.changgou.seckill.dao.SeckillOrderMapper;
import com.changgou.seckill.pojo.SeckillGoods;
import com.changgou.seckill.pojo.SeckillOrder;
import com.changgou.seckill.pojo.SeckillStatus;
import entity.SystemConstants;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/***
 * 监听秒杀的队列
 * @author lx
 * @packagename com.changgou.seckill.consumer
 * @version 1.0
 * @date 2021/9/20
 */
@Component
@RabbitListener(queues = "queue.seckillorder")
public class SeckillOrderPayMessageListener {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SeckillOrderMapper seckillOrderMapper;

    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;

    @Autowired
    private RedissonClient redissonClient;


    /**
     * 监听消息 进行业务处理
     *
     * @param msg
     */
    @RabbitHandler
    public void consumeMessage(String msg) {
        Map<String, String> map = JSON.parseObject(msg, Map.class); //就是通知结果的map对象
        String attach = map.get("attach"); //字符串{from:1,usrename:}
        Map<String,String> attachMap = JSON.parseObject(attach, Map.class);
        if (map != null && map.get("return_code").equals("SUCCESS")
                && map.get("result_code").equals("SUCCESS")) {
            // 如果支付成功的  支付成功的业务处理
            // 1.将数据库的秒杀支付订单进行添加
            SeckillOrder seckillOrder = (SeckillOrder) redisTemplate.boundHashOps(SystemConstants.SEC_KILL_ORDER_KEY).get(attachMap.get("username"));
            // insert 是不管传递是有值还是没有值都插入到数据库中即使该值为null
            // insertSelective 会判断 如果是null 就不插入了
            // 补充属性值
            seckillOrder.setStatus("1"); // 已经支付了
            seckillOrder.setTransactionId(map.get("transaction_id"));//交易流水
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            Date time_end = null;
            try {
                time_end = simpleDateFormat.parse(map.get("time_end"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            // 支付时间
            seckillOrder.setPayTime(time_end);
            seckillOrderMapper.insertSelective(seckillOrder);//不完全
            // 2.删除之前的在redis中的某一个用户的预订单
            redisTemplate.boundHashOps(SystemConstants.SEC_KILL_ORDER_KEY).delete(attachMap.get("username"));
            // 3.删除掉之前的防止重复排队的标记位
            redisTemplate.boundHashOps(SystemConstants.SEC_KILL_QUEUE_REPEAT_KEY).delete(attachMap.get("username"));
            // 4.删除掉之前的抢单的状态
            redisTemplate.boundHashOps(SystemConstants.SEC_KILL_USER_STATUS_KEY).delete(attachMap.get("username"));
        } else {
            //如果是支付失败的 支付失败的业务处理 [简单处理]。 先将原本的支付二维码失效掉，再进行恢复库存。
            //1.删除预订单
            // 恢复库存
            SeckillStatus seckillStatus = (SeckillStatus) redisTemplate.boundHashOps(SystemConstants.SEC_KILL_USER_STATUS_KEY).get(attachMap.get("username"));
            SeckillGoods seckillGoods = (SeckillGoods) redisTemplate.boundHashOps(SystemConstants.SEC_KILL_GOODS_PREFIX + seckillStatus.getTime()).get(seckillStatus.getGoodsId());
            if(seckillGoods==null){
                //还要从数据库查询
                seckillGoods = seckillGoodsMapper.selectByPrimaryKey(seckillStatus.getGoodsId());
            }
            RLock mylock = redissonClient.getLock("Mylock");
            try {
                mylock.lock(20, TimeUnit.SECONDS);

                seckillGoods.setStockCount(seckillGoods.getStockCount()+1);//恢复库存+1
                redisTemplate.boundHashOps(SystemConstants.SEC_KILL_GOODS_PREFIX + seckillStatus.getTime()).put(seckillStatus.getGoodsId(),seckillGoods);

            } catch (Exception e) {
                e.printStackTrace();
                //处理 记录日志，通过人工的处理
            }finally {
                mylock.unlock();
            }
            redisTemplate.boundHashOps(SystemConstants.SEC_KILL_ORDER_KEY).delete(attachMap.get("username"));
            //2.删除掉之前的防止重复排队的标记位
            redisTemplate.boundHashOps(SystemConstants.SEC_KILL_QUEUE_REPEAT_KEY).delete(attachMap.get("username"));
            //3.删除掉之前的抢单的状态
            redisTemplate.boundHashOps(SystemConstants.SEC_KILL_USER_STATUS_KEY).delete(attachMap.get("username"));
        }
    }
}
