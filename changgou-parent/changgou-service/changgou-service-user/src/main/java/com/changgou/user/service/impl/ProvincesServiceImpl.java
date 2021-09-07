package com.changgou.user.service.impl;
import com.changgou.user.dao.ProvincesMapper;
import com.changgou.user.pojo.Provinces;
import com.changgou.user.service.ProvincesService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;
import java.util.List;
/****
 * @Author:admin
 * @Description:Provincesҵ���ӿ�ʵ����
 * @Date 2019/6/14 0:16
 *****/
@Service
public class ProvincesServiceImpl implements ProvincesService {

    @Autowired
    private ProvincesMapper provincesMapper;


    /**
     * Provinces����+��ҳ��ѯ
     * @param provinces ��ѯ����
     * @param page ҳ��
     * @param size ҳ��С
     * @return ��ҳ���
     */
    @Override
    public PageInfo<Provinces> findPage(Provinces provinces, int page, int size){
        //��ҳ
        PageHelper.startPage(page,size);
        //������������
        Example example = createExample(provinces);
        //ִ������
        return new PageInfo<Provinces>(provincesMapper.selectByExample(example));
    }

    /**
     * Provinces��ҳ��ѯ
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Provinces> findPage(int page, int size){
        //��̬��ҳ
        PageHelper.startPage(page,size);
        //��ҳ��ѯ
        return new PageInfo<Provinces>(provincesMapper.selectAll());
    }

    /**
     * Provinces������ѯ
     * @param provinces
     * @return
     */
    @Override
    public List<Provinces> findList(Provinces provinces){
        //������ѯ����
        Example example = createExample(provinces);
        //���ݹ�����������ѯ����
        return provincesMapper.selectByExample(example);
    }


    /**
     * Provinces������ѯ����
     * @param provinces
     * @return
     */
    public Example createExample(Provinces provinces){
        Example example=new Example(Provinces.class);
        Example.Criteria criteria = example.createCriteria();
        if(provinces!=null){
            // ʡ��ID
            if(!StringUtils.isEmpty(provinces.getProvinceid())){
                    criteria.andEqualTo("provinceid",provinces.getProvinceid());
            }
            // ʡ������
            if(!StringUtils.isEmpty(provinces.getProvince())){
                    criteria.andEqualTo("province",provinces.getProvince());
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
        provincesMapper.deleteByPrimaryKey(id);
    }

    /**
     * �޸�Provinces
     * @param provinces
     */
    @Override
    public void update(Provinces provinces){
        provincesMapper.updateByPrimaryKey(provinces);
    }

    /**
     * ����Provinces
     * @param provinces
     */
    @Override
    public void add(Provinces provinces){
        provincesMapper.insert(provinces);
    }

    /**
     * ����ID��ѯProvinces
     * @param id
     * @return
     */
    @Override
    public Provinces findById(String id){
        return  provincesMapper.selectByPrimaryKey(id);
    }

    /**
     * ��ѯProvincesȫ������
     * @return
     */
    @Override
    public List<Provinces> findAll() {
        return provincesMapper.selectAll();
    }
}
