package com.paperfly.system.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Data
@ToString
public class Blog implements Serializable {
    @ApiModelProperty("用户手机号")
    String no;
    @ApiModelProperty("博客文件id")
    String blogFileId;
    @ApiModelProperty("博客文件保存地址")
    String blogFilePath;
    @ApiModelProperty("博客创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    String createTime;
    @ApiModelProperty("博客文件名称")
    String blogFileName;
    @ApiModelProperty("博客标题")
    String blogTitle;
    @ApiModelProperty("是否公开(1,代表公开。0代表不公开)")
    String isOpen;
    @ApiModelProperty("博客文件大小")
    Long size;
    @ApiModelProperty("分页查询的页数,从0开始")
    Integer page;
    String userName;
    Integer views;
}
