package com.changgou.user.service.impl;
import com.changgou.user.dao.OauthClientDetailsMapper;
import com.changgou.user.pojo.OauthClientDetails;
import com.changgou.user.service.OauthClientDetailsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;
import java.util.List;
/****
 * @Author:admin
 * @Description:OauthClientDetailsҵ���ӿ�ʵ����
 * @Date 2019/6/14 0:16
 *****/
@Service
public class OauthClientDetailsServiceImpl implements OauthClientDetailsService {

    @Autowired
    private OauthClientDetailsMapper oauthClientDetailsMapper;


    /**
     * OauthClientDetails����+��ҳ��ѯ
     * @param oauthClientDetails ��ѯ����
     * @param page ҳ��
     * @param size ҳ��С
     * @return ��ҳ���
     */
    @Override
    public PageInfo<OauthClientDetails> findPage(OauthClientDetails oauthClientDetails, int page, int size){
        //��ҳ
        PageHelper.startPage(page,size);
        //������������
        Example example = createExample(oauthClientDetails);
        //ִ������
        return new PageInfo<OauthClientDetails>(oauthClientDetailsMapper.selectByExample(example));
    }

    /**
     * OauthClientDetails��ҳ��ѯ
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<OauthClientDetails> findPage(int page, int size){
        //��̬��ҳ
        PageHelper.startPage(page,size);
        //��ҳ��ѯ
        return new PageInfo<OauthClientDetails>(oauthClientDetailsMapper.selectAll());
    }

    /**
     * OauthClientDetails������ѯ
     * @param oauthClientDetails
     * @return
     */
    @Override
    public List<OauthClientDetails> findList(OauthClientDetails oauthClientDetails){
        //������ѯ����
        Example example = createExample(oauthClientDetails);
        //���ݹ�����������ѯ����
        return oauthClientDetailsMapper.selectByExample(example);
    }


    /**
     * OauthClientDetails������ѯ����
     * @param oauthClientDetails
     * @return
     */
    public Example createExample(OauthClientDetails oauthClientDetails){
        Example example=new Example(OauthClientDetails.class);
        Example.Criteria criteria = example.createCriteria();
        if(oauthClientDetails!=null){
            // �ͻ���ID����Ҫ���ڱ�ʶ��Ӧ��Ӧ��
            if(!StringUtils.isEmpty(oauthClientDetails.getClientId())){
                    criteria.andEqualTo("clientId",oauthClientDetails.getClientId());
            }
            // 
            if(!StringUtils.isEmpty(oauthClientDetails.getResourceIds())){
                    criteria.andEqualTo("resourceIds",oauthClientDetails.getResourceIds());
            }
            // �ͻ�����Կ��BCryptPasswordEncoder�����㷨����
            if(!StringUtils.isEmpty(oauthClientDetails.getClientSecret())){
                    criteria.andEqualTo("clientSecret",oauthClientDetails.getClientSecret());
            }
            // ��Ӧ�ķ�Χ
            if(!StringUtils.isEmpty(oauthClientDetails.getScope())){
                    criteria.andEqualTo("scope",oauthClientDetails.getScope());
            }
            // ��֤ģʽ
            if(!StringUtils.isEmpty(oauthClientDetails.getAuthorizedGrantTypes())){
                    criteria.andEqualTo("authorizedGrantTypes",oauthClientDetails.getAuthorizedGrantTypes());
            }
            // ��֤���ض����ַ
            if(!StringUtils.isEmpty(oauthClientDetails.getWebServerRedirectUri())){
                    criteria.andEqualTo("webServerRedirectUri",oauthClientDetails.getWebServerRedirectUri());
            }
            // 
            if(!StringUtils.isEmpty(oauthClientDetails.getAuthorities())){
                    criteria.andEqualTo("authorities",oauthClientDetails.getAuthorities());
            }
            // ������Ч��
            if(!StringUtils.isEmpty(oauthClientDetails.getAccessTokenValidity())){
                    criteria.andEqualTo("accessTokenValidity",oauthClientDetails.getAccessTokenValidity());
            }
            // ����ˢ������
            if(!StringUtils.isEmpty(oauthClientDetails.getRefreshTokenValidity())){
                    criteria.andEqualTo("refreshTokenValidity",oauthClientDetails.getRefreshTokenValidity());
            }
            // 
            if(!StringUtils.isEmpty(oauthClientDetails.getAdditionalInformation())){
                    criteria.andEqualTo("additionalInformation",oauthClientDetails.getAdditionalInformation());
            }
            // 
            if(!StringUtils.isEmpty(oauthClientDetails.getAutoapprove())){
                    criteria.andEqualTo("autoapprove",oauthClientDetails.getAutoapprove());
            }
        }
        return example;
    }

    /**
     * ɾ��
     * @param id
     */
    @Override
    public void delete(String id){
        oauthClientDetailsMapper.deleteByPrimaryKey(id);
    }

    /**
     * �޸�OauthClientDetails
     * @param oauthClientDetails
     */
    @Override
    public void update(OauthClientDetails oauthClientDetails){
        oauthClientDetailsMapper.updateByPrimaryKey(oauthClientDetails);
    }

    /**
     * ����OauthClientDetails
     * @param oauthClientDetails
     */
    @Override
    public void add(OauthClientDetails oauthClientDetails){
        oauthClientDetailsMapper.insert(oauthClientDetails);
    }

    /**
     * ����ID��ѯOauthClientDetails
     * @param id
     * @return
     */
    @Override
    public OauthClientDetails findById(String id){
        return  oauthClientDetailsMapper.selectByPrimaryKey(id);
    }

    /**
     * ��ѯOauthClientDetailsȫ������
     * @return
     */
    @Override
    public List<OauthClientDetails> findAll() {
        return oauthClientDetailsMapper.selectAll();
    }
}
