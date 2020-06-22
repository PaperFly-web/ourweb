package com.paperfly.system.mapper;

import com.paperfly.system.pojo.Info;
import com.paperfly.system.pojo.User;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User login(User user);
    void regist(User user);
    String selectUserId(String no);
    Info getFilePath(Info file);
    String  isExist(String no);
}
