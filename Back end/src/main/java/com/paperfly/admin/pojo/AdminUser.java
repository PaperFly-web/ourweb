package com.paperfly.admin.pojo;

import lombok.Data;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Data
@ToString
@Component
public class AdminUser {
    String role;
    String perm;
    String no;
}
