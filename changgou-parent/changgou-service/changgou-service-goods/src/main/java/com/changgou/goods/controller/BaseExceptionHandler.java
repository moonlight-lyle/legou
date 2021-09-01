package com.changgou.goods.controller;

import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice // 用来标识该类是一个异常处理类
public class BaseExceptionHandler {

    // 定义一个方法，用于捕获被@GetMapping/@PostMapping/@RequestMapping修饰的方法异常并进行统一处理
    @ExceptionHandler(ArithmeticException.class) // 指定异常，发生ArithmeticException异常才执行该方法，可以不配置，不配置默认所有异常都触发该方法
    @ResponseBody // 返回json格式数据
    public Result exceptionHandler(Exception e){
        System.out.println("方法执行异常");
        return new Result(false, StatusCode.ERROR,e.getMessage());
    }
}
