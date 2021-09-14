package com.changgou.oauth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/***
 * 描述
 * @author
 * @packagename com.changgou.oauth.controller
 * @version 1.0
 * @date 2020/3/31
 */
@Controller
@RequestMapping("/oauth")
public class LoginRedirect {

    @RequestMapping("/login")
    public String login(String From, Model model){
        model.addAttribute("from",From);
        return "login";
    }
}