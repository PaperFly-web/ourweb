package com.paperfly.admin.pojo;

import lombok.Data;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@ToString
@Component
public class AdminUser implements Serializable {
    String role;
    String perm;
    String no;
}
