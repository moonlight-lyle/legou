package com.changgou.user.controller;
import com.changgou.user.pojo.Provinces;
import com.changgou.user.service.ProvincesService;
import com.github.pagehelper.PageInfo;
import entity.Result;
import entity.StatusCode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/****
 * @Author:admin
 * @Description:
 * @Date 2019/6/14 0:18
 *****/

@RestController
@RequestMapping("/provinces")
@CrossOrigin
public class ProvincesController {

    @Autowired
    private ProvincesService provincesService;

    /***
     * Provinces��ҳ��������ʵ��
     * @param provinces
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@RequestBody(required = false)  Provinces provinces, @PathVariable  int page, @PathVariable  int size){
        //����ProvincesServiceʵ�ַ�ҳ������ѯProvinces
        PageInfo<Provinces> pageInfo = provincesService.findPage(provinces, page, size);
        return new Result(true,StatusCode.OK,"��ѯ�ɹ�",pageInfo);
    }

    /***
     * Provinces��ҳ����ʵ��
     * @param page:��ǰҳ
     * @param size:ÿҳ��ʾ������
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@PathVariable  int page, @PathVariable  int size){
        //����ProvincesServiceʵ�ַ�ҳ��ѯProvinces
        PageInfo<Provinces> pageInfo = provincesService.findPage(page, size);
        return new Result<PageInfo>(true,StatusCode.OK,"��ѯ�ɹ�",pageInfo);
    }

    /***
     * ����������Ʒ������
     * @param provinces
     * @return
     */
    @PostMapping(value = "/search" )
    public Result<List<Provinces>> findList(@RequestBody(required = false)  Provinces provinces){
        //����ProvincesServiceʵ��������ѯProvinces
        List<Provinces> list = provincesService.findList(provinces);
        return new Result<List<Provinces>>(true,StatusCode.OK,"��ѯ�ɹ�",list);
    }

    /***
     * ����IDɾ��Ʒ������
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable String id){
        //����ProvincesServiceʵ�ָ�������ɾ��
        provincesService.delete(id);
        return new Result(true,StatusCode.OK,"ɾ���ɹ�");
    }

    /***
     * �޸�Provinces����
     * @param provinces
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    public Result update(@RequestBody  Provinces provinces,@PathVariable String id){
        //��������ֵ
        provinces.setProvinceid(id);
        //����ProvincesServiceʵ���޸�Provinces
        provincesService.update(provinces);
        return new Result(true,StatusCode.OK,"�޸ĳɹ�");
    }

    /***
     * ����Provinces����
     * @param provinces
     * @return
     */
    @PostMapping
    public Result add(@RequestBody   Provinces provinces){
        //����ProvincesServiceʵ�����Provinces
        provincesService.add(provinces);
        return new Result(true,StatusCode.OK,"��ӳɹ�");
    }

    /***
     * ����ID��ѯProvinces����
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Provinces> findById(@PathVariable String id){
        //����ProvincesServiceʵ�ָ���������ѯProvinces
        Provinces provinces = provincesService.findById(id);
        return new Result<Provinces>(true,StatusCode.OK,"��ѯ�ɹ�",provinces);
    }

    /***
     * ��ѯProvincesȫ������
     * @return
     */
    @GetMapping
    public Result<List<Provinces>> findAll(){
        //����ProvincesServiceʵ�ֲ�ѯ����Provinces
        List<Provinces> list = provincesService.findAll();
        return new Result<List<Provinces>>(true, StatusCode.OK,"��ѯ�ɹ�",list) ;
    }
}
