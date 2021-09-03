package com.changgou.goods.dao;

import com.changgou.goods.pojo.Brand;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BrandMapper extends Mapper<Brand> {

    /**
     * 根据前端传递的分类id查询品牌分类
     * select tbb.* from tb_brand tbb,tb_category_brand tbc where tbb.id=tbc.brand_id and tbc.category_id=76;
     * @param categoryId
     * @return
     */
    // 注解方式
    // @Select(value = "select tbb.* from tb_brand tbb,tb_category_brand tbc where tbb.id=tbc.brand_id and tbc.category_id=#{categoryId}")
    List<Brand> findByCategory(Integer categoryId);
}
