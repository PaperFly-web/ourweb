package com.paperfly.system.service;

import com.paperfly.system.mapper.ClassMapper;
import com.paperfly.system.pojo.Class;
import com.paperfly.system.pojo.Task;
import com.paperfly.system.utils.IsAssignSizeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;

@Service
public class ClassServiceImpl implements ClassService {
    Logger logger= LoggerFactory.getLogger(getClass());
    @Autowired
    ClassMapper classMapper;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    HttpSession session;
    @Override
    public String createClass(Class c, HttpSession session) {
        if(!IsAssignSizeUtil.size(c.getCname(), 15)){
            return "创建班级失败,班级名不能超过15个字符!";
        }
        //判断当前用户是否创建过同名班级
        Boolean isCreateCnameExist=null;
        try{//用户还没创建班级的时候，会有异常抛出
            isCreateCnameExist= redisTemplate.opsForSet().isMember("createCname:" +
                    session.getAttribute("no"), c.getCname());
        }catch (Exception e){
            logger.info("新注册的用户还没有创建班级!");
        }
        String no = (String) session.getAttribute("no");
        //判断当前用户是否创建过同名班级，如果存在则不再创建
        if (isCreateCnameExist) {
            return  "对不起,创建班级失败!班级名不能重复【" + c.getCname() + "】!";
        }
        //生成随机班级号,保证班号不重复
        String classCode = UUID.randomUUID().toString().substring(0, 8);
        while (redisTemplate.opsForSet().isMember("cno", classCode)){
            classCode = UUID.randomUUID().toString().substring(0, 8);
        }
        c.setCno(classCode);
        c.setNo(no);
        classMapper.createClass(c);
        redisTemplate.opsForSet().add("cno", classCode);
        //把当前用户创建的班级名称放入redis中，方便下次创建的时候，判断当前用户是否创建过同名用户
        redisTemplate.opsForSet().add("createCname:" + session.getAttribute("no"), c.getCname());
        return "恭喜您班级已经创建完毕，班级号是【" + classCode + "】请妥善保管!";
    }

    @Override
    public String  joinClass(Class c,HttpSession session) {
        String no = (String) session.getAttribute("no");
        //判断加入的班级号是否存在
        Boolean classCnoIsExist = redisTemplate.opsForSet().isMember("cno", c.getCno());
        //判断，当前用户是否已经加入过这个班级
        Boolean isJoinCnoExist=null;
        try{//用户还没加入班级的时候，会有一场抛出
            isJoinCnoExist = redisTemplate.opsForSet().isMember("joinCno:" +
                    session.getAttribute("no"), c.getCno());
        }catch (Exception e){
            logger.info("新创建的用户还没加入过班级!");
        }

        if (!classCnoIsExist) {
            return "对不起【" + c.getCno() + "】班级不存在！";
        }
        if (isJoinCnoExist) {
            return "您已经加入了这个【" + c.getCno() + "】班级,不能重复加入!";
        }
        c.setNo(no);
        classMapper.joinClass(c);
        //把用户加入的班级号，放入redis中，方便下次加入的时候查询是否重复加入
        redisTemplate.opsForSet().add("joinCno:" + session.getAttribute("no"), c.getCno());
        return "恭喜您成功加入了新班级";
    }

    @Override
    public String classIsExist(String cno) {
        return classMapper.classIsExist(cno);
    }

    @Override
    public List<Class> selectJoinClass(String no) {
        return classMapper.selectJoinClass(no);
    }

    @Override
    public List<Class> selectCreateClass(String no) {
        return classMapper.selectCreateClass(no);
    }

    @Override
    public String createTask(Task t) {
        String cno= (String) session.getAttribute("cno");
        String no= (String) session.getAttribute("no");
        try {
            Boolean isMember = redisTemplate.opsForSet().isMember("createTask:" + cno + no, t.getTaskName());
            if(isMember){
                return "对不起任务名【"+t.getTaskName()+"】不能重复!";
            }
        }catch (Exception e){
            logger.info("新创建的班级还没有创建任务!");
        }
        classMapper.createTask(t);
        redisTemplate.opsForSet().add("createTask:"+cno+no,t.getTaskName());
        return "恭喜您任务创建成功!";
    }

    @Override
    public List<Task> selectCreateTask(String cno) {
        List<Task> tasks = classMapper.selectCreateTask(cno);
        return tasks;
    }
}
