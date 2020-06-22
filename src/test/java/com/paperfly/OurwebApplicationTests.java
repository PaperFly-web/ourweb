package com.paperfly;

import com.paperfly.system.mapper.UserInfoMapper;
import com.paperfly.system.pojo.Article;
import com.paperfly.system.utils.DateFomatUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
class OurwebApplicationTests {
    @Autowired
    UserInfoMapper userInfoMapper;
    @Test
    void contextLoads() {
        List<Article> articles = userInfoMapper.selectArticleByCno("78f6dc77");
        Article article = articles.get(0);
        Date articleCreateTime = article.getArticleCreateTime();
        Date date = DateFomatUtil.fomatDate(articleCreateTime);
        System.out.println(date);
    }

}
