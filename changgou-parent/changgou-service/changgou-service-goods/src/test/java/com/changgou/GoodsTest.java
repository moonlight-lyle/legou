package com.changgou;

import com.changgou.goods.dao.BrandMapper;
import com.changgou.goods.pojo.Brand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * 商品服务测试
 */
@SpringBootTest // SpringBoot 集成Junit单元测试注解
@RunWith(SpringRunner.class) // 要在测试类中使用@Autowired注入类，必须使用此注解
public class GoodsTest {

    @Autowired(required = false)
    private BrandMapper brandMapper;

    @Test
    public void findAll(){
        List<Brand> brands = brandMapper.selectAll();
        if (!CollectionUtils.isEmpty(brands)){
            for (Brand brand : brands) {
                System.out.println(brand.getName());
            }
        }
    }
}
