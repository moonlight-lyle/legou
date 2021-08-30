package com.changgou.goods.service.impl;

import com.changgou.goods.dao.BrandMapper;
import com.changgou.goods.pojo.Brand;
import com.changgou.goods.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired(required = false)
    private BrandMapper brandMapper;

    @Override
    public List<Brand> findAll() {
        return brandMapper.selectAll();
    }

    @Override
    public Brand findById(Integer id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    @Override
    public void add(Brand brand) {
//        brandMapper.insert(brand); // 不管为不为空都添加
        brandMapper.insertSelective(brand); // 不为空的添加
    }

    @Override
    public void update(Brand brand) {
        brandMapper.updateByPrimaryKeySelective(brand);
    }

    @Override
    public void delete(Integer id) {
        brandMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Brand> findList(Brand brand) {
        // 根据条件进行搜索
        /**
         *  创建条件，根据条件拼接sql
         */
        Example example=new Example(Brand.class); // 条件对象
        Example.Criteria criteria = example.createCriteria();
       // criteria.andEqualTo("name","张三"); // 指定pojo中的字段名，相当于where name=张三
       // criteria.andEqualTo("letter","M"); // 指定pojo中的字段名，相当于where name=张三 and letter=M
        if (StringUtil.isNotEmpty(brand.getName())){
            criteria.andLike("name","%"+brand.getName()+"%");
        }
        if (StringUtil.isNotEmpty(brand.getLetter())){
            criteria.andEqualTo("letter",brand.getLetter());
        }
        List<Brand> brands = brandMapper.selectByExample(example);
        return brands;
    }

}
