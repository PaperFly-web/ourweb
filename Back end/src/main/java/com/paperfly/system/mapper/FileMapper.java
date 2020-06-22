package com.paperfly.system.mapper;

import com.paperfly.system.pojo.Info;
import com.paperfly.system.pojo.Task;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileMapper {
    List<Info> selectThisClassFile(Info fileInfo);
    void addFileInfo(Info file);
    List<Info> downAllMyTaskFile(Info task);
    void deleteOneFile(String deletePath);
    List<Info> selectTaskFile(Info info);
}
