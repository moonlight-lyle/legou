package com.changgou.user.controller;
import com.changgou.user.pojo.Cities;
import com.changgou.user.service.CitiesService;
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
@RequestMapping("/cities")
@CrossOrigin
public class CitiesController {

    @Autowired
    private CitiesService citiesService;

    /***
     * Cities��ҳ��������ʵ��
     * @param cities
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@RequestBody(required = false)  Cities cities, @PathVariable  int page, @PathVariable  int size){
        //����CitiesServiceʵ�ַ�ҳ������ѯCities
        PageInfo<Cities> pageInfo = citiesService.findPage(cities, page, size);
        return new Result(true,StatusCode.OK,"��ѯ�ɹ�",pageInfo);
    }

    /***
     * Cities��ҳ����ʵ��
     * @param page:��ǰҳ
     * @param size:ÿҳ��ʾ������
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@PathVariable  int page, @PathVariable  int size){
        //����CitiesServiceʵ�ַ�ҳ��ѯCities
        PageInfo<Cities> pageInfo = citiesService.findPage(page, size);
        return new Result<PageInfo>(true,StatusCode.OK,"��ѯ�ɹ�",pageInfo);
    }

    /***
     * ����������Ʒ������
     * @param cities
     * @return
     */
    @PostMapping(value = "/search" )
    public Result<List<Cities>> findList(@RequestBody(required = false)  Cities cities){
        //����CitiesServiceʵ��������ѯCities
        List<Cities> list = citiesService.findList(cities);
        return new Result<List<Cities>>(true,StatusCode.OK,"��ѯ�ɹ�",list);
    }

    /***
     * ����IDɾ��Ʒ������
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable String id){
        //����CitiesServiceʵ�ָ�������ɾ��
        citiesService.delete(id);
        return new Result(true,StatusCode.OK,"ɾ���ɹ�");
    }

    /***
     * �޸�Cities����
     * @param cities
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    public Result update(@RequestBody  Cities cities,@PathVariable String id){
        //��������ֵ
        cities.setCityid(id);
        //����CitiesServiceʵ���޸�Cities
        citiesService.update(cities);
        return new Result(true,StatusCode.OK,"�޸ĳɹ�");
    }

    /***
     * ����Cities����
     * @param cities
     * @return
     */
    @PostMapping
    public Result add(@RequestBody   Cities cities){
        //����CitiesServiceʵ�����Cities
        citiesService.add(cities);
        return new Result(true,StatusCode.OK,"��ӳɹ�");
    }

    /***
     * ����ID��ѯCities����
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Cities> findById(@PathVariable String id){
        //����CitiesServiceʵ�ָ���������ѯCities
        Cities cities = citiesService.findById(id);
        return new Result<Cities>(true,StatusCode.OK,"��ѯ�ɹ�",cities);
    }

    /***
     * ��ѯCitiesȫ������
     * @return
     */
    @GetMapping
    public Result<List<Cities>> findAll(){
        //����CitiesServiceʵ�ֲ�ѯ����Cities
        List<Cities> list = citiesService.findAll();
        return new Result<List<Cities>>(true, StatusCode.OK,"��ѯ�ɹ�",list) ;
    }
}
