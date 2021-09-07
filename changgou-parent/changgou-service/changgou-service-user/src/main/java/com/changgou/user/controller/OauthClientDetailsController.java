package com.changgou.user.controller;
import com.changgou.user.pojo.OauthClientDetails;
import com.changgou.user.service.OauthClientDetailsService;
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
@RequestMapping("/oauthClientDetails")
@CrossOrigin
public class OauthClientDetailsController {

    @Autowired
    private OauthClientDetailsService oauthClientDetailsService;

    /***
     * OauthClientDetails��ҳ��������ʵ��
     * @param oauthClientDetails
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@RequestBody(required = false)  OauthClientDetails oauthClientDetails, @PathVariable  int page, @PathVariable  int size){
        //����OauthClientDetailsServiceʵ�ַ�ҳ������ѯOauthClientDetails
        PageInfo<OauthClientDetails> pageInfo = oauthClientDetailsService.findPage(oauthClientDetails, page, size);
        return new Result(true,StatusCode.OK,"��ѯ�ɹ�",pageInfo);
    }

    /***
     * OauthClientDetails��ҳ����ʵ��
     * @param page:��ǰҳ
     * @param size:ÿҳ��ʾ������
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@PathVariable  int page, @PathVariable  int size){
        //����OauthClientDetailsServiceʵ�ַ�ҳ��ѯOauthClientDetails
        PageInfo<OauthClientDetails> pageInfo = oauthClientDetailsService.findPage(page, size);
        return new Result<PageInfo>(true,StatusCode.OK,"��ѯ�ɹ�",pageInfo);
    }

    /***
     * ����������Ʒ������
     * @param oauthClientDetails
     * @return
     */
    @PostMapping(value = "/search" )
    public Result<List<OauthClientDetails>> findList(@RequestBody(required = false)  OauthClientDetails oauthClientDetails){
        //����OauthClientDetailsServiceʵ��������ѯOauthClientDetails
        List<OauthClientDetails> list = oauthClientDetailsService.findList(oauthClientDetails);
        return new Result<List<OauthClientDetails>>(true,StatusCode.OK,"��ѯ�ɹ�",list);
    }

    /***
     * ����IDɾ��Ʒ������
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable String id){
        //����OauthClientDetailsServiceʵ�ָ�������ɾ��
        oauthClientDetailsService.delete(id);
        return new Result(true,StatusCode.OK,"ɾ���ɹ�");
    }

    /***
     * �޸�OauthClientDetails����
     * @param oauthClientDetails
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    public Result update(@RequestBody  OauthClientDetails oauthClientDetails,@PathVariable String id){
        //��������ֵ
        oauthClientDetails.setClientId(id);
        //����OauthClientDetailsServiceʵ���޸�OauthClientDetails
        oauthClientDetailsService.update(oauthClientDetails);
        return new Result(true,StatusCode.OK,"�޸ĳɹ�");
    }

    /***
     * ����OauthClientDetails����
     * @param oauthClientDetails
     * @return
     */
    @PostMapping
    public Result add(@RequestBody   OauthClientDetails oauthClientDetails){
        //����OauthClientDetailsServiceʵ�����OauthClientDetails
        oauthClientDetailsService.add(oauthClientDetails);
        return new Result(true,StatusCode.OK,"��ӳɹ�");
    }

    /***
     * ����ID��ѯOauthClientDetails����
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<OauthClientDetails> findById(@PathVariable String id){
        //����OauthClientDetailsServiceʵ�ָ���������ѯOauthClientDetails
        OauthClientDetails oauthClientDetails = oauthClientDetailsService.findById(id);
        return new Result<OauthClientDetails>(true,StatusCode.OK,"��ѯ�ɹ�",oauthClientDetails);
    }

    /***
     * ��ѯOauthClientDetailsȫ������
     * @return
     */
    @GetMapping
    public Result<List<OauthClientDetails>> findAll(){
        //����OauthClientDetailsServiceʵ�ֲ�ѯ����OauthClientDetails
        List<OauthClientDetails> list = oauthClientDetailsService.findAll();
        return new Result<List<OauthClientDetails>>(true, StatusCode.OK,"��ѯ�ɹ�",list) ;
    }
}
