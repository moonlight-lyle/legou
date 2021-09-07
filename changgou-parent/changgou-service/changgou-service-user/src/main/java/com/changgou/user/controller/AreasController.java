package com.changgou.user.controller;
import com.changgou.user.pojo.Areas;
import com.changgou.user.service.AreasService;
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
@RequestMapping("/areas")
@CrossOrigin
public class AreasController {

    @Autowired
    private AreasService areasService;

    /***
     * Areas��ҳ��������ʵ��
     * @param areas
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@RequestBody(required = false)  Areas areas, @PathVariable  int page, @PathVariable  int size){
        //����AreasServiceʵ�ַ�ҳ������ѯAreas
        PageInfo<Areas> pageInfo = areasService.findPage(areas, page, size);
        return new Result(true,StatusCode.OK,"��ѯ�ɹ�",pageInfo);
    }

    /***
     * Areas��ҳ����ʵ��
     * @param page:��ǰҳ
     * @param size:ÿҳ��ʾ������
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@PathVariable  int page, @PathVariable  int size){
        //����AreasServiceʵ�ַ�ҳ��ѯAreas
        PageInfo<Areas> pageInfo = areasService.findPage(page, size);
        return new Result<PageInfo>(true,StatusCode.OK,"��ѯ�ɹ�",pageInfo);
    }

    /***
     * ����������Ʒ������
     * @param areas
     * @return
     */
    @PostMapping(value = "/search" )
    public Result<List<Areas>> findList(@RequestBody(required = false)  Areas areas){
        //����AreasServiceʵ��������ѯAreas
        List<Areas> list = areasService.findList(areas);
        return new Result<List<Areas>>(true,StatusCode.OK,"��ѯ�ɹ�",list);
    }

    /***
     * ����IDɾ��Ʒ������
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable String id){
        //����AreasServiceʵ�ָ�������ɾ��
        areasService.delete(id);
        return new Result(true,StatusCode.OK,"ɾ���ɹ�");
    }

    /***
     * �޸�Areas����
     * @param areas
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    public Result update(@RequestBody  Areas areas,@PathVariable String id){
        //��������ֵ
        areas.setAreaid(id);
        //����AreasServiceʵ���޸�Areas
        areasService.update(areas);
        return new Result(true,StatusCode.OK,"�޸ĳɹ�");
    }

    /***
     * ����Areas����
     * @param areas
     * @return
     */
    @PostMapping
    public Result add(@RequestBody   Areas areas){
        //����AreasServiceʵ�����Areas
        areasService.add(areas);
        return new Result(true,StatusCode.OK,"��ӳɹ�");
    }

    /***
     * ����ID��ѯAreas����
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Areas> findById(@PathVariable String id){
        //����AreasServiceʵ�ָ���������ѯAreas
        Areas areas = areasService.findById(id);
        return new Result<Areas>(true,StatusCode.OK,"��ѯ�ɹ�",areas);
    }

    /***
     * ��ѯAreasȫ������
     * @return
     */
    @GetMapping
    public Result<List<Areas>> findAll(){
        //����AreasServiceʵ�ֲ�ѯ����Areas
        List<Areas> list = areasService.findAll();
        return new Result<List<Areas>>(true, StatusCode.OK,"��ѯ�ɹ�",list) ;
    }
}
