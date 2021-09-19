package com.changgou.seckill.service.impl;
import com.changgou.seckill.dao.SeckillGoodsMapper;
import com.changgou.seckill.pojo.SeckillGoods;
import com.changgou.seckill.service.SeckillGoodsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.SystemConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;
import java.util.List;
/****
 * @Author:admin
 * @Description:SeckillGoodsҵ���ӿ�ʵ����
 * @Date 2019/6/14 0:16
 *****/
@Service
public class SeckillGoodsServiceImpl implements SeckillGoodsService {

    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;


    /**
     * SeckillGoods����+��ҳ��ѯ
     * @param seckillGoods ��ѯ����
     * @param page ҳ��
     * @param size ҳ��С
     * @return ��ҳ���
     */
    @Override
    public PageInfo<SeckillGoods> findPage(SeckillGoods seckillGoods, int page, int size){
        //��ҳ
        PageHelper.startPage(page,size);
        //������������
        Example example = createExample(seckillGoods);
        //ִ������
        return new PageInfo<SeckillGoods>(seckillGoodsMapper.selectByExample(example));
    }

    /**
     * SeckillGoods��ҳ��ѯ
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<SeckillGoods> findPage(int page, int size){
        //��̬��ҳ
        PageHelper.startPage(page,size);
        //��ҳ��ѯ
        return new PageInfo<SeckillGoods>(seckillGoodsMapper.selectAll());
    }

    /**
     * SeckillGoods������ѯ
     * @param seckillGoods
     * @return
     */
    @Override
    public List<SeckillGoods> findList(SeckillGoods seckillGoods){
        //������ѯ����
        Example example = createExample(seckillGoods);
        //���ݹ�����������ѯ����
        return seckillGoodsMapper.selectByExample(example);
    }


    /**
     * SeckillGoods������ѯ����
     * @param seckillGoods
     * @return
     */
    public Example createExample(SeckillGoods seckillGoods){
        Example example=new Example(SeckillGoods.class);
        Example.Criteria criteria = example.createCriteria();
        if(seckillGoods!=null){
            // 
            if(!StringUtils.isEmpty(seckillGoods.getId())){
                    criteria.andEqualTo("id",seckillGoods.getId());
            }
            // spu ID
            if(!StringUtils.isEmpty(seckillGoods.getSupId())){
                    criteria.andEqualTo("supId",seckillGoods.getSupId());
            }
            // sku ID
            if(!StringUtils.isEmpty(seckillGoods.getSkuId())){
                    criteria.andEqualTo("skuId",seckillGoods.getSkuId());
            }
            // ����
            if(!StringUtils.isEmpty(seckillGoods.getName())){
                    criteria.andLike("name","%"+seckillGoods.getName()+"%");
            }
            // ��ƷͼƬ
            if(!StringUtils.isEmpty(seckillGoods.getSmallPic())){
                    criteria.andEqualTo("smallPic",seckillGoods.getSmallPic());
            }
            // ԭ�۸�
            if(!StringUtils.isEmpty(seckillGoods.getPrice())){
                    criteria.andEqualTo("price",seckillGoods.getPrice());
            }
            // ��ɱ�۸�
            if(!StringUtils.isEmpty(seckillGoods.getCostPrice())){
                    criteria.andEqualTo("costPrice",seckillGoods.getCostPrice());
            }
            // �������
            if(!StringUtils.isEmpty(seckillGoods.getCreateTime())){
                    criteria.andEqualTo("createTime",seckillGoods.getCreateTime());
            }
            // �������
            if(!StringUtils.isEmpty(seckillGoods.getCheckTime())){
                    criteria.andEqualTo("checkTime",seckillGoods.getCheckTime());
            }
            // ���״̬��0δ��ˣ�1���ͨ����2��˲�ͨ��
            if(!StringUtils.isEmpty(seckillGoods.getStatus())){
                    criteria.andEqualTo("status",seckillGoods.getStatus());
            }
            // ��ʼʱ��
            if(!StringUtils.isEmpty(seckillGoods.getStartTime())){
                    criteria.andEqualTo("startTime",seckillGoods.getStartTime());
            }
            // ����ʱ��
            if(!StringUtils.isEmpty(seckillGoods.getEndTime())){
                    criteria.andEqualTo("endTime",seckillGoods.getEndTime());
            }
            // ��ɱ��Ʒ��
            if(!StringUtils.isEmpty(seckillGoods.getNum())){
                    criteria.andEqualTo("num",seckillGoods.getNum());
            }
            // ʣ������
            if(!StringUtils.isEmpty(seckillGoods.getStockCount())){
                    criteria.andEqualTo("stockCount",seckillGoods.getStockCount());
            }
            // ����
            if(!StringUtils.isEmpty(seckillGoods.getIntroduction())){
                    criteria.andEqualTo("introduction",seckillGoods.getIntroduction());
            }
        }
        return example;
    }

    /**
     * ɾ��
     * @param id
     */
    @Override
    public void delete(Long id){
        seckillGoodsMapper.deleteByPrimaryKey(id);
    }

    /**
     * �޸�SeckillGoods
     * @param seckillGoods
     */
    @Override
    public void update(SeckillGoods seckillGoods){
        seckillGoodsMapper.updateByPrimaryKey(seckillGoods);
    }

    /**
     * ����SeckillGoods
     * @param seckillGoods
     */
    @Override
    public void add(SeckillGoods seckillGoods){
        seckillGoodsMapper.insert(seckillGoods);
    }

    /**
     * ����ID��ѯSeckillGoods
     * @param id
     * @return
     */
    @Override
    public SeckillGoods findById(Long id){
        return  seckillGoodsMapper.selectByPrimaryKey(id);
    }

    /**
     * ��ѯSeckillGoodsȫ������
     * @return
     */
    @Override
    public List<SeckillGoods> findAll() {
        return seckillGoodsMapper.selectAll();
    }

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<SeckillGoods> list(String time) {
        //从redis中获取key 为time 的商品的列表数据  key  field1 value1   key field2 value2
        return redisTemplate.boundHashOps(SystemConstants.SEC_KILL_GOODS_PREFIX+time).values();
    }

    @Override
    public SeckillGoods one(String time, Long id) {
        return (SeckillGoods) redisTemplate.boundHashOps(SystemConstants.SEC_KILL_GOODS_PREFIX+time).get(id);
    }
}
