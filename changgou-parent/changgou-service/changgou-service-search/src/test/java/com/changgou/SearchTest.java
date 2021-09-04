package com.changgou;

import com.changgou.search.dao.SkuEsMapper;
import com.changgou.search.pojo.SkuInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SearchTest {

    @Autowired
    private SkuEsMapper skuEsMapper;

    @Test
    public void createDocument(){
        SkuInfo skuInfo=new SkuInfo();
        skuInfo.setId(1000L);
        skuInfo.setBrandName("华为");
        skuInfo.setName("华为手机，To Be Number One");
        skuInfo.setPrice(10000L);
        skuEsMapper.save(skuInfo);
    }
}
