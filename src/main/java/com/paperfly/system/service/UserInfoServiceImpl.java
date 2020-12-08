package com.paperfly.system.service;

import com.paperfly.system.mapper.UserInfoMapper;
import com.paperfly.system.pojo.Article;
import com.paperfly.system.pojo.Modify;
import com.paperfly.system.pojo.User;
import com.paperfly.system.utils.IsAssignSizeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.List;

@Component
public class UserInfoServiceImpl implements UserInfoService {
    Logger logger= LoggerFactory.getLogger(getClass());
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    HttpSession session;
    @Autowired
    RedisTemplate redisTemplate;
    @Override
    public void addArticle(Article article) {
        userInfoMapper.addArticle(article);
    }

    @Override
    public List<Article> selectArticleByCno(String cno) {
        List<Article> articles = userInfoMapper.selectArticleByCno(cno);
        return articles;
    }

    @Override
    public String modifyPassworrd(Modify modify) {
        try{
            userInfoMapper.modifyPassworrd(modify);
            return "恭喜您,密码更新完成!";
        }catch (Exception e){
            logger.info("密码更新失败:"+e.getMessage());
            return "密码更新失败!";
        }

    }

    @Override
    public String modifyNo(Modify modify) {
        modify.setNo((String) session.getAttribute("no"));
        String code;
        String newNo=modify.getNewNo();
        //防止前端代码被破坏，后端再次验证
        boolean matches = newNo.matches("^[1](([3][0-9])|([4][5-9])|([5][0-3,5-9])|([6][5,6])|([7][0-8])|([8][0-9])|([9][1,8,9]))[0-9]{8}$");
        if (!matches) {
            return "您输入的不是手机号!";
        }
        //判断验证码是否一致
        try {//如果验证码不存在缓存中了，就会抛出异常，然后自己捕获就好了
            code = (String) redisTemplate.opsForValue().get("modifyNoCode:" + modify.getNewNo());
            if (!code.equals(modify.getCode())) {
                return "验证码错误!";
            }
        } catch (Exception e) {
            return "验证码已失效!";
        }
        userInfoMapper.modifyNo(modify);
        //修改redis缓存的手机号
        redisTemplate.opsForSet().remove("no",modify.getNo());
        redisTemplate.opsForSet().add("no",modify.getNewNo());
        redisTemplate.rename("createCname:"+modify.getNo(),"createCname:"+modify.getNewNo());
        redisTemplate.rename("joinCno:"+modify.getNo(),"joinCno:"+modify.getNewNo());
        //把会话中的账号也修改一下
        session.setAttribute("no",modify.getNewNo());
        return "恭喜您,手机号更新完成!";
    }

    @Override
    public String modifyUserName(Modify modify) {
        if(!IsAssignSizeUtil.size(modify.getUserName(),15)){
            return "用户名修改失败,不能超过15个字符!";
        }
        modify.setNo((String) session.getAttribute("no"));
        userInfoMapper.modifyUserName(modify);
        return "恭喜您,用户名修改成功!";
    }

    @Override
    public List<User> selectAllUser(User user) {
        List<User> users = userInfoMapper.selectAllUser(user);
        return users;
    }
}
