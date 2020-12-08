package com.paperfly.admin.controller;

import com.paperfly.admin.pojo.AdminUser;
import com.paperfly.admin.pojo.WebData;
import com.paperfly.admin.service.AdminUserService;
import com.paperfly.system.pojo.Blog;
import com.paperfly.system.pojo.Modify;
import com.paperfly.system.pojo.SignIn;
import com.paperfly.system.pojo.User;
import com.paperfly.system.properties.FileConstantPropertites;
import com.paperfly.system.properties.SmsProperties;
import com.paperfly.system.service.BlogService;
import com.paperfly.system.service.SignInService;
import com.paperfly.system.service.UserInfoService;
import com.paperfly.system.utils.DateFomatUtil;
import com.paperfly.system.utils.MD5Utils;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class AdminController {
    Logger logger= LoggerFactory.getLogger(getClass());
    @Autowired
    SmsProperties smsProperties;
    @Autowired
    SignInService signInService;
    @Autowired
    FileConstantPropertites fileConstantPropertites;
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    AdminUserService userService;
    @Autowired
    BlogService blogService;
    @PostMapping("adminModifySystemPropertites")
    @RequiresPermissions({"3"})
    @RequiresRoles({"3"})
    @RequiresAuthentication
    @ResponseBody
    public String admin(Long fileMaxZize,String fileStartSavePath){
        fileStartSavePath= fileStartSavePath.trim();
        if(fileMaxZize==null||fileMaxZize<=0){
        }else {
            fileConstantPropertites.setFileMaxZize(fileMaxZize);
        }
        if(!fileStartSavePath.equals("-1")){
            fileConstantPropertites.setFileStartSavePath(fileStartSavePath);
        }
        return "恭喜您修改成功!";
    }
    @PostMapping("adminModifyBlogPropertites")
    @RequiresPermissions({"3"})
    @RequiresRoles({"3"})
    @RequiresAuthentication
    @ResponseBody
    public String modifyBlogProperties(Long blogFileMaxSize,String blogStartFilePath){
        blogStartFilePath= blogStartFilePath.trim();
        if(blogFileMaxSize==null||blogFileMaxSize<=0){
        }else {
            fileConstantPropertites.setBlogFileMaxSize(blogFileMaxSize);
        }
        if(!blogStartFilePath.equals("-1")){
            fileConstantPropertites.setBlogStartFilePath(blogStartFilePath);
        }
        return "恭喜您修改成功!";
    }
    @PostMapping("adminModifyUserPassword")
    @ResponseBody
    @Transactional
    @RequiresPermissions({"3"})
    @RequiresRoles({"3"})
    @RequiresAuthentication
    public String modifyPassworrd(Modify modify, HttpSession session){
        Boolean isMember = redisTemplate.opsForSet().isMember("no", modify.getNo());
        if(!isMember){
            return "需要修改的用户不存在!";
        }
        modify.setPwd(MD5Utils.getMD5(modify.getPwd()));
        if(modify.getNo()==session.getAttribute("no")){
            session.setAttribute("no",modify.getNo());
        }
        String info = userInfoService.modifyPassworrd(modify);
        return info;
    }
    @GetMapping("toAdmin")
    @RequiresPermissions({"3"})
    @RequiresRoles({"3"})
    @RequiresAuthentication
    public String toAdmin(){
        return "views/admin/admin";
    }

    @PostMapping("modifyUserRole")
    @RequiresPermissions({"3"})
    @RequiresRoles({"3"})
    @RequiresAuthentication
    @ResponseBody
    public String modifyUserRole(AdminUser adminUser){
        String info = userService.modifyUserRole(adminUser);
        return info;
    }
    @PostMapping("modifyUserPerm")
    @RequiresPermissions({"3"})
    @RequiresRoles({"3"})
    @RequiresAuthentication
    @ResponseBody
    public String modifyUserPerm(AdminUser adminUser){
        String info = userService.modifyUserPerm(adminUser);
        return info;
    }
    @PostMapping("admin:selectAllUser")
    @RequiresPermissions(value = {"3","2"},logical = Logical.OR)
    @RequiresRoles(value = {"3","2"},logical = Logical.OR)
    @ResponseBody
    public List<User> selectAllUser(User user){
        user.setPage(user.getPage()*6);
        List<User> users = userInfoService.selectAllUser(user);
        return users;
    }
    @PostMapping("admin:selectAllUserSignIn")
    @RequiresPermissions(value = {"3","2"},logical = Logical.OR)
    @RequiresRoles(value = {"3","2"},logical = Logical.OR)
    @ResponseBody
    public List<SignIn> selectAllUserSignIn(SignIn signIn){
        if(signIn.getPage()<=0){
            signIn.setPage(0);
        }else {
            signIn.setPage(signIn.getPage()*12);
        }
        List<SignIn> signIns = userService.selectAllUserSignIn(signIn);
        log.info(signIns.toString());
        return signIns;
    }
    @GetMapping("toAdminSignIn")
    @RequiresPermissions(value = {"3","2"},logical = Logical.OR)
    @RequiresRoles(value = {"3","2"},logical = Logical.OR)
    @RequiresAuthentication
    public String toMySignIn(Map<String,Object> map, SignIn signIn){
        String startTime = DateFomatUtil.getCurrentYear()+"-"+DateFomatUtil.getCurrentMonth();
        String lastTime= DateFomatUtil.getCurrentYear()+"-"+(Integer.valueOf(DateFomatUtil.getCurrentMonth())+1);
        System.out.println(startTime+"|"+lastTime);
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM");
        try {
            Date parse1 = sdf2.parse(startTime);
            Date parse2 = sdf2.parse(lastTime);
            signIn.setStartTime(parse1);
            signIn.setLastTime(parse2);
            signIn.setNo(signIn.getNo());
        }catch (Exception e){
            logger.info(e.getMessage());
        }
        List<SignIn> signIns = signInService.selectSignInByNoAndTime(signIn);
        map.put("signIns",signIns);
        map.put("no",signIn.getNo());
        return "views/admin/signIn";
    }
    @PostMapping("admin:selectCurrentAccountOneMonthSignIn")
    @ResponseBody
    @RequiresPermissions(value = {"3","2"},logical = Logical.OR)
    @RequiresRoles(value = {"3","2"},logical = Logical.OR)
    public List<SignIn> selectCurrentAccountSignInByTime(SignIn signIn) {
        signIn.setNo(signIn.getNo());
        List<SignIn> signIns = signInService.selectCurrentAccountOneMonthSignIn(signIn);
        return signIns;
    }
    @GetMapping("toAdminSignInUser")
    @RequiresPermissions(value = {"3","2"},logical = Logical.OR)
    @RequiresRoles(value = {"3","2"},logical = Logical.OR)
    @RequiresAuthentication
    public String toAdminSignInUser(){
        return "views/admin/signUser";
    }
    @PostMapping("adminRegist")
    @RequiresPermissions(value = {"3"})
    @RequiresRoles(value = {"3"})
    @ResponseBody
    public String adminRegist(User user) {
        Date nowTime=new Date();
        nowTime= DateFomatUtil.fomatDate(nowTime);
        user.setCreateTime(nowTime);
        String info = userService.regist(user);
        return info;
    }
    @PostMapping("updataTemplateCode")
    @RequiresPermissions(value = {"3"})
    @RequiresRoles(value = {"3"})
    @ResponseBody
    public String updataTemplateCode(String  templateCode) {
        if(templateCode.equals("")){
            return "模板代码不能为空";
        }
        smsProperties.setTemplateCode(templateCode);
        return "修改成功";
    }
    @GetMapping("selectOurWebData")
    @RequiresPermissions(value = {"3"})
    @RequiresRoles(value = {"3"})
    public String selectOurWebData(Map<String,Object> map) {
        WebData webData = userService.selectOurWebData();
        map.put("webData",webData);
        return "views/admin/ourWebData";
    }

}
