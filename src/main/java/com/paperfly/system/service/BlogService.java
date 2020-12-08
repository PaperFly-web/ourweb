package com.paperfly.system.service;

import com.paperfly.system.pojo.Blog;
import com.paperfly.system.pojo.Task;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface BlogService {
    String uploadBlogFile(Blog blog, MultipartFile file, Map<String,Object> map);
    List<Blog> selectMySelfBlog(Blog blog);
    List<Blog> selectAllBlog(Blog blog);
    List<Blog> selectBlogByNo(Blog blog);
    String deleteOneFile(Blog blog);
    String addBlogFileView(Blog blog);
    String isOpen(Blog blog);
    public List<Blog> selectAllBlogForSuccessView(Blog blog);
}
