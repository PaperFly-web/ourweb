package com.paperfly.system.controller;

import com.paperfly.system.pojo.Blog;
import com.paperfly.system.properties.FileConstantPropertites;
import com.paperfly.system.service.BlogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class BlogController {
    @Autowired
    HttpSession session;
    @Autowired
    BlogService blogService;
    @Autowired
    FileConstantPropertites constantPropertites;
    @GetMapping("toBlog")
    @RequiresAuthentication
    public String toBlog(){
        return "views/blog/comBlog";
    }
    @GetMapping("toMakeBlog")
    @RequiresAuthentication
    public String makeBlog(Map<String,Object> map){
        map.put("fileMaxSize",constantPropertites.getBlogFileMaxSize());
        return "views/blog/makeBlog";
    }

    @GetMapping("displayPDF")
    @RequiresAuthentication
    public void displayPDF(HttpServletResponse response,Blog blog) {
        try {
            File file = new File(blog.getBlogFilePath());
            FileInputStream fileInputStream = new FileInputStream(file);
            response.setHeader("Content-Disposition", "attachment;fileName="+blog.getBlogFileName());
            response.setContentType("multipart/form-data");
            OutputStream outputStream = response.getOutputStream();
            IOUtils.write(IOUtils.toByteArray(fileInputStream), outputStream);
            if(!blog.getNo().equals(session.getAttribute("no"))){
                blogService.addBlogFileView(blog);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    @PostMapping("uploadBlogFile")
    @Transactional
    @RequiresAuthentication
    public String uploadBlogFile(Blog blog , @RequestParam("file") MultipartFile file, Map<String,Object> map){
        String info = blogService.uploadBlogFile(blog, file, map);
        map.put("blogFileMsg",info);
        map.put("fileMaxSize",constantPropertites.getBlogFileMaxSize());
        return "views/blog/makeBlog";
    }
    @PostMapping("selectMySelfBlog")
    @Transactional
    @RequiresAuthentication
    @ResponseBody
    public List<Blog> selectMySelfBlog(Blog blog) {
        List<Blog> blogs = blogService.selectMySelfBlog(blog);
        return blogs;
    }

    @PostMapping("selectAllBlog")
    @RequiresAuthentication
    @ResponseBody
    public List<Blog> selectAllBlog(Blog blog) {
        List<Blog> blogs = blogService.selectAllBlog(blog);
        return blogs;
    }
    @PostMapping("selectBlogByNo")
    @RequiresAuthentication
    @ResponseBody
    public List<Blog> selectBlogByNo(Blog blog) {
        List<Blog> blogs = blogService.selectBlogByNo(blog);
        return blogs;
    }

    @PostMapping("selectAllBlogForSuccessView")
    @Transactional
    @RequiresAuthentication
    @ResponseBody
    public List<Blog> selectAllBlogForSuccessView(Blog blog) {
        List<Blog> blogs = blogService.selectAllBlogForSuccessView(blog);
        log.info("首页博客显示："+blogs.toString());
        return blogs;
    }

    @RequiresAuthentication
    @GetMapping("deleteOneBlogFile")
    public String deleteOneFile(Blog blog, Map<String,Object> map){
        blogService.deleteOneFile(blog);
        return "views/blog/comBlog";
    }
    @RequiresAuthentication
    @GetMapping("isOpen")
    public String isOpen(Blog blog, Map<String,Object> map){
        blogService.isOpen(blog);
        return "views/blog/comBlog";
    }
    @RequiresAuthentication
    @GetMapping("personalBlog")
    public String toPersonalBlog(Blog blog,Map<String,Object> map){
        List<Blog> blogs = blogService.selectBlogByNo(blog);
        map.put("blogs",blogs);
        map.put("blogNo",blog.getNo());
        return "views/blog/personalBlog";
    }


}
