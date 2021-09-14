package com.changgou.user.controller;
import com.changgou.user.config.TokenDecode;
import com.changgou.user.pojo.Address;
import com.changgou.user.service.AddressService;
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
@RequestMapping("/address")
@CrossOrigin
public class AddressController {

    @Autowired
    private AddressService addressService;

    /***
     * Address��ҳ��������ʵ��
     * @param address
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@RequestBody(required = false)  Address address, @PathVariable  int page, @PathVariable  int size){
        //����AddressServiceʵ�ַ�ҳ������ѯAddress
        PageInfo<Address> pageInfo = addressService.findPage(address, page, size);
        return new Result(true,StatusCode.OK,"��ѯ�ɹ�",pageInfo);
    }

    /***
     * Address��ҳ����ʵ��
     * @param page:��ǰҳ
     * @param size:ÿҳ��ʾ������
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@PathVariable  int page, @PathVariable  int size){
        //����AddressServiceʵ�ַ�ҳ��ѯAddress
        PageInfo<Address> pageInfo = addressService.findPage(page, size);
        return new Result<PageInfo>(true,StatusCode.OK,"��ѯ�ɹ�",pageInfo);
    }

    /***
     * ����������Ʒ������
     * @param address
     * @return
     */
    @PostMapping(value = "/search" )
    public Result<List<Address>> findList(@RequestBody(required = false)  Address address){
        //����AddressServiceʵ��������ѯAddress
        List<Address> list = addressService.findList(address);
        return new Result<List<Address>>(true,StatusCode.OK,"��ѯ�ɹ�",list);
    }

    /***
     * ����IDɾ��Ʒ������
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable Integer id){
        //����AddressServiceʵ�ָ�������ɾ��
        addressService.delete(id);
        return new Result(true,StatusCode.OK,"ɾ���ɹ�");
    }

    /***
     * �޸�Address����
     * @param address
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    public Result update(@RequestBody  Address address,@PathVariable Integer id){
        //��������ֵ
        address.setId(id);
        //����AddressServiceʵ���޸�Address
        addressService.update(address);
        return new Result(true,StatusCode.OK,"�޸ĳɹ�");
    }

    /***
     * ����Address����
     * @param address
     * @return
     */
    @PostMapping
    public Result add(@RequestBody   Address address){
        //����AddressServiceʵ�����Address
        addressService.add(address);
        return new Result(true,StatusCode.OK,"��ӳɹ�");
    }

    /***
     * ����ID��ѯAddress����
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Address> findById(@PathVariable Integer id){
        //����AddressServiceʵ�ָ���������ѯAddress
        Address address = addressService.findById(id);
        return new Result<Address>(true,StatusCode.OK,"��ѯ�ɹ�",address);
    }

    /***
     * ��ѯAddressȫ������
     * @return
     */
    @GetMapping
    public Result<List<Address>> findAll(){
        //����AddressServiceʵ�ֲ�ѯ����Address
        List<Address> list = addressService.findAll();
        return new Result<List<Address>>(true, StatusCode.OK,"��ѯ�ɹ�",list) ;
    }

    @Autowired
    private TokenDecode tokenDecode;
    /**
     * ���ݵ�ǰ�ĵ�¼���û���ȡ�û��ĵ�ַ�б�
     * @return
     */
    @GetMapping(value = "/user/list")
    public Result<List<Address>> list(){
        // 1.��ȡ��ǰ��¼���û����û���
        String username = tokenDecode.getUsername();
        // 2.�����û��������ݿ��ѯ���б����� ����
        List<Address> addressList= addressService.list(username);
        return new Result<>(true,StatusCode.OK,"��ѯ��ַ�б�ɹ�",addressList);
    }
}
