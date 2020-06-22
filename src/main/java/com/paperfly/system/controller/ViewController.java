package com.paperfly.system.controller;

import com.paperfly.system.pojo.User;
import com.paperfly.system.service.UserService;
import com.paperfly.system.utils.DateFomatUtil;
import com.paperfly.system.utils.RedisUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

@Controller
public class ViewController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    UserService userService;
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    HttpServletRequest request;

    @GetMapping({"/"})
    public String index() {
        return "sign";
    }

    @PostMapping("login")
    public String login(User user, Map<String,Object> map) {
        String login = userService.login(user, map);
        return login;
    }

    @PostMapping("regist")
    public String regist(User user, Map<String, String> map) {
        Date nowTime=new Date();
        nowTime= DateFomatUtil.fomatDate(nowTime);
        user.setCreateTime(nowTime);
        String info = userService.regist(user);
        map.put("msg",info);
        return "sign";
    }

    @GetMapping("/views/{name}")
    @RequiresAuthentication
    public String toViews(@PathVariable("name") String name) {
        return "views/" + name;
    }

    @RequestMapping("error/unauthorized")
    public String toUnauthorized() {
        return "error/unauthorized";
    }

    @RequestMapping("send")
    @ResponseBody
    public String sendSms(String no) {
        String info = userService.send(no,"code:");
        return info;
    }
}
