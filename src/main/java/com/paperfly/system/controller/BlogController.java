package com.paperfly.system.controller;

import com.sun.deploy.net.URLEncoder;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Base64;

@Controller
public class BlogController {

    @GetMapping("toBlog")
    @RequiresAuthentication
    public String toBlog(){
        return "views/blog/comBlog";
    }
    @GetMapping("toMakeBlog")
    @RequiresAuthentication
    public String makeBlog(){
        return "views/blog/makeBlog";
    }

    @RequestMapping("displayPDF")
    public void displayPDF(HttpServletResponse response) {

        try {
            File file = new File("D:\\1workeFiles\\个人作业\\2019-2020第二学期\\算法分析/测试一（带答案）.pdf");
            FileInputStream fileInputStream = new FileInputStream(file);
            response.setHeader("Content-Disposition", "attachment;fileName=test.pdf");
            response.setContentType("multipart/form-data");
            OutputStream outputStream = response.getOutputStream();
            IOUtils.write(IOUtils.toByteArray(fileInputStream), outputStream);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
