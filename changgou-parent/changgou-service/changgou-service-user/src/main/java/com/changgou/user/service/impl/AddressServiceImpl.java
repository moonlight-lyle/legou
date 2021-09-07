package com.changgou.user.service.impl;
import com.changgou.user.dao.AddressMapper;
import com.changgou.user.pojo.Address;
import com.changgou.user.service.AddressService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;
import java.util.List;
/****
 * @Author:admin
 * @Description:Addressҵ���ӿ�ʵ����
 * @Date 2019/6/14 0:16
 *****/
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;


    /**
     * Address����+��ҳ��ѯ
     * @param address ��ѯ����
     * @param page ҳ��
     * @param size ҳ��С
     * @return ��ҳ���
     */
    @Override
    public PageInfo<Address> findPage(Address address, int page, int size){
        //��ҳ
        PageHelper.startPage(page,size);
        //������������
        Example example = createExample(address);
        //ִ������
        return new PageInfo<Address>(addressMapper.selectByExample(example));
    }

    /**
     * Address��ҳ��ѯ
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Address> findPage(int page, int size){
        //��̬��ҳ
        PageHelper.startPage(page,size);
        //��ҳ��ѯ
        return new PageInfo<Address>(addressMapper.selectAll());
    }

    /**
     * Address������ѯ
     * @param address
     * @return
     */
    @Override
    public List<Address> findList(Address address){
        //������ѯ����
        Example example = createExample(address);
        //���ݹ�����������ѯ����
        return addressMapper.selectByExample(example);
    }


    /**
     * Address������ѯ����
     * @param address
     * @return
     */
    public Example createExample(Address address){
        Example example=new Example(Address.class);
        Example.Criteria criteria = example.createCriteria();
        if(address!=null){
            // 
            if(!StringUtils.isEmpty(address.getId())){
                    criteria.andEqualTo("id",address.getId());
            }
            // �û���
            if(!StringUtils.isEmpty(address.getUsername())){
                    criteria.andLike("username","%"+address.getUsername()+"%");
            }
            // ʡ
            if(!StringUtils.isEmpty(address.getProvinceid())){
                    criteria.andEqualTo("provinceid",address.getProvinceid());
            }
            // ��
            if(!StringUtils.isEmpty(address.getCityid())){
                    criteria.andEqualTo("cityid",address.getCityid());
            }
            // ��/��
            if(!StringUtils.isEmpty(address.getAreaid())){
                    criteria.andEqualTo("areaid",address.getAreaid());
            }
            // �绰
            if(!StringUtils.isEmpty(address.getPhone())){
                    criteria.andEqualTo("phone",address.getPhone());
            }
            // ��ϸ��ַ
            if(!StringUtils.isEmpty(address.getAddress())){
                    criteria.andEqualTo("address",address.getAddress());
            }
            // ��ϵ��
            if(!StringUtils.isEmpty(address.getContact())){
                    criteria.andEqualTo("contact",address.getContact());
            }
            // �Ƿ���Ĭ�� 1Ĭ�� 0��
            if(!StringUtils.isEmpty(address.getIsDefault())){
                    criteria.andEqualTo("isDefault",address.getIsDefault());
            }
            // ����
            if(!StringUtils.isEmpty(address.getAlias())){
                    criteria.andEqualTo("alias",address.getAlias());
            }
        }
        return example;
    }

    /**
     * ɾ��
     * @param id
     */
    @Override
    public void delete(Integer id){
        addressMapper.deleteByPrimaryKey(id);
    }

    /**
     * �޸�Address
     * @param address
     */
    @Override
    public void update(Address address){
        addressMapper.updateByPrimaryKey(address);
    }

    /**
     * ����Address
     * @param address
     */
    @Override
    public void add(Address address){
        addressMapper.insert(address);
    }

    /**
     * ����ID��ѯAddress
     * @param id
     * @return
     */
    @Override
    public Address findById(Integer id){
        return  addressMapper.selectByPrimaryKey(id);
    }

    /**
     * ��ѯAddressȫ������
     * @return
     */
    @Override
    public List<Address> findAll() {
        return addressMapper.selectAll();
    }
}
