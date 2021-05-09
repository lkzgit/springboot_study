package com.demo.test;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.shardingSphere.ShardingApplication;
import com.demo.shardingSphere.domain.Course;
import com.demo.shardingSphere.domain.Dict;
import com.demo.shardingSphere.domain.User;
import com.demo.shardingSphere.mapper.CourseMapper;
import com.demo.shardingSphere.mapper.DictMapper;
import com.demo.shardingSphere.mapper.UserMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShardingApplication.class)
public class Test {


    //注入mapper
    @Autowired
    private CourseMapper courseMapper;

    //注入user的mapper
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DictMapper dictMapper;

    //======================测试公共表===================
    //添加操作
    @org.junit.Test
    public void addDict() {
        Dict udict = new Dict();
        udict.setUstatus("a");
        udict.setUvalue("已启用");
        dictMapper.insert(udict);
    }

    //删除操作
    @org.junit.Test
    public void deleteDict() {
        QueryWrapper<Dict>  wrapper = new QueryWrapper<>();
        //设置userid值
        wrapper.eq("dict_id",598171642639679489L);
        dictMapper.delete(wrapper);
    }

    //======================测试垂直分库==================
    //添加操作
    @org.junit.Test
    public void addUserDb() {
        User user = new User();
        user.setUsername("张三");
        user.setUstatus("a");
        userMapper.insert(user);
    }

   // 查询操作
    @org.junit.Test
    public void findUserDb() {
        QueryWrapper<User>  wrapper = new QueryWrapper<>();
        //设置userid值
        wrapper.eq("user_id",465508031619137537L);
        User user = userMapper.selectOne(wrapper);
        System.out.println(user);
    }


    //======================测试水平分库=====================
    //添加操作
    @org.junit.Test
    public void addCourseDb() {
        for(int i=1;i<=10;i++) {
            Course course = new Course();
            course.setCname("java"+i);
            course.setUserId(100L+i);
            course.setCstatus("Normal"+i);
            courseMapper.insert(course);
        }
    }

    //查询操作i
    @org.junit.Test
    public void findCourseDb() {
        QueryWrapper<Course>  wrapper = new QueryWrapper<>();
        //设置userid值
        wrapper.eq("user_id",100L);
        //设置cid值
        wrapper.eq("cid",465162909769531393L);
        Course course = courseMapper.selectOne(wrapper);
        System.out.println(course);
    }




    //=======================测试水平分表===================
    //添加课程的方法
    @org.junit.Test
    public void addCourse() {
        for(int i=1;i<=10;i++) {
            Course course = new Course();
            course.setCname("java"+i);
            course.setUserId(100L);
            course.setCstatus("Normal"+i);
            courseMapper.insert(course);
        }
    }
    //查询课程的方法
    @org.junit.Test
    public void findCourse() {
        QueryWrapper<Course>  wrapper = new QueryWrapper<>();
        wrapper.eq("cid",598159790354464769L);
        Course course = courseMapper.selectOne(wrapper);
        System.out.println(course);
    }


}
