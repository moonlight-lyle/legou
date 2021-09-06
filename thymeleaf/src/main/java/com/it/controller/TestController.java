package com.it.controller;

import com.it.pojo.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.*;

@Controller
@RequestMapping("/test")
public class TestController {
    @RequestMapping("/index")
    public String showIndex(Model model){
        // 简单类型：key:value
        model.addAttribute("message","张三疯"); // 给resources下templates中index.html中的message设置值
        // pojo类型：
        Person person = new Person(1000L, "telangpu");
        model.addAttribute("person",person);
        // 输出集合
        List<Person> list=new ArrayList<>();
        list.add(new Person(1111L,"喜洋洋"));
        list.add(new Person(2222L,"光头强"));
        list.add(new Person(3333L,"灰太狼"));
        model.addAttribute("myList",list);
        // 输出map类型
        Map<String,Object> map=new HashMap<>();
        map.put("key1","value1");
        map.put("key2","value2");
        model.addAttribute("myMap",map);
        // 超链接拼接参数
        model.addAttribute("age",18);
        //名字
        model.addAttribute("name","zhangsanfeng");
        //日期
        model.addAttribute("date",new Date());
        return "index";
    }
}
