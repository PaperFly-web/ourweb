package com.paperfly.system.service;

import com.paperfly.system.pojo.Info;
import com.paperfly.system.pojo.Task;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface FileService {
    void uploadFile(Task task, MultipartFile file, Map<String,Object> map, HttpSession session);
    List<Info> selectThisClassFile(Info fileInfo);
    void downAllMyClassFile(String cno,String classTaskId, HttpServletResponse response);
    void deleteOneFile(String deletePath);
 /*   List<Info> selectAllMyClassFile(String cno);*/
    String toFile(Info info,Map<String,String> map);
    List<Info> selectTaskFile(Info info);
}
