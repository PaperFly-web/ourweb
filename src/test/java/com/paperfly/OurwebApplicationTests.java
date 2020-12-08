package com.paperfly;

import com.paperfly.system.mapper.SignInMapper;
import com.paperfly.system.mapper.UserInfoMapper;
import com.paperfly.system.pojo.Article;
import com.paperfly.system.pojo.SignIn;
import com.paperfly.system.utils.DateFomatUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@SpringBootTest
class OurwebApplicationTests {
    @Autowired
    SignInMapper signInMapper;
    @Test
    void contextLoads() {
        SignIn signIn=new SignIn();
        String startTime = DateFomatUtil.getCurrentYear()+"-"+DateFomatUtil.getCurrentMonth();
        String lastTime= DateFomatUtil.getCurrentYear()+"-"+(Integer.valueOf(DateFomatUtil.getCurrentMonth())+1);
        System.out.println(startTime+"|"+lastTime);
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM");
        try {
            Date parse1 = sdf2.parse(startTime);
            Date parse2 = sdf2.parse(lastTime);
            signIn.setStartTime(parse1);
            signIn.setLastTime(parse2);
            signIn.setNo("18852936583");
        }catch (Exception e){
            e.printStackTrace();
        }
        List<SignIn> signIns = signInMapper.selectSignInByNoAndTime(signIn);
        for (SignIn in : signIns) {
            System.out.println(in);
        }
    }

}
