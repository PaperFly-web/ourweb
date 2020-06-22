package com.paperfly.admin.controller;

import com.paperfly.admin.pojo.AdminUser;
import com.paperfly.admin.service.AdminUserService;
import com.paperfly.system.pojo.Modify;
import com.paperfly.system.properties.FileConstantPropertites;
import com.paperfly.system.service.UserInfoService;
import com.paperfly.system.utils.MD5Utils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class AdminController {
    @Autowired
    FileConstantPropertites fileConstantPropertites;
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    AdminUserService userService;
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
        if(!fileStartSavePath.equals("")){
            fileConstantPropertites.setFileStartSavePath(fileStartSavePath);
        }
        return "恭喜您修改成功!";
    }
    @PostMapping("/adminModifyUserPassword")
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
    @RequestMapping("toAdmin")
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
}
