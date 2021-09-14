package com.changgou.user.service;
import com.changgou.user.pojo.User;
import com.github.pagehelper.PageInfo;
import java.util.List;
/****
 * @Author:admin
 * @Description:Userҵ���ӿ�
 * @Date 2019/6/14 0:16
 *****/
public interface UserService {

    /***
     * User��������ҳ��ѯ
     * @param user
     * @param page
     * @param size
     * @return
     */
    PageInfo<User> findPage(User user, int page, int size);

    /***
     * User��ҳ��ѯ
     * @param page
     * @param size
     * @return
     */
    PageInfo<User> findPage(int page, int size);

    /***
     * User��������������
     * @param user
     * @return
     */
    List<User> findList(User user);

    /***
     * ɾ��User
     * @param id
     */
    void delete(String id);

    /***
     * �޸�User����
     * @param user
     */
    void update(User user);

    /***
     * ����User
     * @param user
     */
    void add(User user);

    /**
     * ����ID��ѯUser
     * @param id
     * @return
     */
     User findById(String id);

    /***
     * ��ѯ����User
     * @return
     */
    List<User> findAll();

    int addPoints(String username, Integer points);
}
