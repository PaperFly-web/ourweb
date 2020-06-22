package com.paperfly.admin.service;

import com.paperfly.admin.pojo.AdminUser;

public interface AdminUserService {
    String modifyUserRole(AdminUser adminUser);
    String modifyUserPerm(AdminUser adminUser);
}
