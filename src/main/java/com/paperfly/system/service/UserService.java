package com.paperfly.system.service;

import com.paperfly.system.pojo.User;
import org.springframework.ui.Model;

import java.util.Map;

public interface UserService {
    User login(User user);
    String  regist(User user);
    String isExist(String no);
    String send(String no,String prefix);
    String login(User user, Map<String,Object> map);
}
