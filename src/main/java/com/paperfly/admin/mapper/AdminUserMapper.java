package com.paperfly.admin.mapper;

import com.paperfly.admin.pojo.AdminUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminUserMapper {
    void modifyUserRole(AdminUser adminUser);
    void modifyUserPerm(AdminUser adminUser);
}
