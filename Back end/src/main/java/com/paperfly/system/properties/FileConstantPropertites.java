package com.paperfly.system.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Component
public class FileConstantPropertites {
    public static long UnitM = 1048576l;
    private long fileMaxZize = 30l;
    private String fileStartSavePath = "d:/upload/";
    private String fileUploadSuccessInfo = "恭喜您文件上传成功!";
    private String fileUploadFaileInfo = "对不起，您的文件上传失败!可能超出了服务器承受范围!";
}
