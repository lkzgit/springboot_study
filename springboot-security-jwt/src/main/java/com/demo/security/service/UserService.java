package com.demo.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.security.domain.User;


/**

 * @author: lkz
 * @date:Created in 2020/6/24 12:36
 */
public interface UserService extends IService<User> {
    /**
     * 据用户名查询用户信息
     *
     * @param username
     * @return
     */
    User findByUserName(String username);
}
