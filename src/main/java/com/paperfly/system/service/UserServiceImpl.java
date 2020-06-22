package com.paperfly.system.service;

import com.paperfly.system.mapper.UserMapper;
import com.paperfly.system.pojo.User;
import com.paperfly.system.utils.IsAssignSizeUtil;
import com.paperfly.system.utils.MD5Utils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    SendSmsServiceImpl sendSmsServic;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    HttpSession session;
    Logger logger=LoggerFactory.getLogger(getClass());
    @Override
    public User login(User user) {
        logger.info("service传入的user"+user);
        User login = userMapper.login(user);
        logger.info("service:login:"+user);
        return login;
    }

    @Override
    public String regist(User user) {
        if(!IsAssignSizeUtil.size(user.getUserName(),15)){
            return "注册失败,用户名不能超过15个字符!";
        }
        //获取缓存中的验证码code:18852936583
        String code;
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
        //判断验证码是否一致
        try {//如果验证码不存在缓存中了，就会抛出异常，然后自己捕获就好了
            code = (String) redisTemplate.opsForValue().get("code:" + user.getNo());
            if (!code.equals(user.getCode())) {
                return "验证码错误!";
            }
        } catch (Exception e) {
            return "验证码已失效!";
        }

        user.setPassword(MD5Utils.getMD5(user.getPassword()));
        logger.info("controller:regist:" + user);
        if(user.getNo().equals("18852936583")){
            user.setRole("3");
            user.setPerm("3");
        }else {
            user.setRole("0");
            user.setPerm("0");
        }
        logger.info("service:regist:"+user);
        userMapper.regist(user);

        //在注册的时候，就把用户的相关信息在redis中创建好
        redisTemplate.opsForSet().add("no", user.getNo());
        return  "恭喜您已经注册成功,赶快登陆吧!";
    }
    public String isExist(String no){
        String isExist = userMapper.isExist(no);
        return isExist;
    }

    @Override
    public String send(String no,String prefix) {
        Boolean isNo = redisTemplate.opsForSet().isMember("no", no);
        if (isNo) {
            return "对不起,验证码发送失败,手机号已经被注册!";
        }
        //防止前端代码被破坏,后端再次拦截
        Object o = redisTemplate.opsForValue().get(prefix + no);
        if(o!=null){
            return "验证码已经发送过,还未过期!";
        }
        String code = UUID.randomUUID().toString().substring(0, 6);
        Map<String, String> map = new HashMap<>();
        map.put("code", code);
        boolean isSend = sendSmsServic.sendSms(no, map);
        if (isSend) {
            redisTemplate.opsForValue().set(prefix + no, code, 60, TimeUnit.SECONDS);
            return "成功";
        } else {
            return "对不起，验证码发送失败,请您在试一次!";
        }
    }

    @Override
    public String login(User user, Map<String,Object> map) {
        Boolean isStuNumExist = redisTemplate.opsForSet().isMember("no", user.getNo());
        if (!isStuNumExist) {
            map.put("msg", "手机号未注册!");
            logger.info("手机号未注册!");
            return "sign";
        }
        logger.info("controller:login:" + user);
        //然后获取到当前用户
        Subject currentUser = SecurityUtils.getSubject();
        //把密码和用户交给Shiro
        UsernamePasswordToken token = new UsernamePasswordToken(user.getNo(), MD5Utils.getMD5(user.getPassword()));//true代表记住我
        try {
            currentUser.login(token);
            session.setAttribute("no", user.getNo());
            return "views/success";
        } catch (UnknownAccountException e) {
            map.put("msg", "手机号或者密码不正确");
            return "sign";
        } catch (IncorrectCredentialsException ice) {
            map.put("msg", "手机号或者密码不正确");
            return "sign";
        } catch (Exception e) {
            map.put("msg", "登录失败");
            return "sign";
        }
    }
}
