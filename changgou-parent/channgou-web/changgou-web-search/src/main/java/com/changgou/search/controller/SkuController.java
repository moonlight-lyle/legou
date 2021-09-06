package com.changgou.search.controller;

import com.changgou.search.feign.SkuFeign;
import com.changgou.search.pojo.SkuInfo;
import entity.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/search")
public class SkuController {

    @Autowired
    private SkuFeign skuFeign;

    /**
     * @param searchMap {keywords:"手机","category":"笔记本"........}
     * @return 逻辑视图  但是model中会有数据
     */
    @GetMapping("/list")
    public String search(@RequestParam(required = false) Map searchMap, Model model){ // @RequestParam(required = false)：表示参数非必传
        // 1. 接收请求之后，通过Feign调用ES搜索服务
        Map resultMap = skuFeign.search(searchMap);
        // 2. 得到搜索服务返回的JSON数据
        // 3. 转换成Map，返回值已经是Map，无需再转
        // 4. 将数据添加到Model中
        model.addAttribute("result",resultMap);
        model.addAttribute("searchMap",searchMap);
        model.addAttribute("url", url(searchMap));//每次请求都要记录请求路径
        //创建封装的对象，设置需要的值 设置到model中
        Page<SkuInfo> skuInfoPage = new Page<SkuInfo>(
                Long.valueOf(resultMap.get("total").toString()),//总记录数
                Integer.parseInt(resultMap.get("pageNum").toString()),//当前页码
                Integer.parseInt(resultMap.get("pageSize").toString())//每页显示的行
        );
        model.addAttribute("page",skuInfoPage);
        // 5. 模板页面中获取Model中设置的值来进行渲染
        return "search"; // 返回search，自动渲染成resources下templates下的search.html文件
    }

    private String url(Map<String, String> searchMap) { //{keywords:华为,category:笔记本，brand:小米}
        //              /search/list?keywords=华为&category=笔记本
        String url = "/search/list";
        if (searchMap != null && searchMap.size() > 0) {

            url += "?";
            for (Map.Entry<String, String> stringStringEntry : searchMap.entrySet()) {
                String key = stringStringEntry.getKey();// keywords
                String value = stringStringEntry.getValue();// 华为
                //如果是分页的话，实际上不需要再次拼接
                if(key.equals("pageNum")){
                    continue;
                }
                url+=key+"="+value+"&";
            }
            url = url.substring(0, url.length() - 1);
        }

        return url;
    }
 }
