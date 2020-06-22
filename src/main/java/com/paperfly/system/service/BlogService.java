package com.paperfly.system.service;

import com.paperfly.system.pojo.Task;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.Map;

public interface BlogService {
    void uploadFile(Task task, MultipartFile file, Map<String,Object> map, HttpSession session);
}
