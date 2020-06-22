package com.paperfly.system.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class Modify implements Serializable {
    String pwd;
    String no;
    String newNo;
    String code;
    String userName;
}
