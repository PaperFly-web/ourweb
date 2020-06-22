package com.paperfly.system.service;

import com.paperfly.system.pojo.Article;
import com.paperfly.system.pojo.Modify;

import java.util.List;

public interface UserInfoService {
    void addArticle(Article article);
    List<Article> selectArticleByCno(String cno);
    String  modifyPassworrd(Modify modify);
    String modifyNo(Modify modify);
    String modifyUserName(Modify modify);
}
