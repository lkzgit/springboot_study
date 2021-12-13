package com.demo.consumer.mapper;

import com.demo.consumer.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName UserMapper.java
 * @Description TODO
 * @createTime 2021年12月08日 22:12:00
 */
@Mapper
public interface UserMapper {

    List<User> getList();

    int insert(User user);
}
