package com.changgou.goods.controller;

import com.changgou.goods.pojo.Brand;
import com.changgou.goods.service.BrandService;
import com.github.pagehelper.PageInfo;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    /**
     * 查询所有品牌
     * @return
     */
    @GetMapping
    public Result<List<Brand>> findAll(){
        List<Brand> brandList = brandService.findAll();
        return new Result<List<Brand>>(true, StatusCode.OK,"查询所有品牌列表成功",brandList);
    }

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Brand> findById(@PathVariable(name = "id") Integer id){
        Brand brand=brandService.findById(id);
        return new Result<>(true,StatusCode.OK,"根据id查询成功",brand);
    }

    /**
     * 新增品牌
     * @param brand
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Brand brand){ // @RequestBody将前端传递的json数据转换成pojo
        brandService.add(brand);
        return new Result(true,StatusCode.OK,"添加品牌成功",brand);
    }

    /**
     * 更新品牌
     * @param id
     * @param brand
     * @return
     */
    @PutMapping("/{id}")
    public Result update(@PathVariable(name = "id") Integer id,@RequestBody Brand brand){
        brand.setId(id);
        brandService.update(brand);
        return new Result(true,StatusCode.OK,"更新品牌成功",brand);
    }

    /**
     * 删除品牌
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable(name = "id") Integer id){
        brandService.delete(id);
        return new Result(true,StatusCode.OK,"删除品牌成功");
    }

    /**
     * 根据条件搜索品牌
     * @param brand
     * @return
     */
    @PostMapping("/search")
    public Result<List<Brand>> search(@RequestBody Brand brand){
        List<Brand> brandList=brandService.findList(brand);
        return new Result<List<Brand>>(true, StatusCode.OK,"条件搜索列表成功",brandList);
    }

    /**
     * 分页查询（无搜索条件）
     * @param page 当前页码
     * @param size 每页展示多少行数据
     * @return
     */
    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo<Brand>> findPage(@PathVariable(name = "page") Integer page,
                                            @PathVariable(name = "size") Integer size){
        PageInfo<Brand> pageInfo=brandService.findPage(page,size);
        return new Result<PageInfo<Brand>>(true, StatusCode.OK,"分页查询成功",pageInfo);
    }

    /**
     * 有条件的分页查询
     * @param brand 接收条件搜索
     * @param page 当前页码
     * @param size 每页展示多少行数据
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo<Brand>> findPage(@RequestBody Brand brand,
                                            @PathVariable(name = "page") Integer page,
                                            @PathVariable(name = "size") Integer size){
        PageInfo<Brand> pageInfo=brandService.findPage(brand,page,size);
        int i=1/0;
        return new Result<PageInfo<Brand>>(true, StatusCode.OK,"分页查询成功",pageInfo);
    }

    /**
     * 根据前端传递的分类id查询品牌分类
     * select tbb.* from tb_brand tbb,tb_category_brand tbc where tbb.id=tbc.brand_id and tbc.category_id=76;
     * @param categoryId
     * @return
     */
    @GetMapping("/category/{id}")
    public Result<List<Brand>> findBrandByCategory(@PathVariable(name = "id") Integer categoryId){ // @PathVariable(name = "id")中name指定的名称要和 @GetMapping("/category/{id}")中的一致
        List<Brand> categoryList = brandService.findByCategory(categoryId);
        return new Result<List<Brand>>(true,StatusCode.OK,"查询成功！",categoryList);
    }
}
