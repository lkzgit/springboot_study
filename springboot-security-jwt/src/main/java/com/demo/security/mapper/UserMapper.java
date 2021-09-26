package com.demo.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.demo.security.domain.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author lkz
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户名查询用户信息
     *
     * @param userName
     * @return
     */
    User findByUserName(@Param("userName") String userName);
}
