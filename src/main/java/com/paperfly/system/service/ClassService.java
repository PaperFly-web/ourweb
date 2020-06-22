package com.paperfly.system.service;

import com.paperfly.system.pojo.Class;
import com.paperfly.system.pojo.Task;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface ClassService {
    String createClass(Class c, HttpSession session);
    String  joinClass(Class c,HttpSession session);
    String classIsExist(String cno);
    List<Class> selectJoinClass(String no);
    List<Class> selectCreateClass(String no);
    String  createTask(Task t);
    List<Task> selectCreateTask(String cno);
}
