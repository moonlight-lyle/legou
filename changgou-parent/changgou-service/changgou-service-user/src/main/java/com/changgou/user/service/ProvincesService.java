package com.changgou.user.service;
import com.changgou.user.pojo.Provinces;
import com.github.pagehelper.PageInfo;
import java.util.List;
/****
 * @Author:admin
 * @Description:Provincesҵ���ӿ�
 * @Date 2019/6/14 0:16
 *****/
public interface ProvincesService {

    /***
     * Provinces��������ҳ��ѯ
     * @param provinces
     * @param page
     * @param size
     * @return
     */
    PageInfo<Provinces> findPage(Provinces provinces, int page, int size);

    /***
     * Provinces��ҳ��ѯ
     * @param page
     * @param size
     * @return
     */
    PageInfo<Provinces> findPage(int page, int size);

    /***
     * Provinces��������������
     * @param provinces
     * @return
     */
    List<Provinces> findList(Provinces provinces);

    /***
     * ɾ��Provinces
     * @param id
     */
    void delete(String id);

    /***
     * �޸�Provinces����
     * @param provinces
     */
    void update(Provinces provinces);

    /***
     * ����Provinces
     * @param provinces
     */
    void add(Provinces provinces);

    /**
     * ����ID��ѯProvinces
     * @param id
     * @return
     */
     Provinces findById(String id);

    /***
     * ��ѯ����Provinces
     * @return
     */
    List<Provinces> findAll();
}
