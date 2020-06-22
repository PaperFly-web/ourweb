package com.paperfly.system.controller;

import com.paperfly.system.pojo.Class;
import com.paperfly.system.pojo.Task;
import com.paperfly.system.service.ClassService;
import com.paperfly.system.service.FileServiceImpl;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;


@Controller
public class ServiceController {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    FileServiceImpl fileService;
    @Autowired
    ClassService classService;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    HttpServletRequest request;
    @Autowired
    HttpSession session;
    @GetMapping("createClass")
    @RequiresAuthentication
    @RequiresPermissions(value = {"2","3"},logical = Logical.OR)
    @RequiresRoles(value = {"2","3"},logical = Logical.OR)
    @Transactional
    public String createClass(Class c, Map<String, String> map) {
        HttpSession session = request.getSession();
        String info = classService.createClass(c, session);
        map.put("msg",info);
        return "views/success";
    }
    @GetMapping("createTask")
    @RequiresAuthentication
    @RequiresPermissions(value = {"2","3"},logical = Logical.OR)
    @RequiresRoles(value = {"2","3"},logical = Logical.OR)
    @Transactional
    public String createTask(Task t, Map<String, String> map) {
        String cno=t.getCno();
        String cname=t.getCname();
        map.put("createCno",cno);
        map.put("createCname",cname);
        String info = classService.createTask(t);
        map.put("msg",info);
        return "views/file/file-myClass";
    }

    @GetMapping("joinClass")
    @RequiresAuthentication
    @Transactional
    public String joinClass(Class c, Map<String, String> map) {
        HttpSession session = request.getSession();
        String info = classService.joinClass(c, session);
        map.put("msg", info);
        return "views/success";
    }

    @RequestMapping("selectJoinClass")
    @ResponseBody
    @RequiresAuthentication
    public List<Class> selectJoinClass() {
        logger.info("Controller:selectJoinClass:ajax进入来了");
        HttpSession session = request.getSession();
        List<Class> aClass = classService.selectJoinClass((String) session.getAttribute("no"));
        logger.info("Controller:selectJoinClass:" + aClass);
        return aClass;
    }

    @RequestMapping("selectCreateClass")
    @ResponseBody
    @RequiresAuthentication
    public List<Class> selectCreateClass() {
        HttpSession session = request.getSession();
        List<Class> aClass = classService.selectCreateClass((String) session.getAttribute("no"));
        return aClass;
    }
    @RequestMapping("selectCreateTask")
    @ResponseBody
    @RequiresAuthentication
    public List<Task> selectCreateTask(String cno) {
        List<Task> createTask = classService.selectCreateTask(cno);
        return createTask;
    }

}
