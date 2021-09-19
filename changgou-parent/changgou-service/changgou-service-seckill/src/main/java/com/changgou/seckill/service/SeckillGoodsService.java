package com.changgou.seckill.service;
import com.changgou.seckill.pojo.SeckillGoods;
import com.github.pagehelper.PageInfo;
import java.util.List;
/****
 * @Author:admin
 * @Description:SeckillGoodsҵ���ӿ�
 * @Date 2019/6/14 0:16
 *****/
public interface SeckillGoodsService {

    /***
     * SeckillGoods��������ҳ��ѯ
     * @param seckillGoods
     * @param page
     * @param size
     * @return
     */
    PageInfo<SeckillGoods> findPage(SeckillGoods seckillGoods, int page, int size);

    /***
     * SeckillGoods��ҳ��ѯ
     * @param page
     * @param size
     * @return
     */
    PageInfo<SeckillGoods> findPage(int page, int size);

    /***
     * SeckillGoods��������������
     * @param seckillGoods
     * @return
     */
    List<SeckillGoods> findList(SeckillGoods seckillGoods);

    /***
     * ɾ��SeckillGoods
     * @param id
     */
    void delete(Long id);

    /***
     * �޸�SeckillGoods����
     * @param seckillGoods
     */
    void update(SeckillGoods seckillGoods);

    /***
     * ����SeckillGoods
     * @param seckillGoods
     */
    void add(SeckillGoods seckillGoods);

    /**
     * ����ID��ѯSeckillGoods
     * @param id
     * @return
     */
     SeckillGoods findById(Long id);

    /***
     * ��ѯ����SeckillGoods
     * @return
     */
    List<SeckillGoods> findAll();

    List<SeckillGoods> list(String time);

    SeckillGoods one(String time, Long id);
}
