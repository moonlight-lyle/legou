package com.changgou.seckill.timer;

import com.changgou.seckill.dao.SeckillGoodsMapper;
import com.changgou.seckill.pojo.SeckillGoods;
import entity.DateUtil;
import entity.SystemConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Set;

/***
 * 描述
 * @author ljh
 * @packagename com.changgou.seckill.timer
 * @version 1.0
 * @date 2020/4/3
 */
@Component
public class SeckillGoodsPushTask {

    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    // 该方法用于每隔一段时间重复执行 Scheduled 标记该方法执行定时任务
    @Scheduled(cron = "0/5 * * * * ?") // cron 表单时 用于指定何时执行
    public void loadGoodsPushRedis() {
        // 1.查询数据库的符合条件的秒杀商品
        // System.out.println("哈哈哈哈哈哈 我执行了呀");
        List<Date> dateMenus = DateUtil.getDateMenus();
        for (Date dateMenu : dateMenus) {//5个
            String data2str = DateUtil.data2str(dateMenu, DateUtil.PATTERN_YYYYMMDDHH);
            /**
             * select * from tb_seckill_goods where
             stauts=1 and stock_count>0
             and start_time=以当前时间为基准的的开始时间段，
             and end_time<以当前时间为基准的开始时间段+2小时（结束时间段）
             and id not in (redis中已有的id集合)
             */
            Example example = new Example(SeckillGoods.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("status", "1");//where status=1
            criteria.andGreaterThan("stockCount", 0);//and stock_count>0
            criteria.andEqualTo("startTime", dateMenu);
            criteria.andLessThan("endTime", DateUtil.addDateHour(dateMenu, 2));
            Set keys = redisTemplate.boundHashOps(SystemConstants.SEC_KILL_GOODS_PREFIX + data2str).keys();
            if (keys != null && keys.size() > 0) {
                criteria.andNotIn("id", keys);
            }
            List<SeckillGoods> seckillGoods = seckillGoodsMapper.selectByExample(example);
            // 2.将其推送到redis中 key  value
            /**
             *
             * key:时间段(2020040312) field:商品ID1 value：商品对象1
             key:时间段(2020040312) field:商品ID2 value：商品对象2
             */
            for (SeckillGoods seckillGood : seckillGoods) {
                redisTemplate.boundHashOps(SystemConstants.SEC_KILL_GOODS_PREFIX + data2str).put(seckillGood.getId(), seckillGood);
                // 设置有效期 expire key second expireat key timestamp
                redisTemplate.expireAt(SystemConstants.SEC_KILL_GOODS_PREFIX + data2str, DateUtil.addDateHour(dateMenu, 2));
            }
        }
    }

    public static void main(String[] args) {
        List<Date> dateMenus = DateUtil.getDateMenus();
        for (Date dateMenu : dateMenus) {
            System.out.println(dateMenu);
        }
    }
}
