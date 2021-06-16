package com.wenna4.ppp.controller;

import com.wenna4.ppp.Intf.DO.ResponseDO;
import com.wenna4.ppp.Intf.Entity.User;
import com.wenna4.ppp.Intf.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class HelloController {

    @Resource
    private UserService userService;

    @GetMapping("/hello/world")
    @ResponseBody
    public ResponseDO ShowHome(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        System.out.println("hello world");


        //String userName, String password
        String userName = "123";

        User user = userService.loadByName(userName);
        System.out.println(user.toString());

        return new ResponseDO(true, "success", 1, user);


    }

    @GetMapping("")
    public String ShowHome(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        // 1 判断用户是否已经登录，如果登录了，那么就返回首页
        return "index";
    }


}
