package com.paperfly.admin.mapper;

import com.paperfly.admin.pojo.AdminUser;
import com.paperfly.admin.pojo.WebData;
import com.paperfly.system.pojo.SignIn;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminUserMapper {
    void modifyUserRole(AdminUser adminUser);
    void modifyUserPerm(AdminUser adminUser);
    List<SignIn> selectAllUserSignIn(SignIn signIn);
    WebData selectOurWebData();
}
