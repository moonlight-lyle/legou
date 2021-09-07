package com.changgou.user.service;
import com.changgou.user.pojo.Areas;
import com.github.pagehelper.PageInfo;
import java.util.List;
/****
 * @Author:admin
 * @Description:Areasҵ���ӿ�
 * @Date 2019/6/14 0:16
 *****/
public interface AreasService {

    /***
     * Areas��������ҳ��ѯ
     * @param areas
     * @param page
     * @param size
     * @return
     */
    PageInfo<Areas> findPage(Areas areas, int page, int size);

    /***
     * Areas��ҳ��ѯ
     * @param page
     * @param size
     * @return
     */
    PageInfo<Areas> findPage(int page, int size);

    /***
     * Areas��������������
     * @param areas
     * @return
     */
    List<Areas> findList(Areas areas);

    /***
     * ɾ��Areas
     * @param id
     */
    void delete(String id);

    /***
     * �޸�Areas����
     * @param areas
     */
    void update(Areas areas);

    /***
     * ����Areas
     * @param areas
     */
    void add(Areas areas);

    /**
     * ����ID��ѯAreas
     * @param id
     * @return
     */
     Areas findById(String id);

    /***
     * ��ѯ����Areas
     * @return
     */
    List<Areas> findAll();
}
