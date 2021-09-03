package com.changgou.goods.dao;
import com.changgou.goods.pojo.Para;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/****
 * @Author:admin
 * @Description:Para的Dao
 * @Date 2019/6/14 0:12
 *****/
public interface ParaMapper extends Mapper<Para> {
    @Select(value = "select para.* from tb_para para where para.template_id=(select tbc.template_id from tb_category tbc where tbc.id=#{categoryId})")
    List<Para> getByCategoryId(Integer categoryId);
}
