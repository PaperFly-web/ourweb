package com.paperfly.system.controller;

import com.paperfly.system.pojo.Article;
import com.paperfly.system.pojo.Modify;
import com.paperfly.system.pojo.User;
import com.paperfly.system.service.UserInfoService;
import com.paperfly.system.service.UserService;
import com.paperfly.system.utils.DateFomatUtil;
import com.paperfly.system.utils.MD5Utils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class UserInfoController {
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    UserService userService;
    @Autowired
    HttpSession session;
    @Autowired
    RedisTemplate redisTemplate;
    @GetMapping("toArticle")
    public String toArticle(String cno, Map<String,String> map){
        map.put("articleCno",cno);
        map.put("currentNo", (String) session.getAttribute("no"));
        return "views/user/articles";
    }
    @GetMapping("toModifyUserInfo")
    public String toModifyUserInfo(Map<String,Object> map){
        String no = (String) session.getAttribute("no");
        String userName = userService.isExist(no);
        map.put("userName",userName);
        return "views/user/modifyUserInfo";
    }
    @GetMapping("/returnClass/{cno}")
    public String returnClass(@PathVariable("cno") String cno,Map<String,String> map){
        map.put("createCno",cno);
        map.put("createCname", (String) session.getAttribute("cname"));
        map.put("createNo", (String) session.getAttribute("no"));
        map.put("createName", (String) session.getAttribute("createName"));
        return "views/file/file";
    }
    @PostMapping("addArticle")
    @ResponseBody
    @Transactional
    public List<Article> addArticle(Article article){
        Date nowTime=new Date();
        nowTime=DateFomatUtil.fomatDate(nowTime);
        article.setNo((String) session.getAttribute("no"));
        article.setArticle(article.getArticle().trim());
        article.setArticleCreateTime(nowTime);
        userInfoService.addArticle(article);
        List<Article> articles = userInfoService.selectArticleByCno(article.getCno());
        return articles;
    }
    @PostMapping("selectArticleByCno")
    @ResponseBody
    public List<Article> selectArticleByCno(String cno,Map<String,String> map){
        List<Article> articles = userInfoService.selectArticleByCno(cno);
        map.put("currentNo", (String) session.getAttribute("no"));
        return articles;
    }

    @PostMapping("modifyUserPassword")
    @ResponseBody
    @Transactional
    public String modifyPassworrd(Modify modify){
        modify.setNo((String) session.getAttribute("no"));
        modify.setPwd(MD5Utils.getMD5(modify.getPwd()));
        String info = userInfoService.modifyPassworrd(modify);
        return info;
    }
    @GetMapping("modifyNoSend")
    @ResponseBody
    public String sendSms(String no) {
        String info = userService.send(no,"modifyNoCode:");
        return info;
    }
    @PostMapping("modifyNo")
    @ResponseBody
    @Transactional
    @RequiresAuthentication
    public String modifyNo(Modify modify){
        String info = userInfoService.modifyNo(modify);
        return info;
    }

    @PostMapping("modifyUserName")
    @ResponseBody
    @Transactional
    @RequiresAuthentication
    public String modifyUserName(Modify modify){
        String info = userInfoService.modifyUserName(modify);
        return info;
    }
    @PostMapping("selectAllUser")
    @ResponseBody
    @RequiresAuthentication
    public List<User> selectAllUser(User user){
        user.setPage(user.getPage()*6);
        List<User> users = userInfoService.selectAllUser(user);
        return users;
    }

}
