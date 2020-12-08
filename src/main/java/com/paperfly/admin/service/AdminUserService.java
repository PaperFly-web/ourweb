package com.paperfly.admin.service;

import com.paperfly.admin.pojo.AdminUser;
import com.paperfly.admin.pojo.WebData;
import com.paperfly.system.pojo.SignIn;
import com.paperfly.system.pojo.User;

import java.util.List;

public interface AdminUserService {
    String modifyUserRole(AdminUser adminUser);
    String modifyUserPerm(AdminUser adminUser);
    List<SignIn> selectAllUserSignIn(SignIn signIn);
    String regist(User user);
    WebData selectOurWebData();
}
