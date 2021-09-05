package com.changgou.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.changgou.search.pojo.SkuInfo;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/***
 * 描述
 * @author ljh
 * @packagename com.changgou.search.service.impl
 * @version 1.0
 * @date 2020/3/24
 */
public class SearchResultMapperImpl implements SearchResultMapper {
    // 自定义封装：
    // 1.获取高亮的数据封装到POJO中
    // 2.获取高分页的数据
    // 3............
    /**
     * @param response 搜索得到响应的结果
     * @param clazz    泛型
     * @param pageable 分页的数据
     * @param <T>
     * @return
     */
    @Override
    public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
        // 1.获取当前页的记录数
        List<T> content = new ArrayList<T>();
        SearchHits hits = response.getHits();
        if (hits == null || hits.getTotalHits() <= 0) {
            // 空的数据
            return new AggregatedPageImpl<T>(content);
        }
        for (SearchHit hit : hits) {
            String sourceAsString = hit.getSourceAsString(); // 获取没有高亮的数据
            SkuInfo skuInfo = JSON.parseObject(sourceAsString, SkuInfo.class);
            // key  就是 高亮的字段  value 高亮的值对象
            Map<String, HighlightField> highlightFields = hit.getHighlightFields(); // 高亮数据
            if (highlightFields != null
                    && highlightFields.get("name") != null
                    && highlightFields.get("name").getFragments() != null) {
                StringBuffer sb = new StringBuffer();
                for (Text text : highlightFields.get("name").getFragments()) {
                    sb.append(text.string()); // 该数据就是高亮的数据
                }
                if (sb.toString() != null && sb.toString().length() > 0) {
                    skuInfo.setName(sb.toString()); // 已经被替换了
                }
            }
            // 获取高亮的数据替换掉原来不高亮的数据再添加
            content.add((T) skuInfo);//已经是高亮的数据了
        }
        // 2.获取分页的数据 有 了 不需要获取了
        // 3.获取总记录数
        long totalHits = hits.getTotalHits();
        // 4.获取聚合函数的所有值
        Aggregations aggregations = response.getAggregations();
        // 5.获取游标的ID
        String scrollId = response.getScrollId();
        return new AggregatedPageImpl<T>(content, pageable, totalHits, aggregations, scrollId);
    }
}
