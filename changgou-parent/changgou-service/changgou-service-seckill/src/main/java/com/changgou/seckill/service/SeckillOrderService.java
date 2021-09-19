package com.changgou.seckill.service;
import com.changgou.seckill.pojo.SeckillOrder;
import com.changgou.seckill.pojo.SeckillStatus;
import com.github.pagehelper.PageInfo;
import java.util.List;
/****
 * @Author:admin
 * @Description:SeckillOrderҵ���ӿ�
 * @Date 2019/6/14 0:16
 *****/
public interface SeckillOrderService {

    /***
     * SeckillOrder��������ҳ��ѯ
     * @param seckillOrder
     * @param page
     * @param size
     * @return
     */
    PageInfo<SeckillOrder> findPage(SeckillOrder seckillOrder, int page, int size);

    /***
     * SeckillOrder��ҳ��ѯ
     * @param page
     * @param size
     * @return
     */
    PageInfo<SeckillOrder> findPage(int page, int size);

    /***
     * SeckillOrder��������������
     * @param seckillOrder
     * @return
     */
    List<SeckillOrder> findList(SeckillOrder seckillOrder);

    /***
     * ɾ��SeckillOrder
     * @param id
     */
    void delete(Long id);

    /***
     * �޸�SeckillOrder����
     * @param seckillOrder
     */
    void update(SeckillOrder seckillOrder);

    /***
     * ����SeckillOrder
     * @param seckillOrder
     */
    void add(SeckillOrder seckillOrder);

    /**
     * ����ID��ѯSeckillOrder
     * @param id
     * @return
     */
     SeckillOrder findById(Long id);

    /***
     * ��ѯ����SeckillOrder
     * @return
     */
    List<SeckillOrder> findAll();

    boolean add(Long id, String time, String username);

    SeckillStatus queryStatus(String username);
}
