package com.paperfly.system.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Component
public class Task implements Serializable {
    Integer classTaskId;
    String cno;
    String taskName;
    String cname;
    String userName;
    String no;
}
