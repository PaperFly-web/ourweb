package com.paperfly.system.mapper;

import com.paperfly.system.pojo.Article;
import com.paperfly.system.pojo.Modify;
import com.paperfly.system.pojo.User;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserInfoMapper {
    void addArticle(Article article);
    List<Article> selectArticleByCno(String cno);
    void modifyPassworrd(Modify modify);
    void modifyNo(Modify modify);
    void modifyUserName(Modify modify);
    List<User> selectAllUser(User user);
}
