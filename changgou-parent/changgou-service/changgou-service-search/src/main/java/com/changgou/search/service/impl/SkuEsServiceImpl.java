package com.changgou.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.changgou.goods.feign.SkuFeign;
import com.changgou.goods.pojo.Sku;
import com.changgou.search.dao.SkuEsMapper;
import com.changgou.search.pojo.SkuInfo;
import com.changgou.search.service.SkuEsService;
import entity.Result;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SkuEsServiceImpl implements SkuEsService {

    @Autowired
    private SkuEsMapper skuEsMapper;

    @Autowired
    private SkuFeign skuFeign;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public void importSku() {
        // 使用Feign调用商品微服务，将符合条件的数据查询出来
        Result<List<Sku>> result = skuFeign.findByStatus("1");
        List<Sku> skuList = result.getData();
        // 将数据导入到Es服务器中
        // 将Sku数据转换成SkuInfo
        List<SkuInfo> skuInfos = JSON.parseArray(JSON.toJSONString(skuList), SkuInfo.class);

        for (SkuInfo skuInfo : skuInfos) {
            String spec = skuInfo.getSpec(); // {"电视音响效果":"环绕","电视屏幕尺寸":"20英寸","尺码":"170"}
            Map<String, Object> map = JSON.parseObject(spec, Map.class);
            skuInfo.setSpecMap(map);
        }

        skuEsMapper.saveAll(skuInfos);

    }

    @Override
    public Map search(Map<String,String> searchMap) {
        //0 获取搜索的关键字
        String keywords = searchMap.get("keywords");
        if(StringUtils.isEmpty(keywords)){
            keywords="华为"; // 随机函数
        }
        // 1.创建条件对象的构建对象
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        // 2.设置各种条件
        //参数1 指定要搜索的字段名 匹配：先分词再搜索
        //参数2 指定要匹配搜索的值 是页面传递过来的
        nativeSearchQueryBuilder.withQuery(QueryBuilders.matchQuery("name",keywords));
        // 3.构建查询对象
        SearchQuery query = nativeSearchQueryBuilder.build();
        // 4.执行查询方法
        AggregatedPage<SkuInfo> skuInfos = elasticsearchTemplate.queryForPage(query, SkuInfo.class);
        // 5.获取结果集
        List<SkuInfo> content = skuInfos.getContent();//当前页的集合
        long totalElements = skuInfos.getTotalElements();//总记录数
        int totalPages = skuInfos.getTotalPages();//总页数
        // 6.封装成Map对象
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("total", totalElements);
        resultMap.put("totalPages", totalPages);
        resultMap.put("rows", content);
        return resultMap;
    }
}
