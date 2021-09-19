package com.changgou.seckill.controller;
import com.changgou.seckill.pojo.SeckillGoods;
import com.changgou.seckill.service.SeckillGoodsService;
import com.github.pagehelper.PageInfo;
import entity.DateUtil;
import entity.Result;
import entity.StatusCode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/****
 * @Author:admin
 * @Description:
 * @Date 2019/6/14 0:18
 *****/

@RestController
@RequestMapping("/seckillGoods")
@CrossOrigin
public class SeckillGoodsController {

    @Autowired
    private SeckillGoodsService seckillGoodsService;

    /***
     * SeckillGoods��ҳ��������ʵ��
     * @param seckillGoods
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@RequestBody(required = false)  SeckillGoods seckillGoods, @PathVariable  int page, @PathVariable  int size){
        //����SeckillGoodsServiceʵ�ַ�ҳ������ѯSeckillGoods
        PageInfo<SeckillGoods> pageInfo = seckillGoodsService.findPage(seckillGoods, page, size);
        return new Result(true,StatusCode.OK,"��ѯ�ɹ�",pageInfo);
    }

    /***
     * SeckillGoods��ҳ����ʵ��
     * @param page:��ǰҳ
     * @param size:ÿҳ��ʾ������
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@PathVariable  int page, @PathVariable  int size){
        //����SeckillGoodsServiceʵ�ַ�ҳ��ѯSeckillGoods
        PageInfo<SeckillGoods> pageInfo = seckillGoodsService.findPage(page, size);
        return new Result<PageInfo>(true,StatusCode.OK,"��ѯ�ɹ�",pageInfo);
    }

    /***
     * ����������Ʒ������
     * @param seckillGoods
     * @return
     */
    @PostMapping(value = "/search" )
    public Result<List<SeckillGoods>> findList(@RequestBody(required = false)  SeckillGoods seckillGoods){
        //����SeckillGoodsServiceʵ��������ѯSeckillGoods
        List<SeckillGoods> list = seckillGoodsService.findList(seckillGoods);
        return new Result<List<SeckillGoods>>(true,StatusCode.OK,"��ѯ�ɹ�",list);
    }

    /***
     * ����IDɾ��Ʒ������
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable Long id){
        //����SeckillGoodsServiceʵ�ָ�������ɾ��
        seckillGoodsService.delete(id);
        return new Result(true,StatusCode.OK,"ɾ���ɹ�");
    }

    /***
     * �޸�SeckillGoods����
     * @param seckillGoods
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    public Result update(@RequestBody  SeckillGoods seckillGoods,@PathVariable Long id){
        //��������ֵ
        seckillGoods.setId(id);
        //����SeckillGoodsServiceʵ���޸�SeckillGoods
        seckillGoodsService.update(seckillGoods);
        return new Result(true,StatusCode.OK,"�޸ĳɹ�");
    }

    /***
     * ����SeckillGoods����
     * @param seckillGoods
     * @return
     */
    @PostMapping
    public Result add(@RequestBody   SeckillGoods seckillGoods){
        //����SeckillGoodsServiceʵ�����SeckillGoods
        seckillGoodsService.add(seckillGoods);
        return new Result(true,StatusCode.OK,"��ӳɹ�");
    }

    /***
     * ����ID��ѯSeckillGoods����
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<SeckillGoods> findById(@PathVariable Long id){
        //����SeckillGoodsServiceʵ�ָ���������ѯSeckillGoods
        SeckillGoods seckillGoods = seckillGoodsService.findById(id);
        return new Result<SeckillGoods>(true,StatusCode.OK,"��ѯ�ɹ�",seckillGoods);
    }

    /***
     * ��ѯSeckillGoodsȫ������
     * @return
     */
    @GetMapping
    public Result<List<SeckillGoods>> findAll(){
        //����SeckillGoodsServiceʵ�ֲ�ѯ����SeckillGoods
        List<SeckillGoods> list = seckillGoodsService.findAll();
        return new Result<List<SeckillGoods>>(true, StatusCode.OK,"��ѯ�ɹ�",list) ;
    }

    /**
     * ��ȡʱ��Σ������ٴ��Ż�
     * @return
     */
    @RequestMapping("/menus")
    public List<Date> menus(){
        return DateUtil.getDateMenus();
    }

    /**
     * ����ʱ��λ�ȡ��ʱ��ζ�Ӧ����Ʒ���б�
     * @param time ʱ���
     * @return
     */
    @RequestMapping("/list")
    public List<SeckillGoods> list(String time){
        return seckillGoodsService.list(time);
    }

    /**
     *  ������Ʒ��id��ʱ��β�ѯ����Ʒ�Ķ�������
     * @param time ʱ���
     * @param id Ҫ��ѯ����Ʒ��ID
     * @return
     */
    @RequestMapping("/one")
    public SeckillGoods one(String time,Long id){
        return seckillGoodsService.one(time,id);
    }
}
