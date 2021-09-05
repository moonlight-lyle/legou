package com.changgou.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.changgou.goods.feign.SkuFeign;
import com.changgou.goods.pojo.Sku;
import com.changgou.search.dao.SkuEsMapper;
import com.changgou.search.pojo.SkuInfo;
import com.changgou.search.service.SkuEsService;
import entity.Result;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

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
    public Map search(Map<String, String> searchMap) {
        // 0 获取搜索的关键字
        String keywords = searchMap.get("keywords");
        if (StringUtils.isEmpty(keywords)) {
            keywords = "华为";//随机函数
        }

        // 1.创建查询对象的 构建对象
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();

        // 2.设置各种各样的搜索的条件
        // 2.1 设置分组条件 group by categoryName        参数1 指定别名  参数2 指定要分组的字段名 参数3 指定分组的数据大小，默认是10
        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("skuCategorygroup").field("categoryName").size(50000));//
        // 2.2 设置分组条件 group by brandName        参数1 指定别名  参数2 指定要分组的字段名 参数3 指定分组的数据大小，默认是10
        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("skuBrandgroup").field("brandName").size(50000));//
        // 2.3 设置分组条件 group by spec.keyword        参数1 指定别名  参数2 指定要分组的字段名 参数3 指定分组的数据大小，默认是10
        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("skuSpecgroup").field("spec.keyword").size(50000));//

        // 2.3.5 设置高亮  高亮的字段 高亮的前缀和后缀
        nativeSearchQueryBuilder.withHighlightFields(new HighlightBuilder.Field("name"));
        nativeSearchQueryBuilder.withHighlightBuilder(new HighlightBuilder().preTags("<em style=\"color:red\">").postTags("</em>"));

        // 参数1 指定要搜索的字段名 匹配：先分词再搜索
        // 参数2 指定要匹配搜索的值 是页面传递过来的
        nativeSearchQueryBuilder.withQuery(QueryBuilders.matchQuery("name", keywords));

        // 2.4 过滤查询   商品分类的过滤
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        String category = searchMap.get("category");
        if (!StringUtils.isEmpty(category)) {
            // must  filter  必须满足。 filter就是过滤 不打分排序。速度更快  termQuery ：词条查询 不分词的
            boolQueryBuilder.filter(QueryBuilders.termQuery("categoryName", category));//不为空添加条件
        }

        // 2.5 过滤查询   商品品牌的过滤
        String brand = searchMap.get("brand");
        if (!StringUtils.isEmpty(brand)) {
            // must  filter  必须满足。 filter就是过滤 不打分排序。速度更快
            boolQueryBuilder.filter(QueryBuilders.termQuery("brandName", brand));//不为空添加条件  不分词
        }

        // 2.6 过滤查询  规格的过滤查询  searchMap={"keywords":"aaaa","category":"手雷","brand":"TCL","spec_网络制式":"电信2G"}
        for (Map.Entry<String, String> stringEntry : searchMap.entrySet()) {
            // 也不能分词
            String key = stringEntry.getKey(); // spec_网络制式
            String value = stringEntry.getValue(); // 电信2G
            if (key.startsWith("spec_")) {
                boolQueryBuilder.filter(QueryBuilders.termQuery("specMap." + key.substring(5) + ".keyword", value)); // 不为空添加条件  不分词
            }
        }

        // 2.7 过滤查询  价格区间的过滤（范围）{"keywords":"aaaa","category":"手雷","brand":"TCL","spec_网络制式":"电信2G","price":"3000-*"}
        String price = searchMap.get("price"); // 0-1000  3000-*
        if (!StringUtils.isEmpty(price)) {
            String[] split = price.split("-");
            if (split[1].equals("*")) {
                boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").gte(split[0]));//
            } else {
                boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").gte(split[0]).lte(split[1]));
            }
        }

        nativeSearchQueryBuilder.withFilter(boolQueryBuilder);
        // 2.8 分页查询 默认展示第一页的数据，
        String pageNumStr = searchMap.get("pageNum"); //1  2 3
        Integer pageNum=1;
        Integer pageSize=10;
        if(!StringUtils.isEmpty(pageNumStr)){
            pageNum=Integer.parseInt(pageNumStr);
        }
        // 参数1 指定的是页码的值 0 表示第一页 参数2 指定每页显示的行
        Pageable pageable = PageRequest.of(pageNum-1,pageSize);
        nativeSearchQueryBuilder.withPageable(pageable);

        // 2.9 排序
        // 获取页面传递过来的值
        String sortField = searchMap.get("sortField");  // 字符串 price
        String sortRule = searchMap.get("sortRule");  // DESC 一定是大写
        if(!StringUtils.isEmpty(sortField) && !StringUtils.isEmpty(sortRule)){
            nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort(sortField).order(SortOrder.valueOf(sortRule)));
        }
        // 3.构建查询对象
        SearchQuery query = nativeSearchQueryBuilder.build();

        // 4.执行查询spring data elasticsearch 的模板方法  执行查询 查询的到的数据是没有高亮的，你需要自己获取高亮数据
        AggregatedPage<SkuInfo> skuInfos = elasticsearchTemplate.queryForPage(query, SkuInfo.class, new SearchResultMapperImpl());

        // 4.1 获取分组结果 只获取分组条件为skuCategorygroup的结果  商品分类的分组
        StringTerms skuCategorygroup = (StringTerms) skuInfos.getAggregation("skuCategorygroup");
        List<String> categoryList = getStringsCategoryList(skuCategorygroup);

        // 4.2 获取分组结果 只获取分组条件为skuBrandgroup的结果  品牌的分组
        StringTerms sskuBrandgroup = (StringTerms) skuInfos.getAggregation("skuBrandgroup");
        List<String> brandList = getStringsBrandList(sskuBrandgroup);

        // 4.3 获取分组结果 只获取分组条件为skuSpecgroup的结果 规格的分组
        StringTerms skuSpecgroup = (StringTerms) skuInfos.getAggregation("skuSpecgroup");
        //一顿操作返回一个map
        Map<String, Set<String>> specMap = getStringSetMap(skuSpecgroup);

        // 5.获取结果集 总个记录数总页数当前页的集合
        List<SkuInfo> content = skuInfos.getContent();//当前页的集合

        long totalElements = skuInfos.getTotalElements();//总记录数
        int totalPages = skuInfos.getTotalPages();//总页数
        //6.组合到map 返回
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("total", totalElements);
        resultMap.put("totalPages", totalPages);
        resultMap.put("rows", content);
        //设置商品分类列表数据
        resultMap.put("categoryList", categoryList);
        //设置品牌列表的数据
        resultMap.put("brandList", brandList);
        //设置规格的列表数据
        resultMap.put("specMap", specMap);//{"规格名":["具体的选项值1",""具体的选项值2]}

        return resultMap;
    }

    /**
     * 一顿操作 ： 将分组之后的规格的数据进行解析 返回一个map
     *
     * @param stringTermsSpec
     * @return
     */
    private Map<String, Set<String>> getStringSetMap(StringTerms stringTermsSpec) {
        if (stringTermsSpec == null) {
            return new HashMap<String, Set<String>>();
        }
        Map<String, Set<String>> specMap = new HashMap<String, Set<String>>();// key: 规格名  value： 规格的选项值的【集合】不重复

        Set<String> values = new HashSet<String>();

        for (StringTerms.Bucket bucket : stringTermsSpec.getBuckets()) {
            // {"手机屏幕尺寸":"5.5寸","网络":"电信4G","颜色":"白","测试":"s11","机身内存":"128G","存储":"16G","像素":"300万像素"}
            // {"手机屏幕尺寸":"5.6寸","网络":"电信4G","颜色":"白","测试":"s11","机身内存":"128G","存储":"16G","像素":"800万像素"}
            String keyAsString = bucket.getKeyAsString();
            // 转换成MAP  有key 有value
            Map<String, String> map = JSON.parseObject(keyAsString, Map.class);

            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey(); // 手机屏幕尺寸
                String value = entry.getValue(); // 5.5寸
                values = specMap.get(key);
                if (values == null) { // 第一次需要new HashSet<String>();后面再次同一个key(手机屏幕尺寸)，不需要重复new
                    values = new HashSet<String>();
                }
                values.add(value); // ["5.5寸","5.6寸"]
                specMap.put(key, values);
            }
        }
        return specMap;
    }


    private List<String> getStringsCategoryList(StringTerms stringTerms) {
        List<String> categoryList = new ArrayList<>();
        if (stringTerms != null) {
            for (StringTerms.Bucket bucket : stringTerms.getBuckets()) {
                String keyAsString = bucket.getKeyAsString();//分组的值
                categoryList.add(keyAsString);
            }
        }
        return categoryList;
    }

    private List<String> getStringsBrandList(StringTerms stringTermsBrand) {
        List<String> brandList = new ArrayList<>();
        if (stringTermsBrand != null) {
            for (StringTerms.Bucket bucket : stringTermsBrand.getBuckets()) {
                brandList.add(bucket.getKeyAsString());
            }
        }
        return brandList;
    }
}
