package com.paperfly.admin.service;

import com.paperfly.admin.mapper.AdminUserMapper;
import com.paperfly.admin.pojo.AdminUser;
import com.paperfly.admin.pojo.WebData;
import com.paperfly.system.mapper.UserMapper;
import com.paperfly.system.pojo.SignIn;
import com.paperfly.system.pojo.User;
import com.paperfly.system.utils.IsAssignSizeUtil;
import com.paperfly.system.utils.MD5Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminUserServiceImpl implements AdminUserService {
    @Autowired
    AdminUserMapper adminUserMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    RedisTemplate redisTemplate;
    Logger logger= LoggerFactory.getLogger(getClass());
    @Override
    public String modifyUserRole(AdminUser adminUser) {
        Boolean isMember = redisTemplate.opsForSet().isMember("no", adminUser.getNo());
        if(!isMember){
            return "需要修改的用户不存在";
        }else {
            adminUserMapper.modifyUserRole(adminUser);
            return "修改成功!";
        }
    }

    @Override
    public String modifyUserPerm(AdminUser adminUser) {
        Boolean isMember = redisTemplate.opsForSet().isMember("no", adminUser.getNo());
        if(!isMember){
            return "需要修改的用户不存在";
        }else {
            adminUserMapper.modifyUserPerm(adminUser);
            return "修改成功!";
        }
    }

    @Override
    public List<SignIn> selectAllUserSignIn(SignIn signIn) {
        List<SignIn> signIns = adminUserMapper.selectAllUserSignIn(signIn);
        return signIns;
    }
    @Override
    public String regist(User user) {
        Boolean isNo = redisTemplate.opsForSet().isMember("no", user.getNo());
        if (isNo) {
            return "手机号已经被注册!";
        }
        if(!IsAssignSizeUtil.size(user.getUserName(),15)){
            return "注册失败,用户名不能超过15个字符!";
        }
        String no = user.getNo();
        String password = user.getPassword();
        //防止前端代码被破坏，后端再次验证
        boolean matches = no.matches("^[1](([3][0-9])|([4][5-9])|([5][0-3,5-9])|([6][5,6])|([7][0-8])|([8][0-9])|([9][1,8,9]))[0-9]{8}$");
        boolean matches1 = password.matches("^\\S{6,}$");
        if (!matches) {
            return   "您输入的不是手机号!";
        } else if (!matches1) {
            return "密码不符合规定!";
        }

        user.setPassword(MD5Utils.getMD5(user.getPassword()));
        user.setRole("0");
        user.setPerm("0");
        logger.info("adminController:regist:" + user);
        userMapper.regist(user);
        //在注册的时候，就把用户的相关信息在redis中创建好
        redisTemplate.opsForSet().add("no", user.getNo());
        return  "恭喜您已经注册成功,赶快登陆吧!";
    }

    @Override
    public WebData selectOurWebData() {
        WebData webData = adminUserMapper.selectOurWebData();
        return webData;
    }

}
