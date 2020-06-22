package com.paperfly.system.shiro;

import com.paperfly.system.pojo.User;
import com.paperfly.system.service.UserServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    UserServiceImpl userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取登录用户名
        Subject currentUser = SecurityUtils.getSubject();
//        currentUser.getSession().setTimeout(-1000L);//设置用户永不过期
        User user = (User) currentUser.getPrincipal();//这个user使用过下面的方法(验证密码)传的user
        //给url绑定角色和授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermission(user.getPerm());//把当前用户授权
        info.addRole(user.getRole());//给当前用户添加角色
        logger.info("开始授权"+user);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user = new User();
        user.setNo(token.getUsername());
        user.setPassword(String.valueOf(token.getPassword()));
        logger.info("config:认证:" + user);
        user = userService.login(user);
        if (user==null) {
            return null;
        }
        //密码验证是Shiro做，不能把密码暴露出来
        return new SimpleAuthenticationInfo(user, user.getPassword(), "UserRealm");
    }
}
