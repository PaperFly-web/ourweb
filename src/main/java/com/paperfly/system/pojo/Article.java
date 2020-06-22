package com.paperfly.system.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Component
@ToString
@Data
public class Article implements Serializable {
    Integer articleId;
    String article;
    String cno;
    String  no;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    Date articleCreateTime;
    String userName;

}
