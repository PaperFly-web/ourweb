package com.paperfly.system.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Data
@Component
@ToString
public class Info implements Serializable {
    Integer userId;
    Integer fileId;
    String filePath;
    String fileName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    String createTime;
    String no;
    Long size;
    String cno;
    String cname;
    Integer classTaskId;
    String taskName;
    String userName;
    Integer page;
}
