package com.changgou.user.service;
import com.changgou.user.pojo.OauthClientDetails;
import com.github.pagehelper.PageInfo;
import java.util.List;
/****
 * @Author:admin
 * @Description:OauthClientDetailsҵ���ӿ�
 * @Date 2019/6/14 0:16
 *****/
public interface OauthClientDetailsService {

    /***
     * OauthClientDetails��������ҳ��ѯ
     * @param oauthClientDetails
     * @param page
     * @param size
     * @return
     */
    PageInfo<OauthClientDetails> findPage(OauthClientDetails oauthClientDetails, int page, int size);

    /***
     * OauthClientDetails��ҳ��ѯ
     * @param page
     * @param size
     * @return
     */
    PageInfo<OauthClientDetails> findPage(int page, int size);

    /***
     * OauthClientDetails��������������
     * @param oauthClientDetails
     * @return
     */
    List<OauthClientDetails> findList(OauthClientDetails oauthClientDetails);

    /***
     * ɾ��OauthClientDetails
     * @param id
     */
    void delete(String id);

    /***
     * �޸�OauthClientDetails����
     * @param oauthClientDetails
     */
    void update(OauthClientDetails oauthClientDetails);

    /***
     * ����OauthClientDetails
     * @param oauthClientDetails
     */
    void add(OauthClientDetails oauthClientDetails);

    /**
     * ����ID��ѯOauthClientDetails
     * @param id
     * @return
     */
     OauthClientDetails findById(String id);

    /***
     * ��ѯ����OauthClientDetails
     * @return
     */
    List<OauthClientDetails> findAll();
}
