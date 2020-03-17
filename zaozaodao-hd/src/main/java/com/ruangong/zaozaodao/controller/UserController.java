package com.ruangong.zaozaodao.controller;

import com.ruangong.zaozaodao.domain.User;
import com.ruangong.zaozaodao.domain.Result;
import com.ruangong.zaozaodao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.Objects;

@Controller
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserService userService;

    @CrossOrigin
    @PostMapping(value = "/login")
    @ResponseBody   // 将方法返回的对象通过适当的转换器转换为指定的格式之后，写入到response对象的body区
    public Result login(@RequestBody User requestUser) {

        // 对 html 标签进行转义，防止 XSS 攻击
        String username = requestUser.getUsername();
        username = HtmlUtils.htmlEscape(username);
        System.out.println("test");
        User user = userService.get(username, requestUser.getPassword());
        if (null == user) {
            return new Result(400);
        } else {
            return new Result(200);
        }
    }
}
