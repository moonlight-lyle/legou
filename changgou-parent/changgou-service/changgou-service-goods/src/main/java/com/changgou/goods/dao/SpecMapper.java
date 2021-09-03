package com.changgou.goods.dao;
import com.changgou.goods.pojo.Spec;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/****
 * @Author:admin
 * @Description:Spec的Dao
 * @Date 2019/6/14 0:12
 *****/
public interface SpecMapper extends Mapper<Spec> {

    @Select(value = "select spec.* from tb_spec spec where spec.template_id=(select tbc.template_id from tb_category tbc where tbc.id=#{categoryid})")
    List<Spec> findByCategoryId(Integer categoryid);
}
