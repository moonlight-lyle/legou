package com.changgou.user.service.impl;
import com.changgou.user.dao.CitiesMapper;
import com.changgou.user.pojo.Cities;
import com.changgou.user.service.CitiesService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;
import java.util.List;
/****
 * @Author:admin
 * @Description:Citiesҵ���ӿ�ʵ����
 * @Date 2019/6/14 0:16
 *****/
@Service
public class CitiesServiceImpl implements CitiesService {

    @Autowired
    private CitiesMapper citiesMapper;


    /**
     * Cities����+��ҳ��ѯ
     * @param cities ��ѯ����
     * @param page ҳ��
     * @param size ҳ��С
     * @return ��ҳ���
     */
    @Override
    public PageInfo<Cities> findPage(Cities cities, int page, int size){
        //��ҳ
        PageHelper.startPage(page,size);
        //������������
        Example example = createExample(cities);
        //ִ������
        return new PageInfo<Cities>(citiesMapper.selectByExample(example));
    }

    /**
     * Cities��ҳ��ѯ
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Cities> findPage(int page, int size){
        //��̬��ҳ
        PageHelper.startPage(page,size);
        //��ҳ��ѯ
        return new PageInfo<Cities>(citiesMapper.selectAll());
    }

    /**
     * Cities������ѯ
     * @param cities
     * @return
     */
    @Override
    public List<Cities> findList(Cities cities){
        //������ѯ����
        Example example = createExample(cities);
        //���ݹ�����������ѯ����
        return citiesMapper.selectByExample(example);
    }


    /**
     * Cities������ѯ����
     * @param cities
     * @return
     */
    public Example createExample(Cities cities){
        Example example=new Example(Cities.class);
        Example.Criteria criteria = example.createCriteria();
        if(cities!=null){
            // ����ID
            if(!StringUtils.isEmpty(cities.getCityid())){
                    criteria.andEqualTo("cityid",cities.getCityid());
            }
            // ��������
            if(!StringUtils.isEmpty(cities.getCity())){
                    criteria.andEqualTo("city",cities.getCity());
            }
            // ʡ��ID
            if(!StringUtils.isEmpty(cities.getProvinceid())){
                    criteria.andEqualTo("provinceid",cities.getProvinceid());
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
        citiesMapper.deleteByPrimaryKey(id);
    }

    /**
     * �޸�Cities
     * @param cities
     */
    @Override
    public void update(Cities cities){
        citiesMapper.updateByPrimaryKey(cities);
    }

    /**
     * ����Cities
     * @param cities
     */
    @Override
    public void add(Cities cities){
        citiesMapper.insert(cities);
    }

    /**
     * ����ID��ѯCities
     * @param id
     * @return
     */
    @Override
    public Cities findById(String id){
        return  citiesMapper.selectByPrimaryKey(id);
    }

    /**
     * ��ѯCitiesȫ������
     * @return
     */
    @Override
    public List<Cities> findAll() {
        return citiesMapper.selectAll();
    }
}
