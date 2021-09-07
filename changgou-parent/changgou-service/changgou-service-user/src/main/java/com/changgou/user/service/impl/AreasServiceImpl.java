package com.changgou.user.service.impl;
import com.changgou.user.dao.AreasMapper;
import com.changgou.user.pojo.Areas;
import com.changgou.user.service.AreasService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;
import java.util.List;
/****
 * @Author:admin
 * @Description:Areasҵ���ӿ�ʵ����
 * @Date 2019/6/14 0:16
 *****/
@Service
public class AreasServiceImpl implements AreasService {

    @Autowired
    private AreasMapper areasMapper;


    /**
     * Areas����+��ҳ��ѯ
     * @param areas ��ѯ����
     * @param page ҳ��
     * @param size ҳ��С
     * @return ��ҳ���
     */
    @Override
    public PageInfo<Areas> findPage(Areas areas, int page, int size){
        //��ҳ
        PageHelper.startPage(page,size);
        //������������
        Example example = createExample(areas);
        //ִ������
        return new PageInfo<Areas>(areasMapper.selectByExample(example));
    }

    /**
     * Areas��ҳ��ѯ
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Areas> findPage(int page, int size){
        //��̬��ҳ
        PageHelper.startPage(page,size);
        //��ҳ��ѯ
        return new PageInfo<Areas>(areasMapper.selectAll());
    }

    /**
     * Areas������ѯ
     * @param areas
     * @return
     */
    @Override
    public List<Areas> findList(Areas areas){
        //������ѯ����
        Example example = createExample(areas);
        //���ݹ�����������ѯ����
        return areasMapper.selectByExample(example);
    }


    /**
     * Areas������ѯ����
     * @param areas
     * @return
     */
    public Example createExample(Areas areas){
        Example example=new Example(Areas.class);
        Example.Criteria criteria = example.createCriteria();
        if(areas!=null){
            // ����ID
            if(!StringUtils.isEmpty(areas.getAreaid())){
                    criteria.andEqualTo("areaid",areas.getAreaid());
            }
            // ��������
            if(!StringUtils.isEmpty(areas.getArea())){
                    criteria.andEqualTo("area",areas.getArea());
            }
            // ����ID
            if(!StringUtils.isEmpty(areas.getCityid())){
                    criteria.andEqualTo("cityid",areas.getCityid());
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
        areasMapper.deleteByPrimaryKey(id);
    }

    /**
     * �޸�Areas
     * @param areas
     */
    @Override
    public void update(Areas areas){
        areasMapper.updateByPrimaryKey(areas);
    }

    /**
     * ����Areas
     * @param areas
     */
    @Override
    public void add(Areas areas){
        areasMapper.insert(areas);
    }

    /**
     * ����ID��ѯAreas
     * @param id
     * @return
     */
    @Override
    public Areas findById(String id){
        return  areasMapper.selectByPrimaryKey(id);
    }

    /**
     * ��ѯAreasȫ������
     * @return
     */
    @Override
    public List<Areas> findAll() {
        return areasMapper.selectAll();
    }
}
