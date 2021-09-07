package com.changgou.user.service;
import com.changgou.user.pojo.Cities;
import com.github.pagehelper.PageInfo;
import java.util.List;
/****
 * @Author:admin
 * @Description:Citiesҵ���ӿ�
 * @Date 2019/6/14 0:16
 *****/
public interface CitiesService {

    /***
     * Cities��������ҳ��ѯ
     * @param cities
     * @param page
     * @param size
     * @return
     */
    PageInfo<Cities> findPage(Cities cities, int page, int size);

    /***
     * Cities��ҳ��ѯ
     * @param page
     * @param size
     * @return
     */
    PageInfo<Cities> findPage(int page, int size);

    /***
     * Cities��������������
     * @param cities
     * @return
     */
    List<Cities> findList(Cities cities);

    /***
     * ɾ��Cities
     * @param id
     */
    void delete(String id);

    /***
     * �޸�Cities����
     * @param cities
     */
    void update(Cities cities);

    /***
     * ����Cities
     * @param cities
     */
    void add(Cities cities);

    /**
     * ����ID��ѯCities
     * @param id
     * @return
     */
     Cities findById(String id);

    /***
     * ��ѯ����Cities
     * @return
     */
    List<Cities> findAll();
}
