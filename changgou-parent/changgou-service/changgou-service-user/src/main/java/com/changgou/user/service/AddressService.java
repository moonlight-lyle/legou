package com.changgou.user.service;
import com.changgou.user.pojo.Address;
import com.github.pagehelper.PageInfo;
import java.util.List;
/****
 * @Author:admin
 * @Description:Addressҵ���ӿ�
 * @Date 2019/6/14 0:16
 *****/
public interface AddressService {

    /***
     * Address��������ҳ��ѯ
     * @param address
     * @param page
     * @param size
     * @return
     */
    PageInfo<Address> findPage(Address address, int page, int size);

    /***
     * Address��ҳ��ѯ
     * @param page
     * @param size
     * @return
     */
    PageInfo<Address> findPage(int page, int size);

    /***
     * Address��������������
     * @param address
     * @return
     */
    List<Address> findList(Address address);

    /***
     * ɾ��Address
     * @param id
     */
    void delete(Integer id);

    /***
     * �޸�Address����
     * @param address
     */
    void update(Address address);

    /***
     * ����Address
     * @param address
     */
    void add(Address address);

    /**
     * ����ID��ѯAddress
     * @param id
     * @return
     */
     Address findById(Integer id);

    /***
     * ��ѯ����Address
     * @return
     */
    List<Address> findAll();

    List<Address> list(String username);
}
