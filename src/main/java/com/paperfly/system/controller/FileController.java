package com.paperfly.system.controller;

import com.paperfly.system.pojo.Info;
import com.paperfly.system.pojo.Task;
import com.paperfly.system.properties.FileConstantPropertites;
import com.paperfly.system.service.ClassService;
import com.paperfly.system.service.FileServiceImpl;
import com.paperfly.system.service.UserService;
import java.net.URLEncoder;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
public class FileController {
    Logger logger= LoggerFactory.getLogger(getClass());
    @Autowired
    FileServiceImpl fileService;
    @Autowired
    UserService userService;
    @Autowired
    ClassService classService;
    @Autowired
    HttpServletRequest request;
    @Autowired
    HttpSession session;
    @Autowired
    FileConstantPropertites constantPropertites;
    @Autowired
    RedisTemplate redisTemplate;
    //上传文件
    @Transactional
    @PostMapping("upload")
    @RequiresAuthentication
    public String upload(Task task, @RequestParam("file") MultipartFile file, Map<String,Object> map) throws IOException {
        HttpSession session = request.getSession();
        fileService.uploadFile(task,file,map,session);
        map.put("createCno",task.getCno());
        map.put("createCname",task.getCname());
        map.put("taskName",task.getTaskName());
        map.put("createName",task.getUserName());
        map.put("createNo",task.getNo());
        map.put("classTaskId", String.valueOf(task.getClassTaskId()));
        map.put("fileMaxSize",constantPropertites.getFileMaxZize());
        return "views/file/file-task";
    }

    //下载文件
    @PostMapping("download")
    @RequiresAuthentication
    public void download(String filePath,String fileName,HttpServletResponse response){
        try {
            //给浏览器传递的一些信息
            response.setHeader("Content-Disposition", "attachment;filename="+
                    URLEncoder.encode(fileName,"UTF-8"));
            //获取文件
            FileInputStream in = new FileInputStream(filePath);
            byte[] buffer = new byte[1024*1024];
            //获取响应头的流
            ServletOutputStream out = response.getOutputStream();
            int len=0;
            //把文件写入响应头的流中
            while ((len=in.read(buffer))!=-1){
                out.write(buffer,0,len);
                out.flush();
            }
            in.close();
            out.close();
        }catch (Exception e){
            logger.debug(e.getMessage());
        }
    }

    @PostMapping("downloadAll")
    @RequiresAuthentication
    @RequiresRoles(value = {"3","2"},logical = Logical.OR)
    public void downloadAll(String cno,String classTaskId,HttpServletResponse response){
        fileService.downAllMyClassFile(cno,classTaskId,response);
    }

    @GetMapping("file/{cno}/{no}/{cname}")
    @RequiresAuthentication
    public String toFile(@PathVariable("cno") String cno, @PathVariable("no")String no,
                         @PathVariable String cname, Map<String,String> map){
        Info info=new Info();
        info.setCno(cno);
        info.setCname(cname);
        info.setNo(no);
        info.setCname(cname);
        String path = fileService.toFile(info, map);
        return path;
    }
    @GetMapping("task")
    @RequiresAuthentication
    public String toTask(Info task, Map<String,Object> map){
        task.setPage(task.getPage()*12);
        List<Info> infos = fileService.selectTaskFile(task);
        if(infos.size()==0){
            map.put("isNull","");
        }else {
            map.put("isNull","123");
        }
        map.put("createCno",task.getCno());
        map.put("createCname",task.getCname());
        map.put("taskName",task.getTaskName());
        map.put("classTaskId",task.getClassTaskId());
        map.put("myFile",infos);

        return  "views/file/file-myClassTask";
    }

    @GetMapping("myClassTask")
    @RequiresAuthentication
    @ResponseBody
    @RequiresRoles(value = {"2","3"},logical = Logical.OR)
    public List<Info> myClassTask(Info task, Map<String,Object> map){
        task.setPage(task.getPage()*12);
        List<Info> infos = fileService.selectTaskFile(task);
        if(infos.size()==0){
            map.put("isNull","");
        }else {
            map.put("isNull","123");
        }
        logger.info("myClassTask:"+infos);
        return  infos;
    }

    @GetMapping("myTask")
    @RequiresAuthentication
    public String toMyTask( Task task, Map<String,Object> map){
        map.put("createCno",task.getCno());
        map.put("createCname",task.getCname());
        map.put("taskName",task.getTaskName());
        map.put("createName",task.getUserName());
        map.put("createNo",task.getNo());
        map.put("classTaskId",task.getClassTaskId());
        map.put("fileMaxSize",constantPropertites.getFileMaxZize());
        return "views/file/file-task";
    }
    @GetMapping("/returnMyCreateClass")
    @RequiresAuthentication
    public String returnMyCreateClass(Task task,Map<String,String> map){
        map.put("createCno",task.getCno());
        map.put("createCname", task.getCname());
        return "views/file/file-myClass";
    }
    @GetMapping({"file/{cno}/{cname}"})
    @RequiresAuthentication
    public String toMyClass(@PathVariable("cno") String cno,
                         @PathVariable String cname, Map<String,Object> map){
        HttpSession session = request.getSession();
        //班级信息从这里开始
        session.setAttribute("cno",cno);
        session.setAttribute("cname",cname);
        map.put("createCno",cno);
        map.put("createCname",cname);
        return "views/file/file-myClass";
    }


    @RequiresAuthentication
    @GetMapping("selectThisClassFile")
    @ResponseBody
    public List<Info>  selectThisClassFile(Info task){
        HttpSession session = request.getSession();
        task.setNo((String) session.getAttribute("no"));
        task.setPage(task.getPage()*12);
        List<Info> fileInfos = fileService.selectThisClassFile(task);
        logger.info("controller:selectThisClassFile"+fileInfos);
        return fileInfos;
    }

    @RequiresAuthentication
    @GetMapping("deleteOneFile")
    public String deleteOneFile(Info info,Map<String,Object> map){
        map.put("createCno",info.getCno());
        map.put("createCname",info.getCname());
        map.put("createNo",info.getNo());
        map.put("taskName",info.getTaskName());
        map.put("createName",info.getUserName());
        map.put("classTaskId",info.getClassTaskId());
        map.put("fileMaxSize",constantPropertites.getFileMaxZize());

        fileService.deleteOneFile(info.getFilePath());
        return "views/file/file-task";
    }
}
