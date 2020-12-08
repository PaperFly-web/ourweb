package com.paperfly.system.service;

import com.paperfly.system.mapper.ClassMapper;
import com.paperfly.system.mapper.FileMapper;
import com.paperfly.system.pojo.Info;
import com.paperfly.system.pojo.Task;
import com.paperfly.system.properties.FileConstantPropertites;
import com.paperfly.system.utils.ZipUtils;
import java.net.URLEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service
public class FileServiceImpl implements FileService {
    @Autowired
    FileMapper fileMapper;
    @Autowired
    ClassMapper classMapper;
    @Autowired
    UserService userService;
    @Autowired
    HttpSession session;
    @Autowired
    FileConstantPropertites constantPropertites;
    Logger logger= LoggerFactory.getLogger(getClass());
    @Override
    public void uploadFile(Task task, MultipartFile file, Map<String,Object> map,HttpSession session) {
        String fileType;
        Info fileInfo=new Info();
        long size = file.getSize();
        if(size>constantPropertites.getFileMaxZize()*constantPropertites.UnitM){
            map.put("fileMsg","对不起,您的文件上传失败！文件大于" + constantPropertites.getFileMaxZize() + "M");
            return ;
        }else if(size==0){
            map.put("fileMsg","上传失败,文件大小不能为空!");
            return;
        }
        // 获取原始名字
        String fileName = file.getOriginalFilename();
        fileType=fileName.substring(fileName.lastIndexOf("."));
        // 文件保存路径
        String filePath = constantPropertites.getFileStartSavePath()+task.getCno()+"/"+session.getAttribute("no")+"/";
        // 文件重命名，防止重复
        filePath = filePath + UUID.randomUUID()+UUID.randomUUID()+fileType;
        System.out.println(filePath);
        // 文件对象
        File dest = new File(filePath);
        // 判断路径是否存在，如果不存在则创建
        if(!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            // 保存到服务器中
            file.transferTo(dest);
            //保存成功后把文件信息放到数据库中
            fileInfo.setFileName(fileName);
            fileInfo.setFilePath(filePath);
            fileInfo.setSize(size);
            fileInfo.setNo((String) session.getAttribute("no"));
            fileInfo.setCno(task.getCno());
            fileInfo.setClassTaskId(task.getClassTaskId());
            fileMapper.addFileInfo(fileInfo);
            //返回给前端的信息
            map.put("fileMsg",constantPropertites.getFileUploadSuccessInfo());
            return ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("fileMsg",constantPropertites.getFileUploadFaileInfo());
    }

    @Override
    public List<Info> selectThisClassFile(Info fileInfo) {
        List<Info> fileInfos = fileMapper.selectThisClassFile(fileInfo);
        return fileInfos;
    }

    @Override
    public void downAllMyClassFile(String cno,String classTaskId, HttpServletResponse response) {
        Info task=new Info();
        task.setClassTaskId(Integer.valueOf(classTaskId));
        task.setNo((String) session.getAttribute("no"));
        String zipFilePath = ZipUtils.createZipAndReturnPath(fileMapper.downAllMyTaskFile(task), cno);
        ServletOutputStream out=null;
        FileInputStream in=null;
        File file=new File(zipFilePath);
        try {
            //给浏览器传递的一些信息
            response.setHeader("Content-Disposition", "attachment;filename="+
                    URLEncoder.encode(file.getName(),"UTF-8"));
            //获取文件
            in = new FileInputStream(zipFilePath);
            byte[] buffer = new byte[1024*1024];
            //获取响应头的流
            out = response.getOutputStream();
            int len=0;
            //把文件写入响应头的流中
            while ((len=in.read(buffer))!=-1){
                out.write(buffer,0,len);
                out.flush();
            }

        }catch (Exception e){
            logger.debug(e.getMessage());
        }finally {
            try {
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteOneFile(String deletePath) {
        File file=new File(deletePath);
        try{
            boolean delete = file.delete();
            fileMapper.deleteOneFile(deletePath);
        }catch (Exception e){
            logger.info(e.getMessage());
        }

    }

    /*@Override
    public List<Info> selectAllMyClassFile(String cno) {
        return fileMapper.selectAllMyClassFile(cno);
    }*/

    @Override
    public String toFile(Info info,Map<String,String> map) {
        String createName=userService.isExist(info.getNo());
        //班级信息从这里开始
        session.setAttribute("cno",info.getCno());
        session.setAttribute("cname",info.getCname());
        session.setAttribute("createNo",info.getNo());
        session.setAttribute("createName",createName);
        map.put("createCno",info.getCno());
        map.put("createCname",info.getCname());
        map.put("createNo",info.getNo());
        map.put("createName", createName);
        logger.info("controller:toFile:cno"+info.getCno());
        return "views/file/file";
    }


    @Override
    public List<Info> selectTaskFile(Info info) {
        List<Info> Infos = fileMapper.selectTaskFile(info);
        return Infos;
    }

}
