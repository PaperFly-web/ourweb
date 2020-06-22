package com.paperfly.system.mapper;

import com.paperfly.system.pojo.Class;
import com.paperfly.system.pojo.Task;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ClassMapper {
    void createClass(Class c);
    void joinClass(Class c);
    String classIsExist(String cno);
    List<Class> selectJoinClass(String no);
    List<Class> selectCreateClass(String no);
    void createTask(Task t);
    List<Task> selectCreateTask(String cno);
    String selectTaskName(Integer classTaskId);
}
