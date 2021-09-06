package com.changgou.search.controller;

import com.changgou.search.service.SkuEsService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/search")
public class SkuEsController {

    @Autowired
    private SkuEsService skuService;

    /**
     * 导入数据
     * @return
     */
    @GetMapping("/import")
    public Result search(){
        skuService.importSku();
        return new Result(true, StatusCode.OK,"导入数据到索引库中成功！");
    }

    /**
     * 搜索 因为搜索条件众多：关键字、品牌、规格、分类等等，可以使用Map进行传递参数
     * @param searchMap：参数Map
     * @return 后台返回的数据也很多：分页信息，品牌、规格、分类以及列表数据等等，可以使用Map作为返回值
     */
//    @PostMapping
//    public Map search(@RequestBody(required = false) Map<String,String> searchMap){
//        return  skuService.search(searchMap);
//    }

    @GetMapping
    public Map search(@RequestParam(required = false) Map<String,String> searchMap){
        return  skuService.search(searchMap);
    }
}
