package com.paperfly.admin.service;

import com.paperfly.admin.mapper.AdminUserMapper;
import com.paperfly.admin.pojo.AdminUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class AdminUserServiceImpl implements AdminUserService {
    @Autowired
    AdminUserMapper userMapper;
    @Autowired
    RedisTemplate redisTemplate;
    @Override
    public String modifyUserRole(AdminUser adminUser) {
        Boolean isMember = redisTemplate.opsForSet().isMember("no", adminUser.getNo());
        if(!isMember){
            return "需要修改的用户不存在";
        }else {
            userMapper.modifyUserRole(adminUser);
            return "修改成功!";
        }
    }

    @Override
    public String modifyUserPerm(AdminUser adminUser) {
        Boolean isMember = redisTemplate.opsForSet().isMember("no", adminUser.getNo());
        if(!isMember){
            return "需要修改的用户不存在";
        }else {
            userMapper.modifyUserPerm(adminUser);
            return "修改成功!";
        }
    }
}
